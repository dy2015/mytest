package com.listCotent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestListContent {
	public static void comput(DataList init) {
		List<Integer> list = init.getList();//本质是个指针
		for (Iterator<Integer> it = list.iterator(); it.hasNext();) {
			Integer castId = it.next();
			if (castId == 4) {
				it.remove();
			}
		}

		for (Iterator<Integer> itt = init.getList().iterator(); itt.hasNext();) {
			Integer id = itt.next();
			System.out.print(id + ",");
		}
	}

	public static void main(String[] args) {

		DataList init = new DataList();
//		comput(init);
		List<Integer> list=init.getList();
		List<Integer> removelist = new ArrayList<>();
		removelist.add(3);
		removelist.add(1);
		removelist.add(6);
		removelist.add(8);
		
		
		System.out.println("移除之前的数据：");
		for(Integer i:list){
			System.out.print(i+",");
		}
		list.removeAll(removelist);
		System.out.println();
		System.out.println("移除之后的数据：");
		for(Integer i:init.getList()){
			System.out.print(i+",");
		}
		
		System.out.println();
		System.out.println("removelist的数据：");
		for(Integer i:removelist){
			System.out.print(i+",");
		}
		
		list.add(removelist.remove(1));
		
		System.out.println();
		System.out.println("第二次移除之后的数据：");
		for(Integer i:init.getList()){
			System.out.print(i+",");
		}
		
		list.add(removelist.remove(1));
		System.out.println();
		System.out.println("第三次移除之后的数据：");
		for(Integer i:init.getList()){
			System.out.print(i+",");
		}
		
	}

}
