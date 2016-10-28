package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.Set;

public class AnalyzeLog {
	public final static String TARGET = "target";

	// 统计分析曝光量
	public void analyzeShow(String fileName, String castId, Map<String, String> ipMap, Map<String, Integer> piaoyiMap, Map<String, Integer> otherCityMap, Map<String, String> ipAndExtIpMap, Map<String, Integer> targetIpMap, String[] piaoyiMaxIp, Set<String> piaoyiMaxExtIpSet,
			Map<String, Integer> appVersionMap) {// 以行为单位读取文件内容，一次读一整行
		boolean tag = true;
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split("\t");
				if (temp.length < 30) {
					continue;
				}
				if (castId.equals(temp[3])) {
					String[] tempIpSet1 = temp[temp.length - 1].split(",");
					String[] tempIpSet2 = tempIpSet1[tempIpSet1.length - 1].split(":");
					ipAndExtIpMap.put(temp[2], tempIpSet2[tempIpSet2.length - 1]);
					if (piaoyiMaxIp != null) {// analyzebyApp
						for (int i = 0; i < piaoyiMaxIp.length; i++) {
							if (piaoyiMaxIp[i].equals(temp[2])) {
								if (appVersionMap.get(temp[22]) == null) {
									appVersionMap.put(temp[22], 1);
								} else {
									int count = appVersionMap.get(temp[22]);
									appVersionMap.put(temp[22], ++count);
								}
								piaoyiMaxExtIpSet.add(tempIpSet2[tempIpSet2.length - 1]);
							}
						} // end--for
					} else {// analyzeShow
						tag = true;
						/**************** 判断temp[2]的值是否在投放IP区域内**********************/
						for (String key : ipMap.keySet()) {
							if (IpUtil.ipExistsInRange(temp[2], key + "-" + ipMap.get(key))) {
								if (piaoyiMap.get(TARGET) == null) {
									piaoyiMap.put(TARGET, 1);
								} else {
									int num = piaoyiMap.get(TARGET);
									piaoyiMap.put(TARGET, ++num);
								}
								if (!temp[temp.length - 1].contains(temp[2])) {
									if (targetIpMap.get(temp[2]) == null) {
										targetIpMap.put(temp[2], 1);
									} else {
										int num = targetIpMap.get(temp[2]);
										targetIpMap.put(temp[2], ++num);
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
						} // end--if
					}
				} // end--if
			} // end--while
			reader.close();
		} catch (Exception e) {
			System.out.println(fileName);
			e.printStackTrace();
			System.out.println("解析文件:" + fileName + " 失败");
		}
	}

	// 统计分析容量
	public void analyzeAccess(boolean tag,String fileName, String ideaID, Map<String, String> ipMap, Map<String, String> ipCityMap, Map<String, Integer> accessMap,Map<String, Integer> ideaAccessMap) {// 以行为单位读取文件内容，一次读一整行
		System.out.println(fileName);
		long number = 0;// 标识文件的第几行
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				number++;
				String temp[] = tempString.split("\t");
				if (temp.length < 3 || temp[2].length() < 7) {
					StatisticsLog.count++;
					System.out.println(fileName);
					System.out.println(temp[2]);
					System.out.println(temp[2].length());
					System.out.println("number=" + number);
					continue;
				}
				String[] ispTemp1=temp[temp.length-1].split(";");
				String[] ispTemp2=ispTemp1[ispTemp1.length-2].split(":");
				String isp=ispTemp2[1];
				if(!"CMCC".equals(isp)){//过滤非isp！=CMCC的统计
					continue;
				}
				
				String[] ideaList= temp[10].split(",");
				if (ideaList.length > 2) {
					StatisticsLog.ideaCount++;
					System.out.println("出现了数量大于两个的素材");//正常是只有一个素材和一个空字符串的两个长度的情况
					continue;
				}
				
				if (ideaID != null) {//按指定素材id统计
					if(ideaList.length==2){
						if (ideaID.equals(ideaList[0])) {
							access(temp[2], ipMap, ipCityMap, accessMap, null,null);
							break;
						} // end--if
					}// end--if
				} else {// 不按指定素材id统计
					if (tag) {// 进行按素材分类，进行统计某地区的容量
						String key="";
						if("".equals(ideaList[0])){
							key="isEmpty";
						}else{
							key=ideaList[0];
						}
						access(temp[2], ipMap, ipCityMap, accessMap, ideaAccessMap,key );
					} else {//统计某地区的总容量
						access(temp[2], ipMap, ipCityMap, accessMap, null, null);
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(fileName);
			e.printStackTrace();
			System.out.println("解析文件失败");
		}
	}

	public void access(String ip, Map<String, String> ipMap, Map<String, String> ipCityMap, Map<String, Integer> accessMap,Map<String, Integer> ideaAccessMap,String ideaID) {
		for (String key : ipMap.keySet()) {
			if (IpUtil.ipExistsInRange(ip, key + "-" + ipMap.get(key))) {
				String cityID = ipCityMap.get(key);
				if (accessMap.get(cityID) == null) {
					accessMap.put(cityID, 1);
				} else {
					int num = accessMap.get(cityID);
					accessMap.put(cityID, ++num);
				}
				if(ideaAccessMap!=null){
					if (ideaAccessMap.get(ideaID) == null) {
						ideaAccessMap.put(ideaID, 1);
					} else {
						int num = ideaAccessMap.get(ideaID);
						ideaAccessMap.put(ideaID, ++num);
					}
				}
				break;
			} // end--if
		} // end--for
	}
	
}