package com.createlunbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lunbo {

	public static int commonDiv(int a, int b) {// 求最大公约数
		int temp;
		if (a < b) { // swap(a,b) 交换a和b
			temp = b;
			b = a;
			a = temp;
		}
		temp = 0;
		while ((temp = a % b) != 0) // 辗转相除
		{
			a = b;
			b = temp;
		}
		return b;
	}

	public static int commonMul(int a, int b) { // 两个数的最小公倍数 ＝ 两数乘积 / 最大公约数
		return a * b / commonDiv(a, b);
	}

	public static int nCommonMul(int[] pa, int n) {// N个数的最小公倍数
		if (n == 0) {
			return pa[n];
		}
		return commonMul(pa[n - 1], nCommonMul(pa, n - 1));
	}

	/**
	 * 按优先级从高到低，调整为分子之和等于最小公倍数。
	 */
	public static void sortRule(int[][] lunboData, int gongbeishu) {
		int temp = gongbeishu;
		int len = lunboData.length;
		for (int i = 0; i < len; i++) {
			int fenzi = lunboData[i][0] * (gongbeishu / lunboData[i][1]);
			temp -= fenzi;
			if (temp < 0) {
				lunboData[i][0] = temp + fenzi;
				lunboData[i][1] = gongbeishu;
			}
		}
		// 按分母从小到大排序
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (lunboData[i][1] > lunboData[j][1]) {
					int[] tempData = lunboData[i];
					lunboData[i] = lunboData[j];
					lunboData[j] = tempData;
				}
			}
		}
		// 将(轮数/总轮数)化简
		toSimple(lunboData);
	}

	public static void toSimple(int[][] lunboData) {
		for (int i = 0, len = lunboData.length; i < len; i++) {
			if (lunboData[i][0] == 0) {
				continue;
			}
			int gongyueshu = commonDiv(lunboData[i][0], lunboData[i][1]);
			lunboData[i][0] /= gongyueshu;
			lunboData[i][1] /= gongyueshu;

		}
	}

	/**
	 * 乱序排列:
	 * 
	 */
	public static void OutofOrder(int[] result, int size) {
		int temp = 0;
		for (int g = 0; g < result.length; g += size) {
			for (int i = g, j = g + size - 1; i < j;) {
				if (result[i] == 0) {
					i++;
					continue;
				}
				if (result[j] == 0) {
					j--;
					continue;
				}
				temp = result[i++];
				result[i++] = result[j--];
				result[j--] = temp;
			}
		}
	}

	/**
	 * 轮播序列,优先级从高到低。
	 */
	public static Map<String, int[]> LunboSeqRule(Map<String, List<int[]>> posCaroNum) {
		Map<String, int[]> resultPosCaroNum = new HashMap<>();
		for (String pos : posCaroNum.keySet()) {
			List<int[]> lunboDataback = posCaroNum.get(pos);
			int[][] lunboData = new int[lunboDataback.size()][3];
			for (int i = 0; i < lunboData.length; i++) {
				lunboData[i][0] = lunboDataback.get(i)[0];
				lunboData[i][1] = lunboDataback.get(i)[1];
				lunboData[i][2] = lunboDataback.get(i)[2];
			}

			int sum = 0;// 循环次数
			int dataLen = lunboData.length;
			// 将传过来的(轮数/总轮数)化简
			toSimple(lunboData);
			int[] fenmu = new int[dataLen];
			for (int i = 0; i < dataLen; i++) {
				fenmu[i] = lunboData[i][1];
			}
			int gongbeishu = nCommonMul(fenmu, dataLen);// 该广告位总轮播最小公倍数
			System.out.println("公倍数：" + gongbeishu);
			int seqLength = gongbeishu;
			int resultlength = seqLength;
			if (seqLength == 1) {
				seqLength = lunboDataback.get(0)[1];
				resultlength = seqLength;
				if (seqLength == 1) {
					seqLength++;
				}
			}
			String[][] castSeq = new String[dataLen][seqLength];// 投放id--》轮播序列
			int[] result = new int[resultlength];// 最终的轮播序列
			sortRule(lunboData, gongbeishu);

			int[] index = new int[dataLen];// 存放每只广告循环一次大周期后，处在的位置下标，下一次从该位置循环
			StringBuilder seqString = new StringBuilder(gongbeishu);
			/**
			 * 按周期最大的为间隔，先满足每个大周期内包含的小周期的要求， 对于某些跨度到下一个大周期的小周期，先不管，到下一个大周期再做处理
			 */
			int cycle = 0;
			int len = lunboData[dataLen - 1][1];
			int j = 0;
			if (lunboData[0][0] == 1) {
				cycle = 0;
			} else {
				cycle = 1;
			}
			for (int g = 0; g < gongbeishu; g += len) {
				for (int i = 0; i < dataLen && lunboData[i][0] > 0; i++) {
					if (cycle % 2 == 0) {
						if (lunboData[i][0] == 1) {
							cycle = 0;
						} else {
							cycle = 1;
						}
					}
					if (lunboData[i][0] == 1) {// 遇到分子为1的时候，cycle++，奇数从前往后插，偶数从后往前插
						cycle++;
					}
					seqString.delete(0, seqString.length());
					j = index[i];
					for (; j + lunboData[i][1] <= len + g; j += lunboData[i][1]) {
						int num = 0;
						int index2 = j;
						int index1 = 0;
						// 奇数从前往后插
						if (cycle % 2 != 0) {
							for (int v = j; v < j + lunboData[i][1]; v++, index1++) {
								if (num >= lunboData[i][0]) {// 该周期内已经满足该支广告的序列个数要求
									break;
								}
								if (result[v] != 0) {
									continue;
								} else {
									sum++;
									result[v] = lunboData[i][2];
									seqString.append(v + 1).append(",");
									num++;
								}
							} // end--for
						} else {
							// 偶数从后往前插
							index1 = j + lunboData[i][1] - 1;
							for (int v = j + lunboData[i][1] - 1; v >= j; v--, index1--) {
								if (num >= lunboData[i][0]) {// 该周期内已经满足该支广告的序列个数要求
									break;
								}
								if (result[v] != 0) {
									continue;
								} else {
									sum++;
									result[v] = lunboData[i][2];
									seqString.append(v + 1).append(",");
									num++;
								}
							} // end--for
						}
						if (num < lunboData[i][0]) {
							for (int t = index2; t < j + lunboData[i][1]; t++) {
								if (result[t] == 0) {
									result[t] = lunboData[i][2];
									seqString.append(t + 1).append(",");
									num++;
								}
								if (num >= lunboData[i][0]) {
									break;
								}
							}
						}
					} // end--for
					index[i] = j;
					castSeq[i][0] = String.valueOf(lunboData[i][2]);
					String temp = seqString.toString();
					temp = temp.substring(0, seqString.length() - 1);
					castSeq[i][1] = temp;

				} // end--for

			} // end--for
			/**
			 * 乱序排列:
			 */
//			OutofOrder(result, lunboData[0][1]);
			System.out.println("共循环:" + sum + "次");
			if (gongbeishu == 1) {
				seqString.delete(0, seqString.length());
				seqString.append(castSeq[0][1]);
				for (int i = 1; i < result.length; i++) {
					seqString.append(",").append(i + 1);
					result[i] = result[0];
				}
				castSeq[0][1] = seqString.toString();
			}
			resultPosCaroNum.put(pos, result);
		}
		return resultPosCaroNum;
	}

	public static void main(String[] args) {
		/**
		 * 投放ID,优先级假设已经是按从高到低排列,如下例子：new int[]{ 3, 7, 111
		 * }：3代表预定投放轮数，7代表总轮数，111代表对应的投放ID
		 */
		Map<String, List<int[]>> posCaroNum = new HashMap<>();
		List<int[]> init1 = new ArrayList<>();
		init1.add(new int[] { 3, 7, 111 });
		init1.add(new int[] { 1, 7, 222 });
		init1.add(new int[] { 2, 7, 333 });
		init1.add(new int[] { 2, 21, 444 });
		init1.add(new int[] { 1, 21, 555 });
		posCaroNum.put("1", init1);
		List<int[]> init2 = new ArrayList<>();
		init2.add(new int[] { 3, 5, 111 });
		init2.add(new int[] { 1, 3, 222 });
		init2.add(new int[] { 1, 15, 333 });
		posCaroNum.put("2", init2);
		List<int[]> init3 = new ArrayList<>();
		init3.add(new int[] { 1, 3, 111 });
		init3.add(new int[] { 3, 10, 222 });
		init3.add(new int[] { 4, 15, 333 });
		init3.add(new int[] { 1, 10, 4444 });
		posCaroNum.put("3", init3);

		// List<int[]> init4 = new ArrayList<>();
		// init4.add(new int[] { 5, 21, 111 });
		// init4.add(new int[] { 3, 14, 222 });
		// init4.add(new int[] { 4, 21, 333 });
		// init4.add(new int[] { 1, 6, 4444 });
		// init4.add(new int[] { 1, 7, 5555 });
		// init4.add(new int[] { 1, 21, 6666 });
		// posCaroNum.put("4", init4);

		// List<int[]> init5 = new ArrayList<>();
		// init5.add(new int[] { 7, 10, 111 });
		// init5.add(new int[] { 4, 15, 222 });
		// posCaroNum.put("5", init5);

		// List<int[]> init6 = new ArrayList<>();
		// init6.add(new int[] { 2, 2, 111 });
		// init6.add(new int[] { 3, 3, 222 });
		// posCaroNum.put("6", init6);

		// List<int[]> init7 = new ArrayList<>();
		// init7.add(new int[] { 13, 40, 111 });
		// init7.add(new int[] { 5, 16, 222 });
		// init7.add(new int[] { 3, 10, 333 });
		// init7.add(new int[] { 1, 16, 444 });
		// posCaroNum.put("7", init7);

		Map<String, int[]> castSeq = null;// 最终的投放ID对应的投放序列
		double start = System.currentTimeMillis();
		// for (int i = 0; i < 1000; i++) {
		castSeq = LunboSeqRule(posCaroNum);
		// }
		System.out.println("算法耗时：" + (System.currentTimeMillis() - start) + "毫秒");

		// 以下是按[A,B,A,C,A,B,B,C]格式打印，便于验证算法有效性，不属于算法内容。

		for (String pos : castSeq.keySet()) {
			System.out.print("广告位(" + pos + "):");
			int len = posCaroNum.get(pos).size() - 1;
			for (int i = 0; i < len; i++) {
				System.out.print(posCaroNum.get(pos).get(i)[2] + "出现" + posCaroNum.get(pos).get(i)[0] + "/" + posCaroNum.get(pos).get(i)[1] + "次；");
			}
			System.out.println(posCaroNum.get(pos).get(len)[2] + "出现" + posCaroNum.get(pos).get(len)[0] + "/" + posCaroNum.get(pos).get(len)[1] + "次。");
			int[] seq = castSeq.get(pos);
			for (int i = 0; i < seq.length - 1; i++) {
				System.out.print(seq[i] + ",");
			}
			System.out.print(seq[seq.length - 1]);
			System.out.println();
			System.out.println();
		}
		System.out.println("测试OutofOrder");
		int[] temp = { 111,111,111,333,333,444,222,111,111,111,333,333,444,222,111,111,111,333,333,555,222 };
		OutofOrder(temp, 3);
		for (int i : temp) {
			System.out.print(i + ",");

		}
	}
}
