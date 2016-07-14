package com.InterviewQuestion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.QuickSort;

/**
 * ----昨夜偶遇一考题，辗转难眠因此题，盼得高手来解题，虚心求教解难题。--- 问题描述：
 * 如何将一堆大小不等的文件，快速分成几组，使得各组内文件大小之和最接近？ 提示：各组之和，分别与 所有文件大小之和除以组数 的平均值最接近。
 * 注：组数不限，组内文件个数不限，且至少得分成两组。
 *
 */
public class FileAverage {
	private static long[][] resultArray;// 分组结果
	private static long[] initArray = { 5, 30, 78, 34, 50, 88, 66, 67 };// 被分组的文件
	private static long sum = 0;// 文件大小总和
	private static long min = 999999999;// 最小绝对值
	private static long tempmin = 999999999;// 最小绝对值
	private static long avg = 0;// 平均值
	private static boolean flag = false;
	private static int resultIndex = 0;
	private static int minIndex = 0;// 分组结果中第一列，列数最大且不为-1的下标
	private static List<Integer> list = new ArrayList<>();// 临时记录分组的下标
	private static List<Integer> listbak = new ArrayList<>();// 临时记录分组的下标
	private static List<Integer> templistbak = new ArrayList<>();// 临时记录分组的下标
	static {
		for (int i = 0; i < initArray.length; i++) {
			sum += initArray[i];
		}
	}

	/**
	 * @param sum
	 *            文件大小总和
	 * @param n
	 *            分成的组数 5,30,34,50,66,67,78,88 1、求得平均值 avg = sum/n (四舍五入)。
	 *            2、从右到左，依次与平均值 avg进行比较，数本身大于 或者 多个数相加之和等于 平均值 avg,则分为一组。
	 *            3、将（剩下的数本身或者剩下的某几个数相加之和）与平均值 avg之差的绝对值最小的，分为一组。
	 *            4、将被分的数的位置置为-1。同时，剩下未被分组的数为一组。 5、分组结果全部存到resultArray数组中
	 */
	private static void averageFile(int n) {// 将文件分成n组
		resultArray = new long[n][initArray.length];
		// 初始化resultArray第一列全部为-1
		for (int i = 0; i < resultArray.length; i++) {
			resultArray[i][0] = -1;
		}
		avg = Long.valueOf(new BigDecimal(String.valueOf(sum / (double) n)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		list.clear();// 清空记录的下标值
		listbak.clear();// 清空记录的下标值
		for (int i = initArray.length - 1; i >= 0; i--) {
			minIndex = i;
			if (initArray[i] > avg) {
				resultArray[resultIndex][0] = initArray[i];
				initArray[i] = -1;
				resultIndex++;
			} else {
				// 比平均值小的一部分的处理
				int index = 0;
				long temp = 0;
				long linshi = 0;
				for (int h = minIndex; h >= 0; h--) {
					for(int t=initArray.length-1;t>=0;t--){
						if(initArray[t]!=-2){
							tempmin=initArray[t];
							break;
						}
					}
					index = 0;
					temp = 0;
					linshi = 0;
					minAbs(h, index, temp, linshi);
				}
				break;
			}
		} // end--for
		for (int i = 0; i < resultArray.length; i++) {
			for (int j = 0; j < resultArray[i].length; j++) {
				System.out.print(resultArray[i][j] + " ");
			}
			System.out.println();
		} // end--for
	}

	private static void minAbs(int start, int index, long temp, long linshi) {

		for (int i = start; i >= 0; i--) {
			if (i + 1 < initArray.length - 1) {
				if (initArray[i + 1] == -1) {
					initArray[i + 1] = linshi;
				}
				if (initArray[i] == -1) {
					initArray[i] = linshi;
				}
				if (initArray[i] == -2) {
					continue;
				}
			}
			temp += initArray[i];

			list.add(i);
			if (temp == avg) {
				for (int tag : list) {
					resultArray[resultIndex][++index] = initArray[tag];
					initArray[tag] = -1;
				}
				resultIndex++;
				index = 0;
				temp = 0;
				continue;
			}
			if (temp > avg) {
				if (Math.abs(temp - initArray[i] - avg) > Math.abs(temp - avg)) {
					if (temp < min) {
						min = temp;
						flag = true;
					}
				} else {
					if ((temp - initArray[i]) <= min) {
						min = temp - initArray[i];
						flag = true;
					}
				}
				if (flag) {

					flag = false;
					temp -= initArray[i];
					linshi = initArray[i];
					initArray[i] = -1;
					// minIndex -= 1;
					templistbak.clear();
					listbak.clear();
					templistbak.add(i);
					listbak.addAll(list);
					list.removeAll(templistbak);
					
					i--;
					// if(tempmin!=min){
					
					// }else{
					// tempmin=
					// }
					tempmin = min;
					minAbs(i, index, temp, linshi);
					break;
				}
			}
			 else {
			// minIndex -= 1;
				 if (Math.abs(temp - avg) > Math.abs(min - avg)) {
					 list.clear();
					 list.addAll(listbak);
				 }else{
					listbak.clear();
					listbak.addAll(list);
				 }
			 } // end--if

		} // end--for
		if (list.size() > 0) {
			for (int tag : list) {
				resultArray[resultIndex][index++] = initArray[tag];
				initArray[tag] = -2;
			}
			resultIndex++;
			index = 0;
			temp = 0;
			min = 999999999;
			
			initArray[0] = linshi;
			list.clear();
			
		}
	}

	public static void main(String[] args) {
		// 快排
		QuickSort.quicksort(initArray, 0, initArray.length - 1);
		averageFile(3);
	}

}
