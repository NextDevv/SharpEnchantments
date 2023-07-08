package com.nextdevv.sharpenchantments.utils;

import com.avaje.ebean.validation.NotNull;
import com.nextdevv.sharpenchantments.enchantments.AntiExplosion;
import com.nextdevv.sharpenchantments.enchantments.AutoSmelt;
import com.nextdevv.sharpenchantments.enchantments.Brute;
import com.nextdevv.sharpenchantments.enchantments.Bunny;
import com.nextdevv.sharpenchantments.enums.CustomEnchantments;
import com.nextdevv.sharpenchantments.interfaces.CustomEnchant;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.nextdevv.sharpenchantments.utils.ColorCodes.translateColorCodes;
import static com.nextdevv.sharpenchantments.utils.RomanNumbers.convertRomanToInteger;
import static com.nextdevv.sharpenchantments.utils.RomanNumbers.intToRoman;

public class CustomEnchantmentsUtil {

    public static List<String> addCustomEnchant(ItemMeta itemMeta, CustomEnchantments enchant, int level) {
        List<String> lore = new ArrayList<>();

        if (itemMeta.hasLore()) {
            lore = itemMeta.getLore();
            for (String line : lore) {
                String[] split = line.split(" ");
                System.out.println(line);
                try {
                    String[] split2 = enchant.getEnchantName().split(" ");

                    if ((translateColorCodes("&f") + split2[0]).equals(split[0])) {
                        lore.remove(line);
                        lore.add(translateColorCodes("&f") + enchant.getEnchantName() + " " + intToRoman(level));
                        return lore;
                    }
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            lore.add(translateColorCodes("&f") + enchant.getEnchantName() + " " + intToRoman(level));
        } else {
            lore.add(translateColorCodes("&f") + enchant.getEnchantName() + " " + intToRoman(level));
        }

        itemMeta.setLore(lore);
        return lore;
    }

    public static boolean hasCustomEnchant(ItemMeta itemMeta, CustomEnchantments enchant) {
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : null;
        if (lore != null) {
            for (String line : lore) {
                String[] split = line.split(" ");
                String[] split2 = enchant.getEnchantName().split(" ");

                if ((translateColorCodes("&f") + split2[0]).equals(split[0])) {
                    return true;
                }
            }
        }

        return false;
    }

    public static int getEnchantLevel(ItemMeta itemMeta, CustomEnchantments enchant) {
        if (!hasCustomEnchant(itemMeta, enchant)) return 0;

        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : null;
        if (lore != null) {
            for (String line : lore) {
                String[] split = line.split(" ");
                System.out.println(line);

                String[] split2 = enchant.getEnchantName().split(" ");

                if ((translateColorCodes("&f") + split2[0]).equals(split[0])) {
                    return convertRomanToInteger(split[split.length - 1]);
                }
            }
        }

        return 0;
    }

    public static CustomEnchantments getEnchant(String enchantName) {
        switch (enchantName.toLowerCase()) {
            case "explosion":
                return CustomEnchantments.EXPLOSION;
            case "antiexplosion":
            case "anti explosion":
                return CustomEnchantments.ANTI_EXPLOSION;
            case "viper":
                return CustomEnchantments.VIPER;
            case "inquisitive":
                return CustomEnchantments.INQUISITIVE;
            case "miner":
                return CustomEnchantments.MINER;
            case "lumberjack":
                return CustomEnchantments.LUMBERJACK;
            case "brute":
                return CustomEnchantments.BRUTE;
            default:
                return null;
        }
    }

    public static CustomEnchant getEnchantClass(String enchantName) {
        String lowerCaseEnchantName = translateColorCodes(enchantName.split(" ")[0].toLowerCase());
        switch (lowerCaseEnchantName) {
            case "&fanti":
                return new AntiExplosion();
            case "&fbrute":
                return new Brute();
            case "&fbunny":
                return new Bunny();
            case "&fauto":
                return new AutoSmelt();
            default:
                return null;
        }
    }

    public static List<String> getCustomEnchants(ItemMeta itemMeta) {
        List<String> lore = itemMeta.getLore();
        List<String> customEnchants = new ArrayList<>();
        if (lore != null) {
            for (String line : lore) {
                String[] split = line.split(" ");
                System.out.println(split);
                System.out.println(line);

                if (split[0].contains(translateColorCodes("&f"))) {
                    customEnchants.add(line);
                }
            }
        }
        System.out.println(customEnchants);
        return customEnchants;
    }
}
