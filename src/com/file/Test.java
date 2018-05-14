package com.file;

public class Test {
	
	public static void main(String[] args) {
//		Thread t1 = new Thread(new WriteFileThreadUtil("D:\\1.txt","\n123456",false));
//		t1.start();
//		Thread t2 = new Thread(new WriteFileThreadUtil("D:\\1.txt","\n222222",false));
//		t2.start();
//		Thread t3 = new Thread(new WriteFileThreadUtil("D:\\1.txt","\n333333",false));
//		t3.start();
		String content = OpearFileUtil.excute("D:\\1.txt", "",true);//读取文件后，需要清空文件		
		System.out.println(content);
		System.out.println("===================");
		String[] str= content.split("\n");
		for(String s:str) {
			System.out.println("1:"+s);
		}
	}

}
