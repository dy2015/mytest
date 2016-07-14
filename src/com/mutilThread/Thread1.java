package com.mutilThread;

public class Thread1 implements Runnable {

	@Override
	public void run() {
		long start=System.currentTimeMillis();
		int i = 0;
		while (i < Common.MAX) {
			//Common.getResult();
			System.out.println(Common.count2.get()+"当前值");
			System.out.println(Common.count2.addAndGet(100)+"与100相加,返回的是更新的值！");
			Common.count2.incrementAndGet();
			// System.out.println(Thread.currentThread().getName() +
			// "在运行......！");
			i++;
		}// end--while
		long end=System.currentTimeMillis();
		System.out.println("共花时间："+(end-start));
		System.out.println(Thread.currentThread().getName() + "运行了" + i + "次");
	}// end--run
}
