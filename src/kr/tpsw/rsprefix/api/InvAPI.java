package kr.tpsw.rsprefix.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rsprefix.kr.tpsw.api.publica.ObjectAPI;

public class InvAPI {

	private static ItemStack back = new ItemStack(Material.WORKBENCH);
	private static ItemStack next = new ItemStack(Material.ANVIL);
	private static ItemStack main = new ItemStack(Material.ENCHANTMENT_TABLE);
	public static String horusCode = "��a��a";

	static {
		ItemMeta im = back.getItemMeta();
		im.setDisplayName("��e���� ���");
		back.setItemMeta(im);

		im = next.getItemMeta();
		im.setDisplayName("��e���� ���");
		next.setItemMeta(im);

		im = main.getItemMeta();
		im.setDisplayName("��e��ǥ Īȣ");
		main.setItemMeta(im);

	}

	public static void viewInv(String target, Player caster, int index) {
		if (!FileAPI.isLoadedPlayer(target)) {
			caster.sendMessage("��6�������� �÷��̾ �� �� �ֽ��ϴ�.");
			return;
		}
		PrefixPlayer pp = FileAPI.getPrefixPlayer(target);
		if (pp.needUpdateInv) {
			pp.updateInvList();
			pp.needUpdateInv = false;
		}

		List<Inventory> invList = pp.getInvList();
		// System.out.println("�κ� ����Ʈ ����: " + invList.size() + "�ε���" + index);
		if (!ObjectAPI.isListHasIndex(invList, index - 1)) {
			caster.sendMessage("��c�ش� ����� �������� �ʽ��ϴ�.");
			return;
		}

		Inventory inv = invList.get(index - 1);
		caster.openInventory(inv);
	}

	public static Inventory createInv(PrefixPlayer pp, int index) {
		Inventory inv = Bukkit.createInventory(null, 54, horusCode + pp.getName() + " ��b:" + index);
		// 45�� ������ ����
		List<String> list = pp.getList();
		if (list.size() == 0 || index == 0) {
			return null;
		}// ����Ʈ�� 0, �ε����� 0

		if (list.size() > (index - 1) * 45) {
			int start = (index - 1) * 45;
			int end = (index) * 45 - 1;
			ItemStack is = new ItemStack(Material.PAPER);
			ItemMeta im = is.getItemMeta();
			List<String> lore;
			for (int i = start; i <= end; i++) {
				if (list.size() == i) {
					break;
				}// ����Ʈ ���� �ε��� +1 == ���� �ε���

				lore = new ArrayList<String>();
				lore.add("��r" + list.get(i));
				ItemMeta imm = im.clone();
				imm.setLore(lore);
				imm.setDisplayName("��e" + (i) + "�� ° Īȣ");
				ItemStack iss = is.clone();
				iss.setItemMeta(imm);
				inv.addItem(iss);

			}// i�� �ε��� �ѹ�, ǥ���ϴ� �ѹ� �ƴ�

			if (index > 1) {
				inv.setItem(45, back);
				// �ڷΰ��� �߰�
			} else {
				ItemStack iss = main.clone();
				ItemMeta imm = main.getItemMeta();
				lore = new ArrayList<String>();
				lore.add("��r<" + pp.getMainPrefix() + "��r>");
				imm.setLore(lore);
				iss.setItemMeta(imm);
				inv.setItem(45, iss);
			}// ���� Īȣ �߰�

			if (list.size() > index * 45) {
				inv.setItem(53, next);
			}// ���� �߰�
			return inv;
		} else {
			return null;
		}// ���� �ʰ�

	}
}
