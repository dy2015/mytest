package com.methodloop;

public class TestMethodLoop {

	public static int compute(int num) {
		int count = 0;
		for (int i = 1; i <= num; i++) {
			count += i;
		}
		return count;
	}
	public static int compute2(int num) {
		return num;
	}
	
	public static void main(String[] args) {
		int num=100000;
		int count = 0;
		double start = System.currentTimeMillis();
		for (int i = 1; i <= num; i++) {
			count += compute2(i);
		}
		System.out.println("方法循环调用耗时：" + (System.currentTimeMillis() - start));
		count = 0;
		start = System.currentTimeMillis();
		compute(num);
		System.out.println("方法只调用一次耗时：" + (System.currentTimeMillis() - start));
	}

}
