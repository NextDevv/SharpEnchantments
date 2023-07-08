package com.nextdevv.sharpenchantments.enchantments;

import com.nextdevv.sharpenchantments.interfaces.CustomEnchant;
import com.nextdevv.sharpenchantments.utils.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AutoSmelt implements Listener, CustomEnchant {

    @Override
    public String getName() {
        return "Auto Smelt";
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getStarterLevel() {
        return 1;
    }

    @Override
    public boolean isCompatibleWith(ItemStack itemStack) {
        return ItemStackUtil.getArmorType(itemStack) == ArmorType.TOOLS;
    }

    @EventHandler
    public void onAction(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();

        if (item == null || item.getType() == Material.AIR) return;

        if (item.containsEnchantment(Enchantment.getByName("AUTO_SMELT"))) {
            int level = item.getEnchantmentLevel(Enchantment.getByName("AUTO_SMELT"));

            Material blockType = event.getBlock().getType();
            switch (blockType) {
                case STONE:
                case COBBLESTONE:
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.STONE, level));
                    break;
                case IRON_ORE:
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, level));
                    break;
                case GOLD_ORE:
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, level));
                    break;
                default:
                    break;
            }
        }
    }
}