package cn.wanstu.nosurfacemonsters.mixin;

import cn.wanstu.nosurfacemonsters.SurfaceSpawnPolicy;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnPlacements.class)
public abstract class SpawnPlacementsMixin {
    @Inject(
        method = "checkSpawnRules",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void nosurfacemonsters$blockNaturalSurfaceSpawn(
        EntityType<?> entityType,
        ServerLevelAccessor level,
        EntitySpawnReason spawnReason,
        BlockPos pos,
        RandomSource random,
        CallbackInfoReturnable<Boolean> callback
    ) {
        if (spawnReason != EntitySpawnReason.NATURAL) {
            return;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (SurfaceSpawnPolicy.shouldBlockNaturalMonsterSpawn(
            serverLevel,
            entityType.getCategory(),
            pos
        )) {
            callback.setReturnValue(false);
        }
    }
}
