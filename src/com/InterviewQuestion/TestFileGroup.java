package com.InterviewQuestion;

import java.math.BigDecimal;
import java.util.Date;

public class TestFileGroup {

	public static void main(String[] args) {
		 long[] array = { 5, 30, 78, 34, 50, 88, 66, 67 };// 被分组的文件
//		 long[] array = { 1, 2, 3, 4, 5, 10 };// 被分组的文件
//		 long[] array = { 1, 2, 2, 50, 100, 1000 };// 被分组的文件
//		 long[] array = { 4, 5, 6, 11, 24, 39, 101, 120, 140 };// 被分组的文件
//		long[] array = { 1, 5, 6, 7, 11, 18, 23, 45, 98, 150, 160 };// 被分组的文件
//		long[] array = { 1, 1, 1, 1, 1, 1, 2, 5, 1, 2, 1 };// 被分组的文件
		int n = 0;// 分组数
		long[][] resultArray;// 分组结果
		long sum = 0;// 文件大小总和
		for (int i = 0; i < array.length; i++) {// 计算所有值之和
			sum += array[i];
		}
		Date date = new Date();
		n = FileGroup.groupFile(array);
		resultArray = FileGroup.map.get(n);
		System.out.println("分组所用时间 = " + (new Date().getTime() - date.getTime()) + " 毫秒");
		System.out.println("分组结果:");
		for (int i = 0; i < resultArray.length; i++) {
			long count = 0;
			long avg = 0;
			for (int j = 0; j < resultArray[i].length; j++) {
				count += resultArray[i][j];
				System.out.print(resultArray[i][j] + " ");
			}
			avg = Long.valueOf(new BigDecimal(String.valueOf(sum / (double) n)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
			System.out.println("------>" + count + "(和)," + avg + "(平均值),和与平均值相差的绝对值 = " + Math.abs(count - avg));
		} // end--for
	}

}
