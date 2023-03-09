package net.jandie1505.bungeeaccesscontrol.cache;

import java.util.List;
import java.util.UUID;

public class CachedPlayer {
    private final UUID uuid;
    private final String name;
    private final List<String> permissions;
    private final Long lastOnline;

    public CachedPlayer(UUID uuid, String name, List<String> permissions, Long lastOnline) {
        this.uuid = uuid;
        this.name = name;
        this.permissions = permissions;
        this.lastOnline = lastOnline;
    }
}
