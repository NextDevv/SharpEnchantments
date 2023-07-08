package com.nextdevv.sharpenchantments.commands;

import com.nextdevv.sharpenchantments.enums.CustomEnchantments;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.nextdevv.sharpenchantments.utils.CustomEnchantmentsUtil.addCustomEnchant;

public class EnchantCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        CustomEnchantments enchantment = null;

        if (args != null && args.length > 0) {
            if (!args[0].isEmpty()) {
                switch (args[0].toLowerCase()) {
                    case "antiexplosion":
                        enchantment = CustomEnchantments.ANTI_EXPLOSION;
                        break;
                    case "brute":
                        enchantment = CustomEnchantments.BRUTE;
                        break;
                    case "bunny":
                        enchantment = CustomEnchantments.BUNNY;
                        break;
                    case "autosmelt":
                        enchantment = CustomEnchantments.AUTO_SMELT;
                        break;
                    default:
                        player.sendMessage("&cUnknown Enchantment type: " + args[0]);
                        break;
                }
            } else {
                player.sendMessage("&fCOMING SOON");
                return true;
            }

            int level = 1;
            if (args.length > 1 && !args[1].isEmpty()) {
                try {
                    level = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("&cInvalid level");
                    return true;
                }
            }

            ItemStack item = player.getInventory().getItemInHand();
            if (item != null && item.getType() != Material.AIR) {
                ItemMeta meta = item.getItemMeta();

                if (enchantment == null) {
                    player.sendMessage("&cAn error occurred while trying to create a new enchantment");
                    return true;
                }

                meta.setLore(addCustomEnchant(meta, enchantment, level));

                item.setItemMeta(meta);
                player.getInventory().setItemInHand(item);
                player.sendMessage("&eEnchanted");
            } else {
                player.sendMessage("&cNon hai nessun oggetto in mano");
            }
        }

        return true;
    }
}