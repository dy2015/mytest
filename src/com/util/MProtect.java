package com.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class MProtect implements Serializable {
	private static final long serialVersionUID = 6877356852244377283L;

	private int id;
	private String name;
	private Set<String> manageTypeSet;
	private int parent;
	private int resourceId;
	private int color;
	private Set<Integer> castIds;
	private boolean isVip;
	private boolean isPaid;
	private Date endtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Set<Integer> getCastIds() {
		return castIds;
	}

	public void setCastIds(Set<Integer> castIds) {
		this.castIds = castIds;
	}

	public Set<String> getManageTypeSet() {
		return manageTypeSet;
	}

	public void setManageTypeSet(Set<String> manageTypeSet) {
		this.manageTypeSet = manageTypeSet;
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

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

}
