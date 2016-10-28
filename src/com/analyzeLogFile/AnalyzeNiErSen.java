package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzeNiErSen {
	private final static String root = "C://Users//yt//Downloads//";
	private final static String url = root + "niersen.txt";
	private final static String cityUrl = root + "niersenCity.txt";
	private final static String writeUrl = root + "writeniersen.txt";

	private static Map<String, String> niersenipCitymap = new HashMap<String, String>();// key=ip;VALUE=城市
	private static Map<String, String> myipCitymap = new HashMap<String, String>();// key=ip;VALUE=城市
	private static Map<String, Integer> map = new HashMap<String, Integer>();// key=ip;VALUE=出现次数
	private static List<String> list = new ArrayList<>();// 按出现次数排序

	public void analyze() {
		long startTime = System.currentTimeMillis();
		try {

			File file = new File(url);
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;

			File file2 = new File(cityUrl);
			BufferedReader reader2 = null;
			reader2 = new BufferedReader(new FileReader(file2));
			String tempString2 = null;

			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				tempString2 = reader2.readLine();
				niersenipCitymap.put(tempString, tempString2);
				if (map.get(tempString) == null) {
					map.put(tempString, 1);
				} else {
					int num = map.get(tempString);
					map.put(tempString, ++num);
				}
			} // end--while
			long count = 0;
			for (String key : map.keySet()) {
				count += map.get(key);
			}
			System.out.println("总曝光量：" + count);

			campareMap();

			StringBuilder bulid = new StringBuilder();
			for (String str : list) {
				System.out.println(str);
				bulid.append(str);
			}
			
			WriteFile.write(writeUrl, bulid.toString());

			System.out.println("不一样的ip数量：" + list.size());
			reader2.close();
			reader.close();

			campareNiErSenAndMyMap();

			System.out.println("分析所花时间：" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void campareMap() {
		List<String> listTemp = SortMapByValue.sort(map);
		for (String str : listTemp) {
			String[] temp = str.split(",");
			String cityIDIPA = "";
			for (String key : Analyze.routMap.keySet()) {
				if (IpUtil.ipExistsInRange(temp[0], key + "-" + Analyze.routMap.get(key))) {
					cityIDIPA = Analyze.routCityMap.get(key);
					break;
				}
			}
			list.add(temp[0] + " --> " + temp[1] + " --> 尼尔森地域：" + niersenipCitymap.get(temp[0]) + "---> 优酷地域：" + Analyze.areaMap.get(cityIDIPA) + "\n");
			myipCitymap.put(temp[0], Analyze.areaMap.get(cityIDIPA));
		} // end--for
	}

	public void campareNiErSenAndMyMap() {
		for (String key : niersenipCitymap.keySet()) {
			if (!niersenipCitymap.get(key).contains(myipCitymap.get(key))) {
				System.out.println("尼尔森:(" + key + "," + niersenipCitymap.get(key) + ");我方：(" + key + "," + myipCitymap.get(key) + ")");
			}
		}
	}
}
