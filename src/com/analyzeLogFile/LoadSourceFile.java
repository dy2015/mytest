package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * 源文件统一放在"C://Users//yt//Downloads//"目录下;
 * 1、加载reiogn表中 目标地区源文件ip.txt的cityid,startip和endip到ipMap; 
 * 2、加载reiogn表中 非目标地区源文件region.txt的cityid,startip和endip到regionIpMap和regionCityIdMap;
 * 3、加载area表中的全部数据，即源文件area.txt到areaMap.
 **/
public class LoadSourceFile {
	
	public void loadFile(String ipUrl, String regionIpUrl, String areaUrl, Map<String, String> ipMap, Map<String, String> ipCityMap, Map<String, String> regionIpMap, Map<String, String> regionCityIdMap, Map<String, String> areaMap) {
		load(ipUrl, ipMap, ipCityMap, null);
		load(regionIpUrl, regionIpMap, regionCityIdMap, null);
		load(areaUrl, null, null, areaMap);
	}
	
	public void load(String fileName,Map<String, String> map1, Map<String, String> map2, Map<String, String> map3) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split("\t");
				if (map3 == null) {
					map1.put(temp[1], temp[2]);
					map2.put(temp[1], temp[0]);
				} else {//加载地区名称
					map3.put(temp[0], temp[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
