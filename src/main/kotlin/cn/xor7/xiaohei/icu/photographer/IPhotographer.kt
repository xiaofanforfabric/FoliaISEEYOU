package cn.xor7.xiaohei.icu.photographer

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import io.papermc.paper.threadedregions.scheduler.EntityScheduler
import java.io.File
import java.util.UUID

/**
 * Photographer 抽象，兼容 Leaves 与未来自嵌实现。
 * 在 Paper 上无 photographer 时，相关功能不启用。
 */
interface IPhotographer {
    val uniqueId: UUID
    val name: String
    val location: Location?
    val world: World?
    fun setRecordFile(file: File)
    fun setFollowPlayer(player: Player?)
    fun stopRecording(async: Boolean = true, save: Boolean = true)
    fun pauseRecording()
    fun resumeRecording()
    val scheduler: EntityScheduler?
}

internal class PhotographerWrapper(private val handle: Any) : IPhotographer {
    override val uniqueId: UUID
        get() = PhotographerProvider.getUniqueId(handle)!!
    override val name: String
        get() = PhotographerProvider.getName(handle)
    override val location: Location?
        get() = PhotographerProvider.getLocation(handle)
    override val world: World?
        get() = PhotographerProvider.getWorld(handle)
    override fun setRecordFile(file: File) = PhotographerProvider.setRecordFile(handle, file)
    override fun setFollowPlayer(player: Player?) = PhotographerProvider.setFollowPlayer(handle, player)
    override fun stopRecording(async: Boolean, save: Boolean) = PhotographerProvider.stopRecording(handle, async, save)
    override fun pauseRecording() = PhotographerProvider.pauseRecording(handle)
    override fun resumeRecording() = PhotographerProvider.resumeRecording(handle)
    override val scheduler: EntityScheduler?
        get() = PhotographerProvider.getScheduler(handle) as? EntityScheduler
}
