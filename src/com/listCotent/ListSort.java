package com.listCotent;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListSort {
	public static void main(String[] ard) {
		double a =Double.NaN;
		double b =Double.NaN;
		double d =Double.NaN;
		
		double c =10.0;
		if(d-c>0 ||d-c<0  ) {
			System.out.println("能减");
		}else {
			System.out.println("不能减");
			System.out.println(c-a);
		}
		Double[] array1 = {a,b,c,d,a,b,c,d,a,b,c,d,a,b,c,d,a,b,c,d,a,b,c,d,a,b,c,d,a,b,c,d,a,b,c,d };
		List<Double> list1 = Arrays.asList(array1);
		Collections.sort(list1);
		
		Integer[] array = { 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 2, 1, 0, 0, 0, 2, 30, 0, 3 };
		List<Integer> list = Arrays.asList(array);
//		Collections.sort(list);
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer c1, Integer c2) {
				double r2 = c1 - c2;
//				return c1 > c2 ? 1 : -1;  
				if (r2 < 0) {
					return 1;
				} else if (r2 > 0) {
					return -1;
				}
				return 0;
			}
		});
	}
}
