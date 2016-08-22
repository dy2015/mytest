package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LunboRandomUtil {

	private final Random rand = new Random();

	private List<Integer> lstLun; // 剩余轮次

	public LunboRandomUtil(int totalLun) {
		lstLun = new ArrayList<Integer>();
		for (int i = 1; i <= totalLun; i++) {
			lstLun.add(i);
		}
	}

	/**
	 * 获取随机轮次序列
	 * 
	 * @param count 序列个数
	 * @return
	 */
	public List<Integer> getRandomList(int count) {
		List<Integer> lstRandLun = new ArrayList<Integer>();
		if (count >= this.lstLun.size()) {
			for (int i = 0; i < this.lstLun.size(); i++) {
				lstRandLun.add(this.lstLun.get(i));
			}
			lstLun.clear();
			return lstRandLun;
		}

		int startPoint = rand.nextInt(lstLun.size());
		while (count > 0) {
			lstRandLun.add(lstLun.remove(startPoint));
			count--;
			startPoint = (startPoint + 1) % lstLun.size();
		}
		return lstRandLun;
	}

	public static void main(String[] arugs) {
		// 做100次随机测试，每次都3个投放10轮播组合，可不占满
//		int tLun = 10;
//		Random rand = new Random();
//		for (int i = 0; i < 100; i++) {
//			// 生成随机轮数，r1+r2+r3<=10
//			int r1 = 1 + rand.nextInt(tLun - 2);
//			int r2 = 1 + rand.nextInt(tLun - r1 - 1);
//			int r3 = 1 + rand.nextInt(tLun - r1 - r2);
//
//			// 初始化测试用的castLunMap
//			Map<Integer, MLunbo> castLunMap = new HashMap<Integer, MLunbo>();
//			// 这里的MLunbo类我加了个构造函数，未提交,测试时可在MLunbo类加构造函数；或者不加构造函数，在此调用set方法赋值也可
//			MLunbo mlb1 = new MLunbo(500, r1, tLun);
//			MLunbo mlb2 = new MLunbo(501, r2, tLun);
//			MLunbo mlb3 = new MLunbo(502, r3, tLun);
//			castLunMap.put(mlb1.getCastId(), mlb1);
//			castLunMap.put(mlb2.getCastId(), mlb2);
//			castLunMap.put(mlb3.getCastId(), mlb3);
//
//			int totalLun = mlb1.getTotalLun();
//			LunboRandomUtil rl = new LunboRandomUtil(totalLun);
//			// 准备好返回的maps
//			Map<Integer, List<Integer>> maps = new HashMap<Integer, List<Integer>>();
//			Iterator<Entry<Integer, MLunbo>> itr = castLunMap.entrySet().iterator();
//			while (itr.hasNext()) {
//				Entry<Integer, MLunbo> ent = itr.next();
//				int key = ent.getKey();
//				MLunbo mlb = ent.getValue();
//				maps.put(key, rl.getRandomList(mlb.getLun()));
//			}
//			System.out.println(r1 + "," + r2 + "," + r3 + maps);
//		}
	}

}
