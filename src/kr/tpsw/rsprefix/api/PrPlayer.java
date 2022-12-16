package kr.tpsw.rsprefix.api;

import java.util.ArrayList;
import java.util.List;

import rsprefix2.kr.tpsw.api.publica.ObjectAPI;

public class PrPlayer {

	private String name;
	private List<String> titles;
	private String representativeTitle;

	public PrPlayer(String name) {
		this(name, new ArrayList<>(), null);
	}

	public PrPlayer(String name, List<String> titles, String representativeTitle) {
		this.name = name;
		this.titles = titles;
		this.representativeTitle = representativeTitle;
	}

	public String getName() {
		return name;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public String getRepresentativeTitle() {
		if (representativeTitle == null) {
			return "";
		} else {
			return representativeTitle;
		}
	}

	public void setRepresentativeTitle(int index) {
		if (ObjectAPI.isListHasIndex(titles, index)) {
			representativeTitle = titles.get(index);
		} else {
			representativeTitle = null;
		}
	}

	public void setMainPrefix(String prefix) {
		if (titles.contains(prefix)) {
			representativeTitle = prefix;
		} else {
			representativeTitle = null;
		}
	}
}
