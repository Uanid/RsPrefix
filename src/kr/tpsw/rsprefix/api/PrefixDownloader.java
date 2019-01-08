package kr.tpsw.rsprefix.api;

import static kr.tpsw.rsprefix.api.RanPreAPI.saveNewPrefixs;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class PrefixDownloader {
	public final static String PLUGIN_NAME_LOWER = "rsprefix";
	public final static String POST_ID = "344";

	public static void main(String[] args) {
		saveNewRandomPrefixs();
		System.out.println("완료");
	}

	// 스레드로 실행 필수
	public static void saveNewRandomPrefixs() {
		String posturl = "http://tpsw.or.kr/" + POST_ID;
		StringBuilder sb = new StringBuilder();
		List<String> postlist = new LinkedList<String>();

		int i1 = 0;
		int i2 = 0;
		StringBuilder sb2;
		String tag;
		String[] args;

		StringBuilder builder = new StringBuilder();
		try {
			URL url = new URL(posturl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line).append('\n');
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// stream가져오기

		{
			i1 = builder.indexOf("<div class=\"post-content\">");
			builder.delete(0, i1);
			i1 = builder.indexOf("<p>");
			builder.delete(0, i1 + 3);
			i1 = builder.indexOf("</div>");
			args = builder.substring(0, i1).replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;", ">").replace("<p>", "").replace("\n", "").trim().split("</p>");
			for (String line : args) {
				postlist.add(line);
				sb.append(line).append('\n');
			}
		}// 게시물 부분만 자르기

		{
			i1 = sb.indexOf("<" + PLUGIN_NAME_LOWER + ">");
			i2 = sb.indexOf("</" + PLUGIN_NAME_LOWER + ">");
			sb2 = new StringBuilder(sb.substring(i1 + PLUGIN_NAME_LOWER.length() + 3, i2 - 1));
		}// 플러그인 부분 자르기

		{
			tag = "basic";
			i1 = sb2.indexOf("<" + tag + ">");
			i2 = sb2.indexOf("</" + tag + ">");
			if (i1 == -1 || i2 == -1) {
				args = new String[] { "§f뉴비" };
			} else {
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
			}
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\" + tag + ".txt"), args);
		}// 베이직

		{
			tag = "classic";
			i1 = sb2.indexOf("<" + tag + ">");
			i2 = sb2.indexOf("</" + tag + ">");
			if (i1 == -1 || i2 == -1) {
				args = new String[] { "§f[초보자]" };
			} else {
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
			}
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\" + tag + ".txt"), args);
		}// 일반

		{
			tag = "rare";
			i1 = sb2.indexOf("<" + tag + ">");
			i2 = sb2.indexOf("</" + tag + ">");
			if (i1 == -1 || i2 == -1) {
				args = new String[] { "§b[친절한]" };
			} else {
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
			}
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\" + tag + ".txt"), args);
		}// 희귀

		{
			tag = "epic";
			i1 = sb2.indexOf("<" + tag + ">");
			i2 = sb2.indexOf("</" + tag + ">");
			if (i1 == -1 || i2 == -1) {
				args = new String[] { "§f[§6Poi§f]" };
			} else {
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
			}
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\" + tag + ".txt"), args);
		}// 영웅

		{
			tag = "legendary";
			i1 = sb2.indexOf("<" + tag + ">");
			i2 = sb2.indexOf("</" + tag + ">");
			if (i1 == -1 || i2 == -1) {
				args = new String[] { "§a[§c서버마스터§a]" };
			} else {
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
			}
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\" + tag + ".txt"), args);
		}// 전설
	}
}
