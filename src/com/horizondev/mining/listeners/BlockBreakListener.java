package com.horizondev.mining.listeners;

import com.horizondev.mining.MiningPlugin;
import com.horizondev.mining.utils.NumberUtil;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        FileConfiguration configuration = MiningPlugin.getMinePlugin().getConfig();
        Economy economy = MiningPlugin.getMinePlugin().getEconomy();
        Permission permission = MiningPlugin.getMinePlugin().getPerms();

        if (player.getWorld().getName().equalsIgnoreCase(configuration.getString("world"))) {
            event.setCancelled(true);
            if (configuration.getBoolean("use-remove-block-drop")) {
                for (String blockDrop : MiningPlugin.getMinePlugin().getConfig().getStringList("remove-block-drop"))
                    if (event.getBlock().getType() == Material.getMaterial(blockDrop)) {
                        event.setCancelled(true);
                        event.getBlock().setType(Material.AIR);
                        return;
                    }
            }
            for (String blocks : configuration.getStringList("blocks")) {
                String[] type = blocks.split(": ");
                double money = Double.parseDouble(type[1]);
                if (block.getType() == Material.getMaterial(type[0])) {
                    if (configuration.contains("bonus." + permission.getPrimaryGroup(player))) {
                        double playerBonus = configuration.getDouble("bonus." + permission.getPrimaryGroup(player) + ".bonus");
                        double moneyBonus = money * playerBonus;
                        economy.depositPlayer(player, moneyBonus);
                        sendActionBar(player, configuration.getString("bonus-actionbar")
                                .replaceAll("&", "§").replace("%money%",
                                        "" + NumberUtil.formatShort(moneyBonus))
                                .replace("%bonus%", "" + playerBonus));
                    } else {
                        economy.depositPlayer(player, money);
                        sendActionBar(player, configuration.getString("normal-actionbar")
                                .replaceAll("&", "§")
                                .replace("%money%", "" + NumberUtil.formatShort(money)));
                    }
                    block.setType(Material.AIR);
                }
            }
        }
    }

    public void sendActionBar(Player p, String text) {
        PacketPlayOutChat packet = new PacketPlayOutChat(
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }


}
