package com.adposition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortPosition {
	private final static int LEN = 36;
	// 页面编号-->商品类型(a,s,g)-->位置
	private static Map<Integer, Map<Integer, Integer>> map = new HashMap<>();

	public  List<Integer> sort(int pageNum, int[] adArray, int[] specialArray, int[] generalArray) {
		 List<Integer> list =new ArrayList<Integer>();
		 
		 //先将普通商品放进list中 
		 for(int i=0,size=specialArray.length;i<LEN;i++){
			 list.add(i,specialArray[i]);
		 }
		 //将有广告位
		 
		 //将上一页剩余的商品map放入指定位置
		 
		 return list;
	}

	public static void main(String[] args) {
		int[] adArray = { 3, 7, 12, 21, 32 };// 广告位，有指定位置
		int[] specialArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };// 人工指定的商品，有指定位置
		int generalArray = 10;// 普通商品，没有指定位置

	}

}
