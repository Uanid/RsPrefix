package kr.tpsw.rsprefix.command;

import kr.tpsw.rsprefix.RsPrefix;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRsPrefix implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage("��6�÷����� �̸�:��c RsPrefix");
		sender.sendMessage("��6����:��c " + RsPrefix.plugin.getDescription().getVersion());
		sender.sendMessage("��6������:��c TPsw");
		sender.sendMessage("��6��ɾ��:");
		sender.sendMessage("  - ��ersprefix[������Īȣ]: ��a�÷����� ���� Ȯ��");
		sender.sendMessage("  - ��epradmin[Īȣ����]: ��a�÷����� ���� �� ����");
		sender.sendMessage("  - ��eprefix[Īȣ]: ��a������ Īȣ ��ɾ�");
		sender.sendMessage("  - ��eprupdate: ��a�÷����� ������Ʈ (�۵�����, �����߻�����)");
		return true;
	}
}
