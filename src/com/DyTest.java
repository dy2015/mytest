package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DyTest {
	private final static String url1 = "G://1.txt";
	private final static String url2 = "G://2.txt";
	private final static Map<String, String> map1 = new HashMap<>();// odps
	private final static Map<String, String> map2 = new HashMap<>();// atm_log

	public static void main(String[] args) throws Exception {
		List<Student> studentList = new ArrayList<>();
		List<Student> tempStudentList = new ArrayList<>();
		Student s = new Student();
		
		s.setName("a");
		s.setAge(10);
		studentList.add(s);
		tempStudentList.add(s);
		
		Student s1 = new Student();
		s1.setName("b");
		s1.setAge(11);
		studentList.add(s1);
		
		s = new Student();
		s.setName("c");
		s.setAge(12);
		studentList.add(s);
		
		s = new Student();
		s.setName("e");
		s.setAge(222);
		tempStudentList.add(s);
		
		s = new Student();
		s.setName("a");
		s.setAge(222);
		studentList.add(s);
		tempStudentList.add(s);
		
		for (Student stmp : studentList) {
			int newIndex = tempStudentList.indexOf(stmp);// 传进的对象需要实现equals方法
			System.out.println("newIndex:"+newIndex);
			if (newIndex >= 0) {
				Student t = tempStudentList.remove(newIndex);
			} 
		}
		System.out.println("================");
		for (Student stmp : studentList) {
			System.out.println("studentList:"+stmp);
		}
		System.out.println("================");
		for (Student stmp : tempStudentList) {
			System.out.println("tempStudentList:"+stmp);
		}
	}
}
