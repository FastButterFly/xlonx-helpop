package me.xlonx.utils;

import org.bukkit.ChatColor;

public class ColorUtil {
    public static String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
