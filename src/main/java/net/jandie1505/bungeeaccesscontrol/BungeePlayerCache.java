package net.jandie1505.bungeeaccesscontrol;

import net.jandie1505.bungeeaccesscontrol.config.ConfigManager;
import net.jandie1505.bungeeaccesscontrol.config.DefaultConfigValues;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlayerCache extends Plugin {
    private static BungeePlayerCache plugin;
    private ConfigManager configManager;

    @Override
    public void onEnable() {

        // START MESSAGE
        this.getLogger().info("Enabling BungeePlayerCache...");

        // MANAGER OBJECTS

        this.configManager = new ConfigManager(this, DefaultConfigValues.getConfig(), "config.json");
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public static BungeePlayerCache getPlugin() {
        return plugin;
    }
}
