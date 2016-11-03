package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class CastYouxianji {
	private final static String url = "C://Users//yt//Downloads//";
	private final static String castUrl = url + "youxianji.txt";
	private final static String orderUrl = url + "orderID.txt";
	private final static String sortUrl = url + "sort.txt";
	private final static String sortOrderUrl = url + "sortOrder.txt";
	public static Map<String, String> cast_orderMap = new HashMap<String, String>();// castID对应订单ID

	public static void main(String[] args) {
//		loadCastUrl();
		castSort();
	}

	public static void loadCastUrl() {
		try {
			File file = new File(castUrl);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tempString = null;

			int count = 0;
			StringBuilder str = new StringBuilder();
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				count++;
				str.append(tempString).append(",");
				if (count % 500 == 0) {
					System.out.println(str);
					str = new StringBuilder();
					count=0;
				}
			} // end--while
			System.out.println(str);
			System.out.println(count);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void castSort() {
		try {
			File file1 = new File(castUrl);
			BufferedReader reader1 = new BufferedReader(new FileReader(file1));
			String tempString = null;

			File file2 = new File(orderUrl);
			BufferedReader reader2 = new BufferedReader(new FileReader(file2));

			while ((tempString = reader1.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				cast_orderMap.put(tempString, reader2.readLine());
			} // end--while

			File file3 = new File(sortUrl);
			BufferedReader reader3 = new BufferedReader(new FileReader(file3));

			while ((tempString = reader3.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				WriteFile.write(sortOrderUrl, cast_orderMap.get(tempString));
			} // end--while
			System.out.println("分析结束");
			reader1.close();
			reader2.close();
			reader3.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
