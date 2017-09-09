package me.ryans1230.bungeekicklistener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class KickListener implements Listener {
    private BungeeKickListener plugin;
    KickListener(BungeeKickListener plugin) {this.plugin = plugin;}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerKickEvent(ServerKickEvent e) {
        ServerInfo fallback = plugin.fallback;
        if (fallback == null) {
            plugin.getLogger().severe("Unable to find the specified fallback server!!");
        } else {
            String reason = BaseComponent.toLegacyText(e.getKickReasonComponent());
            String from = e.getKickedFrom().getName();
            e.setCancelServer(fallback);
            e.setCancelled(true);

            if (plugin.banCheck) {
                if(reason.contains("banned")) {
                    e.getPlayer().disconnect(e.getKickReasonComponent());
                    return;
                }
            }

            if (plugin.reasonList.contains(reason)) {
                if (!from.equals(fallback.getName())) {
                    e.getPlayer().sendMessage(new ComponentBuilder(ChatColor.RED + from + " is most likely restarting, you have been connected to \"" + fallback.getName() + "\"").create());
                    return;
                }
            }
            e.getPlayer().sendMessage(new ComponentBuilder(ChatColor.RED + "Your connection to " + e.getKickedFrom().getName() + " was interrupted. You have been connected to: " + e.getCancelServer().getName()).color(ChatColor.RED).create());
        }
    }
}