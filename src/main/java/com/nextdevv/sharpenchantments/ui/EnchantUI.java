package com.nextdevv.sharpenchantments.ui;

import com.nextdevv.sharpenchantments.SharpEnchantments;
import com.nextdevv.sharpenchantments.interfaces.CustomEnchant;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.function.Predicate;

import static com.nextdevv.sharpenchantments.utils.ColorCodes.translateColorCodes;
import static com.nextdevv.sharpenchantments.utils.CustomEnchantmentsUtil.*;
import static com.nextdevv.sharpenchantments.utils.RomanNumbers.convertRomanToInteger;
import static com.nextdevv.sharpenchantments.utils.RomanNumbers.intToRoman;

public class EnchantUI implements Listener {
    public void create(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, translateColorCodes("&dEnchant Gui"));
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1));
        }

        // * * * * * * * * *
        // * *{*}*{*}*{*}* *
        // * * * * * * * * *

        inv.setItem(11, new ItemStack(Material.AIR));
        inv.setItem(13, new ItemStack(Material.AIR));
        inv.setItem(15, new ItemStack(Material.AIR));

        player.openInventory(inv);
    }

    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event) {
        if (event.getPlayer().isSneaking() && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.ANVIL) {
            event.setCancelled(true);
            create(event.getPlayer());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        System.out.println("onInventoryClick event received");
        if (event.getClickedInventory() == null) return;
        Inventory inv = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        if (!inv.getTitle().equals(translateColorCodes("&dEnchant Gui"))) return;

        if (event.getSlot() != 11 && event.getSlot() != 13 && event.getSlot() != 15) {
            event.setCancelled(true);
        }

        System.out.println("Runnable started: " + player.getDisplayName());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!event.getView().getTitle().equals(translateColorCodes("&dEnchant Gui"))) {
                    this.cancel();
                    player.sendMessage("Runnable stopped, task id: " + this.getTaskId());
                    return;
                }

                Inventory inventory = event.getView().getTopInventory();
                ItemStack firstSlot = inventory.getItem(11);
                ItemStack secondSlot = inventory.getItem(13);
                ItemStack resultSlot = inventory.getItem(15);

                if (firstSlot == null || secondSlot == null) {
                    inventory.setItem(15, new ItemStack(Material.AIR));
                }

                if (event.getSlot() == 15) {
                    if (inventory.getItem(15) != null && inventory.getItem(15).getType() != Material.AIR) {
                        player.setItemOnCursor(resultSlot);
                        inventory.setItem(15, new ItemStack(Material.AIR));
                        inventory.setItem(13, new ItemStack(Material.AIR));
                        inventory.setItem(11, new ItemStack(Material.AIR));
                        this.cancel();
                    }
                }

                if (inventory.getItem(11) != null && inventory.getItem(11).getType() != Material.AIR) {
                    if (inventory.getItem(13) != null && inventory.getItem(15) == null) {
                        if (inventory.getItem(13).getType() == Material.AIR && inventory.getItem(15) != null && inventory.getItem(15).getType() == Material.AIR) {
                            return;
                        }

                        assert secondSlot != null;
                        if (secondSlot.getItemMeta() == null) return;
                        List<String> enchanterLore = getCustomEnchants(secondSlot.getItemMeta());
                        if (enchanterLore.isEmpty()) return;

                        Map<CustomEnchant, Integer> enchantList = new HashMap<>();
                        try {
                            for (String lore : enchanterLore) {
                                CustomEnchant enchantment = getEnchantClass(lore);
                                if (enchantment != null) {
                                    System.out.println("Found: " + lore);
                                    enchantList.put(enchantment, convertRomanToInteger(lore.split(" ")[lore.split(" ").length - 1]));
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error occurred during conversion from roman to int");
                        }

                        assert firstSlot != null;
                        ItemStack resultItem = firstSlot.clone();
                        ItemMeta meta = resultItem.getItemMeta();
                        Set<String> lore = new HashSet<>();

                        if (firstSlot.getItemMeta().hasLore()) {
                            for (String l : firstSlot.getItemMeta().getLore()) {
                                for (Map.Entry<CustomEnchant, Integer> entry : enchantList.entrySet()) {
                                    CustomEnchant k = entry.getKey();
                                    int v = entry.getValue();
                                    if (l.contains(k.getName())) {
                                        try {
                                            int lvl = convertRomanToInteger(l.split(" ")[l.split(" ").length - 1]);
                                            if (lvl == v) {
                                                enchantList.replace(k, v + 1);
                                                System.out.println("REPLACED");
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("Error occurred during conversion from roman to int");
                                        }
                                    }
                                }
                            }
                        }

                        for (Map.Entry<CustomEnchant, Integer> entry : enchantList.entrySet()) {
                            CustomEnchant k = entry.getKey();
                            int v = entry.getValue();
                            if (k.isCompatibleWith(firstSlot)) {
                                if (v <= k.getMaxLevel()) {
                                    lore.add(translateColorCodes("&f") + k.getName() + " " + intToRoman(v));
                                } else {
                                    player.sendMessage("&cL'incantesimo " + k.getName() + " è al livello massimo!");
                                    for (int i = v; i >= 1; i--) {
                                        if (i <= k.getMaxLevel()) {
                                            lore.add(translateColorCodes("&f") + k.getName() + " " + intToRoman(i));
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (lore.isEmpty()) {
                            if (firstSlot.getItemMeta().hasLore()) {
                                meta.setLore(firstSlot.getItemMeta().getLore());
                            }
                        } else {
                            meta.setLore(new ArrayList<>(lore));
                        }

                        if (firstSlot.getItemMeta().hasLore()) {
                            for (String l : firstSlot.getItemMeta().getLore()) {
                                CustomEnchant enchantment = getEnchantClass(l);
                                Predicate<String> contains = (String it) -> {
                                    for (String s : lore) {
                                        if (s.contains(it)) {
                                            return true;
                                        }
                                    }
                                    return false;
                                };

                                if (enchantment != null && !contains.test(enchantment.getName())) {
                                    meta.setLore(addCustomEnchant(meta, getEnchant(enchantment.getName()), convertRomanToInteger(l.split(" ")[l.split(" ").length - 1])));
                                    System.out.println("Added");
                                }
                            }
                        }

                        System.out.println("Lore: " + lore);
                        System.out.println("Meta lore: " + meta.getLore());
                        resultItem.setItemMeta(meta);

                        System.out.println(resultItem.getItemMeta().getLore());

                        inv.setItem(15, resultItem);
                    }
                }
            }
        }.runTaskTimer(SharpEnchantments.instance, 0L, 0L);
    }
}
