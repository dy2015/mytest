package com.cutprice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 思路1：
 *   1、预先计算出满足要求的砍价次数和每次砍价的金额，真实场景下，无需实时计算砍价金额，每个砍价用户取一个计算好的砍价金额即可。
 *   2、以砍价金额随机次数最多为目标，当前砍价金额如果会导致后续无随机砍价金额，无法满足砍价次数和砍价总金额要求时，移除大于等于当前随机砍价金额值，后续砍价在剩余随机砍价值中选取组合。
 *   3、如果随机砍价金额数组中，全部选中最小的砍价金额，无法满足砍价次数要求时，则以砍价金额为准，实际砍价次数不做要求。
 * 思路2：
 *   将思路1中的算法核心部分解耦，实时计算每个用户可砍价的随机金额
 *   
 *注意：现实应用中，需要二次改造本算法，引入线程，并需要考虑并发（算法中的部分成员变量，是公有数据）
 *
 */


public class CutPriceTest {

	private static List<Double> initRandomPrice = Arrays.asList(560.0, 556.0, 550.0, 356.0, 300.0);// 初始的砍价随机数
	private static double targetPrice = 56000;// 价格总金额
	private static int cutMin = 156;// 可砍价的最小次数
	private static int cutMax = 186;// 可砍价的最大次数
	private static String COMPLETED_PRICE = "completedPrice";// 已完成的砍价金额
	private static String COMPLETED_CUTNUM = "completedCutNum";// 已完成的砍价次数
	private static Map<String, Double> map = new HashMap<String, Double>();
	private static List<Integer> cutNum = new ArrayList<Integer>();//砍价次数数组
	private static List<Double> randomPrice;// 砍价随机数,必须从大到小排好顺序
	private static boolean debug = false;// 是否打印调试信息

	public static void main(String[] args) {
		while (true) {

			init();// 初始化数据
			if (checkCutPriceAndCutNum()) {
				cutPriceStrategies1();// 砍价算法1
			} else {
				cutPriceStrategies2();// 砍价算法2
			}
			printException();// 校验算法结果：是否有异常砍价情况
			System.out.println("已完成的砍价金额:" + map.get(COMPLETED_PRICE));
			System.out.println("已完成的砍价次数:" + (map.get(COMPLETED_CUTNUM)));
		}
	}

	/**
	 * 校验砍价随机金额最小值是否满足砍价次数的要求 校验结果：true：可调用砍价算法1；false：调用砍价算法2
	 */
	private static boolean checkCutPriceAndCutNum() {
		int tempCutMax = 0;// 临时砍价次数最大值
		double cutNum1 = targetPrice / randomPrice.get(randomPrice.size() - 1);// 按最x小砍价金额，计算可砍价的次数
		if (cutNum1 >= cutMin) {// 可砍价的次数，大于了规定的最小砍价次数
			tempCutMax = (int) Math.floor(cutNum1);// 可砍价的最大次数
			cutNum.clear();
			for (int i = cutMin; i <= tempCutMax; i++) {
				cutNum.add(i);
			}
			int index = (int) (Math.random() * cutNum.size());// 获取随机砍价金额
			cutMin = cutNum.get(index);
			return true;
		} else {// 按最小砍价金额计算的砍价次数都小于cutMin，则执行砍价算法2
			return false;
		}
	}

	/**
	 * 砍价算法1
	 */
	private static void cutPriceStrategies1() {
		int index = 0;// 对应可砍价的随机金额数组下标
		List<Double> completedPrice = new ArrayList<Double>();
		for (int i = 1; i <= cutMax;) {
			int preI = i;
			index = (int) (Math.random() * randomPrice.size());// 获取随机砍价金额
			if (check(map, index, randomPrice, completedPrice)) {
				i++;
			}
			if (i == preI) {// 校验前后两次砍价,对最终的砍价结果是否一致
				if (completedPrice.size() <= 0) {
					continue;
				}
				i--;// 取消本次砍价金额,重新获取砍价随机金额
				int removeIndex = completedPrice.size() - 1;
				if (debug) {
					System.out.println("移除第" + map.get(COMPLETED_CUTNUM) + "次砍价，砍价金额：" + completedPrice.get(removeIndex) + ";未完成砍价金额:" + (targetPrice - map.get(COMPLETED_PRICE) + completedPrice.get(removeIndex)));
				}
				map.put(COMPLETED_PRICE, map.get(COMPLETED_PRICE) - completedPrice.get(removeIndex));// 更新已完成的砍价金额
				map.put(COMPLETED_CUTNUM, map.get(COMPLETED_CUTNUM) - 1);// 更新已完成的砍价次数
				List<Double> removeRandomPrice = new ArrayList<Double>();
				int tempIndex = randomPrice.indexOf(completedPrice.get(removeIndex));
				for (int j = 0; j <= tempIndex; j++) {
					removeRandomPrice.add(randomPrice.get(j));
				}
				randomPrice.removeAll(removeRandomPrice);
				completedPrice.remove(removeIndex);
			}
			if (map.get(COMPLETED_PRICE) >= targetPrice) {
				break;
			}
		}
	}

