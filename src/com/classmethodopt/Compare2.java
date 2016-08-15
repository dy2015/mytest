package com.classmethodopt;

public class Compare2 {
	private static boolean tag = true;

	public static boolean compareTime(double time, double expireTime) {	
		if (time > expireTime) {
			tag = true;
		} else {
			tag = false;
		}
		return tag;
	}
}
