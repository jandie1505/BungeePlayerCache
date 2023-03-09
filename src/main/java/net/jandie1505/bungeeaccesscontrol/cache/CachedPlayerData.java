package net.jandie1505.bungeeaccesscontrol.cache;

import java.util.List;
import java.util.UUID;

public class CachedPlayerData {
    private final UUID uuid;
    private String name;
    private List<String> permissions;
    private Long lastOnline;

    public CachedPlayerData(UUID uuid) {
        this(uuid, null, null, null);
    }

    public CachedPlayerData(UUID uuid, String name, List<String> permissions, Long lastOnline) {
        this.uuid = uuid;
        this.name = name;
        this.permissions = permissions;
        this.lastOnline = lastOnline;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public Long getLastOnline() {
        return lastOnline;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public void setLastOnline(Long lastOnline) {
        this.lastOnline = lastOnline;
    }
}
