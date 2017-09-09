package me.ryans1230.bungeekicklistener;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.List;

public final class BungeeKickListener extends Plugin {
    List<String> reasonList;
    boolean banCheck;
    ServerInfo fallback;
    @Override
    public void onEnable() {
        ConfigUtil util = new ConfigUtil(this);
        util.createConfig();
        getProxy().getPluginManager().registerListener(this, new KickListener(this));
        reasonList = ConfigUtil.c.getStringList("list");
        banCheck = ConfigUtil.c.getBoolean("ban-check");
        fallback = getProxy().getServerInfo(ConfigUtil.c.getString("fallback-server"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}