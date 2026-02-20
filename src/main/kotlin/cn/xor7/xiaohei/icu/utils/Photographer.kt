package cn.xor7.xiaohei.icu.utils

import cn.xor7.xiaohei.icu.plugin
import cn.xor7.xiaohei.icu.photographer.IPhotographer
import cn.xor7.xiaohei.icu.photographer.PhotographerProvider
import cn.xor7.xiaohei.icu.photographer.PhotographerWrapper
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID
import java.util.UUID.randomUUID
import kotlin.collections.set

val photographers = mutableSetOf<UUID>()
val player2PhotographerMap = mutableMapOf<UUID, IPhotographer>()

val allPhotographers: Collection<IPhotographer>
    get() = PhotographerProvider.getPhotographers().filterNotNull().map { PhotographerWrapper(it) }

fun isPhotographerSupported(): Boolean = PhotographerProvider.isPhotographerAvailable()

fun getPhotographer(id: String): IPhotographer? =
    PhotographerProvider.getPhotographer(id)?.let { PhotographerWrapper(it) }

fun getPhotographer(uuid: UUID): IPhotographer? =
    PhotographerProvider.getPhotographer(uuid)?.let { PhotographerWrapper(it) }

fun createPhotographer(
    id: String,
    location: Location,
    recorderOption: Any? = PhotographerProvider.createBukkitRecorderOption()
): IPhotographer? = PhotographerProvider.createPhotographer(id, location, recorderOption)?.let { raw ->
    PhotographerWrapper(raw).also { photographers.add(it.uniqueId) }
}

fun IPhotographer.removePhotographer(save: Boolean = true) {
    try {
        this.stopRecording(true, save)
    } catch (e: Exception) {
        plugin.logger.warning("Err while removing photographer ${this.name}: ${e.message}")
    }
    photographers.remove(this.uniqueId)
    player2PhotographerMap.entries.removeIf { it.value.uniqueId == this.uniqueId }
}

fun removeAllPhotographers() = photographers.toSet().forEach {
    getPhotographer(it)?.removePhotographer(false)
}

fun IPhotographer.setFollow(player: Player) {
    player2PhotographerMap[player.uniqueId] = this
    this.setFollowPlayer(player)
}

fun Player.getRecordPhotographer(): IPhotographer? =
    player2PhotographerMap[this.uniqueId]

fun Player.getPhotographerName(type: String = ""): String {
    val trimmedPlayerName = this.name
        .replace(".", "_")
        .replace("-", "_")
    val leftPart = (module.photographerPrefix + trimmedPlayerName).run {
        if (length > 6) substring(0, 6) else this
    }
    val rightPart = type + randomUUID().toString().replace("-", "")
    return "${leftPart}_${rightPart}".run {
        if (length > 16) substring(0, 16) else this
    }
}
