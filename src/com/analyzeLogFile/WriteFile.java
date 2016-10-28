package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class WriteFile {
	public static void write(String fileUrl, String content) {
		content += "\n";
		try {
			File file = new File(fileUrl);
			if (!file.exists()) {
				file.createNewFile();
			}
			byte bytes[] = new byte[512];

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			StringBuilder str= new StringBuilder();
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				str.append(tempString).append("\n");
			}
			reader.close();
			//写文件
			FileOutputStream fos = new FileOutputStream(file);
			str.append(content);
			bytes = str.toString().getBytes("UTF-8");
			fos.write(bytes);
			fos.close();
		} catch (IOException e) {
			System.out.println("写文件失败");
		}
	}

	public static void main(String[] args) {
		String url = "G://4.txt";
		for (int i = 0; i < 10; i++) {
			write(url, String.valueOf(i));
		}
	}
}
