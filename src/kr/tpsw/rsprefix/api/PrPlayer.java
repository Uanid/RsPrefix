package kr.tpsw.rsprefix.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rsprefix2.kr.tpsw.api.publica.ObjectAPI;

public class PrPlayer {

	private UUID uuid;
	private List<String> titles;
	private String representativeTitle;
	public PrPlayer(String name) {
	}

	public PrPlayer(@NotNull UUID uuid, @NotNull List<String> titles, @Nullable String representativeTitle) {
		this.titles = titles;
		this.representativeTitle = representativeTitle;
	}

	public UUID getUuid() {
		return uuid;
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
}
