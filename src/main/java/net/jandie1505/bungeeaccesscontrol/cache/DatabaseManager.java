package net.jandie1505.bungeeaccesscontrol.cache;

import net.jandie1505.bungeeaccesscontrol.BungeePlayerCache;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {
    private final BungeePlayerCache plugin;
    private Connection connection;

    public DatabaseManager(BungeePlayerCache plugin) {
        this.plugin = plugin;

        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://"
                            + this.plugin.getConfigManager().getConfig().optJSONObject("mysql", new JSONObject()).optString("host", "")
                            + ":"
                            + this.plugin.getConfigManager().getConfig().optJSONObject("mysql", new JSONObject()).optInt("port", 3306)
                            + "/"
                            + this.plugin.getConfigManager().getConfig().optJSONObject("mysql", new JSONObject()).optString("database", "bungeeaccesscontrol"),
                    this.plugin.getConfigManager().getConfig().optJSONObject("mysql", new JSONObject()).optString("username", ""),
                    this.plugin.getConfigManager().getConfig().optJSONObject("mysql", new JSONObject()).optString("password", "")
            );

            this.connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS cache (" +
                            "uuid VARCHAR(36) PRIMARY KEY NOT NULL," +
                            "name VARCHAR(255)," +
                            "permissions VARCHAR(1000)," +
                            "lastOnline BIGINT" +
                            ");"
            ).execute();
        } catch (Exception e) {
            this.errorHandler(e);
        }
    }

    public void errorHandler(Exception e) {
        this.plugin.getLogger().warning("DatabaseManager Exception: EXCEPTION=" + e + ";STACKTRACE=" + Arrays.toString(e.getStackTrace()) + ";MESSAGE=" + e.getMessage());
    }

    public boolean cachePlayer(CachedPlayerData playerData) {
        try {

            if (playerData.getUuid() == null) {
                return false;
            }

            PreparedStatement statement1 = this.connection.prepareStatement("SELECT * FROM cache WHERE uuid = ?;");
            statement1.setString(1, playerData.getUuid().toString());
            ResultSet rs1 = statement1.executeQuery();

            JSONArray permissions;
            if (playerData.getPermissions() != null) {
                permissions = new JSONArray(playerData.getPermissions());
            } else {
                permissions = new JSONArray();
            }

            if (rs1.next()) {

                PreparedStatement statement2 = this.connection.prepareStatement("UPDATE cache SET name = ?, permissions = ?, lastOnline = ? WHERE uuid = ?;");

                if (playerData.getName() != null) {
                    statement2.setString(1, playerData.getName());
                } else {
                    statement2.setNull(1, Types.VARCHAR);
                }

                statement2.setString(2, permissions.toString());

                if (playerData.getLastOnline() != null) {
                    statement2.setLong(3, playerData.getLastOnline());
                } else {
                    statement2.setNull(3, Types.BIGINT);
                }

                statement2.setString(4, playerData.getUuid().toString());

                return statement2.executeUpdate() != 0;

            } else {

                PreparedStatement statement2 = this.connection.prepareStatement("INSERT INTO cache (uuid, name, permissions, lastOnline) VALUES (?,?,?,?);");
                statement2.setString(1, playerData.getUuid().toString());

                if (playerData.getName() != null) {
                    statement2.setString(2, playerData.getName());
                } else {
                    statement2.setNull(2, Types.VARCHAR);
                }

                statement2.setString(3, permissions.toString());

                if (playerData.getLastOnline() != null) {
                    statement2.setLong(4, playerData.getLastOnline());
                } else {
                    statement2.setNull(4, Types.BIGINT);
                }

                return statement2.executeUpdate() != 0;

            }

        } catch (Exception e) {
            this.errorHandler(e);
            return false;
        }
    }

    private CachedPlayerData createCachedPlayerData(String uuidString, String nameString, String permissionsString, Long lastOnline) {

        List<String> permissions = new ArrayList<>();
        try {
            JSONArray permissionsArray = new JSONArray(permissionsString);

            for (Object permissionObject : permissionsArray) {

                if (permissionObject instanceof String) {
                    permissions.add((String) permissionObject);
                }

            }
        } catch (Exception ignored) {
            // empty permission list
        }

        try {

            return new CachedPlayerData(
                    UUID.fromString(uuidString),
                    nameString,
                    List.copyOf(permissions),
                    lastOnline
            );

        } catch (IllegalArgumentException e) {
            return null;
        }

    }

    public CachedPlayerData getCachedPlayer(UUID uuid) {
        try {

            if (uuid == null) {
                return null;
            }

            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM cache WHERE uuid = ?;");
            statement.setString(1, uuid.toString());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                Long lastOnline = rs.getLong("lastOnline");
                if (rs.wasNull()) {
                    lastOnline = null;
                }

                return this.createCachedPlayerData(
                        rs.getString("uuid"),
                        rs.getString("name"),
                        rs.getString("permissions"),
                        lastOnline
                );

            } else {
                return null;
            }

        } catch (Exception e) {
            this.errorHandler(e);
            return null;
        }
    }

    public List<CachedPlayerData> getCachedPlayers() {
        try {

            ResultSet rs = this.connection.prepareStatement("SELECT * FROM cache;").executeQuery();

            List<CachedPlayerData> list = new ArrayList<>();

            while (rs.next()) {

                Long lastOnline = rs.getLong("lastOnline");
                if (rs.wasNull()) {
                    lastOnline = null;
                }

                CachedPlayerData cachedPlayerData = this.createCachedPlayerData(
                        rs.getString("uuid"),
                        rs.getString("name"),
                        rs.getString("permissions"),
                        lastOnline
                );

                if (cachedPlayerData != null) {
                    list.add(cachedPlayerData);
                }

            }

            return List.copyOf(list);

        } catch (Exception e) {
            this.errorHandler(e);
            return List.of();
        }
    }
}
