package com;

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
	 * 先调整周期最大的
	 */
	public static void modifyMaxCycle(int[] result, int[][] lunboData, int[] index, int start, int len) {
		for (int g = lunboData.length - 1; g > 0; g--) {
			if (lunboData[g][1] < len) {
				break;
			}
			for (int maxi = start, maxj = start + len - 1; maxi < maxj;) {
				if (result[maxi] != 0) {
					maxi += 1;
					continue;
				}
				if (result[maxj] != lunboData[g][2]) {
					maxj--;
					continue;
				}
				result[maxi++] = result[maxj];
				result[maxj--] = 0;
			} // end--for
		}
	}

	/**
	 * 按周期从小到大，将本周起内前面为0的，用本周期从后向前找本周期的数据填充
	 */
	public static void modifyCycle(int[] result, int start, int size, int value) {
		for (int i = start, j = size + start - 1; i < j;) {
			if (result[i] != 0) {
				i++;
				continue;
			}
			if (result[j] != value) {
				j--;
				continue;
			}
			result[i++] = result[j];
			result[j--] = 0;
		} // end--for
	}

	/**
	 * 轮播序列,优先级从高到低。
	 */
	public static Map<String, int[]> LunboSeqRule(Map<String, List<int[]>> posCaroNum) {
		Map<String, int[]> resultPosCaroNum = new HashMap<>();
		for (String pos : posCaroNum.keySet()) {
			List<int[]> lunboDataback = posCaroNum.get(pos);
			if (lunboDataback == null || lunboDataback.size() <= 0) {
				continue;
			}
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
			// System.out.println("公倍数：" + gongbeishu);
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
			int cycle = 0;
			int zeroNum = 0;
			/**
			 * 按周期最大的为间隔，先满足每个大周期内包含的小周期的要求， 对于某些跨度到下一个大周期的小周期，先不管，到下一个大周期再做处理
			 */
			int len = lunboData[dataLen - 1][1];
			int j = 0;
			if (lunboData[0][0] == 1) {
				cycle = 0;
			} else {
				cycle = 1;
			}
			// int g=0;
			for (int g = 0; g < gongbeishu - len * 0; g += len) {
				zeroNum = 0;
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
									if (index1 == 0 || (lunboData[i][2] != result[v - 1] && num < lunboData[i][0])) {
										result[v] = lunboData[i][2];
										seqString.append(v + 1).append(",");
										num++;
									} else {
										if (result[v] == 0 && result[index2] != 0) {
											index2 = v;
										}
									}
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
									if (index1 == j + lunboData[i][1] - 1 || (lunboData[i][2] != result[v - 1] && num < lunboData[i][0])) {
										result[v] = lunboData[i][2];
										seqString.append(v + 1).append(",");
										num++;
									} else {
										if (result[v] == 0 && result[index2] != 0) {
											index2 = v;
										}
									}
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
						// modifyCycle(result, index[i],
						// lunboData[i][1],j,lunboData[i][2]);
					index[i] = j;
					castSeq[i][0] = String.valueOf(lunboData[i][2]);
					String temp = seqString.toString();
					temp = temp.substring(0, seqString.length() - 1);
					castSeq[i][1] = temp;
				} // end--for
				for (int i = g; i < g + len; i++) {
					if (result[i] == 0) {
						zeroNum++;
						break;
					}
				}
				if (zeroNum != 0) {// 依次将最大周期位置从后往前调整到为0的位置上。
					// modifyMaxCycle(result, lunboData, index, g, len);
				}

			} // end--for
				// System.out.println("共循环:" + sum + "次");
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

	public static void computeJiange(Map<Integer, int[]> datajiange, int gongbeishu, int[][] lunboData) {
		for (int jiangei = 0; jiangei < lunboData.length; jiangei++) {
			int jiangeNum = gongbeishu / lunboData[jiangei][1];
			int[] jiange = new int[jiangeNum];
			for (int jiangecount = 0, jiangej = lunboData[jiangei][1]; jiangej <= gongbeishu; jiangecount++, jiangej += lunboData[jiangei][1]) {
				jiange[jiangecount] = jiangej;
			}
			datajiange.put(jiangei, jiange);
		}
	}

	/**
	 * 轮播序列,优先级从高到低。
	 */
	public static Map<String, int[]> LunboSeqRule2(Map<String, List<int[]>> posCaroNum) {
		Map<String, int[]> resultPosCaroNum = new HashMap<>();
		for (String pos : posCaroNum.keySet()) {
			List<int[]> lunboDataback = posCaroNum.get(pos);
			Map<Integer, int[]> datajiange = new HashMap<>(lunboDataback.size());
			if (lunboDataback == null || lunboDataback.size() <= 0) {
				continue;
			}
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
			// 记录各个数的周期间隔记录下来
			computeJiange(datajiange, gongbeishu, lunboData);

			int[] index = new int[dataLen];// 存放每只广告循环一次大周期后，处在的位置下标，下一次从该位置循环
			StringBuilder seqString = new StringBuilder(gongbeishu);
			int cycle = 0;
			int zeroNum = 0;
			/**
			 * 按周期最大的为间隔，先满足每个大周期内包含的小周期的要求， 对于某些跨度到下一个大周期的小周期，先不管，到下一个大周期再做处理
			 */
			int len = 0;
			int lun = 0;// 主要便于不区别分母中是否有和公倍数相等的分数的个数，如果有，则最后排这些分数
			for (int counti = dataLen - 1; counti >= 0; counti--) {
				if (lunboData[counti][1] >= gongbeishu) {
					continue;
				}
				len = lunboData[counti][1];
				lun = counti + 1;
				break;
			} // end--for
			if (len == 0) {
				len = gongbeishu;
			}
			int j = 0;
			if (lunboData[0][0] == 1) {
				cycle = 0;
			} else {
				cycle = 1;
			}
			// int g=0;
			// 当有分数分母刚好等于公倍数的，先排除，按最大的分母为周期，执行下面的操作
			for (int g = 0; g < gongbeishu - len * 0; g += len) {
				zeroNum = 0;
				for (int cyclei = 0; cyclei < lun && lunboData[cyclei][0] > 0; cyclei++) {
					if (cycle % 2 == 0) {
						if (lunboData[cyclei][0] == 1) {
							cycle = 0;
						} else {
							cycle = 1;
						}
					}
					if (lunboData[cyclei][0] == 1) {// 遇到分子为1的时候，cycle++，奇数从前往后插，偶数从后往前插
						cycle++;
					}
					seqString.delete(0, seqString.length());
					j = index[cyclei];
					for (; j + lunboData[cyclei][1] <= len + g; j += lunboData[cyclei][1]) {
						int num = 0;
						int index2 = j;
						int index1 = 0;
						// 奇数从前往后插
						if (cycle % 2 != 0) {
							for (int v = j; v < j + lunboData[cyclei][1]; v++, index1++) {
								if (num >= lunboData[cyclei][0]) {// 该周期内已经满足该支广告的序列个数要求
									break;
								}
								if (result[v] != 0) {
									continue;
								} else {
									sum++;
									if (index1 == 0 || (lunboData[cyclei][2] != result[v - 1] && num < lunboData[cyclei][0])) {
										result[v] = lunboData[cyclei][2];
										seqString.append(v + 1).append(",");
										num++;
									} else {
										if (result[v] == 0 && result[index2] != 0) {
											index2 = v;
										}
									}
								}
							} // end--for
						} else {
							// 偶数从后往前插
							index1 = j + lunboData[cyclei][1] - 1;
							for (int v = j + lunboData[cyclei][1] - 1; v >= j; v--, index1--) {
								if (num >= lunboData[cyclei][0]) {// 该周期内已经满足该支广告的序列个数要求
									break;
								}
								if (result[v] != 0) {
									continue;
								} else {
									sum++;
									if (index1 == j + lunboData[cyclei][1] - 1 || (lunboData[cyclei][2] != result[v - 1] && num < lunboData[cyclei][0])) {
										result[v] = lunboData[cyclei][2];
										seqString.append(v + 1).append(",");
										num++;
									} else {
										if (result[v] == 0 && result[index2] != 0) {
											index2 = v;
										}
									}
								}
							} // end--for
						}
						if (num < lunboData[cyclei][0]) {
							for (int t = index2; t < j + lunboData[cyclei][1]; t++) {
								if (result[t] == 0) {
									result[t] = lunboData[cyclei][2];
									seqString.append(t + 1).append(",");
									num++;
								}
								if (num >= lunboData[cyclei][0]) {
									break;
								}
							}
						}
						if (index[cyclei] > 0 && index[cyclei] < g) {
							modifyCycle(result, index[cyclei], lunboData[cyclei][1], lunboData[cyclei][2]);
						}
						// 移动完后，可能本次插入数量不足，则往前，将前一个数在自己的周期内往后移
						int countThisLunData = 0;
						for (int luni = j + lunboData[cyclei][1] - 1; luni >= j; luni--) {
							if (result[luni] == lunboData[cyclei][2]) {
								countThisLunData++;
							}
						} // end--for
						if (countThisLunData < lunboData[cyclei][0]) {
							countThisLunData = lunboData[cyclei][0] - countThisLunData;// 需要移动的次数
							int buNum = 0;// 已经将最近的前一个数，再前一个数自己的周期内，向后移动的次数
							int end = 0;
							int[] endlunData = new int[lunboData.length];
							for (int yidong = cyclei - 1; yidong >= 0; yidong--) {// 前i-1个数
								if (buNum >= countThisLunData) {
									break;
								}
								end = lunboData[yidong][1];
								for (;; end += lunboData[yidong][1]) {
									if (end > g) {
										end -= lunboData[yidong][1];
										break;
									}
								}
								endlunData[yidong] = end;
								buNum = moveData(result, end, buNum, countThisLunData, lunboData[yidong][1], lunboData[yidong][2], lunboData[cyclei][2]);

								if (buNum == 0) {

									end += lunboData[yidong][1];

									if (end != j + lunboData[cyclei][1]) {
										for (;; end += lunboData[yidong][1]) {
											if (end > j + lunboData[cyclei][1]) {
												end -= lunboData[yidong][1];
												break;
											}
										}
										if (endlunData[yidong] < end) {
											endlunData[yidong] = end;
											buNum = moveData(result, end, buNum, countThisLunData, lunboData[yidong][1], lunboData[yidong][2], lunboData[cyclei][2]);
										} // end--if
									}
								} // end--if
							} // end--for
							for (int yidong = cyclei - 1; yidong >= 0; yidong--) {// 前i-1个数
								if (buNum >= countThisLunData) {
									break;
								}
								end = endlunData[yidong] + lunboData[yidong][1];
								endlunData[yidong] = end;
								if (end < j + lunboData[cyclei][1]) {
									buNum = moveData(result, end, buNum, countThisLunData, lunboData[yidong][1], lunboData[yidong][2], lunboData[cyclei][2]);
								}
							} // end--for
							if (buNum < countThisLunData) {
								// 从该数前一个数，依次循环往后移动,为了降低算法复杂度，需要提前将各个数的周期间隔记录下来
								cycleMoveData(result, datajiange, j + lunboData[cyclei][1], gongbeishu, countThisLunData, buNum, lunboData, cyclei,endlunData);
							}
						} // end-if
					} // end--for
					index[cyclei] = j;
					castSeq[cyclei][0] = String.valueOf(lunboData[cyclei][2]);
					String temp = seqString.toString();
					temp = temp.substring(0, seqString.length() - 1);
					castSeq[cyclei][1] = temp;
				} // end--for
				for (int zeroi = g; zeroi < g + len; zeroi++) {
					if (result[zeroi] == 0) {
						zeroNum++;
						break;
					}
				}
				if (zeroNum != 0) {// 依次将最大周期位置从后往前调整到为0的位置上。
					modifyMaxCycle(result, lunboData, index, g, len);
				}
			} // end--for
				// 对分数分母刚好等于公倍数的，任意插入到为0的位置上
			for (int teshui = dataLen - 1; teshui >= 0; teshui--) {
				if (lunboData[teshui][1] != gongbeishu) {
					break;
				}
				int resultIndex = 0;
				for (int charuNum = 0; charuNum < lunboData[teshui][0]; charuNum++) {
					for (; resultIndex < result.length; resultIndex++) {
						if (result[resultIndex] != 0) {
							continue;
						}
						result[resultIndex] = lunboData[teshui][2];
						break;
					}
				} // end--for
			} // end--for
				// System.out.println("共循环:" + sum + "次");
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

	public static void cycleMoveData(int[] result, Map<Integer, int[]> datajiange, int start, int gongbeishu, int countThisLunData, int buNum, int[][] lunboData, int cycleIndex,int[] endlunData) {
		// 从插入个数不足的数据的下一个周期开始，到result数组结束，找到为0的数据的位置
		int indexZero = 0;
		for (int movei = result.length - 1; movei >= 0; movei--) {
			if (result[movei] == 0) {
				indexZero = movei + 1;
			}
		}
		int orgin = indexZero;
		for (int movei = cycleIndex; movei >= 0; movei--) {
			if(movei>0){
			endlunData[movei-1] -= lunboData[movei-1][1];
			}
			if (buNum >= countThisLunData) {
				break;
			}
			int[] jiangge = datajiange.get(movei);
			for (int jiangeArray = jiangge.length - 1; jiangeArray > 0; jiangeArray--) {
				if (buNum >= countThisLunData) {
					break;
				}
				if (jiangge[jiangeArray - 1] <= start) {
					break;
				}
				if (jiangge[jiangeArray] >= indexZero && jiangge[jiangeArray - 1] < indexZero && jiangge[jiangeArray - 1] > start) {
					// 将该数在该周期内，从前往后，将该数据移动到该周期末尾为零的位置
					indexZero = moveData(result, jiangge[jiangeArray - 1], buNum, countThisLunData, lunboData[movei][1], lunboData[movei][2]);
				}
				if (orgin != indexZero) {
					orgin = indexZero;
					break;
				}
			} // end--for
		} // end--for
		//移动
		for (int yidong = cycleIndex - 1; yidong >= 0; yidong--) {// 前i-1个数
			if (buNum >= countThisLunData) {
				break;
			}
			if (endlunData[yidong]  < start) {
				buNum = moveData(result, endlunData[yidong], buNum, countThisLunData, lunboData[yidong][1], lunboData[yidong][2], lunboData[cycleIndex][2]);
			}
		} // end--for
	}

	public static int moveData(int[] result, int start, int buNum, int countThisLunData, int fenmu, int castid) {
		for (int movei = start, movej = start + fenmu - 1; movei < movej;) {
			if (buNum >= countThisLunData) {
				break;
			}
			if (result[movei] != castid) {
				movei++;
				continue;
			}
			if (result[movej] != 0) {
				movej--;
				continue;
			}
			if (result[movei] == castid) {
				result[movej--] = result[movei];
				result[movei++] = 0;
				buNum++;
			} else {
				movei++;
			}
		} // end--for
		int zero = 0;
		for (int movei = start; movei < start + fenmu - 1; movei++) {
			if (result[movei] == 0) {
				zero = movei + 1;
				break;
			}
		}
		return zero;
	}

	public static int moveData(int[] result, int end, int buNum, int countThisLunData, int fenmu, int castid, int replaceid) {
		for (int neibui = end, neibuj = end + fenmu - 1; neibui < neibuj;) {
			if (buNum >= countThisLunData) {
				break;
			}
			if (result[neibui] == 0 || result[neibui] != castid) {
				neibui++;
				continue;
			}
			if (result[neibuj] != 0) {
				neibuj--;
				continue;
			}
			if (result[neibui] == castid) {
				result[neibuj--] = result[neibui];
				result[neibui++] = replaceid;
				buNum++;
			} else {
				neibui++;
			}
		} // end--for
		return buNum;
	}

	public static void main(String[] args) {
		/**
		 * 投放ID,优先级假设已经是按从高到低排列,如下例子：new int[]{ 3, 7, 111
		 * }：3代表预定投放轮数，7代表总轮数，111代表对应的投放ID
		 */
		Map<String, List<int[]>> posCaroNum = new HashMap<>();
		// List<int[]> init1 = new ArrayList<>();
		// init1.add(new int[] { 3, 7, 111 });
		// init1.add(new int[] { 1, 7, 222 });
		// init1.add(new int[] { 2, 7, 333 });
		// init1.add(new int[] { 2, 21, 444 });
		// init1.add(new int[] { 1, 21, 555 });
		// posCaroNum.put("1", init1);
		// List<int[]> init2 = new ArrayList<>();
		// init2.add(new int[] { 3, 5, 111 });
		// init2.add(new int[] { 1, 3, 222 });
		// init2.add(new int[] { 1, 15, 333 });
		// posCaroNum.put("2", init2);
		// List<int[]> init3 = new ArrayList<>();
		// init3.add(new int[] { 1, 3, 111 });
		// init3.add(new int[] { 3, 10, 222 });
		// init3.add(new int[] { 4, 15, 333 });
		// init3.add(new int[] { 1, 10, 4444 });
		// posCaroNum.put("3", init3);
		//
		// List<int[]> init4 = new ArrayList<>();
		// init4.add(new int[] { 5, 21, 111 });
		// init4.add(new int[] { 3, 14, 222 });
		// init4.add(new int[] { 4, 21, 333 });
		// init4.add(new int[] { 1, 6, 444 });
		// init4.add(new int[] { 1, 7, 555 });
		// init4.add(new int[] { 1, 21, 666 });
		// posCaroNum.put("4", init4);
		//
		// List<int[]> init5 = new ArrayList<>();
		// init5.add(new int[] { 7, 10, 111 });
		// init5.add(new int[] { 4, 15, 222 });
		// posCaroNum.put("5", init5);
		// List<int[]> init6 = new ArrayList<>();
		// init6.add(new int[] { 2, 2, 111 });
		// init6.add(new int[] { 3, 3, 222 });
		// posCaroNum.put("6", init6);
		//
		// List<int[]> init7 = new ArrayList<>();
		// init7.add(new int[] { 13, 40, 111 });
		// init7.add(new int[] { 5, 16, 222 });
		// init7.add(new int[] { 3, 10, 333 });
		// init7.add(new int[] { 1, 16, 444 });
		// posCaroNum.put("7", init7);
		//
		// List<int[]> init8 = new ArrayList<>();
		// init8.add(new int[] { 5, 12, 111 });
		// init8.add(new int[] { 5, 28, 222 });
		// init8.add(new int[] { 17, 42, 333 });
		// posCaroNum.put("8", init8);
		// List<int[]> init9 = new ArrayList<>();
		// init9.add(new int[] { 1, 12, 111 });
		// init9.add(new int[] { 7, 15, 222 });
		// init9.add(new int[] { 9, 20, 333 });
		// posCaroNum.put("9", init9);
		// List<int[]> init10 = new ArrayList<>();
		// init10.add(new int[] { 7, 16, 111 });
		// init10.add(new int[] { 11, 24, 222 });
		// init10.add(new int[] { 5, 48, 333 });
		// posCaroNum.put("10", init10);
		// List<int[]> init11 = new ArrayList<>();
		// init11.add(new int[] { 7, 15, 111 });
		// init11.add(new int[] { 10, 21, 222 });
		// init11.add(new int[] { 2, 35, 333 });
		// posCaroNum.put("11", init11);
		// List<int[]> init12 = new ArrayList<>();
		// init12.add(new int[] { 5, 12, 111 });
		// init12.add(new int[] { 5, 28, 222 });
		// init12.add(new int[] { 17, 42, 333 });
		// posCaroNum.put("12", init12);
		// List<int[]> init13 = new ArrayList<>();
		// init13.add(new int[] { 13, 45, 11 });
		// init13.add(new int[] { 16, 55, 22 });
		// init13.add(new int[] { 7, 55, 33 });
		// init13.add(new int[] { 29, 99, 44 });
		// posCaroNum.put("13", init13);

		// List<int[]> init14 = new ArrayList<>();
		// init14.add(new int[] { 3, 14, 11 });
		// init14.add(new int[] { 4, 15, 22 });
		// init14.add(new int[] { 9, 35, 33 });
		// init14.add(new int[] { 11, 42, 44 });
		// posCaroNum.put("14", init14);

		// List<int[]> init15 = new ArrayList<>();
		// init15.add(new int[] { 9, 35, 11 });
		// init15.add(new int[] { 8, 35, 22 });
		// init15.add(new int[] { 14, 55, 33 });
		// init15.add(new int[] { 20, 77, 44 });
		// posCaroNum.put("15", init15);

		// List<int[]> init16 = new ArrayList<>();
		// init16.add(new int[] { 17, 110, 11 });
		// init16.add(new int[] { 19, 120, 22 });
		// init16.add(new int[] { 26, 165, 33 });
		// init16.add(new int[] { 41, 264, 44 });
		// init16.add(new int[] { 69, 440, 55 });
		// init16.add(new int[] { 27, 440, 66 });
		// init16.add(new int[] { 103, 660, 77 });
		// posCaroNum.put("16", init16);
//		List<int[]> init17 = new ArrayList<>();
//		init17.add(new int[] { 13, 84, 11 });
//		init17.add(new int[] { 31, 204, 22 });
//		init17.add(new int[] { 37, 238, 33 });
//		init17.add(new int[] { 55, 357, 44 });
//		init17.add(new int[] { 73, 476, 55 });
//		init17.add(new int[] { 37, 476, 66 });
//		init17.add(new int[] { 109, 714, 77 });
//		posCaroNum.put("17", init17);
//		95/396,79/330,43/180,118/495,43/990
		List<int[]> init17 = new ArrayList<>();
		init17.add(new int[] { 43, 180, 11 });
		init17.add(new int[] { 79,330, 22 });
		init17.add(new int[] { 95,396, 33 });
		init17.add(new int[] { 119,495,44 });
		init17.add(new int[] { 41,990, 55 });
		posCaroNum.put("17", init17);
		
		Map<String, int[]> castSeq = null;// 最终的投放ID对应的投放序列
		double start = System.currentTimeMillis();
		// for (int i = 0; i < 1000; i++) {

		castSeq = LunboSeqRule2(posCaroNum);
		// }
		System.out.println("算法耗时：" + (System.currentTimeMillis() - start) + "毫秒");

		// 以下是按[A,B,A,C,A,B,B,C]格式打印，便于验证算法有效性，不属于算法内容。

		for (String pos : castSeq.keySet()) {
			System.out.print("广告位(" + pos + "):");
			int len = posCaroNum.get(pos).size() - 1;
			int[] seq = castSeq.get(pos);
			System.out.println("序列长度=" + seq.length);
			int count = 0;
			int geshu = 0;
			for (int st = 0; st < len + 1; st++) {
				count = 0;
				for (int i = 0; i < len; i++) {
					System.out.print(posCaroNum.get(pos).get(i)[2] + "出现" + posCaroNum.get(pos).get(i)[0] + "/" + posCaroNum.get(pos).get(i)[1] + "次；");
				}
				System.out.println(posCaroNum.get(pos).get(len)[2] + "出现" + posCaroNum.get(pos).get(len)[0] + "/" + posCaroNum.get(pos).get(len)[1] + "次。");
				for (int i = 0; i < seq.length; i++) {
					if (seq[i] == 0) {
						count++;
					}
					if (seq[i] == posCaroNum.get(pos).get(st)[2]) {
						geshu++;
					}
					// System.out.print(seq[i] + "("+(i+1)+"),");
					//
					if (seq[i] == 0 && seq[i - 1] != 0) {
						System.out.print("(" + (i + 1) + ")");
					}
					System.out.print(seq[i]);
					System.out.print(",");
					if ((i + 1) % posCaroNum.get(pos).get(st)[1] == 0 && i != 0) {
						System.out.println("----" + (i + 1) + "---->" + posCaroNum.get(pos).get(st)[2] + "出现" + geshu + "次");
						geshu = 0;
					}
				}
				System.out.println();
				System.out.println("出现0的次数=" + count);
				System.out.println();
			}

		}
	}
}
