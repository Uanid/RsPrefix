package kr.tpsw.rsprefix.services;

import kr.tpsw.rsprefix.api.PrPlayer;
import org.bukkit.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrPlayerService {

    private final Map<String, PrPlayer> container = new HashMap<>();
    private final Map<UUID, PrPlayer> container2 = new HashMap<>();

    private Server server;

    public PrPlayerService(Server server) {
        this.server = server;
    }

    public boolean isLoadedPlayer(String name) {
        return container.containsKey(name);
    }

    public boolean isLoadedPlayer(UUID uuid) {
        return container2.containsKey(uuid);
    }


}
