package com;

import java.util.ArrayList;
import java.util.List;

public class TestJavaMemory {
	public byte[] placeholder = new byte[64 * 1024];

	public static void fillHeap(List<TestJavaMemory> list) throws InterruptedException {
		// 稍做延时，令监视曲线的变化更加明显
		Thread.sleep(50);
		list.add(new TestJavaMemory());
	}

	public static void main(String[] args) throws Exception {
		int num = 1000;
		List<TestJavaMemory> list = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			fillHeap(list);
		}
		System.gc();
	}
}
