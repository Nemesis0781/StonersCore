package fr.nemesis07.stoners.bukkit.utils;

import fr.nemesis07.stoners.bukkit.Core;

public class SettingsUtils {

    public static boolean isDebugEnable() {
        return Core.getInstance().getConfig().getBoolean("debug-mode");
    }
}
