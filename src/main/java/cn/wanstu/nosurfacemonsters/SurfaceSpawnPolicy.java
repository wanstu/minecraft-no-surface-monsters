package cn.wanstu.nosurfacemonsters;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public final class SurfaceSpawnPolicy {
    private static final int MIN_UNDERGROUND_DEPTH = 8;

    private SurfaceSpawnPolicy() {
    }

    public static boolean shouldBlockNaturalMonsterSpawn(
        ServerLevel level,
        MobCategory category,
        BlockPos pos
    ) {
        if (!level.dimension().equals(Level.OVERWORLD)) {
            return false;
        }
        if (category != MobCategory.MONSTER) {
            return false;
        }
        if (!level.getGameRules().get(NoSurfaceMonstersGameRules.NO_SURFACE_MONSTERS)) {
            return false;
        }

        BlockState floor = level.getBlockState(pos.below());

        if (floor.is(BlockTags.GRASS_BLOCKS)
            || floor.is(BlockTags.LEAVES)
            || floor.is(BlockTags.LOGS)) {
            return true;
        }

        if (!isUndergroundCompatibleFloor(floor)) {
            return true;
        }

        if (level.canSeeSky(pos)) {
            return true;
        }

        int surfaceY = level.getHeight(
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            pos.getX(),
            pos.getZ()
        );
        int undergroundDepth = surfaceY - pos.getY();

        return undergroundDepth < MIN_UNDERGROUND_DEPTH;
    }

    private static boolean isUndergroundCompatibleFloor(BlockState floor) {
        return floor.is(BlockTags.BASE_STONE_OVERWORLD)
            || floor.is(BlockTags.ORES)
            || floor.is(BlockTags.DIRT)
            || floor.is(BlockTags.MUD)
            || floor.is(BlockTags.MOSS_BLOCKS)
            || floor.is(BlockTags.SAND)
            || floor.is(BlockTags.TERRACOTTA)
            || floor.is(Blocks.GRAVEL)
            || floor.is(Blocks.CLAY)
            || floor.is(Blocks.DRIPSTONE_BLOCK)
            || floor.is(Blocks.TUFF);
    }

    public static boolean shouldDisableSurfaceCustomSpawner(ServerLevel level) {
        return level.dimension().equals(Level.OVERWORLD)
            && level.getGameRules().get(NoSurfaceMonstersGameRules.NO_SURFACE_MONSTERS);
    }
}
