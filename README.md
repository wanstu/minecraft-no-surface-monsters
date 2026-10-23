# No Surface Monsters

Minecraft 26.3 Fabric mod.

## Behavior

When the `nosurfacemonsters:no_surface_monsters` game rule is enabled (default):

- ordinary hostile `MONSTER` natural spawning is blocked in Overworld surface areas;
- obvious surface / constructed floors are protected immediately;
- any spawn position that can see the sky is considered surface;
- positions hidden from the sky are still considered surface until they are at least 8 blocks below the terrain height;
- only sufficiently deep underground/cave positions on natural underground-compatible blocks keep normal hostile natural spawning;
- special mechanic spawns are left to vanilla behavior;
- Nether and End are unchanged;
- commands, spawn eggs, mob spawners, trial spawners and raids are unchanged.

The implementation does not scan entities or vertical block columns. It blocks only `EntitySpawnReason.NATURAL` during Minecraft's existing spawn-rule check.

## Game rule

```
/gamerule nosurfacemonsters:no_surface_monsters true
/gamerule nosurfacemonsters:no_surface_monsters false
```
