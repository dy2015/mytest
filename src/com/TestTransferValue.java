package com;

import java.util.ArrayList;
import java.util.List;

public class TestTransferValue {
	private static List<Integer> a=new ArrayList<Integer>();

	public static void compute(List<Integer> a, List<Integer> b) {
		for (Integer i : a) {
			b.add(i);
		}
	}

	public static void main(String[] args) {
		System.out.println("赋值前的List长度="+a.size());
		List<Integer> b = new ArrayList<Integer>();
		b.add(1);
		b.add(2);
		b.add(3);
		b.add(4);
		b.add(5);
		compute(b, a);
		System.out.println("赋值后的List长度="+a.size());
		for (Integer i : a) {
			System.out.print(i + ",");
		}
	}

}
