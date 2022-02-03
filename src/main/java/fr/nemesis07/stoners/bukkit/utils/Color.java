package fr.nemesis07.stoners.bukkit.utils;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Optional;

public enum Color {

    WHITE(0),
    ORANGE(1),
    MAGENTA(2),
    LIGHT_BLUE(3),
    YELLOW(4),
    LIME(5),
    PINK(6),
    DARK_GRAY(7),
    LIGHT_GRAY(8),
    CYAN(9),
    PURPLE(10),
    DARK_BLUE(11),
    BROWN(12),
    DARK_GREEN(13),
    RED(14),
    BLACK(15);

    private int colorInt;

    Color(int colorInt) {
        this.colorInt = colorInt;
    }

    public int getColorInt() {
        return colorInt;
    }

}
