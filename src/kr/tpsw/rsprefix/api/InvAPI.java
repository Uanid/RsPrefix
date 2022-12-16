package kr.tpsw.rsprefix.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rsprefix2.kr.tpsw.api.publica.ObjectAPI;

public class InvAPI {

	private static ItemStack back = new ItemStack(Material.BEACON);
	private static ItemStack next = new ItemStack(Material.ANVIL);
	private static ItemStack main = new ItemStack(Material.ENCHANTING_TABLE);
	public static String horusCode = "§a§a";

	static {
		ItemMeta im = back.getItemMeta();
		im.setDisplayName("§e이전 목록");
		back.setItemMeta(im);

		im = next.getItemMeta();
		im.setDisplayName("§e다음 목록");
		next.setItemMeta(im);

		im = main.getItemMeta();
		im.setDisplayName("§e대표 칭호");
		main.setItemMeta(im);

	}

	public static void viewInv(String target, Player caster, int index) {
		if (!FileAPI.isLoadedPlayer(target)) {
			caster.sendMessage("§6접속중인 플레이어만 볼 수 있습니다.");
			return;
		}
		PrefixPlayer pp = FileAPI.getPrefixPlayer(target);
		if (pp.needUpdateInv) {
			pp.updateInvList();
			pp.needUpdateInv = false;
		}

		List<Inventory> invList = pp.getInvList();
		// System.out.println("인벤 리스트 개수: " + invList.size() + "인덱스" + index);
		if (!ObjectAPI.isListHasIndex(invList, index - 1)) {
			caster.sendMessage("§c해당 목록은 존재하지 않습니다.");
			return;
		}

		Inventory inv = invList.get(index - 1);
		caster.openInventory(inv);
	}

	public static Inventory createInv(PrefixPlayer pp, int index) {
		Inventory inv = Bukkit.createInventory(null, 54, horusCode + pp.getName() + " §b:" + index);
		// 45개 단위로 끊김
		List<String> list = pp.getList();
		if (list.size() == 0 || index == 0) {
			return null;
		}// 리스트가 0, 인덱스가 0

		if (list.size() > (index - 1) * 45) {
			int start = (index - 1) * 45;
			int end = (index) * 45 - 1;
			ItemStack is = new ItemStack(Material.PAPER);
			ItemMeta im = is.getItemMeta();
			List<String> lore;
			for (int i = start; i <= end; i++) {
				if (list.size() == i) {
					break;
				}// 리스트 최종 인덱스 +1 == 현재 인덱스

				lore = new ArrayList<String>();
				lore.add("§r" + list.get(i));
				ItemMeta imm = im.clone();
				imm.setLore(lore);
				imm.setDisplayName("§e" + (i) + "번 째 칭호");
				ItemStack iss = is.clone();
				iss.setItemMeta(imm);
				inv.addItem(iss);

			}// i는 인덱스 넘버, 표기하는 넘버 아님

			if (index > 1) {
				inv.setItem(45, back);
				// 뒤로가기 추가
			} else {
				ItemStack iss = main.clone();
				ItemMeta imm = main.getItemMeta();
				lore = new ArrayList<String>();
				lore.add("§r<" + pp.getMainPrefix() + "§r>");
				imm.setLore(lore);
				iss.setItemMeta(imm);
				inv.setItem(45, iss);
			}// 메인 칭호 추가

			if (list.size() > index * 45) {
				inv.setItem(53, next);
			}// 다음 추가
			return inv;
		} else {
			return null;
		}// 개수 초과

	}
}
