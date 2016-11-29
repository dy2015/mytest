package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class AnalyzePriority {
	private final static String readUrl = "G://1.txt";
	private final static String writeUrl = "G://2.txt";
	public static void analyze(String fileName1,String fileName2){
		File file1 = new File(fileName1);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file1));
			String tempString = null;

			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				tempString=tempString.replace("\t", "");
				tempString=tempString.replace(" ", "");
				tempString=tempString.replace("|", "\t");
				String temp[] = tempString.split("\t");
				WriteFile.write(fileName2, temp[17]);
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		analyze(readUrl,writeUrl);
	}

}
