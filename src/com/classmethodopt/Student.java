package com.classmethodopt;

public class Student {
	private double expireTime;

	public double getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(double expireTime) {
		this.expireTime = expireTime;
	}

	public boolean compareTime(double time) {
		if (time > expireTime) {
			return true;
		}
		return false;
	}
}
