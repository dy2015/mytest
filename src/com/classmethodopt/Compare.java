package com.classmethodopt;

public class Compare {
	public synchronized static  boolean compareTime(double time, double expireTime) {
		if (time > expireTime) {
			return true;
		}
		return false;
	}
}
