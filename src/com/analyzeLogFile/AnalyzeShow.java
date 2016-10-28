package com.analyzeLogFile;

public class AnalyzeShow {
	
	
	
	public  void show(Analyze analyze,String castID,String cityID,String ideaID) {
        long startTime=System.currentTimeMillis();
        
		analyze.startAnalyze(castID,ideaID,false,true,false);//开始分析曝光量:第一个标记表示是否按app版本统计；第二个标记表示是否统计曝光量
		analyze.analyzePiaoyiMap(cityID);//传空值：表示不分析漂移地区的详细IP对应的曝光量，即实际投放地域
		analyze.printShow(castID);//打印展示结果
		
		System.out.println("曝光分析所花时间："+(System.currentTimeMillis()-startTime)/1000+"秒");
	}
}
