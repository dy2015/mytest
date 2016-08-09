package com.vmload;

class TestThread {
	public TestThread(){
		System.out.println(Thread.currentThread() + "我是构造方法");
	}
	static {
		System.out.println(Thread.currentThread() + "我被初始化！");
	}
}

public class ThreadInit {

	public static void main(String[] args) {
		Runnable s = new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread() + "线程开始");
				TestThread t = new TestThread();
				System.out.println(Thread.currentThread() + "线程结束");
			}
		};
		Thread t1 = new Thread(s);
		Thread t2 = new Thread(s);
		t1.start();
		t2.start();
		System.out.println("=================================");
		TestThread tt = new TestThread();
		TestThread ttt = new TestThread();
	}

}
