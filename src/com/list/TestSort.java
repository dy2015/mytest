package com.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestSort {

	public static void main(String[] args) {
		 List<Student> list = new ArrayList<Student>();  
         
	        //创建3个学生对象，年龄分别是20、19、21，并将他们依次放入List中  
	        Student s1 = new Student();  
	        s1.setName("B");
	        s1.setAddress("上海");
	        Student s2 = new Student();  
	        s2.setName("A");
	        s2.setAddress("北京");
	        Student s3 = new Student();  
	        s3.setName("C");
	        s3.setAddress("广东");
	        list.add(s1);  
	        list.add(s2);  
	        list.add(s3);  
	          
	        System.out.println("排序前："+list);  
	          
	     // 按选项名称，以A,B,C排序
			Collections.sort(list, new Comparator<Student>() {
				public int compare(Student o1, Student o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
	        System.out.println("排序后："+list);  

	}

}
