package com.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {

	public void test1() {
		int a = 0;
		for (int i = 0; i < 10; i++) {
			a = 0;
			for (int j = 0; j < 10; j++) {
				a++;
			}
			System.out.println(a);
		}
	}

	public void test2() {

		for (int i = 0; i < 10; i++) {
			int a = 0;
			for (int j = 0; j < 10; j++) {
				a++;
			}
			System.out.println(a);
		}
	}

	public static void main(String[] args) {
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(2);
		// BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		// 不设置的话，LinkedBlockingQueue默认大小为Integer.MAX_VALUE

		// BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);

		Consumer consumer = new Consumer(queue);
		Producer producer = new Producer(queue);
		for (int i = 0; i < 5; i++) {
			new Thread(producer, "Producer" + (i + 1)).start();

			new Thread(consumer, "Consumer" + (i + 1)).start();
		}
	}
}