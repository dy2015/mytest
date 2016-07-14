package com.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestRemove {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		Student s= new Student(list);
		List<Integer> c=s.getList();
		System.out.println("原来的数据:");
		for(int i:c){
			System.out.print(i+",");
		}
		for (Iterator<Integer> it = c.iterator(); it.hasNext();) {
			if(it.next()==5||it.next()==7){
				it.remove();
			}
		}//end--for
		c=s.getList();
		System.out.println();
		System.out.println("现在的数据:");
		for(int i:c){
			System.out.print(i+",");
		}
	}

}
