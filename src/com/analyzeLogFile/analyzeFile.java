package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class analyzeFile {
	Map<String, Integer> map = new HashMap<String, Integer>();

	public static void main(String[] agrs) {
		analyzeFile u = new analyzeFile();
		u.updateAreaData();
	}

	public void updateAreaData() { 
		int countfile = 0;
		String url = "C://Users//yt//Downloads//";
		String id="1416289";
		int totalIMP=0;
		int standIMP=0;
		long start=System.currentTimeMillis();
		for (int j = 0; j <= 9; j++) {
			for (int i = 41; i <= 45; i++) {
				countfile++;
				readFile(url + "0" + j + "_10.100.9." + i, "G://1.txt", id);
			}

			for (int i = 49; i <= 56; i++) {
				countfile++;
				readFile(url + "0" + j + "_10.100.9." + i, "G://1.txt", id);
			}
			for (int i = 60; i <= 69; i++) {
				countfile++;
				readFile(url + "0" + j + "_10.100.9." + i, "G://1.txt", id);
			}
			for (int i = 31; i <= 40; i++) {
				countfile++;
				readFile(url + "0" + j + "_10.101.9." + i, "G://1.txt", id);
			}
			for (int i = 61; i <= 70; i++) {
				countfile++;
				readFile(url + "0" + j + "_10.103.9." + i, "G://1.txt", id);
			}
		}
		for (int j = 10; j <= 23; j++) {
			for (int i = 41; i <= 45; i++) {
				countfile++;
				readFile(url + j + "_10.100.9." + i, "G://1.txt", id);
			}

			for (int i = 49; i <= 56; i++) {
				countfile++;
				readFile(url + j + "_10.100.9." + i, "G://1.txt", id);
			}
			for (int i = 60; i <= 69; i++) {
				countfile++;
				readFile(url + j + "_10.100.9." + i, "G://1.txt", id);
			}
			for (int i = 31; i <= 40; i++) {
				countfile++;
				readFile(url + j + "_10.101.9." + i, "G://1.txt", id);
			}
			for (int i = 61; i <= 70; i++) {
				countfile++;
				readFile(url + j + "_10.103.9." + i, "G://1.txt", id);
			}
		}
//		readFile(url +"10_10.101.9.32", "G://1.txt", id);
		System.out.println("共执行" + countfile + "个文件");
		for (String key : map.keySet()) {
			System.out.println(key + " --> " + map.get(key));
			totalIMP+=map.get(key);
			if(map.get(key)<=3){
				standIMP+=map.get(key);
			}
		}
		System.out.println("用户数:" + map.size());
		System.out.println("totalIMP:" + totalIMP);
		System.out.println("standIMP:" + standIMP);
		System.out.println("分析时间:" + (System.currentTimeMillis()-start));
	}

	public void readFile(String fileName1, String fileName2, String castId) {// 以行为单位读取文件内容，一次读一整行
		File file1 = new File(fileName1);
		BufferedReader reader1 = null;
		try {
			reader1 = new BufferedReader(new FileReader(file1));
			String tempString = null;

			File txt = new File(fileName2);
			if (!txt.exists()) {
				txt.createNewFile();
			}
			byte bytes[] = new byte[512];
			FileOutputStream fos = new FileOutputStream(txt);

			// Map<String,Integer>:cookieId--->次数

			while ((tempString = reader1.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split("\t");
				if(temp.length<11){
					continue;
				}
				if (castId.equals(temp[3])) {
					if (map.get(temp[10]) == null) {//是按cookiesid统计
						map.put(temp[10], 1);
					} else {
						int count = map.get(temp[10]);
						map.put(temp[10], ++count);
					}
				}

				// 写文件
				// bytes = cookieId.getBytes(); // 新加的
				// int b = cookieId.length(); // 改
				// fos.write(bytes, 0, b);
			}
			fos.close();
			reader1.close();
		} catch (Exception e) {
			System.out.println(fileName1);
			e.printStackTrace();
			System.out.println("解析文件,存入数据库area失败");
		}
	}
}
