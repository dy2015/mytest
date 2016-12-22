package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class castyouxianji {
	private final static DecimalFormat df = new DecimalFormat("######0.0000");
	private final static String url = "G://";
	private final static String ipUrl = url + "3.txt";
	private final static String url2 = url + "2.txt";

	public static void main(String[] args) {
		try {
			File file = new File(ipUrl);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int count = 0;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				count++;
				// tempString = tempString.replace("\t", ",");
				// tempString=tempString.replace("|", "\t");
				String str[] = tempString.split(",");
				if (tempString == null || "".equals(tempString)) {
					System.out.println(tempString);
				}

				// double s = ((Integer.valueOf(str[0]) -
				// Integer.valueOf(str[1])) * 100.0) / Integer.valueOf(str[0]);
				// System.out.println(df.format(s));
				// System.out.println(str[i+1]);

				String temp = "insert into blacklist(type,value) values(\"ip\",\"" + tempString + "\");";
				// System.out.println(temp);
				write(url2, temp);
				Thread.sleep(5);
			}
			System.out.println("count=" + count);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
			StringBuilder str = new StringBuilder();
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				str.append(tempString).append("\n");
			}
			reader.close();
			// 写文件
			FileOutputStream fos = new FileOutputStream(file);
			str.append(content);
			bytes = str.toString().getBytes("UTF-8");
			fos.write(bytes);
			fos.close();
		} catch (IOException e) {
			System.out.println("写文件失败" + content);
		}
	}
}
