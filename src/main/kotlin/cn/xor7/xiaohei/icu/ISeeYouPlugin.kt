package cn.xor7.xiaohei.icu

import cn.xor7.xiaohei.icu.commands.registerICUCommand
import cn.xor7.xiaohei.icu.commands.registerInstantReplayCommand
import cn.xor7.xiaohei.icu.commands.registerPhotographerCommand
import cn.xor7.xiaohei.icu.utils.initConfig
import cn.xor7.xiaohei.icu.utils.removeAllPhotographers
import cn.xor7.xiaohei.icu.utils.scheduleDeleteOutdateFiles
import cn.xor7.xiaohei.icu.utils.tryRemoveTempFile
import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin: ISeeYouPlugin

@Suppress("unused")
class ISeeYouPlugin : JavaPlugin() {
    override fun onLoad() {
        plugin = this
    }

    override fun onEnable() {
        initConfig()
        if (!cn.xor7.xiaohei.icu.utils.isPhotographerSupported()) {
            logger.warning("Photographer API not found. Recording features disabled. Use Leaves or Folia (with photographer) for full functionality.")
        } else {
            logger.info("找到 Photographer API，录制功能已启用。")
        }
        registerCommands()
        tryRemoveTempFile()
        scheduleDeleteOutdateFiles()
    }

    override fun onDisable() {
        removeAllPhotographers()
    }

    private fun registerCommands() {
        if (!isCommandAPIAvailable()) {
            logger.warning("CommandAPI not found. Install CommandAPI plugin for full command support.")
            return
        }
        registerPhotographerCommand()
        registerInstantReplayCommand()
        registerICUCommand()
    }

    private fun isCommandAPIAvailable(): Boolean = try {
        Class.forName("dev.jorel.commandapi.CommandTree")
        true
    } catch (_: ClassNotFoundException) {
        false
    }
}