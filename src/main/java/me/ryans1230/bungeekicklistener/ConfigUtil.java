package me.ryans1230.bungeekicklistener;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("ResultOfMethodCallIgnored")
class ConfigUtil {
    private volatile BungeeKickListener plugin;
    ConfigUtil(BungeeKickListener plugin) {this.plugin = plugin;}
    static Configuration c;
    private static ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);

    synchronized void createConfig() {
        plugin.getLogger().info("Generating default configuration file. Edit it and restart your network.");
        File f = plugin.getDataFolder();
        File conf = new File(f, "config.yml");
        try {
            plugin.getDataFolder().mkdir();
            conf.createNewFile();
            Configuration config = provider.load(conf);
            c = config;

            if(config.getString("fallback-server").isEmpty()) {
                config.set("fallback-server", "lobby");
            }
            if(!config.getBoolean("ban-check")) {
                config.set("ban-check", false);
            }
            if (config.getStringList("list").isEmpty()) {
                config.set("list", Arrays.asList("kicked", "restarting"));
            }
            provider.save(config, conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}