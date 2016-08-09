package com.listCotent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestForEach {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		Student[] s=new Student[2];
		
		Student s1=new Student();
		s1.setName("张三");
		Student s2=new Student();
		s2.setName("李四");
		s[0]=s1;
		s[1]=s2;
		for(Student temp:s){
			System.out.println(temp);
		}
		
		
		int runTime = 10000;// 执行次数
		int[] arr=new int[runTime];
		for (int i = 0; i < runTime; i++) {
			list.add(i);
			arr[i]=i;
		}
		int size = list.size();
		double currTime = System.currentTimeMillis();// 开始分析前的系统时间

		// 基本的for
		for (int i = 0; i < size; i++) {
			list.get(i);
		}
		System.out.println("基本的for耗时：" + (System.currentTimeMillis() - currTime));
		currTime = System.currentTimeMillis();// 开始分析前的系统时间

		// foreach
		for (Integer integer : list) {
		}
		System.out.println("foreach耗时：" + (System.currentTimeMillis() - currTime));
		currTime = System.currentTimeMillis();// 开始分析前的系统时间
		// 数组
		int temp=0;
		for (int i = 0; i < runTime; i++) {
			temp=arr[i];
		}
		System.out.println("数组耗时：" + (System.currentTimeMillis() - currTime));
		
		for(Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
			int o = (int) it.next();
			System.out.println(o);
			o = (int) it.next();
			System.out.println(o);
			System.out.println("===============");
			}
		
		
	}

}
