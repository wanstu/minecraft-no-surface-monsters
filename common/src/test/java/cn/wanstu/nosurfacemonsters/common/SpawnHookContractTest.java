package cn.wanstu.nosurfacemonsters.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class SpawnHookContractTest {
    private static int assertions;

    public static void main(String[] args) throws Exception {
        Class<?> hookClass = Class.forName("cn.wanstu.nosurfacemonsters.mixin.SpawnPlacementsMixin");
        Method hook = Arrays.stream(hookClass.getDeclaredMethods())
            .filter(method -> method.getName().equals("nosurfacemonsters$blockNaturalSurfaceSpawn"))
            .findFirst().orElseThrow();
        hook.setAccessible(true);
        Annotation inject = Arrays.stream(hook.getDeclaredAnnotations())
            .filter(annotation -> annotation.annotationType().getName().equals("org.spongepowered.asm.mixin.injection.Inject"))
            .findFirst().orElseThrow();
        check((Boolean) inject.annotationType().getMethod("cancellable").invoke(inject), "hook can reject a surface spawn");
        String[] target = (String[]) inject.annotationType().getMethod("method").invoke(inject);
        check(Arrays.equals(target, new String[] {"checkSpawnRules"}), "only the existing placement-check method is hooked");
        Object[] points = (Object[]) inject.annotationType().getMethod("at").invoke(inject);
        check(points.length == 1, "one injection point");
        Annotation point = (Annotation) points[0];
        check(point.annotationType().getMethod("value").invoke(point).equals("RETURN"), "original predicate executes before this restriction");

        Class<?> callbackClass = hook.getParameterTypes()[5];
        Constructor<?> callbackConstructor = callbackClass.getConstructor(String.class, boolean.class, boolean.class);
        Method result = callbackClass.getMethod("getReturnValueZ");
        Method cancelled = callbackClass.getMethod("isCancelled");
        Class<?> reasonClass = hook.getParameterTypes()[2];

        for (Object reason : reasonClass.getEnumConstants()) {
            Object denied = callbackConstructor.newInstance("test", true, false);
            hook.invoke(null, null, null, reason, null, null, denied);
            check(!(Boolean) result.invoke(denied) && !(Boolean) cancelled.invoke(denied),
                reason + ": keep an existing denial without touching world/entity data");

            Object allowed = callbackConstructor.newInstance("test", true, true);
            hook.invoke(null, null, null, reason, null, null, allowed);
            check((Boolean) result.invoke(allowed) && !(Boolean) cancelled.invoke(allowed),
                reason + ": special reasons/non-server accessors stay untouched");
        }

        Class<?> category = Class.forName("net.minecraft.world.entity.MobCategory");
        Class<?> policyClass = Class.forName("cn.wanstu.nosurfacemonsters.SurfaceSpawnPolicy");
        Method policy = Arrays.stream(policyClass.getDeclaredMethods())
            .filter(method -> method.getName().equals("shouldBlockNaturalMonsterSpawn"))
            .findFirst().orElseThrow();
        for (Object mobCategory : category.getEnumConstants()) {
            if (!((Enum<?>) mobCategory).name().equals("MONSTER")) {
                check(!(Boolean) policy.invoke(null, null, mobCategory, null),
                    mobCategory + ": no world access for non-monster categories");
            }
        }
        System.out.println("Spawn hook scope and rejection regressions passed (" + assertions + " assertions).");
    }

    private static void check(boolean value, String message) {
        assertions++;
        if (!value) throw new AssertionError(message);
    }
}
