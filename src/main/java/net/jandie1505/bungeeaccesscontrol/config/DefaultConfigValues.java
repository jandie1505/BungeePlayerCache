package net.jandie1505.bungeeaccesscontrol.config;

import org.json.JSONArray;
import org.json.JSONObject;

public final class DefaultConfigValues {

    private DefaultConfigValues() {
    }

    public static JSONObject getConfig() {
        JSONObject config = new JSONObject();

        JSONObject mysqlConfig = new JSONObject();
        mysqlConfig.put("host", "");
        mysqlConfig.put("port", 3306);
        mysqlConfig.put("username", "");
        mysqlConfig.put("password", "");
        mysqlConfig.put("database", "bungeeaccesscontrol");
        config.put("mysql", mysqlConfig);

        JSONObject dateTimeConfig = new JSONObject();
        dateTimeConfig.put("datetime", "dd.MM.yyyy HH:mm:ss");
        dateTimeConfig.put("time", "HH:mm:ss");
        dateTimeConfig.put("date", "dd.MM.yyyy");
        config.put("dateTime", dateTimeConfig);

        JSONObject commandConfig = new JSONObject();
        commandConfig.put("command", "playercache");
        JSONArray accessControlAliasesCommandConfig = new JSONArray();
        accessControlAliasesCommandConfig.put("pcache");
        accessControlAliasesCommandConfig.put("bungeeplayercache");
        commandConfig.put("aliases", accessControlAliasesCommandConfig);
        commandConfig.put("permission", "playercache.command");
        commandConfig.put("allowCacheAddCommand", false);
        config.put("command", commandConfig);

        JSONObject cachingConfig = new JSONObject();
        cachingConfig.put("enableCaching", true);
        cachingConfig.put("cachePlayerNames", true);
        cachingConfig.put("cachePlayerPermissions", false);
        cachingConfig.put("cachePlayerLastOnline", true);
        config.put("caching", cachingConfig);

        return config;
    }
}
