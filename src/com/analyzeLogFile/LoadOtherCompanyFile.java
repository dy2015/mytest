package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class LoadOtherCompanyFile {
	private final static String url = "C://Users//yt//Downloads//ip.lib";
	private final static String ipUrl = "C://Users//yt//Downloads//ip.txt";
	private final static String regionUrl = "C://Users//yt//Downloads//region.txt";
	
	private final static String city = "440300";
	
	public static void main(String[] args) {
		try {
			File txt1 = new File(ipUrl);
			File txt2 = new File(regionUrl);
			
			if (!txt1.exists()) {
				txt1.createNewFile();
			}
			if (!txt2.exists()) {
				txt2.createNewFile();
			}
			
			byte bytes1[] = new byte[512];
			FileOutputStream fos1 = new FileOutputStream(txt1);
			byte bytes2[] = new byte[512];
			FileOutputStream fos2 = new FileOutputStream(txt2);
			
		
		File file = new File(url);
		BufferedReader reader= new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				StringBuilder str=new StringBuilder();
				String temp[] = tempString.split(",");
				if(temp[temp.length-1].contains(city)){
					str.append(city).append("\t").append(temp[0]).append("\t").append(temp[1]).append("\n");
					bytes1 = str.toString().getBytes("utf-8");
					int b = str.length();
					fos1.write(bytes1,0,b);
				}else{
					if(temp[temp.length-1].substring(4).equals("000000")){
						str.append(temp[temp.length-1]).append("\t").append(temp[0]).append("\t").append(temp[1]).append("\n");
					}else{
						str.append(temp[temp.length-1].substring(4)).append("\t").append(temp[0]).append("\t").append(temp[1]).append("\n");
					}
					bytes2 = str.toString().getBytes("utf-8");
					int b = str.length();
					fos2.write(bytes2,0,b);
				}
			}
			reader.close();
			fos1.close();
			fos2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
