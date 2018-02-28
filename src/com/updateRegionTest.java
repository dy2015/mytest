package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.analyzeLogFile.WriteFile;

public class updateRegionTest {

	private final static String writeUrl = "G://5.txt";
	private final static String init = "INSERT INTO region(id,city_id,province_id,start_ip,end_ip,geoid) VALUES";
	private final static StringBuilder initStr = new StringBuilder();

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String fileName = "G://1.txt";
		// regionDao.deleteRegion();//删除region表数据
		readFileByLines(fileName);
		System.out.println("执行时间：" + (System.currentTimeMillis() - start));
	}

	public static void readFileByLines(String fileName) {// 以行为单位读取文件内容，一次读一整行

		StringBuilder str = new StringBuilder(init);
		File file = new File(fileName);
		BufferedReader reader = null;
		int count = 0;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			Map<String, String> map = new HashMap<String, String>();
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temps[] = tempString.split(",");
				String startIP = temps[0];
				String endIP = temps[1];
				String geoid = temps[2];// 行政编码
				String cityId = geoid.substring(4, geoid.length());
				String provinceId = cityId.substring(0, 2);
				if ("00".equals(provinceId)) {
					provinceId = "90";
				}
				if ("1344000000".equals(geoid)) {
					cityId = "810000";
					provinceId = "81";
				} else if ("1446000000".equals(geoid)) {
					cityId = "820000";
					provinceId = "82";
				} else if ("1158000000".equals(geoid)) {
					cityId = "710000";
					provinceId = "71";
				} else if ("000000".equals(cityId)) {// 如果是海外的，则用行政编码代替cityId
					cityId = geoid;
				}
				map.put("cityId", cityId);
				map.put("provinceId", provinceId);
				map.put("startIp", startIP);
				map.put("endIp", endIP);
				map.put("geoid", geoid);
				String temp = "";
				if (count % 1000 == 0) {
					initStr.append("\n").append(str.toString().substring(0,str.toString().length()-1)).append(";");
					str = new StringBuilder(init);
				}
				temp = "("+(count+1)+",'" + cityId + "','" + provinceId + "','" + startIP + "','" + endIP + "','" + geoid + "'),";
				str.append("\n").append(temp);
				// regionDao.add(map);
				count++;
			}
			initStr.append("\n").append(str.toString().substring(0,str.toString().length()-1)).append(";");
			System.out.println(count);
			WriteFile.write(writeUrl, initStr.toString());
			reader.close();
		} catch (IOException e) {
			System.out.println("解析文件,存入数据库region失败");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					System.out.println("关闭文件失败");
				}
			}
		}
	}
}
