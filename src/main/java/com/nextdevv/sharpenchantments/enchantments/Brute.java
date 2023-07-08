package com.nextdevv.sharpenchantments.enchantments;

import com.nextdevv.sharpenchantments.enums.CustomEnchantments;
import com.nextdevv.sharpenchantments.events.ArmorEquipEvent;
import com.nextdevv.sharpenchantments.interfaces.CustomEnchant;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.nextdevv.sharpenchantments.utils.CustomEnchantmentsUtil.getEnchantLevel;
import static com.nextdevv.sharpenchantments.utils.CustomEnchantmentsUtil.hasCustomEnchant;
import static com.nextdevv.sharpenchantments.utils.ItemStackUtil.getArmorType;

public class Brute implements Listener, CustomEnchant {
    @Override
    public String getName() {
        return "Brute";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getStarterLevel() {
        return 1;
    }

    @Override
    public boolean isCompatibleWith(ItemStack itemStack) {
        return getArmorType(itemStack) == ArmorType.CHESTPLATE;
    }

    @EventHandler
    public void onArmorEquipped(ArmorEquipEvent event) {
        Player player = event.getPlayer();
        ItemStack newArmor = event.getNewArmorPiece();
        ItemStack oldArmor = event.getOldArmorPiece();

        ItemMeta newMeta = newArmor != null ? newArmor.getItemMeta() : null;
        ItemMeta oldMeta = oldArmor != null ? oldArmor.getItemMeta() : null;

        CustomEnchantments enchant = CustomEnchantments.BRUTE;
        if (newMeta != null && hasCustomEnchant(newMeta, enchant)) {
            if (oldMeta != null && !hasCustomEnchant(oldMeta, enchant)) return;

            int level = getEnchantLevel(newMeta, enchant);
            int damageResistanceLevel = Math.min(level - 1, 4);
            int increaseDamageLevel = Math.max(level - 3, 0);

            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, damageResistanceLevel, true, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, increaseDamageLevel, true, false));
        } else if (oldMeta != null && hasCustomEnchant(oldMeta, enchant)) {
            if (newMeta != null && !hasCustomEnchant(newMeta, enchant)) return;

            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        }
    }
}
