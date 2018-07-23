package com.lock;

public class MyThread extends Thread {
	private Object lock;
	private String showChar;
	private int showNumber;
	private int printCount = 0;
	volatile private static int addNumber = 1;

	public MyThread(Object lock, String showChar, int showNumber) {
		super();
		this.lock = lock;
		this.showChar = showChar;
		this.showNumber = showNumber;
	}

	public void add(String name) {
		addNumber++;
		System.out.println("ThreadName ="+name+",addNumber=" + addNumber);
		printCount++;
		System.out.println("ThreadName ="+name+",printCount=" + printCount);
		System.out.println();
	}

	public void run() {
		try {
			synchronized (lock) {
				while (true) {
					if (addNumber % 3 == showNumber) {
						System.out.println("ThreadName=" + Thread.currentThread().getName() + " runCount=" + addNumber + " " + showChar);
						lock.notifyAll();
						addNumber++;
						printCount++;
						if (printCount == 3) {
							break;
						}
					} else {
						lock.wait();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
