package com.listCotent;

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
		comput(init);
	}

}
