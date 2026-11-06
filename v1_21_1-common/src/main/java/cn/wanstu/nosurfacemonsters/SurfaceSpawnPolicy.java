package cn.wanstu.nosurfacemonsters;

import cn.wanstu.nosurfacemonsters.common.SurfaceSpawnRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public final class SurfaceSpawnPolicy {
    private SurfaceSpawnPolicy() {
    }

    public static boolean shouldBlockNaturalMonsterSpawn(
        ServerLevel level,
        MobCategory category,
        BlockPos pos
    ) {
        if (category != MobCategory.MONSTER) {
            return false;
        }
        if (!level.dimension().equals(Level.OVERWORLD)) {
            return false;
        }
        if (!level.getGameRules().getBoolean(NoSurfaceMonstersGameRules.NO_SURFACE_MONSTERS)) {
            return false;
        }

        // Read existing world data only; block identity does not define a cave.
        // Both queries use the current column's cached height/light data.
        return level.canSeeSky(pos) || SurfaceSpawnRules.isSurface(
            level.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                pos.getX(),
                pos.getZ()
            ) - pos.getY()
        );
    }
}
