package kr.tpsw.rsprefix.command;

import java.util.List;

import kr.tpsw.rsprefix.RsPrefix;
import kr.tpsw.rsprefix.api.FileAPI;
import kr.tpsw.rsprefix.api.InvAPI;
import kr.tpsw.rsprefix.api.PrefixPlayer;
import kr.tpsw.rsprefix.api.RanPreAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rsprefix.kr.tpsw.api.bukkit.API;
import rsprefix.kr.tpsw.api.bukkit.PlayersAPI;
import rsprefix.kr.tpsw.api.bukkit.VaultHook;
import rsprefix.kr.tpsw.api.publica.ObjectAPI;
import rsprefix.kr.tpsw.api.publica.StringAPI;

public class CommandPrAdmin implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		int len = args.length;
		if (len == 0) {
			if (label.equals("pradmin")) {
				sender.sendMessage("��6/pradmin give <user> <prefix>");
				sender.sendMessage("��6/pradmin view <user>");
				sender.sendMessage("��6/pradmin remove <user> <index>");
				sender.sendMessage("��6/pradmin gui <user>");
				sender.sendMessage("��6/pradmin mode <default|prefix|suffix>");
				sender.sendMessage("��c/pradmin preran (book|inst)");
				sender.sendMessage("��c/pradmin addran <class> <prefix>");
				sender.sendMessage("��c/pradmin viewran <class>");
				sender.sendMessage("��c/pradmin removeran <class> <index>");
				sender.sendMessage("��eclass=[basic, classic, rare, epic, legendary]");
			} else if (label.equals("Īȣ����")) {
				sender.sendMessage("��6/Īȣ���� �ֱ� <�÷��̾�> <Īȣ>");
				sender.sendMessage("��6/Īȣ���� ���� <�÷��̾�>");
				sender.sendMessage("��6/Īȣ���� ���� <�÷��̾�> <��ȣ>");
				sender.sendMessage("��6/Īȣ���� gui <�÷��̾�>");
				sender.sendMessage("��6/Īȣ���� ��� <�⺻|���λ�|���̻�>");
				sender.sendMessage("��c/Īȣ���� ����Īȣ (å|���)");
				sender.sendMessage("��c/Īȣ���� �����߰� <���> <Īȣ>");
				sender.sendMessage("��c/Īȣ���� �������� <���>");
				sender.sendMessage("��c/Īȣ���� �������� <���> <��ȣ>");
				sender.sendMessage("��e���=[basic, classic, rare, epic, legendary]");
			}

		} else if ((args[0].equals("give") || args[0].equals("�ֱ�")) && len >= 3) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target == null) {
				sender.sendMessage("��6�ش� �̸����� �˻��� ������ �����ϴ�.");
				return true;
			}

			boolean isLoaded = true;
			if (!FileAPI.isLoadedPlayer(target)) {
				FileAPI.loadPlayer(target);
				isLoaded = false;
			}// ���� �α׾ƿ��� �� ������ �ε�

			PrefixPlayer pp = FileAPI.getPrefixPlayer(target);
			List<String> list = pp.getList();

			boolean isLastNo = args[args.length - 1].equals("no!");
			String prefix;
			if (isLastNo) {
				prefix = StringAPI.mergeArgs(args, 2, args.length - 1, ' ');
			} else {
				prefix = StringAPI.mergeArgs(args, 2);
			}

			prefix = prefix.replace("&", "��");
			if (!list.contains(prefix)) {
				list.add(prefix);
				pp.needUpdateInv = true;
			}

			if (!isLoaded) {
				FileAPI.savePlayer(target, true);
			}

			if (isLastNo) {
				return true;
			}
			sender.sendMessage("��6" + target + " ������ " + prefix + "��6Īȣ�� �߰��߽��ϴ�.");

		} else if ((args[0].equals("remove") || args[0].equals("����")) && (len == 3 || len == 4)) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target == null) {
				sender.sendMessage("��6�ش� �̸����� �˻��� ������ �����ϴ�.");
				return true;
			}

			if (!FileAPI.isLoadedPlayer(target)) {
				sender.sendMessage("��6�������� �÷��̾ �� �� �ֽ��ϴ�.");
				return true;
			}

			PrefixPlayer pp = FileAPI.getPrefixPlayer(target);
			int index = 1;
			if (API.isIntegerPositive(args[2])) {
				index = Integer.valueOf(args[2]);
			}

			List<String> list = pp.getList();
			if (ObjectAPI.isListHasIndex(list, index)) {
				list.remove(index);
			} else {
				sender.sendMessage("��6Īȣ ����� ��� �����Դϴ�.");
				return true;
			}// Īȣ ����
			pp.needUpdateInv = true;

			if (len == 4 && args[3].equals("��!")) {
				return true;
			}// ��!
			sender.sendMessage("��6" + target + " ������ " + args[2] + "��6��° Īȣ�� �����߽��ϴ�.");
		} else if ((args[0].equals("view") || args[0].equals("����")) && (len == 3 || len == 2)) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target == null) {
				sender.sendMessage("��6�ش� �̸����� �˻��� ������ �����ϴ�.");
				return true;
			}

			if (!FileAPI.isLoadedPlayer(target)) {
				sender.sendMessage("��6�������� �÷��̾ �� �� �ֽ��ϴ�.");
				return true;
			}

			PrefixPlayer pp = FileAPI.getPrefixPlayer(target);
			int index = 1;
			if (len == 3) {
				if (API.isIntegerPositive(args[2])) {
					index = Integer.valueOf(args[2]);
				}
			}// 3�� ������ ��� index �����

			API.sendMessageList(sender, pp.getList(), index, "pradmin view");
		} else if (args[0].equals("gui") && (len == 2 || len == 3)) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target == null) {
				sender.sendMessage("��6�ش� �̸����� �˻��� ������ �����ϴ�.");
				return true;
			}

			int index = 1;
			if (len == 3) {
				if (API.isIntegerPositive(args[2])) {
					index = Integer.valueOf(args[2]);
				}
			}// 2�� ������ ��� index �����
			InvAPI.viewInv(target, (Player) sender, index);
		} else if ((args[0].equals("addran") || args[0].equals("�����߰�")) && len >= 3) {
			if (RanPreAPI.isRanpreClass(args[1])) {
				List<String> ranpre = RanPreAPI.getRanpreList(args[1]);
				String prefix = StringAPI.mergeArgs(args, 2).replace("&", "��");
				ranpre.add(prefix);
				sender.sendMessage("��6" + args[1] + "�� ��r<" + prefix + "��r>��6�� �߰��߽��ϴ�.");
			} else {
				sender.sendMessage("��6�ùٸ� ��� �̸��� �ƴմϴ�.");
			}
		} else if ((args[0].equals("mode") || args[0].equals("���")) && len == 2) {
			if (args[1].equals("default") || args[1].equals("�⺻")) {
				RsPrefix.config.set("config.mode", 1);
				RsPrefix.prefixMode = 1;
				sender.sendMessage("��6�ش� ��� ��� ���� ����: ��btrue");
			} else if (args[1].equals("prefix") || args[1].equals("���λ�")) {
				RsPrefix.config.set("config.mode", 2);
				RsPrefix.prefixMode = 2;
				sender.sendMessage("��6�ش� ��� ��� ���� ����: " + (VaultHook.isChatHook ? "��btrue" : "��cfalse"));
			} else if (args[1].equals("suffix") || args[1].equals("���̻�")) {
				RsPrefix.config.set("config.mode", 3);
				sender.sendMessage("��6�ش� ��� ��� ���� ����: " + (VaultHook.isChatHook ? "��btrue" : "��cfalse"));
				RsPrefix.prefixMode = 3;
			} else {
				sender.sendMessage("��6�ùٸ� ��� Ÿ���� �Է��Ͻʽÿ�.");
				return true;
			}
			sender.sendMessage("��6Īȣ ǥ�� ��带 " + args[1] + "(��)�� �����߽��ϴ�.");
		} else if ((args[0].equals("viewran") || args[0].equals("��������")) && (len == 3 || len == 2)) {
			if (RanPreAPI.isRanpreClass(args[1])) {
				List<String> ranpre = RanPreAPI.getRanpreList(args[1]);

				int index = 1;
				if (len == 3) {
					if (API.isIntegerPositive(args[2])) {
						index = Integer.valueOf(args[2]);
					}
				}// 3�� ������ ��� index �����

				API.sendMessageList(sender, ranpre, index, "pradmin viewran");
			} else {
				sender.sendMessage("��6�ùٸ� ��� �̸��� �ƴմϴ�.");
			}
		} else if ((args[0].equals("removeran") || args[0].equals("��������")) && len == 3) {
			if (RanPreAPI.isRanpreClass(args[1])) {
				List<String> ranpre = RanPreAPI.getRanpreList(args[1]);

				int index = 1;
				if (API.isIntegerPositive(args[2])) {
					index = Integer.valueOf(args[2]);
				}

				if (ObjectAPI.isListHasIndex(ranpre, index)) {
					ranpre.remove(index);
				} else {
					sender.sendMessage("��6Īȣ ����� ��� �����Դϴ�.");
					return true;
				}// Īȣ ����

				sender.sendMessage("��6" + args[1] + "�� " + index + "��° Īȣ�� �����߽��ϴ�.");
			} else {
				sender.sendMessage("��6�ùٸ� ��� �̸��� �ƴմϴ�.");
			}
		} else if ((args[0].equals("preran") || args[0].equals("����Īȣ")) && len == 2) {
			if (sender instanceof Player) {
				if (args[1].equals("book") || args[1].equals("å")) {
					Player p = (Player) sender;
					RanPreAPI.giveRanPreBook(p);
					sender.sendMessage("��6���� Īȣ ȹ�� �������� ������ϴ�.");
				} else if (args[1].equals("inst") || args[1].equals("���")) {
					Player p = (Player) sender;
					RanPreAPI.runRandomPrefix(p);
				} else {
					sender.sendMessage("��6�߸��� ���ڸ� �Է��߽��ϴ�.");
				}
			} else {
				sender.sendMessage("�ܼ��� ����� �Ұ����� ��ɾ��Դϴ�.");
			}
		} else {
			sender.sendMessage("��6�߸��� ��ɾ� ���ڸ� �Է��߽��ϴ�.");
		}
		return true;
	}

}
