package cn.wanstu.nosurfacemonsters;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public final class NoSurfaceMonstersGameRules {
    public static final GameRule<Boolean> NO_SURFACE_MONSTERS = GameRuleBuilder
        .forBoolean(true)
        .category(GameRuleCategory.MOBS)
        .buildAndRegister(
            Identifier.fromNamespaceAndPath(
                NoSurfaceMonstersMod.MOD_ID,
                "no_surface_monsters"
            )
        );

    private NoSurfaceMonstersGameRules() {
    }

    public static void initialize() {
        // Class loading performs registration.
    }
}
