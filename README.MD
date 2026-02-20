# ISeeYou (Folia)

_Also known as ICU — Folia-only build_

[![License](https://img.shields.io/github/license/MC-XiaoHei/ISeeYou)](LICENSE)

ISeeYou is a simple and lightweight plugin to record players in `.mcpr` format. [What's `.mcpr`?](https://github.com/ReplayMod/ReplayMod)

> [!WARNING]
> **This is a Folia-only build.** It does **not** support original Leaves, Leaf, Lophine, or vanilla Paper.
>
> This plugin only works on **Folia with Photographer API**. Use a server built from:
>
> **[Foliaphotographer](https://github.com/xiaofanforfabric/Foliaphotographer/tree/ver/1.21.4-PhotographerAPI)** — Folia fork that adds the Photographer API for `.mcpr` recording.
>
> Compatibility with other cores is not supported.

## Features

- **Automatic Recording**: No manual operation required. By default, the plugin automatically records all players.
- **Flexible Configuration**: You can configure whitelists, blacklists, and recording paths through configuration files.
- **Anti-Cheat Support**: Compatible with multiple anti-cheat plugins. It automatically starts recording when a
  suspicious player is detected.
- **Instant Replay**: You can use commands to save recent seconds gameplay.

## Anti-Cheat Compatibility

| Name                                                                                                                                          | Availability |
|-----------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| [Themis Anti Cheat](https://www.spigotmc.org/resources/themis-anti-cheat-1-17-1-20-bedrock-support-paper-compatibility-free-optimized.90766/) | ✅ Available  |
| [Matrix](https://matrix.rip/)                                                                                                                 | ✅ Available  |
| [Vulcan Anti-Cheat](https://www.spigotmc.org/resources/vulcan-anti-cheat-advanced-cheat-detection-1-7-1-20-4.83626/)                          | ⚠️ Beta      |
| [AC - Negativity](https://www.spigotmc.org/resources/ac-negativity-spigot-1-8-1-20-bungeecord-velocity.48399/)                                | ⚠️ Beta      |
| [Grim Anticheat](https://www.spigotmc.org/resources/grim-anticheat.99923/)                                                                    | ⚠️ Beta      |
| [LightAntiCheat](https://github.com/tiredvekster/LightAntiCheat)                                                                              | ⚠️ Beta      |

> [!TIP]
> If you would like us to add support for more anti-cheat plugins, please create
> an [issue](https://github.com/MC-XiaoHei/ISeeYou/issues) and let us know!

## Dependencies

* [CommandAPI](https://docs.commandapi.dev/) 11.0.0 or higher

### How to install

1. **Install the Plugin**: Place the plugin file(and dependencies) in the plugins directory of the server, and restart the server.

2. **Configure Settings**: Edit the `plugins/ISeeYou/icu.conf` file to adjust settings.

## Download

- **This repository (Folia build)**  
  - [FoliaISEEYOU](https://github.com/xiaofanforfabric/FoliaISEEYOU) — source and releases for this Folia-only build.

## Building From Source

### 1. Clone the project repository

```shell
git clone https://github.com/xiaofanforfabric/FoliaISEEYOU.git
```

### 2. Compile and Build

```shell
./gradlew shadowJar
```

After the build is complete, you will find the JAR (e.g. `ISeeYou-master-2.2.0-xiaofanforfolia.jar`) in the `build/libs` directory.

## License

This project is under [MIT License](LICENSE).
