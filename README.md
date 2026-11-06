# No Surface Monsters

A lightweight Minecraft mod that prevents ordinary hostile mobs from naturally spawning on the surface of the Overworld while preserving normal underground spawning and vanilla mechanic-driven spawns.

## Features

- Prevents ordinary hostile mobs from naturally spawning on the Overworld surface.
- Keeps normal hostile mob spawning in caves and underground areas.
- Does not affect the Nether or the End.
- Does not affect commands, spawn eggs, mob spawners, trial spawners, raids, or other mechanic-driven spawns.
- Adds one restriction after Minecraft's existing spawn checks have returned, before entity creation.
- Does not continuously scan mobs, chunks, or vertical block columns.
- Does not require Fabric API or another gameplay/configuration mod.

## Supported Versions

| Minecraft | Fabric | NeoForge |
| --- | --- | --- |
| 1.21.1 | ✅ | ✅ |
| 1.21.11 | ✅ | ✅ |
| 26.1.2 | ✅ | ✅ |
| 26.2 | ✅ | ✅ |
| 26.3 | ✅ | ✅ |

## Behavior

When the mod game rule is enabled (default):

- ordinary hostile natural spawning is blocked in Overworld surface areas;
- any spawn position that can see the sky is considered surface;
- positions hidden from the sky are still considered surface until they are at least 8 blocks below the terrain height;
- sky-hidden positions at least 8 blocks below the column height keep the original spawn result, regardless of floor material;
- special mechanic-driven spawns are left to vanilla behavior.

## Compatibility and Scope

- A single return hook only narrows a successful natural-spawn result; a rejected spawn stays rejected.
- Vanilla, loader and other mods' spawn predicates run before the surface restriction.
- Only natural spawns in the Overworld monster category are considered, including modded monsters using that category and spawn-check path.
- Floor blocks, block tags and biome contents are neither rewritten nor whitelisted. Modded cave floors follow the same geometry rule as vanilla floors.
- There are no entity-removal, tick-scan, mob-cap, AI or world-generation hooks.
- Surface depth is relative to the current column height, including roofs. Deep covered rooms follow the underground rule too.
- Mods that bypass the normal spawn-check path are outside this hook's scope.

## Configuration

The mod is enabled by default and provides a game rule for controlling whether Overworld surface monster spawning is blocked.

No additional configuration mod is required.

## Architecture

The project keeps gameplay rules separate from Minecraft-version and loader adapters:

- `common`: loader/version-neutral surface classification logic.
- `v1_21_1-common`: Minecraft 1.21.1 shared logic for Fabric and NeoForge.
- `v1_21_11-common`: Minecraft 1.21.11 shared logic.
- `v26-common`: shared Minecraft 26.x game-rule and resource layer.
- `v26_1-common`, `v26_2-common`, `v26_3-common`: Minecraft-version compatibility layers.
- `adapters/*-fabric`: thin Fabric entrypoints and metadata.
- `adapters/*-neoforge`: thin NeoForge entrypoints and metadata.

## CI and Releases

Every adapter's Gradle check task runs the shared surface-depth regressions without extra test libraries. GitHub Actions builds and verifies all 10 supported Minecraft/loader targets on pushes and pull requests.

A release tag in the form `vMAJOR.MINOR.PATCH` or `vMAJOR.MINOR.PATCH-rc.N` publishes one GitHub Release containing all 10 distributable JARs, then publishes each Minecraft/loader target as a separate Modrinth version.

Each CI target verifies that its JAR contains loader metadata, the loader entrypoint, the spawn policy, the Mixin configuration, and the shared surface-rule implementation.

## License

This project is licensed under the **BSD 3-Clause License**. See [LICENSE](LICENSE).

---

# 中文说明

No Surface Monsters 是一个轻量级 Minecraft 模组，用于阻止普通敌对生物在主世界地表自然生成，同时保留洞穴、地下区域中的正常刷怪以及原版机制触发的生物生成。

## 功能

- 阻止普通敌对生物在主世界地表自然生成。
- 保留洞穴和地下区域中的正常敌对生物生成。
- 不影响下界和末地。
- 不影响命令、刷怪蛋、刷怪笼、试炼刷怪笼、袭击以及其他由游戏机制触发的生物生成。
- 在 Minecraft 原有刷怪判定返回后追加限制，仍在实体生成前完成判断。
- 不持续扫描怪物、区块或整条垂直方块列。
- 不需要 Fabric API，也不依赖额外的玩法或配置模组。

## 支持版本

| Minecraft | Fabric | NeoForge |
| --- | --- | --- |
| 1.21.1 | ✅ | ✅ |
| 1.21.11 | ✅ | ✅ |
| 26.1.2 | ✅ | ✅ |
| 26.2 | ✅ | ✅ |
| 26.3 | ✅ | ✅ |

## 生成规则

模组规则默认启用：

- 普通敌对生物不会在主世界地表自然生成；
- 能直接看见天空的位置会被视为地表；
- 即使看不见天空，只要距离地形表面不足 8 格，仍会被视为地表区域；
- 看不见天空且距离当前列地表高度至少 8 格的位置，保留原有刷怪判定结果，不再限制地面方块材质；
- 由原版特殊机制触发的生成保持原版行为。

## 兼容性与影响范围

- 只保留一个刷怪判定返回点的限制：原本不允许的生成仍不允许，只对原本允许的自然生成追加地表检查。
- 原版、Loader 和其他模组的刷怪判定先执行。
- 只处理主世界、怪物分类、自然生成；使用同一分类与判定路径的模组怪物也适用。
- 不改方块、方块标签或群系内容，不再维护地面材质白名单，模组洞穴地面按同一位置规则判断。
- 不挂接实体删除、逐 tick 扫描、怪物数量上限、AI 或世界生成。
- 深度以当前列高度为准，屋顶也会计入；足够深的封闭房间同样按地下区域处理。
- 完全绕过常规刷怪判定的模组生成流程不在这个拦截点的作用范围内。

## 配置

模组默认启用，并提供 GameRule 用于控制是否禁止主世界地表怪物自然生成。

不需要额外安装配置模组。

## 项目结构

项目将玩法逻辑、Minecraft 版本差异和 Loader 适配分离：

- `common`：与 Loader 和 Minecraft 版本无关的地表判断逻辑。
- `v1_21_1-common`：Minecraft 1.21.1 的 Fabric / NeoForge 共用逻辑。
- `v1_21_11-common`：Minecraft 1.21.11 共用逻辑。
- `v26-common`：Minecraft 26.x 共用的 GameRule 和资源层。
- `v26_1-common`、`v26_2-common`、`v26_3-common`：不同 Minecraft 版本的兼容层。
- `adapters/*-fabric`：Fabric 的轻量入口与 metadata。
- `adapters/*-neoforge`：NeoForge 的轻量入口与 metadata。

## CI 与发布

每个适配模块的 Gradle check 都会运行公共地表深度回归检查，不增加测试库依赖。GitHub Actions 会在 Push 和 Pull Request 时自动构建并验证全部 10 个 Minecraft / Loader 目标。

创建 `vMAJOR.MINOR.PATCH` 或 `vMAJOR.MINOR.PATCH-rc.N` 格式的 tag 后，会自动创建包含全部 10 个可发布 JAR 的 GitHub Release，并将每个 Minecraft / Loader 目标作为独立版本发布到 Modrinth。

每个 CI 构建目标都会检查 JAR 中是否包含 Loader metadata、Loader 入口类、刷怪策略、Mixin 配置以及公共地表规则实现。

## 许可证

本项目采用 **BSD 3-Clause License** 开源。详见 [LICENSE](LICENSE)。
