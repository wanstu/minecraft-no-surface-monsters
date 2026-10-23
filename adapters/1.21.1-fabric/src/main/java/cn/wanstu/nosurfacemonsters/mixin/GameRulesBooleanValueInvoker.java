package cn.wanstu.nosurfacemonsters.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.BooleanValue.class)
public interface GameRulesBooleanValueInvoker {
    @Invoker("create")
    static GameRules.Type<GameRules.BooleanValue> nosurfacemonsters$create(
        boolean initialValue
    ) {
        throw new AssertionError();
    }
}
