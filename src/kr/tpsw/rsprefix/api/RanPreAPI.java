package kr.tpsw.rsprefix.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import kr.tpsw.rsprefix.RsPrefix;
import kr.tpsw.rsprefix.WordPressParsing;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RanPreAPI {

	public static List<String> basic;// �길 ����
	private static List<String> classic;
	private static List<String> rare;
	private static List<String> epic;
	private static List<String> legendary;
	private static Random rr = new Random();

	public static ItemStack ranpre = new ItemStack(Material.BOOK);
	// Īȣ ����

	static {
		ItemMeta im = ranpre.getItemMeta();
		im.setDisplayName("��a[��c���� Īȣ�ѡ�a]");
		List<String> list = new ArrayList<String>();
		list.add("��r��5��Ŭ���Ͽ� ���� Īȣ�� ȹ���� �� �ֽ��ϴ�.");
		im.setLore(list);
		ranpre.setItemMeta(im);
	}

	public static void giveRanPreBook(Player player) {
		int firstEmpty = player.getInventory().firstEmpty();
		if (firstEmpty == -1) {
			player.getWorld().dropItem(player.getLocation(), ranpre);
		} else {
			player.getInventory().addItem(ranpre);
		}
	}

	public static void runRandomPrefix(Player sender) {
		PrefixPlayer pp = FileAPI.getPrefixPlayer(sender.getName());
		int i = RanPreAPI.getRanClass(sender.getName());
		if (i == 1) {// �Ϲ�
			String pre = getRan(classic);
			List<String> list = pp.getList();
			if (!list.contains(pre)) {
				list.add(pre);
			}
			pp.needUpdateInv = true;
			if (RsPrefix.useRandomPrefixBroadCast) {
				Bukkit.broadcastMessage("��e" + sender.getName() + "���� ��f�Ϲ� ��r<" + pre + "��r> ��eĪȣ�� ȹ���߽��ϴ�");
			} else {
				sender.sendMessage("��f�Ϲ� ��r<" + pre + "��r> ��eĪȣ�� ȹ���߽��ϴ�");
			}
		} else if (i == 2) {// ���
			String pre = getRan(rare);
			List<String> list = pp.getList();
			if (!list.contains(pre)) {
				list.add(pre);
			}
			pp.needUpdateInv = true;
			if (RsPrefix.useRandomPrefixBroadCast) {
				Bukkit.broadcastMessage("��e" + sender.getName() + "���� ��b��� ��r<" + pre + "��r> ��eĪȣ�� ȹ���߽��ϴ�!");
			} else {
				sender.sendMessage("��b��� ��r<" + pre + "��r> ��eĪȣ�� ȹ���߽��ϴ�!");
			}
		} else if (i == 3) {// ����
			String pre = getRan(epic);
			List<String> list = pp.getList();
			if (!list.contains(pre)) {
				list.add(pre);
			}
			pp.needUpdateInv = true;
			if (RsPrefix.useRandomPrefixBroadCast) {
				Bukkit.broadcastMessage("��e�Ϳ� " + sender.getName() + "���� ��6���� ��r<" + pre + "��r> ��eĪȣ�� ȹ���߽��ϴ�!");
			} else {
				sender.sendMessage("��e�Ϳ�! ��6���� ��r<" + pre + "��r> ��eĪȣ�Դϴ�!");
			}
		} else if (i == 4) {// ����
			String pre = getRan(legendary);
			List<String> list = pp.getList();
			if (!list.contains(pre)) {
				list.add(pre);
			}
			pp.needUpdateInv = true;
			Bukkit.broadcastMessage("��e�Ϳ�! ��c" + sender.getName() + "��e���� ��f[��6������f] ��r<" + pre + "��r> ��eĪȣ�� ȹ���߽��ϴ�!");
			// sender.sendMessage("��e�Ϳ�! ��6���� ��r< " + pre + "��r> ��eĪȣ�Դϴ�!");
		} else {
			// ���� �Ұ����� ���
		}
	}

	public static void initSetting() {
		if (WordPressParsing.IS_DATA_PAGE_CONNETED) {
			PrefixDownloader.saveNewRandomPrefixs();
		} else {
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\basic.txt"), new String[] { "��f����" });
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\classic.txt"), new String[] { "��f[�ʺ���]" });
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\rare.txt"), new String[] { "��b[ģ����]" });
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\epic.txt"), new String[] { "��f[��6���ȡ�f]" });
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\legendary.txt"), new String[] { "��a[��c���������͡�a]" });
		}
	}

	public static void saveNewPrefixs(File f, String s[]) {
		try {
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			for (String prefix : s) {
				bw.write(prefix + "\n");
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initLoad() {
		basic = getBuffered("plugins\\RsPrefix\\RanPrefixs\\basic.txt");
		classic = getBuffered("plugins\\RsPrefix\\RanPrefixs\\classic.txt");
		rare = getBuffered("plugins\\RsPrefix\\RanPrefixs\\rare.txt");
		epic = getBuffered("plugins\\RsPrefix\\RanPrefixs\\epic.txt");
		legendary = getBuffered("plugins\\RsPrefix\\RanPrefixs\\legendary.txt");
	}

	public static void initSave() {
		saveBuffered(basic, "plugins\\RsPrefix\\RanPrefixs\\basic.txt");
		saveBuffered(classic, "plugins\\RsPrefix\\RanPrefixs\\classic.txt");
		saveBuffered(rare, "plugins\\RsPrefix\\RanPrefixs\\rare.txt");
		saveBuffered(epic, "plugins\\RsPrefix\\RanPrefixs\\epic.txt");
		saveBuffered(legendary, "plugins\\RsPrefix\\RanPrefixs\\legendary.txt");
	}

	private static String getRan(List<String> list) {
		int size = rr.nextInt(list.size());
		return list.get(size);
	}

	private static int getRanClass(String name) {
		int ran = rr.nextInt(1000);
		if (getMap(Pclass.LEGENDARY, name) >= 200) {
			// Bukkit.broadcastMessage("���� ����");
			setMap(Pclass.LEGENDARY, name);
			return 4;
		} else if (getMap(Pclass.EPIC, name) >= 50) {
			if (ran < 10) {
				// Bukkit.broadcastMessage("���� ���� +����");
				setMap(Pclass.LEGENDARY, name);
				return 4;
			} else {
				// Bukkit.broadcastMessage("���� ����");
				addMap(Pclass.LEGENDARY, name);
				setMap(Pclass.EPIC, name);
				return 3;
			}
		} else if (getMap(Pclass.RARE, name) >= 5) {
			if (ran < 10) {
				// Bukkit.broadcastMessage("���� ���� +����");
				setMap(Pclass.LEGENDARY, name);
				return 4;
			} else if (ran < 50) {
				// Bukkit.broadcastMessage("���� ���� +����");
				addMap(Pclass.LEGENDARY, name);
				setMap(Pclass.EPIC, name);
				return 3;
			} else {
				// Bukkit.broadcastMessage("���� ����");
				addMap(Pclass.LEGENDARY, name);
				addMap(Pclass.EPIC, name);
				setMap(Pclass.RARE, name);
				return 2;
			}
		} else {
			if (ran < 10) {
				// Bukkit.broadcastMessage("�Ϲ� +����");
				setMap(Pclass.LEGENDARY, name);
				return 4;
			} else if (ran < 50) {
				// Bukkit.broadcastMessage("�Ϲ� +����");
				addMap(Pclass.LEGENDARY, name);
				setMap(Pclass.EPIC, name);
				return 3;
			} else if (ran < 250) {
				// Bukkit.broadcastMessage("�Ϲ� +���");
				addMap(Pclass.LEGENDARY, name);
				addMap(Pclass.EPIC, name);
				setMap(Pclass.RARE, name);
				return 2;
			} else {
				addMap(Pclass.LEGENDARY, name);
				addMap(Pclass.EPIC, name);
				addMap(Pclass.RARE, name);
				return 1;
			}
		}
	}

	public static enum Pclass {// ������, Ŭ������ ������� ����
		BASIC, CLASSIC, RARE, EPIC, LEGENDARY;
		public Map<String, Integer> map = new HashMap<String, Integer>();

		@SuppressWarnings("unchecked")
		public void loadMap() {
			File f = new File("plugins\\RsPrefix\\RanPrefixs2\\" + this.name() + ".ser");
			if (f.exists())
				try {
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
					map = (Map<String, Integer>) ois.readObject();
					ois.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

		public void saveMap() {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("plugins\\RsPrefix\\RanPrefixs2\\" + this.name() + ".ser")));
				oos.writeObject(map);
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isRanpreClass(String classs) {
		for (Pclass pc : Pclass.values()) {
			if (pc.name().toLowerCase().equals(classs)) {
				return true;
			}
		}
		return false;
	}

	public static List<String> getRanpreList(String classs) {
		switch (classs) {
		case "basic":
			return basic;
		case "classic":
			return classic;
		case "rare":
			return rare;
		case "epic":
			return epic;
		case "legendary":
			return legendary;
		default:
			return null;
		}
	}

	private static void setMap(Pclass pc, String name) {
		pc.map.put(name, 0);
	}

	private static int getMap(Pclass pc, String name) {
		if (pc.map.containsKey(name)) {
			return pc.map.get(name);
		} else {
			return 0;
		}
	}

	private static void addMap(Pclass pc, String name) {
		if (pc.map.containsKey(name)) {
			pc.map.put(name, pc.map.get(name) + 1);
		} else {
			pc.map.put(name, 1);
		}
	}

	private static void saveBuffered(List<String> list, String path) {
		try {
			File file = new File(path);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (String s : list) {
				bw.write(s + "\n");
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<String> getBuffered(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String s;
		List<String> list = new ArrayList<String>();
		try {
			while ((s = br.readLine()) != null) {
				list.add(s);
			}
			br.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
