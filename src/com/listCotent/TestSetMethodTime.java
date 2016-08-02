package com.listCotent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestSetMethodTime {

	public static void createProtectResult(Set<Integer> cast, Set<SetModel> protectList,Set<Integer> removeCast) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("count", 0);
		map.put("flag", false);
		map.put("length", cast.size());
		for (SetModel mProtect : protectList) {
			mergeProtect(cast, mProtect.getSet(), map,removeCast);
			if ((Boolean) map.get("flag")) {
				break;
			}
		}
		
	}

	public static void mergeProtect(Set<Integer> cast, Set<Integer> protect, Map<String, Object> map,Set<Integer> removeCast) {
		int count = (Integer) map.get("count");// 统计定向投放列表在保护投放列表中出现的个数
		int length = (Integer) map.get("length");
		for (int i : cast) {
			if (protect.contains(i)) {
				removeCast.add(i);
				count++;
				if (count >= length) {
					map.put("flag", true);
					break;
				}
			}
		}
		map.put("count", count);
	}

	public static void compareArray(Integer[] array1, Integer[] array2) {
		Map<Integer, Integer> map = new HashMap<>(array1.length + array2.length);
		for (int i : array2) {
			map.put(i, 1);
		}
		for (int i : array1) {
			if (!map.containsKey(i)) {
				System.out.println(i + " ");
			}
		}
	}

	public static void compareSet(Set<Integer> set1, Set<Integer> set2) {
		Map<Integer, Integer> map = new HashMap<>(set1.size() + set2.size());
		Set<Integer> set = new HashSet<>(set1.size() + set2.size());
		for (int i : set1) {
			map.put(i, 1);
		}
		for (int i : set2) {
			if (map.containsKey(i)) {
				set.add(i);
			}
		}
	}

	public static void main(String[] args) {
		// Set<Integer> set1 = new HashSet<>();
		// Set<Integer> set2 = new HashSet<>();
		// for (int i = 1; i < 20; i++) {
		// set1.add(i);
		// }
		// for (int i = 10; i < 50; i++) {
		// set2.add(i);
		// }
		// long start = System.currentTimeMillis();
		// set1.retainAll(set2);
		// set1.addAll(set2);
		// System.out.println("消耗时间：" + (System.currentTimeMillis() - start));
		//
		// for (int i : set1) {
		// System.out.println(i + " ");
		// }
		// System.out.println("=======================");
		//
		// compareSet(set1,set2);
		Integer[] array1 = {1272102,1317037,1308671,1276363,1276162,1299452,1317031,1319484,1317697,1317108,1317406,1280988,1317043,1250761,1301933,1301022,1313209,1309816,1302350,1298947,1307874,1269402,1304832,1289605,1267770,1287301,1303561,1287300,1317325,1277243,1312177,1308714,1316955,1311703,1312530,1311037,1299703,1305880,1293211};
		Integer[] array2 = {1272102,1317037,1276363,1276162,1299452,1317031,1319484,1317697,1317108,1280988,1317043,1250761,1301933,1301022,1313209,1309816,1302350,1298947,1307874,1269402,1304832,1289605,1267770,1287301,1303561,1287300,1317325,1277243,1312177,1316955,1311703,1312530,1311037,1299703,1305880,1293211};
		compareArray(array1, array2);
		
//		Set<Integer> removeCast = new HashSet<>();
//		Set<Integer> cast = new HashSet<>();
//		for (int i = 1; i < 20; i++) {
//			 cast.add(i);
//		}
//		
//		Set<SetModel> protectList=new HashSet<>();
//
//		SetModel setModel=new SetModel();	
//		protectList.add(setModel);
//		Set<Integer> set=new HashSet<>();
//        set.add(3);
//        set.add(4);
//        set.add(16);
//		setModel.setSet(set);
//		cast.retainAll(set);
//		System.out.println(" 处理前的结果：");
//		for(int i:cast){
//			System.out.print(i+" ");
//		}
//		SetModel setModel1=new SetModel();	
//		protectList.add(setModel1);
//		Set<Integer> set1=new HashSet<>();
//        set1.add(2);
//        set1.add(14);
//        set1.add(17);
//		setModel1.setSet(set1);	
//		createProtectResult(cast,protectList,removeCast);
//		cast.removeAll(removeCast);
//		System.out.println();
//		System.out.println(" 处理后的结果：");
//		for(int i:cast){
//			System.out.print(i+" ");
//		}
	}

}
