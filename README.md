# No Surface Monsters

A small Minecraft mod that prevents ordinary hostile mobs from naturally spawning on the Overworld surface while preserving underground spawning and vanilla mechanic-driven spawns.

## Supported targets

| Minecraft | Fabric | NeoForge |
| --- | --- | --- |
| 1.21.1 | Yes | Yes |
| 1.21.11 | Yes | Yes |
| 26.1.2 | Yes | Yes |
| 26.2 | Yes | Yes |
| 26.3 | Yes | Yes |

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

The project keeps gameplay rules separate from Minecraft-version and loader adapters:

- `common`: loader/version-neutral surface classification algorithm.
- `v1_21_1-common`: Minecraft 1.21.1 shared logic for Fabric and NeoForge.
- `v1_21_11-common`: Minecraft 1.21.11 shared logic after the game-rule/spawn API changes.
- `v26-common`: shared Minecraft 26.x game-rule/resources layer.
- `v26_1-common`, `v26_2-common`, `v26_3-common`: version-specific Minecraft API compatibility layers.
- `adapters/*-fabric`: thin Fabric entrypoints and metadata.
- `adapters/*-neoforge`: thin NeoForge entrypoints and metadata.

## CI and releases

GitHub Actions builds and verifies all 10 supported Minecraft/loader targets on pushes and pull requests.

A release tag in the form `vMAJOR.MINOR.PATCH` or `vMAJOR.MINOR.PATCH-rc.N` publishes one GitHub Release containing all 10 distributable JARs:

- 1.21.1 Fabric / NeoForge
- 1.21.11 Fabric / NeoForge
- 26.1.2 Fabric / NeoForge
- 26.2 Fabric / NeoForge
- 26.3 Fabric / NeoForge

Each CI target verifies that its JAR contains loader metadata, the loader entrypoint, the spawn policy, the Mixin configuration and the shared surface-rule implementation.
