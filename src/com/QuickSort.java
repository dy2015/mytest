package com;

public class QuickSort {
	static int count = 0;

	public static void quicksort(long[] n, int left, int right) {
		int dp;
		if (left < right) {
			dp = sort(n, left, right);
			quicksort(n, left, dp - 1);
			quicksort(n, dp + 1, right);
		}
	}

	private static int sort(long[] n, int left, int right) {
		count++;
		long pivot = n[left];
		while (left < right) {
			while (left < right && n[right] >= pivot)
				right--;
			if (left < right)
				n[left++] = n[right];
			while (left < right && n[left] <= pivot)
				left++;
			if (left < right)
				n[right--] = n[left];
		}
		n[left] = pivot;
//		System.out.println();
//		System.out.print("第" + count + "趟排序后：");
//		System.out.println("i=" + left + ",right=" + right);
//		for (int j = 0; j < n.length; j++) {
//			System.out.print(n[j] + ",");
//		}
		return left;
	}

	public static void main(String[] args) {
		long[] a = { 5, 30, 78, 34, 50, 88, 66, 67 };
		System.out.println("排序前：");
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + ",");
		}
		quicksort(a, 0, a.length - 1);
		QuickSort q= new QuickSort();
		System.out.println();
		System.out.println(q.getClass());
		System.out.println(q.getClass().getSimpleName());
		
		System.out.println(false&&false);
		System.out.println("排序后：");
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + ",");
		}
	}
}
