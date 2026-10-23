package cn.wanstu.nosurfacemonsters;

import cn.wanstu.nosurfacemonsters.mixin.GameRulesBooleanValueInvoker;
import cn.wanstu.nosurfacemonsters.mixin.GameRulesInvoker;
import net.minecraft.world.level.GameRules;

public final class NoSurfaceMonstersGameRules {
    public static final GameRules.Key<GameRules.BooleanValue> NO_SURFACE_MONSTERS =
        GameRulesInvoker.nosurfacemonsters$register(
            "nosurfacemonsters.no_surface_monsters",
            GameRules.Category.MOBS,
            GameRulesBooleanValueInvoker.nosurfacemonsters$create(true)
        );

    private NoSurfaceMonstersGameRules() {
    }

    public static void initialize() {
        // Class loading performs registration.
    }
}
