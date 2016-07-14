package com.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class Test {

	public static void main(String[] args) throws Exception{
		MProtect mProtect = new MProtect();
		Set<Integer> castIds = new HashSet<Integer>();
		for (int i = 1; i <= 5; i++) {
			castIds.add(i);
		}
		mProtect.setCastIds(castIds);
		mProtect.setId(5);
		Date date=new Date();
		mProtect.setEndtime(date);
		ProtectResult protectResult = new ProtectResult();
		List<Integer> protectIds = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			protectIds.add(i);
		}
		Set<Integer> whiteCastIds = new HashSet<Integer>();
		for (int i = 1; i <= 10; i++) {
			whiteCastIds.add(i);
		}
		Set<Integer> blackCastIds = new HashSet<Integer>();
		for (int i = 1; i <= 10; i++) {
			blackCastIds.add(i);
		}
		protectResult.setBlackCastIds(blackCastIds);
		protectResult.setWhiteCastIds(whiteCastIds);
		protectResult.setProtectIds(protectIds);
		System.out.println("====================原数据================");
		System.out.println("=====whiteCastIds:");
		for(Integer i:whiteCastIds){
			System.out.print(i+" ");
		}
		
		
		System.out.println();
		System.out.println("=====blackCastIds:");
		for(Integer i:blackCastIds){
			System.out.print(i+" ");
		}
		System.out.println();
		System.out.println("=====protectIds:");
		for(Integer i:protectIds){
			System.out.print(i+" ");
		}
		System.out.println();
		System.out.println("过去date="+mProtect.getEndtime());
		
		Thread.sleep(3000);
		System.out.println("+++++++++++++");
		System.out.println("当前date="+new Date());
		if(mProtect.getEndtime().before(new Date())){
			System.out.println("===========================");
			List<Integer> temp=new ArrayList<>();
			temp.add(mProtect.getId());
			protectResult.getProtectIds().removeAll(temp);
			protectResult.getWhiteCastIds().removeAll(mProtect.getCastIds());
			protectResult.getBlackCastIds().addAll(mProtect.getCastIds());
		}
		System.out.println("====================处理后的数据================");
		System.out.println("=====whiteCastIds:");
		for(Integer i:protectResult.getWhiteCastIds()){
			System.out.print(i+" ");
		}
		System.out.println();
		System.out.println("=====blackCastIds:");
		for(Integer i:protectResult.getBlackCastIds()){
			System.out.print(i+" ");
		}
		System.out.println();
		System.out.println("=====protectIds:");
		for(Integer i:protectResult.getProtectIds()){
			System.out.print(i+" ");
		}
		System.out.println();
	}

}
