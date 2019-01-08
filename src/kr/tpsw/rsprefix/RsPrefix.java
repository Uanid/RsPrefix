package kr.tpsw.rsprefix;

import java.io.File;

import kr.tpsw.rsprefix.api.FileAPI;
import kr.tpsw.rsprefix.api.RanPreAPI;
import kr.tpsw.rsprefix.api.RanPreAPI.Pclass;
import kr.tpsw.rsprefix.command.CommandPrAdmin;
import kr.tpsw.rsprefix.command.CommandPrefix;
import kr.tpsw.rsprefix.command.CommandRsPrefix;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import rsprefix.kr.tpsw.api.bukkit.PlayersAPI;
import rsprefix.kr.tpsw.api.bukkit.VaultHook;
import rsprefix.kr.tpsw.api.publica.YamlConfiguration;

public class RsPrefix extends JavaPlugin {

	public static boolean useRandomPrefixBroadCast = true;
	public static YamlConfiguration config;
	public static int prefixMode = 1;
	public static Plugin plugin;

	public void onEnable() {
		plugin = this;
		config = new YamlConfiguration("plugins\\RsPrefix\\config.yml");
		if (config.getInt("config.mode") == 0) {
			config.set("config.mode", 1);
		} else {
			prefixMode = config.getInt("config.mode");
		}

		if (config.isBoolean("config.UseRandomPrefixBroadcast")) {
			useRandomPrefixBroadCast = config.getBoolean("config.UseRandomPrefixBroadcast");
		} else {
			config.set("config.UseRandomPrefixBroadcast", true);
		}
		
		getCommand("prefix").setExecutor(new CommandPrefix());
		getCommand("pradmin").setExecutor(new CommandPrAdmin());
		getCommand("rsprefix").setExecutor(new CommandRsPrefix());
		Bukkit.getPluginManager().registerEvents(new EventListener(), this);

		PlayersAPI.initLoad(this);
		WordPressParsing.initRegister(this, getCommand("prupdate"), this.getFile());

		File f = new File("plugins\\RsPrefix\\Users");
		if (!f.exists()) {
			f.mkdirs();
		}// ���� ���� ����

		f = new File("plugins\\RsPrefix\\RanPrefixs");
		if (!f.exists()) {
			f.mkdirs();
		}// ���� Īȣ ���� ����

		f = new File("plugins\\RsPrefix\\RanPrefixs2");
		if (!f.exists()) {
			f.mkdirs();
		}// ���� Īȣ ���� ����

		if (false == new File("plugins\\RsPrefix\\RanPrefixs\\basic.txt").exists()) {
			RanPreAPI.initSetting();
		}

		RanPreAPI.initLoad();
		for (Pclass pc : RanPreAPI.Pclass.values()) {
			pc.loadMap();// Ȯ�� ���� �ε�
		}
		FileAPI.initLoad();// ���ӣO�� ���� ������ �ε�

		VaultHook.hookSetup();
		System.out.println("[RsPrefix] Vault ���� ����: " + VaultHook.isVaulthooked);
		System.out.println("[RsPrefix] Chat ���� ����: " + VaultHook.isChatHook);
	}

	public void onDisable() {
		config.saveYaml();
		FileAPI.endSave();// ���� ������ ���̺�
		for (Pclass pc : RanPreAPI.Pclass.values()) {
			pc.saveMap();// Ȯ�� ���� ���̺�
		}
		RanPreAPI.initSave();
	}
}
