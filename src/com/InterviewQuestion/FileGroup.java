package com.InterviewQuestion;

import java.util.HashMap;
import java.util.Map;

/**
 * 问题描述： 
 *    如何将一堆大小不等的文件，快速分成几组，使得各组内文件大小之和最接近？ 
 *  提示：各组之和，分别与 所有文件大小之和除以组数 的平均值最接近。
 *  注：组数不限，组内文件个数不限，且至少得分成两组。
 */
public class FileGroup {
	public static Map<Integer, long[][]> map;
	private static int n;// 最佳分组数
	private static long[] avgArray;// 记录不同组数下平均值avg
	private static boolean mark;// 对于分组结果第一列有-1发生，说明被分文件数已经分组完毕，此时不应该再求该分组数下的绝对值。

	private static void init(long[] array) {
		n = 0;
		mark = false;
		avgArray = new long[array.length];
		for (int i = 0; i < array.length; i++) {// 找最小的绝对值，因此初始值取最大
			avgArray[i] = FileAverage.MAXNUMBER;
		}
		map = new HashMap<Integer, long[][]>();
	}

	public static int groupFile(long[] array) {
		init(array);
		for (int i = 2; i <= array.length; i++) {
			mark = true;
			FileAverage.averageFile(array, i);// 调用分组算法
			map.put(i, FileAverage.resultArray);// 保存分组结果
			for (int t = 0; t < FileAverage.resultArray.length; t++) {
				if (FileAverage.resultArray[t][0] == -1) {
					mark = false;
				}
			}
			if (mark) {
				computeEveryAvg(FileAverage.resultArray, FileAverage.avg, i);
			}

		}
		return findMinAvg();
	}

	/**
	 * 思路： 分组结果中每组与平均值的绝对值之和，除以分组数等到的平均值avg，存放到数组avgArray中
	 * 
	 * @param resultArray
	 *            分组结果
	 * @param avg
	 *            全部文件大小和，除以分组数的平均值
	 * @param number
	 *            分组数
	 */
	private static void computeEveryAvg(long[][] resultArray, long avg, int number) {
		long avgSum = 0;// 每组平均值相加之和
		for (int i = 0; i < resultArray.length; i++) {
			long count = 0;
			for (int j = 0; j < resultArray[i].length; j++) {// 统计每一组的和
				if(resultArray[i][j]==0){
					break;
				}
				count += resultArray[i][j];
			}
			avgSum += Math.abs(count - avg);
		} // end--for
		avgArray[number - 1] = (long) (avgSum / (double) number);
	}

	/**
	 * 思路： 基于数组avgArray，找出最佳分组数n。
	 */
	private static int findMinAvg() {
		long minAvg = avgArray[1];
		n = 2;
		for (int i = 2; i < avgArray.length; i++) {
			if (minAvg >= avgArray[i]) {//如果当绝对值平均值一样的时候，要求分组最少，就可以不取等于的情况。否则，加上等于。
				minAvg = avgArray[i];
				n = i + 1;
			}
		}
		return n;
	}
}