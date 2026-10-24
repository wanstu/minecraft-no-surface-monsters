package cn.wanstu.nosurfacemonsters;

import cn.wanstu.nosurfacemonsters.mixin.GameRulesInvoker;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public final class NoSurfaceMonstersGameRules {
    public static final GameRule<Boolean> NO_SURFACE_MONSTERS =
        GameRulesInvoker.nosurfacemonsters$registerBoolean(
            "nosurfacemonsters:no_surface_monsters",
            GameRuleCategory.MOBS,
            true
        );

    private NoSurfaceMonstersGameRules() {
    }

    public static void initialize() {
        // Class loading performs registration.
    }
}
