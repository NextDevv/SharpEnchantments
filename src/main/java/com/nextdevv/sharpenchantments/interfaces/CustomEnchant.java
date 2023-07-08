package com.nextdevv.sharpenchantments.interfaces;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public interface CustomEnchant {
    String getName();
    int getMaxLevel();
    int getStarterLevel();
    boolean isCompatibleWith(ItemStack itemStack);


    public enum ArmorType {
        CHESTPLATE(1),
        HELMET(2),
        LEGGINGS(3),
        BOOTS(4),
        FULL_ARMOR(5),
        SWORD(6),
        TOOLS(7),
        AIR(8);

        ArmorType(int i) {

        }
    }
}