	/**
	 * 砍价算法2
	 */
	private static void cutPriceStrategies2() {
		int index = 0;// 对应可砍价的随机金额数组下标
		for (int i = 1; i <= cutMax; i++) {
			index = (int) (Math.random() * randomPrice.size());// 获取随机砍价金额
			map.put(COMPLETED_PRICE, map.get(COMPLETED_PRICE) + randomPrice.get(index));// 更新已完成的砍价金额
			map.put(COMPLETED_CUTNUM, map.get(COMPLETED_CUTNUM) + 1);// 更新已完成的砍价次数
			double endCutPrice = targetPrice - map.get(COMPLETED_PRICE);
			if (endCutPrice < 0) {// 超过了砍价总金额
				endCutPrice = targetPrice - map.get(COMPLETED_PRICE) + randomPrice.get(index);
				map.put(COMPLETED_PRICE, map.get(COMPLETED_PRICE) - randomPrice.get(index) + endCutPrice);// 更新已完成的砍价金额
				if (debug) {
					System.out.println("第" + map.get(COMPLETED_CUTNUM) + "次砍价金额：" + endCutPrice);
				}
			} else {
				if (debug) {
					System.out.println("第" + map.get(COMPLETED_CUTNUM) + "次砍价金额：" + randomPrice.get(index));
				}
			}

			if (map.get(COMPLETED_PRICE) >= targetPrice) {
				break;
			}
		}
	}

	/**
	 * 功能：校验砍价次数和金额在要求范围内。
	 * 
	 * @return 校验结果：true：继续砍；false：结束砍价
	 */
	private static boolean check(Map<String, Double> map, int index, List<Double> randomPrice, List<Double> completedPrice) {
		double endCutPrice = targetPrice - map.get(COMPLETED_PRICE);
		boolean tag = true;// 校验本次砍价金额，是否会影响砍价次数的要求
		for (int i = 0; i < randomPrice.size(); i++) {
			double endCutNum = endCutPrice / randomPrice.get(i);// 剩余金额，按最大砍价金额计算可砍价的次数
			if (map.get(COMPLETED_CUTNUM) + endCutNum >= cutMin) {
				tag = false;
				break;
			}
		}
		if (tag) {
			return false;
		}
		double tempCompletedPrice = map.get(COMPLETED_PRICE) + randomPrice.get(index);
		if (endCutPrice < randomPrice.get(index) || map.get(COMPLETED_CUTNUM) == cutMax - 1) {
			tempCompletedPrice = map.get(COMPLETED_PRICE) + endCutPrice;
		}
		double currntCutPrice = tempCompletedPrice - map.get(COMPLETED_PRICE);
		map.put(COMPLETED_PRICE, tempCompletedPrice);// 更新已完成的砍价金额
		map.put(COMPLETED_CUTNUM, map.get(COMPLETED_CUTNUM) + 1);// 更新已完成的砍价次数
		completedPrice.add(currntCutPrice);
		if (debug) {
			System.out.println("第" + map.get(COMPLETED_CUTNUM) + "次砍价金额：" + currntCutPrice);
		}
		return true;
	}

	/**
	 * 功能：打印异常砍价情况
	 */
	private static void printException() {
		if (map.get(COMPLETED_PRICE) != targetPrice || map.get(COMPLETED_CUTNUM) > cutMax) {
			System.out.print("异常砍价情况:");
			System.out.print("已完成的砍价金额:" + map.get(COMPLETED_PRICE));
			System.out.print("，已完成的砍价次数:" + (map.get(COMPLETED_CUTNUM)));
		}
		System.out.println("");
	}

	/**
	 * 功能：初始化原始数据
	 */
	private static void init() {
		map.put(COMPLETED_PRICE, 0.0);// 初始化已完成的砍价金额
		map.put(COMPLETED_CUTNUM, 0.0);// 初始化已完成的砍价次数

		Collections.sort(initRandomPrice);// 从小到大排序

		randomPrice = new ArrayList<Double>(initRandomPrice.size());// 砍价算法使用的；临时砍价数组
		for (int i = initRandomPrice.size() - 1; i >= 0; i--) {
			randomPrice.add(initRandomPrice.get(i));
		}

		for (int i = cutMin; i <= cutMax; i++) {
			cutNum.add(i);
		}

	}
}
