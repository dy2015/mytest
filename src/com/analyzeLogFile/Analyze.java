package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Analyze {
	private final static String url = "C://Users//yt//Downloads//";
	private final static String ipUrl = url + "ip.txt";//目标地区对应的cityid，startip和endip
	private final static String regionIpUrl = url + "region.txt";
	private final static String areaUrl = url + "area.txt";
	private final static String routUrl = url + "rout.txt";
	private final static String wirteUrl = url + "write.txt";
	
	private final static int[][] fileCycle = { { 0, 9 }, { 10, 23 } };// 文件按小时拆分为0-9和10-23
	private final static int[][] fileNumber ={{41,45,100},{49,56,100},{60,69,100},{31,40,101},{61,70,103}};	
	
	public  static long ileageIP = 0;//全部日志中ext(ip)中不满足要求的IP对应的日志条数
	
	public  static long leageIP = 0;//占比最大的IP所对应的ext(ip)中满足要求的IP对应的日志条数
	public  static long isleageIP = 0;//占比最大的IP所对应的ext(ip)中不满足要求的IP对应的日志条数
	
	public static Map<String, String> ipMap = new HashMap<String, String>();// 目标地区源文件ip.txt:key=startIp;VALUE=endIp
	public static Map<String, String> ipCityMap = new HashMap<String, String>();// 目标地区源文件ip.txt:key=startIp;VALUE=endIp
	public static Map<String, String> regionIpMap = new HashMap<String, String>();// 非目标地区源文件region.txt:key=startIp;VALUE=endIp
	public static Map<String, String> regionCityIdMap = new HashMap<String, String>();// 非目标地区源文件region.txt:key=startIp;VALUE=cityId
	public static Map<String, String> areaMap = new HashMap<String, String>();// key=cityId;VALUE=名字
	public static TreeMap<String, String> routMap = new TreeMap<String, String>();// 全部ip源文件region.txt和ip.txt之和:key=startIp;VALUE=endIp
	public static TreeMap<String, String> routCityMap = new TreeMap<String, String>();// 全部ip源文件region.txt和ip.txt之和:key=startIp;VALUE=cityId
	
	
	
	private static Map<String, Integer> piaoyiMap = new HashMap<String, Integer>();//key=startIp;VALUE=曝光量,包括目标地区和非目标地区。目标地区统一由TARGET作为key
	private static Map<String, Integer> otherCityMap = new HashMap<String, Integer>();// key=cityId;VALUE=曝光量
	
	private static Map<String, Integer> accessMap = new HashMap<String, Integer>();// key=cityId;VALUE=容量;指定地区的容量
	private static Map<String, Integer> ideaAccessMap = new HashMap<String, Integer>();// key=cityId;VALUE=容量，指定地区的，按素材统计的容量
	
	private static Map<String, String> ipAndExtIpMap = new HashMap<String, String>();// key=Ip;VALUE=extIp,日志中指定投放的正式请求的ip，和ext中的ip
	private static Map<String, Integer> targetIpMap = new HashMap<String, Integer>();//key=startIp;VALUE=曝光量,将piaoyiMap中key为TARGET,即目标区ip和extIp不一致的曝光量统计出来
	private static Map<String, Integer> notKnowMap = new HashMap<String, Integer>();// key=Ip;VALUE=曝光量；指定统计某地区的详细IP，对应的曝光量。可进一步判断这些Ip对应的extIP是多少，即实际投放在哪个地区
	
	private static String[] piaoyiMaxIp = new String[1];// 发生漂移IP中，占比最大的IP
	private static Set<String> piaoyiMaxExtIpSet = new HashSet<>();// 发生漂移IP中，占比最大的IP,对应日志中ext的ip
	private static Map<String, Integer> appVersionMap = new HashMap<String, Integer>();// key=appVersion;VALUE=曝光量.将占比最大的IP，再按app版本进行统计
	
	public void init(){
		LoadSourceFile loadSourceFile = new LoadSourceFile();		
		loadSourceFile.loadFile(ipUrl, regionIpUrl, areaUrl, ipMap,ipCityMap, regionIpMap, regionCityIdMap, areaMap);// 加载源文件
		
		for (String key : ipMap.keySet()) {
			routMap.put(key, ipMap.get(key));
			routCityMap.put(key, ipCityMap.get(key));
		}
		for (String key : regionIpMap.keySet()) {
			routMap.put(key, regionIpMap.get(key));
			routCityMap.put(key, regionCityIdMap.get(key));
		}
	}

	/**
	 * 分析从区域定向IPA，到中间路由remote、xf等中间网关ip,到曝光ip，不一致的情况,从文件中筛选出该数据
	 */
	public void analyzeRout() {
		long startTime=System.currentTimeMillis();
		try {
			File txt = new File(wirteUrl);
			if (!txt.exists()) {
				txt.createNewFile();
			}
			byte bytes[] = new byte[512];
			FileOutputStream fos = new FileOutputStream(txt);
			
			List<String> list = new ArrayList<>();
			TreeMap<String,Map<String,Integer>> piaoyiCityMap=new TreeMap<>();
			
			
			long countRout = 0;
			long totalRow = 0;
			File file = new File(routUrl);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				totalRow++;
//				StringBuilder str = new StringBuilder();
				tempString = tempString.replace(" ", ",");
				String temp[] = tempString.split(",");
				String temp1[] = temp[temp.length - 1].split(":");
				String cityIDIP = null;
				String cityIDIPA = null;
				for (String key : routMap.keySet()) {
					if (IpUtil.ipExistsInRange(temp1[2], key + "-" + routMap.get(key))) {
						cityIDIP = routCityMap.get(key);
						break;
					} // end--if
				} // end--for
				for (String key : routMap.keySet()) {
					if (IpUtil.ipExistsInRange(temp1[4], key + "-" + routMap.get(key))) {
						cityIDIPA = routCityMap.get(key);
						break;
					} // end--if
				} // end--for

				if (!cityIDIP.equals(cityIDIPA)) {// 曝光城市和容量城市不一致
					countRout++;
					if (piaoyiCityMap.get(cityIDIPA) == null) {
						Map<String, Integer> map = new HashMap<>();
						map.put(cityIDIP, 1);
						piaoyiCityMap.put(cityIDIPA, map);
					} else {
						if (piaoyiCityMap.get(cityIDIPA).get(cityIDIP) == null) {
							piaoyiCityMap.get(cityIDIPA).put(cityIDIP, 1);
						} else {
							int num = piaoyiCityMap.get(cityIDIPA).get(cityIDIP);
							piaoyiCityMap.get(cityIDIPA).put(cityIDIP, ++num);
						}
					}
					
//					System.out.println("行号：" + countRout + "------" + tempString);
//					str.append("----------------------");
//					str.append("IPA:" + areaMap.get(cityIDIPA));
//					str.append("----------------------");
					
//					for (String key : routMap.keySet()) {
//						if (IpUtil.ipExistsInRange(temp1[temp1.length - 1], key + "-" + routMap.get(key))) {
//							String cityID = routCityMap.get(key);
//							str.append(temp1[temp1.length - 2] + ":" + areaMap.get(cityID));
//							break;
//						} // end--if
//					} // end--for
//					str.append(temp1[temp1.length - 2] + ":" + areaMap.get(cityIDIP));
//					str.append("----------------------");
//					str.append("IP:" + areaMap.get(cityIDIP));
//					System.out.println(str.toString());
//					list.add(str.toString());
				} // end--if
			} // end--while
//			System.out.println("集中：");
//			for (String s : list) {
//				System.out.println(s);
//				s += "\n";
//				bytes = s.getBytes();
//				int b = s.length();
//				fos.write(bytes, 0, b);
//				
//			}
			reader.close();
			fos.close();
			System.out.println("共有 " + countRout + " 个请求发生了城市间IP漂移。");
			System.out.println("漂移占比： " + (countRout * 100.0 / totalRow) + "%");
			System.out.println("漂移的城市分布： ");
			for (String ss : piaoyiCityMap.keySet()) {
				Map<String, Integer> map = piaoyiCityMap.get(ss);
				for (String key : map.keySet()) {
					if ("1000000000".equals(key)) {
						System.out.println(areaMap.get(ss) + " --> " + key + " -- > " + map.get(key));
					} else {
						System.out.println(areaMap.get(ss) + " --> " + areaMap.get(key) + " -- > " + map.get(key));
					}
				}
			}
			
			System.out.println("分析所花时间："+(System.currentTimeMillis()-startTime)/1000+"秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
/**
 * 1、分析指定投放区域的曝光量piaoyiMap，及ip和extIp不一致的ip对应的曝光量targetIpMap；统计非指定区域的ip曝光量piaoyiMap
 * 2、统计指定投放区域日志中ip和extIp不一致ipAndExtIpMap（说明：实际投放不是指定区域，是IP漂移导致有投放在指定区域的数据）
 * @param tag true:表示按app版本统计
 * @param flag true:表示统计曝光量，false:表示统计容量
 */
	public void startAnalyze(String castID, String ideaID, boolean tag, boolean flag,boolean ideaTag) {
		int countfile = 0;// 统计文件总个数
		AnalyzeLog analyzeLog = new AnalyzeLog();
		for (int[] f : fileCycle) {
			for (int j = f[0]; j <= f[1]; j++) {
				for (int[] h : fileNumber) {
					for (int i = h[0]; i <= h[1]; i++) {
						String urlTemp="";
						if(f[0]<=9){
							urlTemp = url + "0" + j + "_10." + String.valueOf(h[2]) + ".9." + i;
						}else{
							urlTemp = url + j + "_10." + String.valueOf(h[2]) + ".9." + i;
						}
						countfile++;
						if (flag) {// 统计曝光量
							if (tag) {// analyzebyApp
								analyzeLog.analyzeShow(urlTemp, castID, ipMap, piaoyiMap, otherCityMap, ipAndExtIpMap, targetIpMap, piaoyiMaxIp, piaoyiMaxExtIpSet, appVersionMap);
							} else {// analyzeShow
								analyzeLog.analyzeShow(urlTemp, castID, ipMap, piaoyiMap, otherCityMap, ipAndExtIpMap, targetIpMap, null, null, null);
							}
						} else {// 统计容量
							analyzeLog.analyzeAccess(ideaTag,urlTemp, ideaID, ipMap, ipCityMap, accessMap,ideaAccessMap);
						}

					}
				}
			}
		}
		System.out.println("共执行" + countfile + "个文件");
	}
	/**
	 * 1、分析漂移的IP(piaoyiMap中key为非target)，是属于那些城市otherCityMap---key=cityId;VALUE=曝光量
	 * 2、指定统计某地区的详细IP(notKnowMap)，对应的曝光量。可进一步判断这些Ip对应的extIP是多少，即实际投放在哪个地区
	 *  @param cityID 某地域的城市ID
	 */
	public void analyzePiaoyiMap(String cityID) {
		boolean flag = false;
		for (String ss : piaoyiMap.keySet()) {
			flag = false;
			if (!AnalyzeLog.TARGET.equals(ss)) {
				for (String key : regionIpMap.keySet()) {
					if (IpUtil.ipExistsInRange(ss, key + "-" + regionIpMap.get(key))) {
						String cityId = regionCityIdMap.get(key);
						if ((cityID != null) && cityId.equals(cityID)) {// 指定统计某地区的详细IP，对应的曝光量
							notKnowMap.put(ss, piaoyiMap.get(ss));
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
		if (cityID != null) {
			detailedAnalyzePiaoyiMap(cityID);
		}
	}
	/**
	 * 分析某地区的详细IP(notKnowMap)，对应的曝光量。进一步判断这些Ip对应的extIP是多少，即实际投放在哪个地区
	 */
	public void detailedAnalyzePiaoyiMap(String cityID) {
		int sum = 0;
		boolean ipAndExtIpTag = false;
		for (String key : notKnowMap.keySet()) {
			ipAndExtIpTag = false;
			sum += notKnowMap.get(key);
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
			System.out.println("ip:"+key +"(曝光城市id：" +cityID+") --> " + notKnowMap.get(key) + " ; extip: " + ipAndExtIpMap.get(key) + "(容量ip所属地区:" + name+ ",容量ip是否属于投放地区的：" + ipAndExtIpTag +")");
		}//end--for
		System.out.println("属于 "+cityID+" 地区的ip总曝光量:" + sum);
	}
	
	/**
	 * 展示分析结果
	 */
	public void printShow(String castID) {
		System.out.println("目标地区曝光量:" + piaoyiMap.get(AnalyzeLog.TARGET));
		System.out.println("漂移的城市数量:" + (piaoyiMap.size() - 1));
		/******************************************************************************/
		int otherNum = 0;
		long max = 0;
		String indexIp = "";
		for (String key : piaoyiMap.keySet()) {
			if (!AnalyzeLog.TARGET.equals(key)) {
				otherNum += piaoyiMap.get(key);
				if (max < piaoyiMap.get(key)) {
					max = piaoyiMap.get(key);
					indexIp = key;
				}
			}
		}
		System.out.println("发生漂移的IP总曝光量:" + otherNum);
		System.out.println("发生漂移的IP曝光量占比最大的ip--->曝光量:");
		System.out.println(indexIp + " --> " + piaoyiMap.get(indexIp));
		/******************************************************************************/
		System.out.println("发生漂移的地区对应的曝光量:");
		otherNum = 0;
		for (String key : otherCityMap.keySet()) {
			otherNum += otherCityMap.get(key);
			System.out.println(areaMap.get(key) + " --> " + key + " --> " + otherCityMap.get(key));
		}
		System.out.println("发生漂移的地区总曝光量:" + otherNum);
		/******************************************************************************/
		piaoyiMaxIp[0]=indexIp;//发生漂移的IP曝光量占比最大的ip
		if (piaoyiMaxIp.length > 0) {
			startAnalyze(castID,null,true,true,false);// 进行app版本的统计

			System.out.println("发生漂移IP中，占比最大的IP:"+indexIp+",按app版本统计的对应的曝光量:");
			otherNum = 0;
			for (String key : appVersionMap.keySet()) {
				otherNum += appVersionMap.get(key);
				System.out.println(key + " --> " + appVersionMap.get(key));
			}
			System.out.println("发生漂移IP中，占比最大的IP,按app版本的总曝光量:" + otherNum);
			/******************************************************************************/
			System.out.println("发生漂移IP中，占比最大的IP,对应日志中ext的ip:(ext条数："+piaoyiMaxExtIpSet.size()+")");
			boolean notTag = false;
			for (String s : piaoyiMaxExtIpSet) {
				 if(s.length() <7||s.length() >15){
					 isleageIP++;
						continue;
					}
				notTag = false;
				for (String key : ipMap.keySet()) {
					try{
					if (IpUtil.ipExistsInRange(s, key + "-" + ipMap.get(key))) {
						leageIP++;
						notTag = true;
						break;
					} // end--if
					}catch(Exception e){
						e.printStackTrace();
					}
				} // end--for
				System.out.println(s + " --> 是否属于投放目标地区ip：" + notTag);
			} // end--for
		} // end--if
		System.out.println("占比最大的IP所对应的ext(ip)中满足要求的IP对应的日志条数：" + leageIP);
		System.out.println("占比最大的IP所对应的ext(ip)中不满足要求的IP对应的日志条数：" + isleageIP);
		System.out.println("全部日志中ext(ip)中不满足要求的IP对应的日志条数：" + ileageIP);
	}//end--print
	
	public void printAccess() {
		long num = 0;
		System.out.println("各地区容量:");
		for (String key : accessMap.keySet()) {
			System.out.println(areaMap.get(key) + " --> " + key + " --> " + accessMap.get(key));
			num += accessMap.get(key);
		}
		System.out.println("总容量:" + num);
		System.out.println("共有无效行数：" + StatisticsLog.count);
		System.out.println("共有出现了数量大于两个的素材行数量：" + StatisticsLog.ideaCount);
		System.out.println("按素材分类，对某地区的容量统计结果：");
		num=0;
		for (String key : ideaAccessMap.keySet()) {
			System.out.println(key + " --> " + ideaAccessMap.get(key));
			num += ideaAccessMap.get(key);
		}
		System.out.println("该地区的所有素材的总容量:" + num);
	}
}
