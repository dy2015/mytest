package com.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class updateFileTest {
	
	
	public static void main(String[] agrs) {
		updateFileTest u = new updateFileTest();
		u.updateAreaData();
	}

	public void updateAreaData() {// 找出只在msg.tmp里出现的行
		long start = System.currentTimeMillis();
		readFileByCity("G://msg.tmp", "G://reqid.tmp", "G://1.txt", "G://2.txt");
		System.out.println("执行时间：" + (System.currentTimeMillis() - start));
	}

	public void readFileByCity(String fileName1, String fileName2, String fileName3, String fileName4) {// 以行为单位读取文件内容，一次读一整行
		File file1 = new File(fileName1);
		File file2 = new File(fileName2);
		BufferedReader reader1 = null;
		BufferedReader reader2 = null;
		try {
			reader1 = new BufferedReader(new FileReader(file1));
			reader2 = new BufferedReader(new FileReader(file2));
			String tempString = null;
			String temp = null;
			boolean tag = true;
			int count = 0;
			int count2 = 0;

			File txt = new File(fileName3);
			if (!txt.exists()) {
				txt.createNewFile();
			}
			byte bytes[] = new byte[512];
			FileOutputStream fos = new FileOutputStream(txt);

			File txt2 = new File(fileName4);
			if (!txt2.exists()) {
				txt2.createNewFile();
			}
			byte bytes2[] = new byte[512];
			FileOutputStream fos2 = new FileOutputStream(txt2);

			List<String> list = new ArrayList<String>();
			while ((temp = reader2.readLine()) != null) {
				list.add(temp);
			}
			while ((tempString = reader1.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				tag = true;
				for (String s : list) {
					if (tempString.contains(s)) {
						count2++;
						String str = tempString.split("&u:")[1] + "\n";
						bytes2 = str.getBytes(); // 新加的
						int b = str.length(); // 改
						fos2.write(bytes2, 0, b);
						tag = false;
						break;
					}
				}
				if (tag) {
					count++;
					String str = tempString.split("&u:")[1] + "\n";
					bytes = str.getBytes(); // 新加的
					int b = str.length(); // 改
					fos.write(bytes, 0, b);
				}
			}
			System.out.println("不在总条数：" + count2);
			System.out.println("在总条数：" + count);
			System.out.println("总条数：" + (count + count2));
			fos.close();
			fos2.close();
			reader1.close();
			reader2.close();
		} catch (IOException e) {
			System.out.println("解析文件,存入数据库area失败");
		}
	}
}
