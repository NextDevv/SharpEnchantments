package com.nextdevv.sharpenchantments.utils;

import com.nextdevv.sharpenchantments.events.ArmorType;
import com.nextdevv.sharpenchantments.interfaces.CustomEnchant;
import org.bukkit.inventory.ItemStack;

public class ItemStackUtil {
    public static CustomEnchant.ArmorType getArmorType(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case LEATHER_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
                return CustomEnchant.ArmorType.CHESTPLATE;
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case LEATHER_HELMET:
            case CHAINMAIL_HELMET:
                return CustomEnchant.ArmorType.HELMET;
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case LEATHER_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
                return CustomEnchant.ArmorType.LEGGINGS;
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case LEATHER_BOOTS:
            case CHAINMAIL_BOOTS:
                return CustomEnchant.ArmorType.BOOTS;
            case DIAMOND_SWORD:
            case GOLD_SWORD:
            case IRON_SWORD:
            case WOOD_SWORD:
                return CustomEnchant.ArmorType.SWORD;
            case DIAMOND_AXE:
            case DIAMOND_PICKAXE:
            case GOLD_AXE:
            case GOLD_PICKAXE:
            case IRON_AXE:
            case IRON_PICKAXE:
            case WOOD_AXE:
            case WOOD_PICKAXE:
                return CustomEnchant.ArmorType.TOOLS;
            default:
                return CustomEnchant.ArmorType.AIR;
        }
    }
}
