package com.createlunbo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public class Lunbo {
	private List<Integer> lstLun; // 剩余轮次
	private static final Random rand = new Random();

	public Lunbo(int totalLun) {
		lstLun = new ArrayList<Integer>();
		for (int i = 1; i <= totalLun; i++) {
			lstLun.add(i);
		}
	}

	public List<Integer> getRandomList(int count) {
		List<Integer> lstRandLun = new ArrayList<Integer>();
		if (count >= this.lstLun.size()) {
			for (int i = 0; i < this.lstLun.size(); i++) {
				lstRandLun.add(this.lstLun.get(i));
			}
			lstLun.clear();
			return lstRandLun;
		}

		int startPoint = rand.nextInt(lstLun.size());
		while (count > 0) {
			lstRandLun.add(lstLun.remove(startPoint));
			count--;
			startPoint = (startPoint + 1) % lstLun.size();
		}
		return lstRandLun;
	}

	public static void main(String[] args) {
		Lunbo lun = new Lunbo(10);
		List<Integer> list = lun.getRandomList(4);
		list.add(-1);
		for (Integer i : list) {
			System.out.println(i);
		}
		System.out.println(list.size());
		System.out.println("随机数:" + (rand.nextInt(10) + 1));

		int a = 12;
		int b = 15;
		System.out.println(commonMul( a,  b));
		int[] c = {4,5,8};
		System.out.println(nCommonMul(c,c.length));
		
		
		String str="4,70,86,90";
		System.out.println("字符串长度="+str.length());
	}

	public static int commonDiv(int a, int b) {// 求最大公约数
		int temp;
		if (a < b) { // swap(a,b) 交换a和b
			temp = b;
			b = a;
			a = temp;
		}
		temp = 0;
		while ((temp = a % b) != 0) // 辗转相除
		{
			a = b;
			b = temp;
		}
		return b;
	}

	public static int commonMul(int a, int b) { // 两个数的最小公倍数 ＝ 两数乘积 / 最大公约数
		return a * b / commonDiv(a, b);
	}

	public static int nCommonMul(int[] pa, int n) {//N个数的最小公倍数
		if (n == 0) {
			return pa[n];
		}
		return commonMul(pa[n - 1], nCommonMul(pa, n - 1));
	}
}
