package com.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestArray {

	public static void main(String[] args) {

		Object[] list1 = { 1, 2, 3 };
		Object[] list2 = { 4, 5, 6 };
		Object[] list3 = { 7, 8, 9 };
		List<Object[]> nArray = new ArrayList<Object[]>();
		nArray.add(list1);
		nArray.add(list2);
		nArray.add(list3);
		LinkedList<LinkedList<Object>> list = combineArray(nArray);
		for (LinkedList<Object> o : list) {
			System.out.println(o.toString());
		}
	}

	private static LinkedList<LinkedList<Object>> combineArray(List<Object[]> nArray) {
		int n = nArray.size();// 数组个数

		int[] count = new int[n];
		for (int i = 0; i < n; i++) {
			count[i] = 0;
		}

		LinkedList<LinkedList<Object>> result = new LinkedList<LinkedList<Object>>();// 存放笛卡尔积结果

		int flag = 0;
		do {
			LinkedList<Object> list = new LinkedList<Object>();
			for (int i = 0; i < n; i++) {
				list.add(nArray.get(i)[count[i]]);
			}

			result.add(list);

			// change the count array;
			flag = ChangeArrayCount(count, nArray);

		} while (flag != 1);

		return result;
	}

	private static int ChangeArrayCount(int[] count, List<Object[]> nArray) {
		for (int i = nArray.size() - 1; i >= 0; i--) {
			if (count[i] == nArray.get(i).length - 1) {
				count[i] = 0;
			} else {
				count[i]++;
				return 0;
			}
		}
		return 1;
	}
}
