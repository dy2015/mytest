package com.mutilThread;

import java.util.HashMap;
import java.util.Map;

public class Thread5 extends Thread {

	private String key = null;

	public Thread5(String key) {
		super();
		this.key = key;
	}

	@Override
	public void run() {
		while (true) {
			// long start=System.currentTimeMillis();
			Map<String, String> condition = new HashMap<String, String>();
			condition.put("a", "123");
			condition.put("b", "234");
			condition.put("test", key);
			System.out.println("========================================");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			for (String key : condition.keySet()) {
//				System.out.println("key:" + key + ";value:" + condition.get(key));
//			}
//			System.out.println("========================================");
		}
		// while (i < Common.MAX) {
		// Common.getResult();
		// System.out.println(Common.count2.get()+"当前值");
		//// System.out.println(Common.count2.addAndGet(100)+"与100相加,返回的是更新的值！");
		// Common.count2.incrementAndGet();
		// // System.out.println(Thread.currentThread().getName() +
		// // "在运行......！");
		// i++;
		// }// end--while
		// long end=System.currentTimeMillis();
		// System.out.println("共花时间："+(end-start));
		// System.out.println(Thread.currentThread().getName() + "运行了" + i + "次");
	}// end--run
}
