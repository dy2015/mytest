package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ModifyMap {
	private static final Random random = new Random();
	private static final List<String> PrioritySeq = new ArrayList<String>();
	static {
		PrioritySeq.add("age");
		PrioritySeq.add("sex");
	}

	public static int compare(Student s1, Student s2) {
		Map<String, Integer> aMap = s1.getPrioritySeq();
		if (aMap == null) {
			return -1;
		}
		Map<String, Integer> bMap = s2.getPrioritySeq();
		if (bMap == null) {
			return 1;
		}
		for (String key : PrioritySeq) {
			System.out.println("比较的优先级是" + key);
			Integer aVal = aMap.get(key);
			if (aVal == null) {
				return -1;
			}

			Integer bVal = bMap.get(key);
			if (bVal == null) {
				return 1;
			}

			int result = aVal.compareTo(bVal);
			if (result > 0) {
				return 1;
			}
			if (result < 0) {
				return -1;
			}

		}// end--for
		return random.nextInt() < 0 ? -1 : 1;
	}

	public static void mySort(Map<Integer, Student> map) {
		Student[] student = map.values().toArray(new Student[map.size()]);
		// 降序排序
		Student temp;
		for (int i = 0; i < student.length; i++) {
			for (int j = 0; j < student.length - i - 1; j++) {
				if (compare(student[j],student[j + 1])<0) {
					temp = student[j];
					student[j] = student[j + 1];
					student[j + 1] = temp;
				}
			}// end--for
		}// end--for
		for (int i = 0; i < student.length; i++) {
			student[i].setTag(i + 1);
		}
	}

	public static void main(String[] args) {
		Map<Integer, Student> map = new HashMap<Integer, Student>();
		Map<String, Integer> map1 = new HashMap<String, Integer>();
		map1.put("age", 1);
		map1.put("sex", 2);
		Student s1 = new Student("a", 10, 1);
		s1.setPrioritySeq(map1);
		Map<String, Integer> map2 = new HashMap<String, Integer>();
		map2.put("age", 2);
		map2.put("sex", 1);
		Student s2 = new Student("b", 11, 2);
		s2.setPrioritySeq(map2);
		Map<String, Integer> map3 = new HashMap<String, Integer>();
		map3.put("age", 3);
		map3.put("sex", 1);
		Student s3 = new Student("c", 12, 3);
		s3.setPrioritySeq(map3);
		map.put(1, s1);
		map.put(2, s2);
		map.put(3, s3);
		System.out.println("修改前的数据：");
		for (Integer key : map.keySet()) {
			System.out.println("<Key,value> = <" + key + "," + map.get(key) + ">");
			map.get(key).setAge(map.get(key).getAge() * 10);
		}
		System.out.println("修改后的数据：");
		for (Integer key : map.keySet()) {
			System.out.println("<Key,value> = <" + key + "," + map.get(key) + ">");
		}
		mySort(map);
		System.out.println("按优先级的降序排序后的数据：");
		for (Integer key : map.keySet()) {
			System.out.println("<Key,value> = <" + key + "," + map.get(key) + ">");
		}
	}

}
