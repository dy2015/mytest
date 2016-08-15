package com.classmethodopt;

/**
 *面试题：一个类有一个过期时间属性，一个当前时间和过期时间比较大小的方法。
 *     如果现在有100万个类的实例，都要访问这个方法，有没有更好的方案实现这个效果？
 *         将当前时间和过期时间比较大小的方法，成静态方法即可。如果有共享数据，则需要对静态方法加同步！
 *
 **/
class TestClassMethodIn2 implements Runnable{

	@Override
	public void run() {
		Student s = new Student();
		s.setExpireTime(1000);
		Compare2.compareTime(2000,1000);
//		System.out.println(this.getClass()+":"+Thread.currentThread().getName()+":"+tag);
	}
}

class TestClassMethodIn1 implements Runnable{
	@Override
	public void run() {
		Student s = new Student();
		s.setExpireTime(1000);
		Compare.compareTime(1000,2000);
//		System.out.println(this.getClass()+":"+Thread.currentThread().getName()+":"+tag);
	}
}

public class TestClassMethod implements Runnable{

	public static void main(String[] args) {
		int num = 10000;
		double start=System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			Thread t=new Thread(new TestClassMethod());
			t.start();
		}
		System.out.println("耗时1："+(System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			Thread t=new Thread(new TestClassMethodIn1());
			t.start();
		}
		System.out.println("耗时2(同步)："+(System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			Thread t=new Thread(new TestClassMethodIn2());
			t.start();
		}
		System.out.println("耗时3(不同步)："+(System.currentTimeMillis()-start));
		
	}

	@Override
	public void run() {
		Student s = new Student();
		s.setExpireTime(1000);
		s.compareTime(System.currentTimeMillis());
	}

}
