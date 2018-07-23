package com.lock;

public class Test {

	public static void main(String[] args) {
//		readAndRead();
//		writeAndWrite();
		readAndWrite();
	}

	public static void readAndRead() {
		Service service = new Service();
		ThreadA a = new ThreadA(service);
		a.setName("A");
		ThreadA b = new ThreadA(service);
		b.setName("B");
		a.start();
		b.start();
	}
	public static void writeAndWrite() {
		Service service = new Service();
		ThreadB a = new ThreadB(service);
		a.setName("A");
		ThreadB b = new ThreadB(service);
		b.setName("B");
		a.start();
		b.start();
	}
	public static void readAndWrite() {
		Service service = new Service();
		ThreadA a = new ThreadA(service);
		a.setName("A");
		ThreadB b = new ThreadB(service);
		b.setName("B");
		a.start();
		b.start();
	}
}
