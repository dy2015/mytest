package com.analyzeLogFile;

public class AnalyzeAccess {

	public void access(Analyze analyze, String castID, String ideaID,boolean tag) {
		long startTime = System.currentTimeMillis();

		analyze.startAnalyze(castID, ideaID, false, false,tag);// 开始分析容量:第一个标记表示是否按app版本统计（统计容量时暂不支持）；第二个标记,false表示统计容量,true表示统计曝光量
		analyze.printAccess();

		System.out.println("容量分析所花时间：" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
	}

	
}