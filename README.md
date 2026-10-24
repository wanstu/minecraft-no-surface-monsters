# No Surface Monsters

A small Fabric mod that prevents ordinary hostile mobs from naturally spawning on the Overworld surface while preserving underground spawning and vanilla mechanic-driven spawns.

## Supported Fabric baselines

- Minecraft 26.3
- Minecraft 26.2
- Minecraft 26.1.2
- Minecraft 1.21.1

The mod depends on Fabric Loader only. It does not require Fabric API or another gameplay/configuration mod.

## Behavior

When the mod game rule is enabled (default):

- ordinary hostile natural spawning is blocked in Overworld surface areas;
- obvious surface / constructed floors are protected immediately;
- any spawn position that can see the sky is considered surface;
- positions hidden from the sky are still considered surface until they are at least 8 blocks below the terrain height;
- only sufficiently deep underground/cave positions on natural underground-compatible blocks keep normal hostile natural spawning;
- special mechanic spawns are left to vanilla behavior;
- Nether and End are unchanged;
- commands, spawn eggs, mob spawners, trial spawners and raids are unchanged.

The implementation does not scan entities or vertical block columns. It hooks Minecraft's existing natural-spawn rule check before entity creation.

## Architecture

- `common`: loader/version-neutral surface classification algorithm.
- `v26-common`: shared vanilla GameRule registration for Minecraft 26.x.
- `adapters/*`: only Minecraft-version API/mapping differences.

Fabric Loader is used only as the required mod loader / entrypoint platform.
