package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class analyzeFileIP {
	Map<String, Integer> map = new HashMap<String, Integer>();
	static Map<String, String> ipMap = new HashMap<String, String>();
	static Map<String, String> thirdIpMap = new HashMap<String, String>();
	static Map<String, String> regionIpMap = new HashMap<String, String>();// key=startId;VALUE=endId
	static Map<String, String> regionCityIdMap = new HashMap<String, String>();// key=startId;VALUE=cityId
	static Map<String, String> areaMap = new HashMap<String, String>();// key=cityId;VALUE=名字
	static Map<String, Integer> piaoyiMap = new HashMap<String, Integer>();
	static Map<String, Integer> otherCityMap = new HashMap<String, Integer>();// key=cityId;VALUE=曝光量
	static Map<String, Integer> appVersionMap = new HashMap<String, Integer>();// key=appVersion;VALUE=曝光量
	static String[] notIp = {};// 发生漂移IP中，占比最大的IP
	static boolean[] notIpTag = { true, true, true, true, true };// 未包含在ip库内的IP
	static Set<String> notIpSet = new HashSet();// 发生漂移IP中，占比最大的IP,对应日志中ext的ip
	static Map<String, Integer> notKnowMap = new HashMap<String, Integer>();// key=Ip;VALUE=曝光量
	static Map<String, String> ipAndExtIpMap = new HashMap<String, String>();// key=Ip;VALUE=extIp

	static Map<String, Integer> targetTempMap = new HashMap<String, Integer>();

	static long targetNUm = 0;// 表示投放在指定地区的量
	final static String TARGET = "target";
	static long piaoyi = 0;

	public static void main(String[] agrs) {
		String url = "C://Users//yt//Downloads//";
		String id = "1417846";
		String ipUrl = "C://Users//yt//Downloads//ip.txt";
		String regionIpUrl = "C://Users//yt//Downloads//region.txt";
		String thirdIpUrl = "C://Users//yt//Downloads//Shen.csv";
		String areaUrl = "C://Users//yt//Downloads//area.txt";
		analyzeFileIP u = new analyzeFileIP();
		u.loadIPData(ipUrl, ipMap);
		u.loadRegionIPData(regionIpUrl, regionIpMap, regionCityIdMap);
		u.loadAreaData(areaUrl, areaMap);
		// u.loadThirdIPData(thirdIpUrl);
		// u.compareIp(ipMap, thirdIpMap);
		u.updateAreaData(url, id);
	}

	public void loadAreaData(String fileName, Map<String, String> map) {// 加载
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split("\t");
				map.put(temp[0], temp[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadRegionIPData(String fileName, Map<String, String> map1, Map<String, String> map2) {// 加载ip
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split("\t");
				map1.put(temp[1], temp[2]);
				map2.put(temp[1], temp[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void compareIp(Map<String, String> ipMap, Map<String, String> thirdIpMap) {// 比较我方和第三方的ip
		System.out.println("我方共有IP " + ipMap.size() + "个，第三方共有IP " + thirdIpMap.size() + "个.");
		int count = 0;// 统计我方未在第三方出现的IP
		boolean tag = false;
		for (String key : ipMap.keySet()) {
			tag = false;
			for (String thirdKey : thirdIpMap.keySet()) {
				if (key.equals(thirdKey)) {
					tag = true;
					break;
				}
			}
			if (!tag) {
				count++;
			}
		}
		System.out.println("我方未在第三方出现的IP有 " + count + " 个.");
		for (String key : ipMap.keySet()) {
			if (!ipMap.get(key).equals(thirdIpMap.get(key))) {
				System.out.println("我方和第三方出现的IP不一样." + "myIP(" + key + "," + ipMap.get(key) + "),thirdIp(" + key + "," + thirdIpMap.get(key) + ").");
				break;
			}
		}
		System.out.println("ip分析结束.");

	}

	public void loadThirdIPData(String fileName) {// 加载第三方的ip
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split(",");
				thirdIpMap.put(temp[0], temp[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadIPData(String fileName, Map<String, String> map) {// 加载ip
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split("\t");
				map.put(temp[1], temp[2]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateAreaData(String url, String id) {
		int countfile = 0;
		int totalIMP = 0;
		int standIMP = 0;
		long start = System.currentTimeMillis();
		for (int j = 0; j <= 9; j++) {
			for (int i = 41; i <= 45; i++) {
				countfile++;
				readLogFile(url + "0" + j + "_10.100.9." + i, id);
			}

			for (int i = 49; i <= 56; i++) {
				countfile++;
				readLogFile(url + "0" + j + "_10.100.9." + i, id);
			}
			for (int i = 60; i <= 69; i++) {
				countfile++;
				readLogFile(url + "0" + j + "_10.100.9." + i, id);
			}
			for (int i = 31; i <= 40; i++) {
				countfile++;
				readLogFile(url + "0" + j + "_10.101.9." + i, id);
			}
			for (int i = 61; i <= 70; i++) {
				countfile++;
				readLogFile(url + "0" + j + "_10.103.9." + i, id);
			}
		}
		for (int j = 10; j <= 23; j++) {
			for (int i = 41; i <= 45; i++) {
				countfile++;
				readLogFile(url + j + "_10.100.9." + i, id);
			}

			for (int i = 49; i <= 56; i++) {
				countfile++;
				readLogFile(url + j + "_10.100.9." + i, id);
			}
			for (int i = 60; i <= 69; i++) {
				countfile++;
				readLogFile(url + j + "_10.100.9." + i, id);
			}
			for (int i = 31; i <= 40; i++) {
				countfile++;
				readLogFile(url + j + "_10.101.9." + i, id);
			}
			for (int i = 61; i <= 70; i++) {
				countfile++;
				readLogFile(url + j + "_10.103.9." + i, id);
			}
		}
		// readLogFile(url +"10_10.101.9.32", id);
		System.out.println("共执行" + countfile + "个文件");
		for (String key : map.keySet()) {
			System.out.println(key + " --> " + map.get(key));
			totalIMP += map.get(key);
			if (map.get(key) <= 3) {
				standIMP += map.get(key);
			}
		}

		System.out.println("targetTempMap的长度：" + targetTempMap.size());
		for (String key : map.keySet()) {
			System.out.println(key + " --> " + map.get(key));
			totalIMP += map.get(key);
			if (map.get(key) <= 3) {
				standIMP += map.get(key);
			}
		}

		// 分析其他非目标地区ip区域
//		for (String ss : targetTempMap.keySet()) {
//			notKnowMap.put(ss, targetTempMap.get(ss));
//		}

		/*********************
		 * 判断piaoyiMap的值所属区域
		 **********************/
		boolean flag = false;
		for (String ss : piaoyiMap.keySet()) {
			flag = false;
			if (!TARGET.equals(ss)) {
				for (String key : regionIpMap.keySet()) {
					if (IpUtil.ipExistsInRange(ss, key + "-" + regionIpMap.get(key))) {
						String cityId = regionCityIdMap.get(key);
						if (cityId.equals("1000000000")) {
							 notKnowMap.put(ss,piaoyiMap.get(ss));
						}
						if (otherCityMap.get(cityId) == null) {
							otherCityMap.put(cityId, piaoyiMap.get(ss));
						} else {
							int num = otherCityMap.get(cityId);
							otherCityMap.put(cityId, piaoyiMap.get(ss) + num);
						}
						flag = true;
						break;

					} // end--if
				} // end--for
				if (!flag) {
					System.out.println("未包含在ip库内的IP:" + ss + "，对应的曝光量：" + piaoyiMap.get(ss));
				}

			} // end--if
		} // end--for
		System.out.println("用户数:" + map.size());
		System.out.println("totalIMP:" + totalIMP);
		System.out.println("standIMP:" + standIMP);
		System.out.println("漂移量:" + piaoyi);
		System.out.println("目标地区曝光量:" + piaoyiMap.get(TARGET));
		System.out.println("漂移城市数量:" + (piaoyiMap.size() - 1));
		int otherNum = 0;
		System.out.println("发生漂移的IP对应的曝光量:");
		for (String key : piaoyiMap.keySet()) {
			if (!TARGET.equals(key)) {
				otherNum += piaoyiMap.get(key);
				System.out.println(key + " --> " + piaoyiMap.get(key));
			}
		}
		System.out.println("发生漂移的IP总曝光量:" + otherNum);
		System.out.println("发生漂移的地区对应的曝光量:");
		otherNum = 0;
		for (String key : otherCityMap.keySet()) {
			otherNum += otherCityMap.get(key);
			System.out.println(areaMap.get(key) + " --> " + key + " --> " + otherCityMap.get(key));
		}
		System.out.println("发生漂移的地区总曝光量:" + otherNum);
		System.out.println("发生漂移IP中，占比最大的IP,再按app版本统计的对应的曝光量:");
		otherNum = 0;
		for (String key : appVersionMap.keySet()) {
			otherNum += appVersionMap.get(key);
			System.out.println(key + " --> " + appVersionMap.get(key));
		}
		System.out.println("发生漂移IP中，占比最大的IP,再按app版本的总曝光量:" + otherNum);

		System.out.println("发生漂移IP中，占比最大的IP,对应日志中ext的ip:");
		boolean notTag = false;
		for (String s : notIpSet) {
			notTag = false;
			for (String key : ipMap.keySet()) {
				if (IpUtil.ipExistsInRange(s, key + "-" + ipMap.get(key))) {
					notTag = true;
					break;
				} // end--if
			} // end--for
			System.out.println(s + " --> 是否属于投放目标地区ip：" + notTag);
		}
		System.out.println("发生漂移IP中，占比最大的IP,对应日志中ext的ip总量:" + notIpSet.size());
		System.out.println("属于1000000000地区的ip信息:==========================================================================================");

		File txt = new File("G://2.txt");
		try {
			if (!txt.exists()) {
				txt.createNewFile();
			}
			byte bytes[] = new byte[512];
			FileOutputStream fos = new FileOutputStream(txt);
		
		otherNum = 0;
		boolean ipAndExtIpTag = false;
		for (String key : notKnowMap.keySet()) {
			ipAndExtIpTag = false;
			otherNum += notKnowMap.get(key);
			for (String ss : ipMap.keySet()) {
				if (IpUtil.ipExistsInRange(ipAndExtIpMap.get(key), ss + "-" + ipMap.get(ss))) {
					ipAndExtIpTag = true;
					break;
				}
			}
			String cityId = "";
			String name = "";
			if (!ipAndExtIpTag) {
				for (String ss : regionIpMap.keySet()) {
					if (IpUtil.ipExistsInRange(ipAndExtIpMap.get(key), ss + "-" + regionIpMap.get(ss))) {
						cityId = regionCityIdMap.get(ss);
						name = areaMap.get(cityId);
						break;
					}
				}
			}
//			System.out.println(key + " --> " + notKnowMap.get(key) + "++++++++++++++++++++++ extip: " + ipAndExtIpMap.get(key) + "是否属于投放地区的ip：" + ipAndExtIpTag + ";所属地区:" + name);
			// 写文件
//			String tepStr=key+"\t"+ipAndExtIpMap.get(key)+"\n";
//			bytes = tepStr.getBytes(); // 新加的
//			int b = tepStr.length(); // 改
//			fos.write(bytes, 0, b);
		} // end--for
		fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("属于1000000000地区的ip总曝光量:" + otherNum);
		System.out.println("notKnowMap长度" + notKnowMap.size());
		System.out.println("分析时间:" + (System.currentTimeMillis() - start));
	}

	public void readLogFile(String fileName1, String castId) {// 以行为单位读取文件内容，一次读一整行
		boolean tag = true;
		File file1 = new File(fileName1);
		BufferedReader reader1 = null;
		try {
			reader1 = new BufferedReader(new FileReader(file1));
			String tempString = null;
			while ((tempString = reader1.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split("\t");
				if (temp.length < 30) {
					continue;
				}
				if (castId.equals(temp[3])) {
					String[] tempIpSet1 = temp[temp.length - 1].split(",");
					String[] tempIpSet2 = tempIpSet1[tempIpSet1.length - 1].split(":");
					ipAndExtIpMap.put(temp[2], tempIpSet2[tempIpSet2.length - 1]);
					tag = true;
					for (int i = 0; i < notIp.length; i++) {
						if (notIp[i].equals(temp[2]) && notIpTag[i]) {
							// notIpTag[i]=false;
							if (appVersionMap.get(temp[22]) == null) {
								appVersionMap.put(temp[22], 1);
							} else {
								int count = appVersionMap.get(temp[22]);
								appVersionMap.put(temp[22], ++count);
							}
							notIpSet.add(tempIpSet2[tempIpSet2.length - 1]);
							// System.out.println("file name:" + fileName1 +
							// "；real ip:" + temp[2]+"；param
							// ip:"+temp[temp.length - 1]);
						}
					}
					/**********************
					 * 判断temp[2]的值是否在投放IP区域内
					 **********************/
					for (String key : ipMap.keySet()) {
						if (IpUtil.ipExistsInRange(temp[2], key + "-" + ipMap.get(key))) {
							if (piaoyiMap.get(TARGET) == null) {
								piaoyiMap.put(TARGET, 1);
							} else {
								int num = piaoyiMap.get(TARGET);
								piaoyiMap.put(TARGET, ++num);
							}
							if (!temp[temp.length - 1].contains(temp[2])) {
								if (targetTempMap.get(temp[2]) == null) {
									targetTempMap.put(temp[2], 1);
								} else {
									int num = targetTempMap.get(temp[2]);
									targetTempMap.put(temp[2], ++num);
								}
							}

							tag = false;
							break;

						} // end--if
					} // end--for
					if (tag) {// temp[2]的值不在投放IP区域内
						if (piaoyiMap.get(temp[2]) == null) {
							piaoyiMap.put(temp[2], 1);
						} else {
							int num = piaoyiMap.get(temp[2]);
							piaoyiMap.put(temp[2], ++num);
						}
					}
				}
			}
			reader1.close();
		} catch (Exception e) {
			System.out.println(fileName1);
			e.printStackTrace();
			System.out.println("解析文件,存入数据库area失败");
		}
	}
}
