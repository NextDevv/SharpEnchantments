package com.nextdevv.sharpenchantments;

import com.nextdevv.sharpenchantments.commands.EnchantCommand;
import com.nextdevv.sharpenchantments.commands.EnchantUICommand;
import com.nextdevv.sharpenchantments.enchantments.AntiExplosion;
import com.nextdevv.sharpenchantments.enchantments.AutoSmelt;
import com.nextdevv.sharpenchantments.enchantments.Brute;
import com.nextdevv.sharpenchantments.enchantments.Bunny;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SharpEnchantments extends JavaPlugin {

    public static SharpEnchantments instance;
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public SharpEnchantments() {
        instance = this;
    }

    public void log(String message) {
        console.sendMessage(message);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        log("======================= Sharp Enchantments =======================");

        log("Loading configuration file...");
        saveDefaultConfig();

        log("Registering events...");

        List<Listener> listeners = new ArrayList<Listener>() {
            {
                add(new AntiExplosion());
                add(new AutoSmelt());
                add(new Brute());
                add(new Bunny());
            }
        };

        listeners.forEach(this::registerEvent);

        log("Registering commands");
        getCommand("enchantsharp").setExecutor(new EnchantCommand());
        getCommand("enchgui").setExecutor(new EnchantUICommand());

        log("======================= Sharp Enchantments =======================");
    }

    private void registerEvent(Listener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
