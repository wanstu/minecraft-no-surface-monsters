package cn.wanstu.nosurfacemonsters.common;

public final class SurfaceSpawnRules {
    public static final int MIN_UNDERGROUND_DEPTH = 8;

    private SurfaceSpawnRules() {
    }

    /**
     * Classifies a sky-hidden position by its distance below the surface height.
     * Floor materials deliberately play no part: modded and constructed caves
     * follow the same depth rule as vanilla caves.
     */
    public static boolean isSurface(int undergroundDepth) {
        return undergroundDepth < MIN_UNDERGROUND_DEPTH;
    }
}
