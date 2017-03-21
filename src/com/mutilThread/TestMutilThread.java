package com.mutilThread;

public class TestMutilThread {

	public static void main(String[] args) throws InterruptedException {

//		Thread t2 = new Thread(new Thread2());
//		Thread t3 = new Thread(new Thread3());
//		Thread t4 = new Thread(new Thread4());
		for(int i=0;i<5;i++){
			Thread t1 = new Thread(new Thread1(String.valueOf(i)));
			t1.start();
		}
		
//		t2.start();
//		t3.start();
//		t4.start();
//		Thread.sleep(1000*15);
//		System.out.println("========================================");
//		System.out.println("count1="+Common.count1);
//		System.out.println("count2="+Common.count2.intValue());
	}

}
