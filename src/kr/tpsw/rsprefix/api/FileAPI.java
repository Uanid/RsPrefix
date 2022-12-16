package kr.tpsw.rsprefix.api;

import java.io.File;
import java.util.*;

import org.bukkit.entity.Player;

import kr.tpsw.rsprefix.services.BukkitPlayersService;
import rsprefix2.kr.tpsw.api.publica.YamlConfiguration;

public class FileAPI {

	private static final String KEY_TITLES = "titles";
	private static final String KEY_REPRESENTATIVE_TITLE = "representative";
	private static final String KEY_UUID = "uuid";
	private File dataPath;

	public FileAPI(File dataPath) {
		this.dataPath = dataPath;
	}

	// Make new User if File does not Exist
	public PrPlayer loadPrPlayer(UUID uuid) {
		File file = new File(dataPath, uuid.toString() + ".yaml");
		YamlConfiguration user = new YamlConfiguration(file);

		List<String> list = user.getStringList(KEY_TITLES);
		if (list == null) {
			list = new LinkedList<>();
		}
		String main = user.getString(KEY_REPRESENTATIVE_TITLE);

		return new PrPlayer(uuid, list, main);
	}

	public void savePrPlayer(PrPlayer player) {
		File file = new File(dataPath, player.getUuid() + ".yaml");
		YamlConfiguration user = new YamlConfiguration(file);

		user.set(KEY_TITLES, player.getTitles());
		user.set(KEY_REPRESENTATIVE_TITLE, player.getRepresentativeTitle());
		user.saveYaml();
	}
}
