package kr.tpsw.rsprefix.command;

import kr.tpsw.rsprefix.RsPrefix;
import kr.tpsw.rsprefix.api.FileAPI;
import kr.tpsw.rsprefix.api.InvAPI;
import kr.tpsw.rsprefix.api.PrefixPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rsprefix.kr.tpsw.api.bukkit.API;
import rsprefix.kr.tpsw.api.bukkit.VaultHook;
import rsprefix.kr.tpsw.api.publica.ObjectAPI;

public class CommandPrefix implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("�ܼ��� ����� �Ұ����� ��ɾ��Դϴ�.");
			return true;
		}
		int len = args.length;
		if (len == 0) {
			if (label.equals("prefix")) {
				sender.sendMessage("��6/prefix main <index> ��f-���� Īȣ ����");// gui������
				sender.sendMessage("��6/prefix main ��f-���� Īȣ ����");// gui����
				sender.sendMessage("��6/prefix list ��f-Īȣ ��� ����");// gui����
				sender.sendMessage("��6/prefix gui ��f-���� ��� gui�� ����");
			} else {
				sender.sendMessage("��6/Īȣ ��ǥ <��ȣ> ��f-��ǥ Īȣ ����");// gui������
				sender.sendMessage("��6/Īȣ ��ǥ ��f-��ǥ Īȣ ����");// gui����
				sender.sendMessage("��6/Īȣ ��� ��f-Īȣ ��� ����");// gui����
				sender.sendMessage("��6/Īȣ gui ��f-���� ��� gui�� ����");
			}
		} else if ((args[0].equals("main") || args[0].equals("��ǥ")) && (len == 1 || len == 2)) {
			PrefixPlayer pp = FileAPI.getPrefixPlayer(sender.getName());
			if (len == 2) {
				if (API.isIntegerPositive(args[1]) || args[1].equals("-1")) {
					int index = Integer.valueOf(args[1]);
					if (index == -1 || ObjectAPI.isListHasIndex(pp.getList(), index)) {
						String main;
						if (index == -1) {
							main = "����";
						} else {
							main = pp.getList().get(index);
						}
						pp.setMainPrefix(index);
						pp.needUpdateInv = true;
						sender.sendMessage("��6��ǥ Īȣ�� ��r<" + main + "��r>��6(��)�� �����߽��ϴ�.");

						if (VaultHook.isChatHook) {
							if (index == -1) {
								main = "";
							}
							if (RsPrefix.prefixMode == 2) {
								VaultHook.chat.setPlayerPrefix((Player) sender, main);
							} else if (RsPrefix.prefixMode == 3) {
								VaultHook.chat.setPlayerSuffix((Player) sender, main);
							}
							
						}//Īȣ ����2

					} else {
						sender.sendMessage("��6Īȣ ����� ��� �����Դϴ�.");
					}
				} else {
					sender.sendMessage("��6<index>�� �ùٸ� ���ڸ� �Է��Ͻʽÿ�.");
				}
			} else {
				String main = pp.getMainPrefix();
				sender.sendMessage("��6��ǥ Īȣ�� ��r<" + main + "��r>��6�Դϴ�.");
			}
		} else if ((args[0].equals("list") || args[0].equals("���")) && (len == 2 || len == 1)) {
			PrefixPlayer pp = FileAPI.getPrefixPlayer(sender.getName());
			int index = 1;
			if (len == 2) {
				if (API.isIntegerPositive(args[1])) {
					index = Integer.valueOf(args[1]);
				}
			}// 2�� ������ ��� index �����

			API.sendMessageList(sender, pp.getList(), index, "prefix list");
		} else if (args[0].equals("gui") && (len == 1 || len == 2)) {
			int index = 1;
			if (len == 2) {
				if (API.isIntegerPositive(args[1])) {
					index = Integer.valueOf(args[1]);
				}
			}// 2�� ������ ��� index �����

			InvAPI.viewInv(sender.getName(), (Player) sender, index);
		} else {
			sender.sendMessage("��6�߸��� ��ɾ� ���ڸ� �Է��߽��ϴ�.");
		}
		return true;
	}
}
