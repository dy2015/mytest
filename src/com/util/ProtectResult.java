package com.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProtectResult {
	private List<Integer> protectIds = new ArrayList<Integer>();
	private Set<Integer> whiteCastIds; // null时没有设置白名单
	private Set<Integer> blackCastIds = new HashSet<Integer>();
	private boolean isVip;
    private boolean isPaid;

	public List<Integer> getProtectIds() {
		return protectIds;
	}

	public void setProtectIds(List<Integer> protectIds) {
		this.protectIds = protectIds;
	}

	public Set<Integer> getWhiteCastIds() {
		return whiteCastIds;
	}

	public void setWhiteCastIds(Set<Integer> whiteCastIds) {
		this.whiteCastIds = whiteCastIds;
	}

	public Set<Integer> getBlackCastIds() {
		return blackCastIds;
	}

	public void setBlackCastIds(Set<Integer> blackCastIds) {
		this.blackCastIds = blackCastIds;
	}

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

}
