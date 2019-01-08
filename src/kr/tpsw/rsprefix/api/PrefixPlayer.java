package kr.tpsw.rsprefix.api;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.inventory.Inventory;

import rsprefix.kr.tpsw.api.publica.ObjectAPI;

public class PrefixPlayer {

	private String name;
	private List<String> list;
	private String mainPrefix;
	private List<Inventory> invList = new LinkedList<Inventory>();
	public boolean needUpdateInv = false;

	public PrefixPlayer(String name) {
		this(name, new ArrayList<String>(), null);
	}

	public PrefixPlayer(String name, List<String> list, String mainPrefix) {
		this.name = name;
		this.list = list;
		this.mainPrefix = mainPrefix;
	}

	public List<Inventory> getInvList() {
		return invList;
	}

	public void updateInvList() {
		invList.clear();
		int i = (list.size() / 45) + 1;
		;
		for (int j = 1; j <= i; j++) {
			invList.add(InvAPI.createInv(this, j));
		}
	}

	public String getName() {
		return name;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getMainPrefix() {
		if (mainPrefix == null) {
			return "";
		} else {
			return mainPrefix;
		}
	}

	public void setMainPrefix(int index) {
		if (ObjectAPI.isListHasIndex(list, index)) {
			mainPrefix = list.get(index);
		} else {
			mainPrefix = null;
		}
	}

	public void setMainPrefix(String prefix) {
		if (list.contains(prefix)) {
			mainPrefix = prefix;
		} else {
			mainPrefix = null;
		}
	}
}
