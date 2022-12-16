package kr.tpsw.rsprefix.api;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import rsprefix2.kr.tpsw.api.bukkit.PlayersAPI;
import rsprefix2.kr.tpsw.api.publica.YamlConfiguration;

public class FileAPI {

	private static final Map<String, PrPlayer> container = new HashMap<>();

	public static boolean isLoadedPlayer(String name) {
		return container.containsKey(name);
	}

	public static void initLoad() {
		for (Player p : PlayersAPI.getOnlinePlayers()) {
			if (!isLoadedPlayer(p.getName())) {
				loadPlayer(p.getName());
			}
		}
	}

	public static void endSave() {
		for (String name : container.keySet()) {
			savePlayer(name, false);
		}
		container.clear();
	}

	public static void loadPlayer(String name) {
		File file = new File("plugins/RsPrefix/users/" + name + ".yml");
		boolean firstaccess = false;
		if (!file.exists()) {
			firstaccess = true;
		}// 처음 접속

		YamlConfiguration user = new YamlConfiguration("plugins/RsPrefix/users/" + name + ".yml");
		List<String> list = user.getStringList("list");
		String main = user.getString("main");
		PrPlayer pp = new PrPlayer(name, list, main);
		container.put(name, pp);
		if (firstaccess) {
			List<String> li = pp.getTitles();
			if (li == null || li.size() == 0 || li.get(0).equals("#null")) {
				// 리스트 비어있거나 #null이면
			} else {
				for (String str : RanPreAPI.basic) {
					li.add(str);
				}
			}
		}
	}

	public static void savePlayer(String name, boolean removePlayer) {
		YamlConfiguration user = new YamlConfiguration("plugins/RsPrefix/users/" + name + ".yml");
		PrPlayer pp = container.get(name);
		user.set("list", pp.getTitles());
		user.set("main", pp.getRepresentativeTitle());
		user.saveYaml();
		if (removePlayer)
			container.remove(name);
	}// 자체 언로드 기능 포함

	public static PrPlayer getPrefixPlayer(String name) {
		return container.get(name);
	}

}
