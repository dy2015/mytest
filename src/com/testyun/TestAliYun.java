package com.testyun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.analyzeLogFile.IpUtil;

public class TestAliYun {
	static Map<String, String> regionIpMap = new HashMap<String, String>();// key=cityId;VALUE=startId-endId
	static Map<String, String> areaMap = new HashMap<String, String>();// key=cityId;VALUE=名字
	static Map<String, Set<String>> ipMap = new HashMap<String, Set<String>>();// key=castId;VALUE=ip
	static Map<String, Integer> resultMap = new HashMap<String, Integer>();// key=城市;VALUE=ip数量

	public static void loadIPData(String fileName, Map<String, String> map) {// 加载ip
		int count = 0;
		StringBuilder stemp=new StringBuilder("select direct_value from cast_area where direct_value in(");
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split(",");
				String temp2[] = temp[0].split("id=");
				String temp3 = temp2[1];
				count++;
				stemp.append(temp3).append(",");
				System.out.println(temp3);
			}
			System.out.println("count:" + count);
			stemp.append(") ");
			System.out.println(stemp.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadIPData2(String fileName, Map<String, Set<String>> map) {// 加载ip
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			Set<String> ip1 = new HashSet<>();
			Set<String> ip2 = new HashSet<>();
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split("\t");
				if ("1565616".equals(temp[1])) {
					ip1.add(temp[0]);
				} else {
					ip2.add(temp[0]);
				}
			}
			map.put("1565616", ip1);
			map.put("1554504", ip2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void compara() {
		for (String key1 : ipMap.keySet()) {
			for (String value : ipMap.get(key1)) {

				for (String key : regionIpMap.keySet()) {
					if (IpUtil.ipExistsInRange(value, regionIpMap.get(key))) {
						int count = 0;
						if (resultMap.get(areaMap.get(key)) != null) {
							count = resultMap.get(areaMap.get(key));
						}
						resultMap.put(areaMap.get(key), ++count);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		String areaUrl = "G://3.txt";
		String regionUrl = "G://region.txt";
		String dataUrl = "G://ip.2017-01-24-14.txt";
		loadIPData(areaUrl, areaMap);
	}

}
