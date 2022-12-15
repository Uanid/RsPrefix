package kr.tpsw.rsprefix.command;

import kr.tpsw.rsprefix.RsPrefix;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRsPrefix implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage("§6플러그인 이름:§c RsPrefix");
		sender.sendMessage("§6버전:§c " + RsPrefix.plugin.getDescription().getVersion());
		sender.sendMessage("§6제작자:§c TPsw");
		sender.sendMessage("§6명령어들:");
		sender.sendMessage("  - §ersprefix[알피지칭호]: §a플러그인 정보 확인");
		sender.sendMessage("  - §epradmin[칭호관리]: §a플러그인 관리 및 설정");
		sender.sendMessage("  - §eprefix[칭호]: §a유저용 칭호 명령어");
		sender.sendMessage("  - §eprupdate: §a플러그인 업데이트 (작동안함, 오류발생위험)");
		return true;
	}
}
