# No Surface Monsters

A small Minecraft mod that prevents ordinary hostile mobs from naturally spawning on the Overworld surface while preserving underground spawning and vanilla mechanic-driven spawns.

## Supported targets

### Fabric

- Minecraft 26.3
- Minecraft 26.2
- Minecraft 26.1.2
- Minecraft 1.21.1

### NeoForge

- Minecraft 1.21.1

No Surface Monsters does not require Fabric API or another gameplay/configuration mod.

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

The project follows the same layered approach used by GeoWorld:

- `common`: loader/version-neutral surface classification algorithm.
- `v1_21_1-common`: Minecraft 1.21.1 GameRule, spawn policy and Mixins shared by Fabric and NeoForge.
- `v26-common`: shared Minecraft 26.x logic.
- `adapters/1.21.1-fabric`: thin Fabric entrypoint and Fabric metadata.
- `adapters/1.21.1-neoforge`: thin NeoForge entrypoint and NeoForge metadata.
- other `adapters/*`: Minecraft-version/loader-specific shells.

Minecraft 1.21.1 targets Java 21. The NeoForge 1.21.1 adapter is built against NeoForge 21.1.211 and declares NeoForge 21.1.211 or newer for Minecraft 1.21.1.
