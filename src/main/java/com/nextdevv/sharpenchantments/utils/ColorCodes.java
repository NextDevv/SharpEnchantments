package com.nextdevv.sharpenchantments.utils;

import org.bukkit.ChatColor;

public class ColorCodes {
    public static String translateColorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
