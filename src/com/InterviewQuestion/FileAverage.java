package com.InterviewQuestion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.QuickSort;

/**
 * 分组思路： 1、求得平均值 avg = sum/n (四舍五入)。 2、从右到左，依次与平均值 avg进行比较，数本身大于或者多个数相加之和等于
 * 平均值avg,则分为一组。 3、将（剩下的数本身或者剩下的某几个数相加之和）与平均值avg之差的绝对值最小的，分为一组。
 * 4、将被分的数的位置置为-2。同时，剩下未被分组的数为一组。 5、分组结果全部存到resultArray数组中。
 */
public class FileAverage {
	public static final long MAXNUMBER = 999999999;// 初始化一个最大的数，用于判断分租之和与平均值的绝对值的最小值
	public static long[][] resultArray;// 分组结果
	public static long avg ;// 平均值
	private static long sum;// 文件大小总和
	private static long[] initArray;// 被分组的文件
	private static long num;// 统计已被分组的个数
	private static long min;// 最小绝对值
	private static long tempmin;// 最小绝对值
	private static boolean flag;// 是否保留该次结果
	private static int resultIndex ;// 用于判断是否达到了目标分组数
	private static int minIndex;// 过滤已被分组的下标
	private static List<Integer> list;// 记录本次分组的下标
	private static List<Integer> listbak;// 记录历史最佳分组的下标
	private static List<Integer> templistbak;// 临时变量

	private static void init(long[] array,int n) {
		sum=0;
		avg = 0;
		num = 0;
		min = MAXNUMBER;
		tempmin = MAXNUMBER;
		flag = false;
		resultIndex = 0;
		minIndex = 0;
		list = new ArrayList<>();
		listbak = new ArrayList<>();
		templistbak = new ArrayList<>();
		resultArray = new long[n][array.length];
		for (int i = 0; i < resultArray.length; i++) {// 初始化resultArray第一列全部为-1
			resultArray[i][0] = -1;
		}
		initArray = new long[array.length];
		QuickSort.quicksort(array, 0, array.length - 1);// 快排
		int v = 0;
		for (long t : array) {//复制一份排好序的被分组的文件
			initArray[v++] = t;
		}
		for (int i = 0; i < initArray.length; i++) {//计算所有值之和
			sum += initArray[i];
		}
		avg = Long.valueOf(new BigDecimal(String.valueOf(sum / (double) n)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		list.clear();// 清空记录的下标值
		listbak.clear();// 清空记录的下标值
	}
	
	/**
	 * @param initArray 被分组的文件
	 * @param n 分成n组
	 */
	public static void averageFile(long[] array,int n) {
		init(array,n);//初始化操作
		for (int i = initArray.length - 1; i >= 0; i--) {
			minIndex = i;
			if (initArray[i] > avg) {// 比平均值大的一部分的处理
				resultArray[resultIndex][0] = initArray[i];
				initArray[i] = -2;
				num++;
				resultIndex++;
			} else {// 比平均值小的一部分的处理
				int index = 0;
				long temp = 0;
				long linshi = 0;
				for (int h = minIndex; h >= 0; h--) {
					if (resultIndex >= n || num >= initArray.length) {// 数已被分组完毕
						break;
					}
					list.clear();// 清空记录的下标值
					listbak.clear();// 清空记录的下标值
					tempmin = MAXNUMBER;
					index = 0;
					temp = 0;
					linshi = 0;
					minAbs(h, index, temp, linshi,array);
				} // end--for
				break;
			}
		} // end--for
	}

	private static void minAbs(int start, int index, long temp, long linshi,long[] array) {
		for (int i = start; i >= 0; i--) {
			if (i + 1 < initArray.length - 1) {
				if (initArray[i + 1] == -1) {
					initArray[i + 1] = linshi;
				}
				if (initArray[i] == -2) {
					continue;
				}
			}
			if (initArray[i] == -2) {
				continue;
			}
			temp += initArray[i];
			list.add(i);
			if (temp == avg) {
				linshi = initArray[0];
				break;
			}
			if (temp > avg) {
				if (Math.abs(temp - initArray[i] - avg) > Math.abs(temp - avg)) {
					if (temp < min) {
						min = temp;
						flag = true;
					}
				} else {
					if ((temp - initArray[i]) < min) {
						min = temp - initArray[i];
						flag = true;
					}
					if ((temp - initArray[i]) == min) {
						flag = true;
					}
				}
				if (flag) {
					flag = false;
					templistbak.clear();
					templistbak.add(i);
					temp -= initArray[i];
					linshi = initArray[i];
					initArray[i] = -1;
					if (min != tempmin) {
						if (min < tempmin) {
							listbak.clear();
							listbak.addAll(list);
							list.removeAll(templistbak);
						} else {
							list.clear();
							list.addAll(listbak);
						}
					} else {
						list.removeAll(templistbak);
						listbak.clear();
						listbak.addAll(list);
					}
					i--;
					tempmin = min;
					minAbs(i, index, temp, linshi,array);
					break;
				}
			} else {
				linshi = initArray[i];
			} // end--if

		} // end--for
		
		/*************************将分组的信息，保存到resultArray数组中*************************/
		if (list.size() > 0) {
			List<Integer> templist = new ArrayList<>();// 挑选最佳的结果
			long temp1 = 0;
			for (int t : listbak) {
				temp1 += array[t];
			}
			long temp2 = 0;
			for (int t : list) {
				temp2 += array[t];
			}
			if (Math.abs(temp1 - avg) > Math.abs(temp2 - avg)) {
				templist.addAll(list);
			} else {
				templist.addAll(listbak);
			}
			int initValue=0;
			for (int tag : templist) {
				resultArray[resultIndex][index++] = initArray[tag];
				initArray[tag] = -2;
				initValue=tag;
				num++;
			}
			resultIndex++;
			index = 0;
			temp = 0;
			min = MAXNUMBER;
			if(initValue==0){
				initArray[initValue] = -2;
			}else{
				initArray[0] = linshi;
			}
			list.clear();
			listbak.clear();
		} else {
			linshi = initArray[0];
		}
	}
	
	public static void main(String[] args) {
//		long[] array = { 5, 30, 78, 34, 50, 88, 66, 67};//被分组的文件
//		long[] array = { 1, 2, 3, 4, 5, 10 };// 被分组的文件
//		long[] array = { 1, 2, 2, 50, 100, 1000 };// 被分组的文件
		long[] array = { 1, 1, 1, 1, 1, 1, 2, 5, 1, 2, 1 };// 被分组的文件
		averageFile(array,7);

		System.out.println("分组结果:");
		for (int i = 0; i < resultArray.length; i++) {
			long count = 0;
			for (int j = 0; j < resultArray[i].length; j++) {
				count += resultArray[i][j];
				System.out.print(resultArray[i][j] + " ");
			}
			System.out.println("------>" + count + "(和)," + avg + "(平均值),和与平均值相差的绝对值 = " + Math.abs(count - avg));
		} // end--for
	}

}