package com.analyzeLogFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortMapByValue {

	public static List<String> sort(Map<String, Integer> map) {

		List<String> list = new ArrayList<>();// 按出现value值从大到小排序
		long size = map.size();
		for (int i = 0; i < size; i++) {
			long max = 0;
			String index = "";
			for (String key : map.keySet()) {
				if (max < map.get(key)) {
					index = key;
					max = map.get(key);
				}
			} // end--for
			list.add(index + "," + map.get(index));
			map.remove(index);
		} // end--for
		return list;
	}

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<>();
		map.put("1", 99);
		map.put("2", 60);
		map.put("3", 80);
		map.put("4", 70);
		List<String> list = sort(map);
		for (String s : list) {
			System.out.println(s);
		}
	}
}
