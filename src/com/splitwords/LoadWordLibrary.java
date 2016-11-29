package com.splitwords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

public class LoadWordLibrary {
	private final static String url = "G://优酷资料//学习资料//word.txt";

	public static String[] Load(Map<String, String> map) {
		try {
			File file = new File(url);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				String temp[] = tempString.split("\t");
				map.put(temp[0], PinYinUtil.CN2Spell(temp[0]));
				
			} // end--while
			reader.close();
			String[] word=new String[map.size()];
			int index=0;
			for(String key: map.keySet()){
				word[index++]=key;
			}
			return word;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
