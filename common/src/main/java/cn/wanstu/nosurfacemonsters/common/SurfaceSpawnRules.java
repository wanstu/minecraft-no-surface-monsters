package cn.wanstu.nosurfacemonsters.common;

import java.util.function.IntSupplier;

public final class SurfaceSpawnRules {
    public static final int MIN_UNDERGROUND_DEPTH = 8;

    private SurfaceSpawnRules() {
    }

    public static boolean isSurface(
        boolean obviousSurfaceFloor,
        boolean undergroundCompatibleFloor,
        boolean canSeeSky,
        IntSupplier undergroundDepth
    ) {
        if (obviousSurfaceFloor) {
            return true;
        }
        if (!undergroundCompatibleFloor) {
            return true;
        }
        if (canSeeSky) {
            return true;
        }
        return undergroundDepth.getAsInt() < MIN_UNDERGROUND_DEPTH;
    }
}
