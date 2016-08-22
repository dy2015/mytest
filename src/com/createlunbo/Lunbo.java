package com.createlunbo;

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
	}
	
	/**
	 * 轮播序列,优先级从高到低。
	 */
//	public static String[][] LunboSeqRule(int[][] lunboDataback) {
		
	public static Map<String, int[]> LunboSeqRule(Map<String, List<int[]>> posCaroNum) {
		Map<String, int[]> resultPosCaroNum = new HashMap<>();
		for (String pos : posCaroNum.keySet()) {
			List<int[]> lunboDataback = posCaroNum.get(pos);
			int[][] lunboData = new int[lunboDataback.size()][3];
			for (int i = 0; i < lunboData.length; i++) {
				lunboData[i][0] = lunboDataback.get(i)[0];
				lunboData[i][1] = lunboDataback.get(i)[1];
				lunboData[i][2] = lunboDataback.get(i)[2];
			}

			int sum = 0;// 循环次数
			int len = lunboData.length;

			// 将传过来的(轮数/总轮数)化简
			for (int i = 0; i < len; i++) {
				int gongyueshu = commonDiv(lunboData[i][0], lunboData[i][1]);
				lunboData[i][0] /= gongyueshu;
				lunboData[i][1] /= gongyueshu;
			}
			int[] fenmu = new int[len];
			for (int i = 0; i < len; i++) {
				fenmu[i] = lunboData[i][1];
			}
			int gongbeishu = nCommonMul(fenmu, len);// 该广告位总轮播最小公倍数
			String[][] castSeq = new String[len][gongbeishu];// 投放id--》轮播序列
			int[] result = new int[gongbeishu];// 最终的轮播序列
			sortRule(lunboData, gongbeishu);
			StringBuilder seqString = new StringBuilder(gongbeishu);
			for (int i = 0; i < len && lunboData[i][0] > 0; i++) {
				seqString.delete(0, seqString.length());
				for (int j = 0; j < gongbeishu; j += lunboData[i][1]) {
					int num = 0;
					int index2 = j;
					int index1 = 0;
					for (int v = j; v < j + lunboData[i][1]; v++, index1++) {
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
				castSeq[i][0] = String.valueOf(lunboData[i][2]);
				String temp = seqString.toString();
				temp = temp.substring(0, seqString.length() - 1);
				castSeq[i][1] = temp;
			} // end--for
			// System.out.println("共循环:" + sum + "次");
			resultPosCaroNum.put(pos, result);
		}

		return resultPosCaroNum;
	}

	/**
	 * 打印结果
	 * 
	 **/
	public static void print(String[][]  castSeqback , int[][] lunboData) {
		Map<Integer, List<Integer>> castSeq= new  HashMap<Integer, List<Integer>>(lunboData.length);
		for(int i=0;i<lunboData.length;i++){
			List<Integer> list=new ArrayList<>();
			String[] tempStr=castSeqback[i][1].split(",");
			for(String id:tempStr){
				list.add(Integer.valueOf(id));
			}
			castSeq.put(Integer.valueOf(castSeqback[i][0]),list);
		}
		
		Map<Integer, String> ad = new HashMap<Integer, String>(lunboData.length);
		char temp = 'A';
		String name = "";
		for (int i = 0; i < lunboData.length; i++) {
			name = temp + "";
			ad.put(lunboData[i][2], name);
			temp += 1;
		}
		System.out.println("轮播序列:");
		for (Integer id : castSeq.keySet()) {
			System.out.println("castID:" + id + "(" + ad.get(id) + ")--->seq:" + castSeq.get(id).toString());
		}

		System.out.println("=========================================");

		System.out.println("轮播新格式序列:");
		System.out.print("+++++++ ");
		int len = lunboData.length - 1;
		for (int i = 0; i < len; i++) {
			System.out.print(ad.get(lunboData[i][2]) + "出现" + lunboData[i][0] + "/" + lunboData[i][1] + "次；");
		}
		System.out.println(ad.get(lunboData[len][2]) + "出现" + lunboData[len][0] + "/" + lunboData[len][1] + "次。+++++++");
		int num = 0;
		for (Integer id : castSeq.keySet()) {
			List<Integer> list = castSeq.get(id);
			for (Integer i : list) {
				if (num < i) {
					num = i;
				}
			}
		}
		String[] result = new String[num];
		for (Integer id : castSeq.keySet()) {
			List<Integer> list = castSeq.get(id);
			for (Integer i : list) {
				result[i - 1] = ad.get(id);
			}
		}
		for (int i = 0; i < num - 1; i++) {
			if (i + 1 >= 10) {
				System.out.print((i + 1) + ", ");
			} else {
				System.out.print((i + 1) + " , ");
			}
		}
		System.out.print(num);
		System.out.println();
		for (int i = 0; i < result.length - 1; i++) {
			if (result[i] == null) {
				result[i] = "0";
			}
			if (i + 1 >= 10) {
				System.out.print(result[i] + ",  ");
			} else {
				System.out.print(result[i] + " , ");
			}
		}
		System.out.print(result[result.length - 1]);
	}

	public static void main(String[] args) {
		/**
		 * 投放ID,优先级假设已经是按从高到低排列,如下例子：{ 3, 7,111 }：3代表预定投放轮数，7代表总轮数，111代表对应的投放ID
		 */
		int[][] lunboData = { { 3, 7, 111 }, { 1, 7, 222 }, { 2, 7, 333 }, { 2, 21, 444 }, { 1, 21, 555 } };// 每个投放对应的轮数和总轮数
//		 int[][] lunboData = { { 3, 5 ,111}, { 1, 3 , 222}, { 1, 15,333 }};//每个投放对应的轮数和总轮数
//		 int[][] lunboData = { { 1, 10 ,111}, { 1, 10 , 222},{1, 10,333}};//每个投放对应的轮数和总轮数
//		 int[][] lunboData = { { 4, 10 ,111}, { 3, 10, 222 },{ 5, 10,333}};//每个投放对应的轮数和总轮数
//		 int[][] lunboData = { { 3, 4 ,111}, { 2, 3, 222 } };// 每个投放对应的轮数和总轮数
//		 int[][] lunboData = { { 3, 7 ,111}, { 4, 7 , 222} };// 每个投放对应的轮数和总轮数
//		 int[][] lunboData = { { 21, 100 ,111}, { 11, 60, 222 }, { 13, 60,333}, { 7, 60,444 }, { 8, 60 ,555} };// 每个投放对应的轮数和总轮数
		Map<String, List<int[]>> posCaroNum=new HashMap<>();
		List<int[]> init1=new ArrayList<>();
		init1.add(new int[]{ 3, 7, 111 });
		init1.add(new int[]{ 1, 7, 222 });
		init1.add(new int[]{ 2, 7, 333 });
		init1.add(new int[]{ 2, 21, 444 });
		init1.add(new int[]{ 1, 21, 555 });
		posCaroNum.put("1", init1);
		List<int[]> init2=new ArrayList<>();
		init2.add(new int[] { 3, 5 ,111});
		init2.add(new int[]{ 1, 3 , 222});
		init2.add(new int[]{ 1, 15,333 });
		posCaroNum.put("2", init2);
		Map<String, int[]>  castSeq = null;// 最终的投放ID对应的投放序列
		double start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			castSeq = LunboSeqRule(posCaroNum);
		}
		System.out.println("算法耗时：" + (System.currentTimeMillis() - start) + "毫秒");
		
		// 以下是按[A,B,A,C,A,B,B,C]格式打印，便于验证算法有效性，不属于算法内容。
		
		
		
		for (String pos : castSeq.keySet()) {
			System.out.print("广告位("+pos+"):");
			int len = posCaroNum.get(pos).size() - 1;
			for (int i = 0; i < len; i++) {
				System.out.print(posCaroNum.get(pos).get(i)[2]+ "出现" + posCaroNum.get(pos).get(i)[0] + "/" + posCaroNum.get(pos).get(i)[1] + "次；");
			}
			System.out.println(posCaroNum.get(pos).get(len)[2] + "出现" + posCaroNum.get(pos).get(len)[0] + "/" + posCaroNum.get(pos).get(len)[1] + "次。");
			int[] seq=castSeq.get(pos);
			for(int i=0;i<seq.length-1;i++){
				System.out.print(seq[i]+",");
			}
			System.out.print(seq[seq.length-1]);
			System.out.println();
			System.out.println();
		}
				
//		print(castSeq, lunboData);
		
	}
}
