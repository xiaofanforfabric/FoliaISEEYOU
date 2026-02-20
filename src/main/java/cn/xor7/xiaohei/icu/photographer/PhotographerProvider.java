package cn.xor7.xiaohei.icu.photographer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * 平台检测：Leaves / Folia（Foliaphotographer）提供 PhotographerManager，Paper 原版无此 API。
 * 通过反射获取，在 Paper 上返回 null 并优雅降级。
 */
public final class PhotographerProvider {

    private static Object manager; // PhotographerManager from Leaves
    private static boolean initialized;
    private static boolean available;

    public static boolean isPhotographerAvailable() {
        ensureInit();
        return available;
    }

    /**
     * 获取 PhotographerManager（Leaves 或 Folia 才有）。Paper 原版为 null。
     */
    public static Object getManager() {
        ensureInit();
        return manager;
    }

    private static void ensureInit() {
        if (initialized) return;
        initialized = true;
        try {
            Server server = Bukkit.getServer();
            Method m = server.getClass().getMethod("getPhotographerManager");
            manager = m.invoke(server);
            available = manager != null;
        } catch (Throwable ignored) {
            manager = null;
            available = false;
        }
    }

    public static Object getPhotographer(String id) {
        if (!available || manager == null) return null;
        try {
            Method m = manager.getClass().getMethod("getPhotographer", String.class);
            return m.invoke(manager, id);
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static Object getPhotographer(UUID uuid) {
        if (!available || manager == null) return null;
        try {
            Method m = manager.getClass().getMethod("getPhotographer", UUID.class);
            return m.invoke(manager, uuid);
        } catch (Throwable ignored) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static Collection<?> getPhotographers() {
        if (!available || manager == null) return Collections.emptyList();
        try {
            Method m = manager.getClass().getMethod("getPhotographers");
            Object result = m.invoke(manager);
            return result != null ? (Collection<?>) result : Collections.emptyList();
        } catch (Throwable ignored) {
            return Collections.emptyList();
        }
    }

    public static Object createPhotographer(String id, Location location, Object recorderOption) {
        if (!available || manager == null) return null;
        try {
            if (recorderOption != null) {
                Method m = manager.getClass().getMethod("createPhotographer", String.class, Location.class, recorderOption.getClass());
                return m.invoke(manager, id, location, recorderOption);
            }
            Method m2 = manager.getClass().getMethod("createPhotographer", String.class, Location.class);
            return m2.invoke(manager, id, location);
        } catch (Throwable ignored) {
            return null;
        }
    }

    /**
     * 创建录制选项。优先 Leaves，其次 Folia（Foliaphotographer）。
     */
    public static Object createBukkitRecorderOption() {
        if (!available) return null;
        String[] classes = {
            "org.leavesmc.leaves.replay.BukkitRecorderOption",
            "dev.folia.replay.BukkitRecorderOption"
        };
        for (String className : classes) {
            try {
                Class<?> clazz = Class.forName(className);
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Throwable ignored) {
                // try next
            }
        }
        return null;
    }

    // Helper: call setRecordFile on photographer
    public static void setRecordFile(Object photographer, File file) {
        if (photographer == null) return;
        try {
            Method m = photographer.getClass().getMethod("setRecordFile", File.class);
            m.invoke(photographer, file);
        } catch (Throwable ignored) {}
    }

    public static void setFollowPlayer(Object photographer, Player player) {
        if (photographer == null) return;
        try {
            Method m = photographer.getClass().getMethod("setFollowPlayer", Player.class);
            m.invoke(photographer, player);
        } catch (Throwable ignored) {}
    }

    public static void stopRecording(Object photographer, boolean async, boolean save) {
        if (photographer == null) return;
        try {
            Method m = photographer.getClass().getMethod("stopRecording", boolean.class, boolean.class);
            m.invoke(photographer, async, save);
        } catch (Throwable ignored) {}
    }

    public static void pauseRecording(Object photographer) {
        if (photographer == null) return;
        try {
            Method m = photographer.getClass().getMethod("pauseRecording");
            m.invoke(photographer);
        } catch (Throwable ignored) {}
    }

    public static void resumeRecording(Object photographer) {
        if (photographer == null) return;
        try {
            Method m = photographer.getClass().getMethod("resumeRecording");
            m.invoke(photographer);
        } catch (Throwable ignored) {}
    }

    public static Object getScheduler(Object photographer) {
        if (photographer == null) return null;
        try {
            Method m = photographer.getClass().getMethod("getScheduler");
            return m.invoke(photographer);
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static UUID getUniqueId(Object photographer) {
        if (photographer == null) return null;
        try {
            Method m = photographer.getClass().getMethod("getUniqueId");
            return (UUID) m.invoke(photographer);
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static String getName(Object photographer) {
        if (photographer == null) return "";
        try {
            Method m = photographer.getClass().getMethod("getName");
            return (String) m.invoke(photographer);
        } catch (Throwable ignored) {
            return "";
        }
    }

    public static org.bukkit.Location getLocation(Object photographer) {
        if (photographer == null) return null;
        try {
            Method m = photographer.getClass().getMethod("getLocation");
            return (org.bukkit.Location) m.invoke(photographer);
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static org.bukkit.World getWorld(Object photographer) {
        if (photographer == null) return null;
        try {
            Method m = photographer.getClass().getMethod("getWorld");
            return (org.bukkit.World) m.invoke(photographer);
        } catch (Throwable ignored) {
            return null;
        }
    }
}
