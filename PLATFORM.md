# ISeeYou (Folia) — Platform support

## Photographer and server core

Recording `.mcpr` replays requires a **Photographer API** provided by the server (e.g. `PlayerList.placeNewPhotographer`). It cannot be implemented as a plain plugin on unmodified Paper.

## This build: Folia only

This is a **Folia-only** build. It does **not** support:

- Original [Leaves](https://github.com/LeavesMC/Leaves)
- Leaf, Lophine, or other photographer-capable cores

## Supported server

Use **Folia with Photographer API**:

- **[Foliaphotographer](https://github.com/xiaofanforfabric/Foliaphotographer/tree/ver/1.21.4-PhotographerAPI)** — Folia fork that adds the Photographer API for `.mcpr` recording.

Build the server from that branch (e.g. `createMojmapPaperclipJar`), then put this plugin in `plugins/`.

## Development

Configure your run to use a Folia server that includes the Photographer API (e.g. a local Foliaphotographer build). This repo’s `runServer` may be set to a Folia/Paper download; ensure the server JAR is one that exposes `getPhotographerManager()`.
