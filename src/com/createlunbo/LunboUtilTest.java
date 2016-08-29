package com.createlunbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class LunboUtilTest {
	public static final int START_CASTID=10000;

	static class SumComb {
		private  ArrayList<Integer> findNumIndex;// 存储符合条件的数组元素下表
		private  List<List<Integer>> all = new ArrayList<List<Integer>>();
		private  boolean findon;// 是否可以从数组中找到相加等于“和”的元素
		private  boolean someoneEqualSum;// 数组中是否有某个元素等于“和”本身
		private  int[] arr;// 数组
		private  int sum;// “和”

		public SumComb(int[] a, int sum) {
			this.arr = a;
			this.sum = sum;
			findon = false;
			findNumIndex = new ArrayList<Integer>();
			someoneEqualSum = false;
		}

		public void start() {
			List<Integer> list = new LinkedList<Integer>();
			for (int i = 0; i < arr.length; i++) {// 把int数组付给list
				list.add(arr[i]);
			}
			boolean flag = true;
			do {
				int mix = list.get(0);// 当前最小值
				int max = list.get(list.size() - 1);// 当前最大值
				if (max == sum) {// 找到等于“和”的元素,打个标记
					someoneEqualSum = true;
				}
				if (mix + max > sum && flag) {// 删除没用的最大值
					list.remove(list.size() - 1);
				} else {
					flag = false;
				}
			} while (flag&&list.size()>0);
			startMath(list, sum);
			if (!findon) {
				System.out.println("未找到符合条件的数组");
			}
		}

		public void startMath(List<Integer> list, int sum) {
			for (int i = 0; i <= list.size() - 2; i++) {
				findNumIndex.clear();
				findNumIndex.add(list.size() - 1 - i);// 记录第一个元素坐标
				int indexNum = list.get(list.size() - 1 - i);// 从最大的元素开始，依次往前推
				action(list, indexNum, list.size() - 1 - i, sum);
			}
		}

		/**
		 * 递归方法
		 * 
		 * @param list
		 *            被查询的数组
		 * @param indexsum
		 *            当前元素相加的和
		 * @param index
		 *            下一个元素位置
		 * @param sum
		 *            要匹配的和
		 */
		public void action(List<Integer> list, int indexsum, int index, int sum) {
			if (index == 0)
				return;
			if (indexsum + list.get(index - 1) > sum) {// 元素【index-1】太大了，跳到下一个元素继续遍历
				action(list, indexsum, index - 1, sum);
			} else if (indexsum + list.get(index - 1) < sum) {// 元素【index-1】可能符合条件，继续往下找
				findNumIndex.add(index - 1);// 记录此元素坐标
				indexsum = indexsum + list.get(index - 1);// 更新元素的和
				action(list, indexsum, index - 1, sum);
			} else if (indexsum + list.get(index - 1) == sum) {
				findNumIndex.add(index - 1);
				findon = true;// 告诉系统找到了
				ArrayList<Integer> temp = (ArrayList<Integer>) findNumIndex.clone();
				all.add(temp);
				return;
			}
		}

		//TODO 有必要的话，控制数字组合个数，暂不控制看需要
		public  Map<String,List<int[]>> calCombNums(){
			Map<String,List<int[]>> posCaroNum = new HashMap<String,List<int[]>>();
			for(int j =0;j<all.size();j++){
				List<Integer> list=all.get(j);
				List<int[]> caros = new ArrayList<int[]>();
				posCaroNum.put(""+(j+1), caros);
				for(int i =0;i< list.size();i++){
					int caroNum = arr[list.get(i)];
					int gld=LunboUtil.calGCD(caroNum, sum);
					caroNum=caroNum/gld;
					int totalCaroNum =sum/gld;
					int castId = START_CASTID+i;
					caros.add(new int[]{caroNum,totalCaroNum,castId});
				}
			}
			return posCaroNum;
		}
	}



	@Test
//	public void testCalCaroTac(){
	public static void main(String[] args) {	
        int num=0;
        System.out.println("执行开始，请等待.......");
        long strat=System.currentTimeMillis();
		for(int i =1;i<2000;i++){
			try{
//			System.out.println("执行第"+i+"次，========================================");
			int[] arr=new int[i];
			for(int j=0;j<i;j++){
				arr[j]=j+1;
			}
			SumComb sc=new SumComb(arr, i);
			sc.start();
			Map<String,List<int[]>> posCaroNum =sc.calCombNums();
//			LunboUtil.sortCaroByDeno(posCaroNum);
			Map<String,int[]> posSeqs=null;
//			Map<String,int[]> posSeqs = LunboUtil.calCaroTacNow(posCaroNum);
			
			try{
			     posSeqs = Lunbo.LunboSeqRule(posCaroNum);
			}catch(Exception e){
				for (String pos : posCaroNum.keySet()) {
					System.out.print("广告位(" + pos + "):");
					int len = posCaroNum.get(pos).size();
					for (int t= 0; t < len; t++) {
						System.out.print(posCaroNum.get(pos).get(t)[2] + "出现" + posCaroNum.get(pos).get(t)[0] + "/" + posCaroNum.get(pos).get(t)[1] + "次；");
					}
					System.out.println();
				}
				System.out.println("第一种方法报错："+e);
				break;
			}finally {
//				System.out.println("posCaroNum长度="+posCaroNum.size());
				boolean  result=LunboUtil.checkCaroTac(posCaroNum, posSeqs);
				if(!result){
					num++;
					System.out.println("dierzhong");
					try{
					posSeqs = Lunbo.LunboSeqRule(posCaroNum);
					}catch(Exception e){
						System.out.println("第二种方法报错："+e);
					}
					result=LunboUtil.checkCaroTac(posCaroNum, posSeqs);
				}
				if(!result){
					System.out.println("第二种方法也不满足要求");
					   break;
				}
//					Assert.assertEquals(true, result);
			}
			}catch(Exception e){
				System.out.println("其他地方报错："+e);
			}
		}//end-for
		System.out.println("恭喜你，算法执行结束，测试通过!");
		System.out.println("算法耗时："+(System.currentTimeMillis()-strat));
	}

}
