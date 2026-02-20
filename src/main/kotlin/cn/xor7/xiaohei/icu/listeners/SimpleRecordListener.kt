package cn.xor7.xiaohei.icu.listeners

import cn.xor7.xiaohei.icu.plugin
import cn.xor7.xiaohei.icu.utils.module
import cn.xor7.xiaohei.icu.utils.createPhotographer
import cn.xor7.xiaohei.icu.utils.createRecordFile
import cn.xor7.xiaohei.icu.utils.getPhotographerName
import cn.xor7.xiaohei.icu.utils.getRecordPhotographer
import cn.xor7.xiaohei.icu.utils.removePhotographer
import cn.xor7.xiaohei.icu.utils.setFollow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import cn.xor7.xiaohei.icu.photographer.IPhotographer
import java.util.UUID

class SimpleRecordListener : Listener {
    private val highSpeedPausedPhotographers = mutableSetOf<IPhotographer>()
    /** 玩家 UUID -> 录像保存路径，用于玩家离开时打日志 */
    private val playerRecordPathMap = mutableMapOf<UUID, String>()

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        if (!module.shouldRecordPlayer(player)) return

        val photographer = createSimplePhotographer(player)
        val recordFile = createRecordFile(module.path, player)
        val path = recordFile.absolutePath

        photographer.setRecordFile(recordFile)
        photographer.setFollow(player)
        playerRecordPathMap[player.uniqueId] = path

        plugin.logger.info("玩家进入开始录像: ${player.name}, 保存路径: $path")
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    fun onPlayerMove(event: PlayerMoveEvent) {
        if (!module.pauseRecordingOnHighSpeed.enable) return

        val player = event.player
        val photographer = player.getRecordPhotographer() ?: return

        if (player.velocity.length() > module.pauseRecordingOnHighSpeed.thresholdTicks) {
            if (!highSpeedPausedPhotographers.contains(photographer)) {
                photographer.pauseRecording()
                highSpeedPausedPhotographers.add(photographer)
            }
        } else {
            if (highSpeedPausedPhotographers.remove(photographer)) {
                photographer.resumeRecording()
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        val photographer = player.getRecordPhotographer() ?: return

        highSpeedPausedPhotographers.remove(photographer)
        val path = playerRecordPathMap.remove(player.uniqueId) ?: "(未知路径)"
        plugin.logger.info("玩家离开，保存在: $path")
        photographer.removePhotographer()
    }

    private fun createSimplePhotographer(player: Player): IPhotographer =
        createPhotographer(
            player.getPhotographerName(),
            player.location,
        ) ?: throw RuntimeException(
            "Error when create photographer for player: {name:${player.name},UUID:${player.uniqueId}}",
        )
}