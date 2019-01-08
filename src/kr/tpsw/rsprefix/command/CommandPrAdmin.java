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
				sender.sendMessage("§6/pradmin give <user> <prefix>");
				sender.sendMessage("§6/pradmin view <user>");
				sender.sendMessage("§6/pradmin remove <user> <index>");
				sender.sendMessage("§6/pradmin gui <user>");
				sender.sendMessage("§6/pradmin mode <default|prefix|suffix>");
				sender.sendMessage("§c/pradmin preran (book|inst)");
				sender.sendMessage("§c/pradmin addran <class> <prefix>");
				sender.sendMessage("§c/pradmin viewran <class>");
				sender.sendMessage("§c/pradmin removeran <class> <index>");
				sender.sendMessage("§eclass=[basic, classic, rare, epic, legendary]");
			} else if (label.equals("칭호관리")) {
				sender.sendMessage("§6/칭호관리 주기 <플레이어> <칭호>");
				sender.sendMessage("§6/칭호관리 보기 <플레이어>");
				sender.sendMessage("§6/칭호관리 삭제 <플레이어> <번호>");
				sender.sendMessage("§6/칭호관리 gui <플레이어>");
				sender.sendMessage("§6/칭호관리 모드 <기본|접두사|접미사>");
				sender.sendMessage("§c/칭호관리 랜덤칭호 (책|즉시)");
				sender.sendMessage("§c/칭호관리 랜덤추가 <등급> <칭호>");
				sender.sendMessage("§c/칭호관리 랜덤보기 <등급>");
				sender.sendMessage("§c/칭호관리 랜덤삭제 <등급> <번호>");
				sender.sendMessage("§e등급=[basic, classic, rare, epic, legendary]");
			}

		} else if ((args[0].equals("give") || args[0].equals("주기")) && len >= 3) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target == null) {
				sender.sendMessage("§6해당 이름으로 검색된 유저가 없습니다.");
				return true;
			}

			boolean isLoaded = true;
			if (!FileAPI.isLoadedPlayer(target)) {
				FileAPI.loadPlayer(target);
				isLoaded = false;
			}// 유저 로그아웃일 때 데이터 로드

			PrefixPlayer pp = FileAPI.getPrefixPlayer(target);
			List<String> list = pp.getList();

			boolean isLastNo = args[args.length - 1].equals("no!");
			String prefix;
			if (isLastNo) {
				prefix = StringAPI.mergeArgs(args, 2, args.length - 1, ' ');
			} else {
				prefix = StringAPI.mergeArgs(args, 2);
			}

			prefix = prefix.replace("&", "§");
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
			sender.sendMessage("§6" + target + " 유저에 " + prefix + "§6칭호를 추가했습니다.");

		} else if ((args[0].equals("remove") || args[0].equals("삭제")) && (len == 3 || len == 4)) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target == null) {
				sender.sendMessage("§6해당 이름으로 검색된 유저가 없습니다.");
				return true;
			}

			if (!FileAPI.isLoadedPlayer(target)) {
				sender.sendMessage("§6접속중인 플레이어만 볼 수 있습니다.");
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
				sender.sendMessage("§6칭호 목록을 벗어난 숫자입니다.");
				return true;
			}// 칭호 삭제
			pp.needUpdateInv = true;

			if (len == 4 && args[3].equals("노!")) {
				return true;
			}// 노!
			sender.sendMessage("§6" + target + " 유저의 " + args[2] + "§6번째 칭호를 삭제했습니다.");
		} else if ((args[0].equals("view") || args[0].equals("보기")) && (len == 3 || len == 2)) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target == null) {
				sender.sendMessage("§6해당 이름으로 검색된 유저가 없습니다.");
				return true;
			}

			if (!FileAPI.isLoadedPlayer(target)) {
				sender.sendMessage("§6접속중인 플레이어만 볼 수 있습니다.");
				return true;
			}

			PrefixPlayer pp = FileAPI.getPrefixPlayer(target);
			int index = 1;
			if (len == 3) {
				if (API.isIntegerPositive(args[2])) {
					index = Integer.valueOf(args[2]);
				}
			}// 3개 인자일 경우 index 덮어쓰기

			API.sendMessageList(sender, pp.getList(), index, "pradmin view");
		} else if (args[0].equals("gui") && (len == 2 || len == 3)) {
			String target = PlayersAPI.findOfflinePlayerName(args[1]);
			if (target == null) {
				sender.sendMessage("§6해당 이름으로 검색된 유저가 없습니다.");
				return true;
			}

			int index = 1;
			if (len == 3) {
				if (API.isIntegerPositive(args[2])) {
					index = Integer.valueOf(args[2]);
				}
			}// 2개 인자일 경우 index 덮어쓰기
			InvAPI.viewInv(target, (Player) sender, index);
		} else if ((args[0].equals("addran") || args[0].equals("랜덤추가")) && len >= 3) {
			if (RanPreAPI.isRanpreClass(args[1])) {
				List<String> ranpre = RanPreAPI.getRanpreList(args[1]);
				String prefix = StringAPI.mergeArgs(args, 2).replace("&", "§");
				ranpre.add(prefix);
				sender.sendMessage("§6" + args[1] + "에 §r<" + prefix + "§r>§6를 추가했습니다.");
			} else {
				sender.sendMessage("§6올바른 등급 이름이 아닙니다.");
			}
		} else if ((args[0].equals("mode") || args[0].equals("모드")) && len == 2) {
			if (args[1].equals("default") || args[1].equals("기본")) {
				RsPrefix.config.set("config.mode", 1);
				RsPrefix.prefixMode = 1;
				sender.sendMessage("§6해당 모드 사용 가능 여부: §btrue");
			} else if (args[1].equals("prefix") || args[1].equals("접두사")) {
				RsPrefix.config.set("config.mode", 2);
				RsPrefix.prefixMode = 2;
				sender.sendMessage("§6해당 모드 사용 가능 여부: " + (VaultHook.isChatHook ? "§btrue" : "§cfalse"));
			} else if (args[1].equals("suffix") || args[1].equals("접미사")) {
				RsPrefix.config.set("config.mode", 3);
				sender.sendMessage("§6해당 모드 사용 가능 여부: " + (VaultHook.isChatHook ? "§btrue" : "§cfalse"));
				RsPrefix.prefixMode = 3;
			} else {
				sender.sendMessage("§6올바른 모드 타입을 입력하십시오.");
				return true;
			}
			sender.sendMessage("§6칭호 표시 모드를 " + args[1] + "(으)로 변경했습니다.");
		} else if ((args[0].equals("viewran") || args[0].equals("랜덤보기")) && (len == 3 || len == 2)) {
			if (RanPreAPI.isRanpreClass(args[1])) {
				List<String> ranpre = RanPreAPI.getRanpreList(args[1]);

				int index = 1;
				if (len == 3) {
					if (API.isIntegerPositive(args[2])) {
						index = Integer.valueOf(args[2]);
					}
				}// 3개 인자일 경우 index 덮어쓰기

				API.sendMessageList(sender, ranpre, index, "pradmin viewran");
			} else {
				sender.sendMessage("§6올바른 등급 이름이 아닙니다.");
			}
		} else if ((args[0].equals("removeran") || args[0].equals("랜덤삭제")) && len == 3) {
			if (RanPreAPI.isRanpreClass(args[1])) {
				List<String> ranpre = RanPreAPI.getRanpreList(args[1]);

				int index = 1;
				if (API.isIntegerPositive(args[2])) {
					index = Integer.valueOf(args[2]);
				}

				if (ObjectAPI.isListHasIndex(ranpre, index)) {
					ranpre.remove(index);
				} else {
					sender.sendMessage("§6칭호 목록을 벗어난 숫자입니다.");
					return true;
				}// 칭호 삭제

				sender.sendMessage("§6" + args[1] + "의 " + index + "번째 칭호를 삭제했습니다.");
			} else {
				sender.sendMessage("§6올바른 등급 이름이 아닙니다.");
			}
		} else if ((args[0].equals("preran") || args[0].equals("랜덤칭호")) && len == 2) {
			if (sender instanceof Player) {
				if (args[1].equals("book") || args[1].equals("책")) {
					Player p = (Player) sender;
					RanPreAPI.giveRanPreBook(p);
					sender.sendMessage("§6랜덤 칭호 획득 아이템을 얻었습니다.");
				} else if (args[1].equals("inst") || args[1].equals("즉시")) {
					Player p = (Player) sender;
					RanPreAPI.runRandomPrefix(p);
				} else {
					sender.sendMessage("§6잘못된 인자를 입력했습니다.");
				}
			} else {
				sender.sendMessage("콘솔은 사용이 불가능한 명령어입니다.");
			}
		} else {
			sender.sendMessage("§6잘못된 명령어 인자를 입력했습니다.");
		}
		return true;
	}

}
