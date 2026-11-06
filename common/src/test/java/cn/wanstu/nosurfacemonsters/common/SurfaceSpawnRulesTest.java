package cn.wanstu.nosurfacemonsters.common;

/** Regression checks run by every adapter's Gradle check task. No test library required. */
public final class SurfaceSpawnRulesTest {
    public static void main(String[] args) {
        expectSurface("above the terrain", -20);
        expectSurface("at the terrain height", 0);
        expectSurface("one block under cover", 1);
        expectSurface("last protected shallow block", 7);
        expectUnderground("first underground block", 8);
        expectUnderground("deep vanilla or modded cave", 64);
        expectUnderground("deep constructed room", 32);

        // Use the same depth under very different terrain elevations.
        expectSurface("shallow high mountain", 300 - 293);
        expectUnderground("cave below sea level", 63 - (-40));
        expectUnderground("cave below negative terrain", -30 - (-38));
        System.out.println("Surface spawn depth regressions passed (10 cases).");
    }

    private static void expectSurface(String scenario, int depth) {
        if (!SurfaceSpawnRules.isSurface(depth)) {
            throw new AssertionError(scenario + " must be protected");
        }
    }

    private static void expectUnderground(String scenario, int depth) {
        if (SurfaceSpawnRules.isSurface(depth)) {
            throw new AssertionError(scenario + " must keep its original spawn result");
        }
    }
}
