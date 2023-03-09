package net.jandie1505.bungeeaccesscontrol.cache;

import net.jandie1505.bungeeaccesscontrol.BungeePlayerCache;

import java.sql.Connection;
import java.util.Arrays;

public class PlayerCacheManager {
    private final BungeePlayerCache plugin;
    private Connection connection;

    public PlayerCacheManager(BungeePlayerCache plugin) {
        this.plugin = plugin;

        try {

        } catch (Exception e) {
            this.errorHandler(e);
        }
    }

    public void errorHandler(Exception e) {
        this.plugin.getLogger().warning("DatabaseManager Exception: EXCEPTION=" + e + ";STACKTRACE=" + Arrays.toString(e.getStackTrace()) + ";MESSAGE=" + e.getMessage());
    }
}
