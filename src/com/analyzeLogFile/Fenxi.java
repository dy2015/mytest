package com.analyzeLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Fenxi {
	private final static String url = "C://Users//yt//Downloads//";
	private final static String ipUrl = url + "1.txt";

	private static void fen() throws Exception {
		int sum = 0;
		int zbsum = 0;
//		for (int j = 0; j <= 9; j++) {
//			for (int i = 41; i <= 45; i++) {
//				File file = new File(url + "0" + j + "_10.100.9." + i);
//				BufferedReader reader = new BufferedReader(new FileReader(file));
//				String tempString = null;
//				while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
//					String temStr[] = tempString.split(";");
//					if (!temStr[temStr.length - 1].equals("env:zb")) {
//						sum++;
//					}
//				} // end--while
//			} // end--for
//			for (int i = 49; i <= 56; i++) {
//				File file = new File(url + "0" + j + "_10.100.9." + i);
//				BufferedReader reader = new BufferedReader(new FileReader(file));
//				String tempString = null;
//				while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
//					String temStr[] = tempString.split(";");
//					if (!temStr[temStr.length - 1].equals("env:zb")) {
//						sum++;
//					}
//				} // end--while
//			} // end--for
//			for (int i = 60; i <= 69; i++) {
//				File file = new File(url + "0" + j + "_10.100.9." + i);
//				BufferedReader reader = new BufferedReader(new FileReader(file));
//				String tempString = null;
//				while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
//					String temStr[] = tempString.split(";");
//					if (!temStr[temStr.length - 1].equals("env:zb")) {
//						sum++;
//					}
//				} // end--while
//			} // end--for
//			for (int i = 31; i <= 40; i++) {
//				File file = new File(url + "0" + j + "_10.101.9." + i);
//				BufferedReader reader = new BufferedReader(new FileReader(file));
//				String tempString = null;
//				while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
//					String temStr[] = tempString.split(";");
//					if (!temStr[temStr.length - 1].equals("env:zb")) {
//						sum++;
//					}
//				} // end--while
//			} // end--for
//			for (int i = 62; i <= 70; i++) {
//				File file = new File(url + "0" + j + "_10.103.9." + i);
//				BufferedReader reader = new BufferedReader(new FileReader(file));
//				String tempString = null;
//				while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
//					String temStr[] = tempString.split(";");
//					if (!temStr[temStr.length - 1].equals("env:zb")) {
//						sum++;
//					}
//				} // end--while
//			} // end--for
//		} // end--for
		for (int j = 15; j <= 15; j++) {
			for (int i = 41; i <= 45; i++) {
				File file = new File(url + j + "_10.100.9." + i);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
					String temStr[] = tempString.split(";");
					if (!temStr[temStr.length - 1].replace("\t", "").equals("env:zb")) {
						sum++;
					}else{
						zbsum++;
					}
				} // end--while
			} // end--for
			for (int i = 49; i <= 56; i++) {
				File file = new File(url +  j + "_10.100.9." + i);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
					String temStr[] = tempString.split(";");
					if (!temStr[temStr.length - 1].replace("\t", "").equals("env:zb")) {
						sum++;
					}else{
						zbsum++;
					}
				} // end--while
			} // end--for
			for (int i = 60; i <= 69; i++) {
				File file = new File(url +  j + "_10.100.9." + i);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
					String temStr[] = tempString.split(";");
					if (!temStr[temStr.length - 1].replace("\t", "").equals("env:zb")) {
						sum++;
					}else{
						zbsum++;
					}
				} // end--while
			} // end--for
			for (int i = 31; i <= 40; i++) {
				File file = new File(url +  j + "_10.101.9." + i);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
					String temStr[] = tempString.split(";");
					if (!temStr[temStr.length - 1].replace("\t", "").equals("env:zb")) {
						sum++;
					}else{
						zbsum++;
					}
				} // end--while
			} // end--for
			for (int i = 62; i <= 70; i++) {
				File file = new File(url +  j + "_10.103.9." + i);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
					String temStr[] = tempString.split(";");
					if (!temStr[temStr.length - 1].replace("\t", "").equals("env:zb")) {
						sum++;
					}else{
						zbsum++;
					}
				} // end--while
			} // end--for
		} // end--for
		System.out.println("sum="+sum);
		System.out.println("zbsum="+zbsum);
	}

	public static void main(String[] args) throws Exception {
		fen();
	}

}
