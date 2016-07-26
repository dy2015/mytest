package com;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class TestMapEntry {
	private static Map<Integer, String> ccMap = new ConcurrentHashMap<Integer, String>();

	public static void main(String[] s) {
		Iterator<Entry<Integer, String>> iter = ccMap.entrySet().iterator();
		while (iter.hasNext()) {
			System.out.println("有下一个");
			Entry<Integer, String> e = iter.next();
			if (e == null) {
				System.out.println("下一个是空的");
				continue;
			}
		}
	}
}
