package com.horizondev.mining;

import com.horizondev.mining.commands.ReloadCommand;
import com.horizondev.mining.listeners.BlockBreakListener;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MiningPlugin extends JavaPlugin {

    private Economy econ;
    private Permission perms;

    @Override
    public void onEnable() {
        load();
        Bukkit.getConsoleSender().sendMessage("§aPlugin feito por Morais_#0638");
    }

    public static MiningPlugin getMinePlugin() {
        return getPlugin(MiningPlugin.class);
    }


    private void load() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getCommand("horizonmining").setExecutor(new ReloadCommand());
        setupEconomy();
        setupPermissions();
    }

    private void setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
    }

    public Economy getEconomy() {
        return econ;
    }

    public Permission getPerms() {
        return perms;
    }

}
