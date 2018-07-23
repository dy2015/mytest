package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class DyTest {

	private final static String url = "C://1.txt";
	private String test = "C://1.txt";

	public static void main(String[] args) {
		// method1();
		// method2();
//		int result = method3();
//		System.out.println("result = " + result);
		System.out.println(method5(1234567891L)); 
	}
	
	private static String method5(Long userId) {
		String invitationDate = "20180710";
		String dateString = com.bigData.BigDataUtil.multiply(invitationDate, invitationDate);
        long tempDate = Long.valueOf(dateString.substring(dateString.length()-10));
		String code = String.valueOf(userId.longValue() + tempDate);
		long lotteryCode = Long.valueOf(new StringBuffer(code).reverse().toString());		
		return String.format("%012d", lotteryCode);
	}
	
	public static void method4() throws Exception {
		Callable<Integer> call = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				System.out.println("thread start .. ");
				Thread.sleep(2000);
				return 1;
			}
		};

		FutureTask<Integer> task = new FutureTask<>(call);
		Thread t = new Thread(task);

		t.start();
		System.out.println("do other thing .. ");
		System.out.println("拿到线程的执行结果 ： " + task.get());
	}

	public static int method3() {
		String s = "a12";
		int result = 0;
		try {
			result = Integer.valueOf(s);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = -1;
		return result;
	}

	public static void method1() {
		File file = new File(url);
		BufferedReader reader = null;
		int sum = 0;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				if (tempString.indexOf("SUCCESS") == -1 && tempString.trim().length() > 0) {
					System.out.println(tempString);
					sum += Integer.valueOf(tempString);
				}
			}
			System.out.println("总和=" + sum);
		} catch (Exception e) {
		}
	}

	public static void method2() {
		File file = new File(url);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {// 一次读入一行，直到读入null为文件结束
				if (tempString.trim().length() > 1) {
					System.out.println(tempString);
				}
			}
		} catch (Exception e) {
		}
	}
}
