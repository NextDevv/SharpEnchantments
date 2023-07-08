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

public class Bunny implements Listener, CustomEnchant {

    @Override
    public String getName() {
        return "Bunny";
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isCompatibleWith(ItemStack itemStack) {
        return false;
    }

    @Override
    public int getStarterLevel() {
        return 1;
    }

    @EventHandler
    public void onArmorEquipped(ArmorEquipEvent event) {
        Player player = event.getPlayer();
        ItemStack newArmor = event.getNewArmorPiece();
        ItemStack oldArmor = event.getOldArmorPiece();

        ItemMeta newMeta = newArmor != null ? newArmor.getItemMeta() : null;
        ItemMeta oldMeta = oldArmor != null ? oldArmor.getItemMeta() : null;

        CustomEnchantments enchant = CustomEnchantments.BUNNY;
        if (newMeta != null && hasCustomEnchant(newMeta, enchant)) {
            if (oldMeta != null && !hasCustomEnchant(oldMeta, enchant)) return;

            int level = getEnchantLevel(newMeta, enchant);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, level - 1, true, false));
        } else if (oldMeta != null && hasCustomEnchant(oldMeta, enchant)) {
            if (newMeta != null && !hasCustomEnchant(newMeta, enchant)) return;

            player.removePotionEffect(PotionEffectType.JUMP);
        }
    }
}
