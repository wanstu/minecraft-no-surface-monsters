package cn.wanstu.nosurfacemonsters;

import cn.wanstu.nosurfacemonsters.common.SurfaceSpawnRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public final class SurfaceSpawnPolicy {
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
        boolean obviousSurfaceFloor = floor.is(BlockTags.GRASS_BLOCKS)
            || floor.is(BlockTags.LEAVES)
            || floor.is(BlockTags.LOGS);

        return SurfaceSpawnRules.isSurface(
            obviousSurfaceFloor,
            isUndergroundCompatibleFloor(floor),
            level.canSeeSky(pos),
            () -> level.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                pos.getX(),
                pos.getZ()
            ) - pos.getY()
        );
    }

    private static boolean isUndergroundCompatibleFloor(BlockState floor) {
        return floor.is(BlockTags.BASE_STONE_OVERWORLD)
            || isOverworldOre(floor)
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

    private static boolean isOverworldOre(BlockState floor) {
        return floor.is(Blocks.COAL_ORE)
            || floor.is(Blocks.DEEPSLATE_COAL_ORE)
            || floor.is(Blocks.IRON_ORE)
            || floor.is(Blocks.DEEPSLATE_IRON_ORE)
            || floor.is(Blocks.COPPER_ORE)
            || floor.is(Blocks.DEEPSLATE_COPPER_ORE)
            || floor.is(Blocks.GOLD_ORE)
            || floor.is(Blocks.DEEPSLATE_GOLD_ORE)
            || floor.is(Blocks.REDSTONE_ORE)
            || floor.is(Blocks.DEEPSLATE_REDSTONE_ORE)
            || floor.is(Blocks.EMERALD_ORE)
            || floor.is(Blocks.DEEPSLATE_EMERALD_ORE)
            || floor.is(Blocks.LAPIS_ORE)
            || floor.is(Blocks.DEEPSLATE_LAPIS_ORE)
            || floor.is(Blocks.DIAMOND_ORE)
            || floor.is(Blocks.DEEPSLATE_DIAMOND_ORE);
    }
}
