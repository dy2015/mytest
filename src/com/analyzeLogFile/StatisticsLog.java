package com.analyzeLogFile;

public class StatisticsLog {
	private final static String castID = "1419840";// 必选：统计曝光时，投放ID
	private final static String cityID = "441900";// 可选时，必须为null:统计曝光时，某地域的城市ID,指定统计某地区的详细IP对应的曝光量。进一步判断这些Ip对应的extIP是多少，即实际投放在哪个地区

	private final static String ideaID = null;// 可选时，必须为null：统计容量时，素材ID
	public static long count = 0;// 统计容量时，统计一下日志中无效的行数量
	public static long ideaCount = 0;// 统计容量时，出现了数量大于两个的素材行数量
	private final static boolean tag = true;// 是否按素材分类进行某地区的容量统计(包括不按素材分类的总容量统计功能)

	private final static Analyze analyze = new Analyze();
	private final static AnalyzeShow analyzeShow = new AnalyzeShow();
	private final static AnalyzeAccess analyzeAccess = new AnalyzeAccess();

	private final static AnalyzeNiErSen analyzeNiErSen = new AnalyzeNiErSen();

	/**
	 * 
	 * 目前曝光量和容量不能同时统计
	 * 
	 */
	public static void main(String[] args) {
		// 加载原始地区相关文件：ip,城市id，地区area表等
		 analyze.init();
		
		 // 分析从区域定向IPA，到中间路由remote、xf等中间网关ip,到曝光ip，不一致的情况,从文件中筛选出该数据
		// analyze.analyzeRout();
		// 统计尼尔森的数据
		analyzeNiErSen.analyze();

		// 统计曝光量
//		 analyzeShow.show(analyze, castID, cityID, ideaID);
		
		 // 统计容量
		// analyzeAccess.access(analyze, castID, ideaID,tag);
	}
}
