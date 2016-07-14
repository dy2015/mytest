package com.mutilThread;

import java.util.concurrent.atomic.AtomicInteger;

public class Common {
	public static int MAX = 5;
	public static volatile int count1 = 0;
	public static AtomicInteger count2 = new AtomicInteger();

	public static synchronized void getResult() {
		//System.out.println(Thread.currentThread().getName() + "在执行加1操作！");
		count1++;
	}
}
