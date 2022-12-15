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
			sender.sendMessage("콘솔은 사용이 불가능한 명령어입니다.");
			return true;
		}
		int len = args.length;
		if (len == 0) {
			if (label.equals("prefix")) {
				sender.sendMessage("§6/prefix main <index> §f-메인 칭호 설정");// gui미지원
				sender.sendMessage("§6/prefix main §f-메인 칭호 보기");// gui지원
				sender.sendMessage("§6/prefix list §f-칭호 목록 보기");// gui지원
				sender.sendMessage("§6/prefix gui §f-메인 목록 gui로 보기");
			} else {
				sender.sendMessage("§6/칭호 대표 <번호> §f-대표 칭호 설정");// gui미지원
				sender.sendMessage("§6/칭호 대표 §f-대표 칭호 보기");// gui지원
				sender.sendMessage("§6/칭호 목록 §f-칭호 목록 보기");// gui지원
				sender.sendMessage("§6/칭호 gui §f-메인 목록 gui로 보기");
			}
		} else if ((args[0].equals("main") || args[0].equals("대표")) && (len == 1 || len == 2)) {
			PrefixPlayer pp = FileAPI.getPrefixPlayer(sender.getName());
			if (len == 2) {
				if (API.isIntegerPositive(args[1]) || args[1].equals("-1")) {
					int index = Integer.valueOf(args[1]);
					if (index == -1 || ObjectAPI.isListHasIndex(pp.getList(), index)) {
						String main;
						if (index == -1) {
							main = "없음";
						} else {
							main = pp.getList().get(index);
						}
						pp.setMainPrefix(index);
						pp.needUpdateInv = true;
						sender.sendMessage("§6대표 칭호를 §r<" + main + "§r>§6(으)로 설정했습니다.");

						if (VaultHook.isChatHook) {
							if (index == -1) {
								main = "";
							}
							if (RsPrefix.prefixMode == 2) {
								VaultHook.chat.setPlayerPrefix((Player) sender, main);
							} else if (RsPrefix.prefixMode == 3) {
								VaultHook.chat.setPlayerSuffix((Player) sender, main);
							}
							
						}//칭호 설정2

					} else {
						sender.sendMessage("§6칭호 목록을 벗어난 숫자입니다.");
					}
				} else {
					sender.sendMessage("§6<index>에 올바른 숫자를 입력하십시오.");
				}
			} else {
				String main = pp.getMainPrefix();
				sender.sendMessage("§6대표 칭호는 §r<" + main + "§r>§6입니다.");
			}
		} else if ((args[0].equals("list") || args[0].equals("목록")) && (len == 2 || len == 1)) {
			PrefixPlayer pp = FileAPI.getPrefixPlayer(sender.getName());
			int index = 1;
			if (len == 2) {
				if (API.isIntegerPositive(args[1])) {
					index = Integer.valueOf(args[1]);
				}
			}// 2개 인자일 경우 index 덮어쓰기

			API.sendMessageList(sender, pp.getList(), index, "prefix list");
		} else if (args[0].equals("gui") && (len == 1 || len == 2)) {
			int index = 1;
			if (len == 2) {
				if (API.isIntegerPositive(args[1])) {
					index = Integer.valueOf(args[1]);
				}
			}// 2개 인자일 경우 index 덮어쓰기

			InvAPI.viewInv(sender.getName(), (Player) sender, index);
		} else {
			sender.sendMessage("§6잘못된 명령어 인자를 입력했습니다.");
		}
		return true;
	}
}
