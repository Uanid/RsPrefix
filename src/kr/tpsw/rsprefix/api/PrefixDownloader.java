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
		System.out.println("�Ϸ�");
	}

	// ������� ���� �ʼ�
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
		// stream��������

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
		}// �Խù� �κи� �ڸ���

		{
			i1 = sb.indexOf("<" + PLUGIN_NAME_LOWER + ">");
			i2 = sb.indexOf("</" + PLUGIN_NAME_LOWER + ">");
			sb2 = new StringBuilder(sb.substring(i1 + PLUGIN_NAME_LOWER.length() + 3, i2 - 1));
		}// �÷����� �κ� �ڸ���

		{
			tag = "basic";
			i1 = sb2.indexOf("<" + tag + ">");
			i2 = sb2.indexOf("</" + tag + ">");
			if (i1 == -1 || i2 == -1) {
				args = new String[] { "��f����" };
			} else {
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
			}
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\" + tag + ".txt"), args);
		}// ������

		{
			tag = "classic";
			i1 = sb2.indexOf("<" + tag + ">");
			i2 = sb2.indexOf("</" + tag + ">");
			if (i1 == -1 || i2 == -1) {
				args = new String[] { "��f[�ʺ���]" };
			} else {
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
			}
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\" + tag + ".txt"), args);
		}// �Ϲ�

		{
			tag = "rare";
			i1 = sb2.indexOf("<" + tag + ">");
			i2 = sb2.indexOf("</" + tag + ">");
			if (i1 == -1 || i2 == -1) {
				args = new String[] { "��b[ģ����]" };
			} else {
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
			}
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\" + tag + ".txt"), args);
		}// ���

		{
			tag = "epic";
			i1 = sb2.indexOf("<" + tag + ">");
			i2 = sb2.indexOf("</" + tag + ">");
			if (i1 == -1 || i2 == -1) {
				args = new String[] { "��f[��6Poi��f]" };
			} else {
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
			}
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\" + tag + ".txt"), args);
		}// ����

		{
			tag = "legendary";
			i1 = sb2.indexOf("<" + tag + ">");
			i2 = sb2.indexOf("</" + tag + ">");
			if (i1 == -1 || i2 == -1) {
				args = new String[] { "��a[��c���������͡�a]" };
			} else {
				args = sb2.substring(i1 + tag.length() + 3, i2 - 1).split("\n");
			}
			saveNewPrefixs(new File("plugins\\RsPrefix\\RanPrefixs\\" + tag + ".txt"), args);
		}// ����
	}
}
