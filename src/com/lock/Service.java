package com.lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Service {
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private ReentrantLock lock2 = new ReentrantLock();
	public void read() {
		try {
			lock.readLock().lock();
			lock2.lock();
			System.out.println("获得读锁" + Thread.currentThread().getName());
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
	}

	public void write() {
		try {
			lock.writeLock().lock();
			System.out.println("获得写锁" + Thread.currentThread().getName());
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}
}
