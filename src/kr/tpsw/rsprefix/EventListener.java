package kr.tpsw.rsprefix;

import kr.tpsw.rsprefix.api.FileAPI;
import kr.tpsw.rsprefix.api.InvAPI;
import kr.tpsw.rsprefix.api.PrefixPlayer;
import kr.tpsw.rsprefix.api.RanPreAPI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rsprefix.kr.tpsw.api.bukkit.VaultHook;

public class EventListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR && event.hasItem()) {
			if (event.getHand() == EquipmentSlot.HAND) {
				if (event.getItem().isSimilar(RanPreAPI.ranpre)) {
					ItemStack is = event.getItem();
					if (is.getAmount() == 1) {
						event.getPlayer().setItemInHand(new ItemStack(0));
					} else {
						is.setAmount(is.getAmount() - 1);
						event.getPlayer().setItemInHand(is);
					}
					RanPreAPI.runRandomPrefix(event.getPlayer());
				}
			}
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (RsPrefix.prefixMode == 1) {
			PrefixPlayer pp = FileAPI.getPrefixPlayer(event.getPlayer().getName());
			if (pp.getMainPrefix().length() != 0) {
				event.setFormat(pp.getMainPrefix() + " ��r" + event.getFormat());
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		String name = event.getPlayer().getName();
		if (!FileAPI.isLoadedPlayer(name)) {
			FileAPI.loadPlayer(name);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		String name = event.getPlayer().getName();
		FileAPI.savePlayer(name, true);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getSlot() == -999) {
			return;
		} // �κ�â ����� ����

		if (event.getInventory().getName().startsWith(InvAPI.horusCode)) {
			event.setCancelled(true);
			Material m = event.getCurrentItem().getType();
			if (m == Material.WORKBENCH || m == Material.ANVIL) {
				// ����, ���� ��� ��ư�� ������ ��
				ItemStack is = event.getCurrentItem();
				if (is.hasItemMeta()) {
					ItemMeta im = is.getItemMeta();
					if (im.hasDisplayName()) {
						String name = im.getDisplayName();
						if (name.equals("��e���� ���") || name.equals("��e���� ���")) {
							String inv = event.getInventory().getName();
							int i1 = inv.indexOf("��b:");
							String target = inv.substring(4, i1 - 1);
							int index = Integer.valueOf(inv.substring(i1 + 3, inv.length()));
							if (name.equals("��e���� ���")) {
								index--;
							} else {
								index++;
							}
							InvAPI.viewInv(target, (Player) event.getWhoClicked(), index);
						}
					}
				}
			} else if (m == Material.PAPER) {
				String inv = event.getInventory().getName();
				int i1 = inv.indexOf("��b:");
				String target = inv.substring(4, i1 - 1);
				if (event.getWhoClicked().getName().equals(target)) {
					ItemStack is = event.getCurrentItem();
					if (is.hasItemMeta()) {
						ItemMeta im = is.getItemMeta();
						if (im.hasDisplayName() && im.getDisplayName().matches("(��e)[0-9]+(�� ° Īȣ)")) {
							String prefix = im.getLore().get(0);
							prefix = prefix.substring(2, prefix.length());
							PrefixPlayer pp = FileAPI.getPrefixPlayer(target);

							pp.setMainPrefix(prefix);
							pp.needUpdateInv = true;
							((Player) event.getWhoClicked()).sendMessage("��6��ǥ Īȣ�� ��r<" + prefix + "��r>��6(��)�� �����߽��ϴ�.");

							if (VaultHook.isChatHook) {
								if (RsPrefix.prefixMode == 2) {
									VaultHook.chat.setPlayerPrefix((Player) event.getWhoClicked(), prefix);
								} else if (RsPrefix.prefixMode == 3) {
									VaultHook.chat.setPlayerSuffix((Player) event.getWhoClicked(), prefix);
								}
							} // Īȣ ����2
							event.getWhoClicked().closeInventory();
						}
					}
				}

			}
		}
	}
}
