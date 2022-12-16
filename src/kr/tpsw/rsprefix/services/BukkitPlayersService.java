package kr.tpsw.rsprefix.services;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.Nullable;

public class BukkitPlayersService implements Listener {

	private final Map<UUID, Player> online;
	private final Map<UUID, OfflinePlayer> offline;
	private final Map<String, UUID> uuidMap;

	public BukkitPlayersService(Server server, PluginManager pm, Plugin pl) {
		this.online = new HashMap<>();
		this.offline = new HashMap<>();
		this.uuidMap = new HashMap<>();

		for (Player player : server.getOnlinePlayers()) {
			online.put(player.getUniqueId(), player);
		}
		for (OfflinePlayer player : server.getOfflinePlayers()) {
			offline.put(player.getUniqueId(), player);
			if (player.getName() != null) {
				uuidMap.put(player.getName(), player.getUniqueId());
			}
		}

		pm.registerEvents(new Listener() {
			@EventHandler
			public void onPlayerJoin(PlayerJoinEvent event) {
				Player p = event.getPlayer();
				online.put(p.getUniqueId(), p);
				offline.put(p.getUniqueId(), p);
			}

			@EventHandler
			public void onPlayerQuit(PlayerQuitEvent event) {
				online.remove(event.getPlayer().getUniqueId());
			}

			@EventHandler
			public void onPlayerKick(PlayerKickEvent event) {
				online.remove(event.getPlayer().getUniqueId());
			}
		}, pl);
	}

	@Nullable
	public UUID nameToUuid(String name) {
		return this.uuidMap.get(name);
	}

	public boolean isOnline(String name) {
		UUID uuid = nameToUuid(name);
		if (uuid == null) {
			return false;
		}
		return this.online.containsKey(uuid);
	}

	public boolean isOnline(UUID uuid) {
		return this.online.containsKey(uuid);
	}

	public boolean isRegistered(String name) {
		UUID uuid = nameToUuid(name);
		return this.offline.containsKey(uuid);
	}

	public boolean isRegistered(UUID uuid) {
		return this.offline.containsKey(uuid);
	}

	@Nullable
	public Player getPlayer(String name) {
		UUID uuid = nameToUuid(name);
		return this.online.get(uuid);
	}

	@Nullable
	public Player getPlayer(UUID uuid) {
		return this.online.get(uuid);
	}

	@Nullable
	public OfflinePlayer getOfflinePlayer(String name) {
		UUID uuid = nameToUuid(name);
		return this.offline.get(uuid);
	}


	@Nullable
	public OfflinePlayer getOfflinePlayer(UUID uuid) {
		return this.offline.get(uuid);
	}

	public Collection<Player> getOnlinePlayers() {
		return this.online.values();
	}

	public Collection<OfflinePlayer> getOfflinePlayers() {
		return this.offline.values();
	}

}
