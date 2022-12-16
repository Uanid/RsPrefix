package kr.tpsw.rsprefix.services;

import kr.tpsw.rsprefix.api.PrPlayer;
import org.bukkit.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrPlayerService {

    private final Map<UUID, PrPlayer> players = new HashMap<>();

    private Server server;

    public PrPlayerService(Server server) {
        this.server = server;
    }

    public void addPrPlayer(PrPlayer player) {
        this.players.put(player.getUuid(), player);
    }
}
