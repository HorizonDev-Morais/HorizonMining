package com.horizondev.mining.commands;

import com.horizondev.mining.MiningPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("horizonmining.admin")) {
            MiningPlugin.getMinePlugin().reloadConfig();
            sender.sendMessage("§aPlugin recarregado.");
            sender.sendMessage("§aPlugin feito por Morais_#0638");
        } else sender.sendMessage("§c[HorizonMining] Não tens permissão para isso.");
        return false;
    }
}
