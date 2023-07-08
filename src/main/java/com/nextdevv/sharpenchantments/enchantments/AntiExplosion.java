package com.nextdevv.sharpenchantments.enchantments;

import com.nextdevv.sharpenchantments.enums.CustomEnchantments;
import com.nextdevv.sharpenchantments.interfaces.CustomEnchant;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.nextdevv.sharpenchantments.utils.CustomEnchantmentsUtil.hasCustomEnchant;
import static com.nextdevv.sharpenchantments.utils.ItemStackUtil.getArmorType;

public class AntiExplosion implements Listener, CustomEnchant {

    @Override
    public String getName() {
        return "Anti Explosion";
    }

    @Override
    public int getMaxLevel() {
        return 1;
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
    public void onExplosion(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            if (!(entity instanceof Player)) return;

            CustomEnchantments enchant = CustomEnchantments.ANTI_EXPLOSION;

            ItemStack armor = ((Player) entity).getInventory().getChestplate();

            if (armor == null || armor.getType() == Material.AIR) return;

            ItemMeta meta = armor.getItemMeta();
            if (hasCustomEnchant(meta, enchant)) {
                event.setDamage(0.0);
            }
        }
    }
}
