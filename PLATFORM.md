# ISEEYOU 平台支持说明

## Photographer 与服务器核心

Photographer 是录制 .mcpr 回放的核心，依赖服务器 NMS（PlayerList.placeNewPhotographer 等）。  
**必须由服务器核心提供**，无法以纯插件形式在未改动的 Paper 上实现。

## 当前支持

### Leaves 服务器（完整功能）✅
- 录制功能可用
- 使用 Leaves 内置的 Photographer API
- **开发时**：`./gradlew runServer` 已配置为使用 Leaves，可直接测试录制

### Paper / Spigot 服务器（部分功能）
- 插件可正常加载
- 录制相关功能禁用，并提示使用 Leaves

## 使用 Leaves 获得完整功能

1. 下载 [Leaves](https://github.com/LeavesMC/Leaves) 服务器
2. 将 `ISeeYou-*.jar` 放入 `plugins` 目录
3. 启动服务器

## 开发

```bash
./gradlew runServer
```
会下载并启动 Leaves 服务器，photographer 功能可用。
