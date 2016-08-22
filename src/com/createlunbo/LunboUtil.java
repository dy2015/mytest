
package com.createlunbo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class LunboUtil {

	private static final int DEFAULT_CARONUM = 10;//默认总轮数正常情况不会用到
	
	private static final int MAX_CARONUM = 100;//超过此值时报警
	
	private final Random rand = new Random();

	private List<Integer> lstLun; // 剩余轮次

	public LunboUtil(int totalLun) {
		lstLun = new ArrayList<Integer>();
		for (int i = 1; i <= totalLun; i++) {
			lstLun.add(i);
		}
	}

	/**
	 * 获取随机轮次序列
	 * 
	 * @param count 序列个数
	 * @return
	 */
	public List<Integer> getRandomList(int count) {
		List<Integer> lstRandLun = new ArrayList<Integer>();
		if (count >= this.lstLun.size()) {
			for (int i = 0; i < this.lstLun.size(); i++) {
				lstRandLun.add(this.lstLun.get(i));
			}
			lstLun.clear();
			return lstRandLun;
		}

		int startPoint = rand.nextInt(lstLun.size());
		while (count > 0) {
			lstRandLun.add(lstLun.remove(startPoint));
			count--;
			startPoint = (startPoint + 1) % lstLun.size();
		}
		return lstRandLun;
	}

	/*
	 * calculate lowest common multiple
	 */
	public static int calLCM(List<Integer> nums){
		if(nums==null||nums.size()==0){
			return DEFAULT_CARONUM;
		}
		uniqueList(nums);
		Collections.sort(nums);
		int startNum=nums.get(nums.size()-1);
		int lcm = 0;
		for (int i = startNum; i < Integer.MAX_VALUE; i++) {
			int num = nums.size();
			while(num > 0) {
				int count = 0;
				for(int array : nums){
					if(i%array!=0){
						break;
					}else{
						count ++;
					}
				}
				if(count==nums.size()){
					lcm = i;
					break;
				}
				num --;
			}
			if(lcm>0){
				break;
			}
		}
		
		return lcm;
	}
	
	/*
	 * calculate  greatest common divisor
	 */
	public static int calGCD(int M, int N)
	{
		if(N<0||M<0)
		{
			return -1;
		}
		if(N==0)
		{
			return M;
		}
		return calGCD(N,M%N);
	}

	/*
	 * Get distinct list
	 */
	public static <T> void uniqueList(List<T> list) {  
		List<T> tempList= new ArrayList<T>();  
		for(T i:list){  
			if(!tempList.contains(i)){  
				tempList.add(i);  
			}  
		} 
		list.clear();
		list.addAll(tempList);
	}  
	public static Map<String,Integer[]> calCaroTac(Map<String,List<Integer[]>> posCaroNum){
		int sum1=0;
		int sum2=0;
		for(String pos:posCaroNum.keySet()){
			List<Integer[]> caros = posCaroNum.get(pos);
			List<Integer> nums = new ArrayList<Integer>();
			for(Integer[] arr:caros){
				if(arr!=null && arr.length==3){
					nums.add(arr[1]);
				}
			}
			int lcm = calLCM(nums);
			int[] playArr = new int[lcm];
			for(Integer[] arr:caros){
				if(arr!=null && arr.length==3){
					int caroNum = arr[0];
					int totalCaroNum = arr[1];
					int castId = arr[2];
					//step length
					int step=(totalCaroNum%caroNum==0?totalCaroNum/caroNum:totalCaroNum/caroNum+1);
					int times = lcm/totalCaroNum;
					//周期插空仍未填上，总周期内加上
					int insertTag=0;
					for(int i =0;i<times;i++){
						int index=i*totalCaroNum;
						int count=0;
						for(int j=0;j<caroNum;j++){
							int dataIndex=index+j*step;
							while((dataIndex>=i*totalCaroNum && dataIndex<(i+1)*totalCaroNum) && playArr[dataIndex]!=0){
								sum1++;
								sum2++;
								index++;
								dataIndex++;
							}
							if(dataIndex>=i*totalCaroNum && dataIndex<(i+1)*totalCaroNum){
								playArr[dataIndex]=castId;
								count++;
							}
						}
						//插空
						if(count!=caroNum){
							for(int j=i*totalCaroNum;j<(i+1)*totalCaroNum;j++){
								sum2++;
								if(playArr[j]==0){
									playArr[j]=castId;
									count++;
								}
								if(count==caroNum){
									break;
								}
							}
						}
						if(count!=caroNum){
							insertTag=caroNum-count+insertTag;
						}
					}
					if(insertTag!=0){
						for(int i=0;i<lcm;i++){
							
							if(playArr[i]==0){
								playArr[i]=castId;
								insertTag--;
							}
							if(insertTag==0){
								break;
							}
						}
					}
			
				}
			}
//			System.out.print("[");
//			for(int i: playArr){
//				if(i!=0){
//					char c=(char)i;
//					System.out.print(c+" ");
//				}else{
//					System.out.print(i+" ");
//				}
//				
//			}
//			System.out.print("]");
//			System.out.println();
		}
//		System.out.println("共循环1:"+sum1+"次");
//		System.out.println("共循环2:"+sum2+"次");
		return null;

	}
	
	public static void main(String[] arugs) {
		//position -> [TotalCaroNum]
		Map<String,List<Integer[]>> posCaroNum = new HashMap<String,List<Integer[]>>();
		List<Integer[]> caros = new ArrayList<Integer[]>();
//		caros.add(new Integer[]{1,7,65});
//		caros.add(new Integer[]{1,2,66});
//		caros.add(new Integer[]{2,7,67});
		
		caros.add(new Integer[]{1,7,65});
		caros.add(new Integer[]{3,7,66});
		caros.add(new Integer[]{2,7,67});
		caros.add(new Integer[]{1,21,68});
		caros.add(new Integer[]{2,21,69});
		
		posCaroNum.put("3", caros);
		
		
//		LunboUtil util = new LunboUtil(10);
//		long time3 = System.currentTimeMillis();
//		for(int i=0;i<1000;i++){
//			util.getRandomList(5);
//			util.getRandomList(5);
//			util.getRandomList(5);
//		}
//		long time4 = System.currentTimeMillis();
//		System.out.println(time4-time3);
//		System.out.println("---------------"+time3);
//		System.out.println("---------------"+time4);
		
		
		long time1 = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			calCaroTac(posCaroNum);
		}
		long time2 = System.currentTimeMillis();
		System.out.println(time2-time1);
//		System.out.println("---------------"+time1);
//		System.out.println("---------------"+time2);
		
		// 做100次随机测试，每次都3个投放10轮播组合，可不占满
		//		int tLun = 10;
		//		Random rand = new Random();
		//		for (int i = 0; i < 100; i++) {
		//			// 生成随机轮数，r1+r2+r3<=10
		//			int r1 = 1 + rand.nextInt(tLun - 2);
		//			int r2 = 1 + rand.nextInt(tLun - r1 - 1);
		//			int r3 = 1 + rand.nextInt(tLun - r1 - r2);
		//
		//			// 初始化测试用的castLunMap
		//			Map<Integer, MLunbo> castLunMap = new HashMap<Integer, MLunbo>();
		//			// 这里的MLunbo类我加了个构造函数，未提交,测试时可在MLunbo类加构造函数；或者不加构造函数，在此调用set方法赋值也可
		//			MLunbo mlb1 = new MLunbo(500, r1, tLun);
		//			MLunbo mlb2 = new MLunbo(501, r2, tLun);
		//			MLunbo mlb3 = new MLunbo(502, r3, tLun);
		//			castLunMap.put(mlb1.getCastId(), mlb1);
		//			castLunMap.put(mlb2.getCastId(), mlb2);
		//			castLunMap.put(mlb3.getCastId(), mlb3);
		//
		//			int totalLun = mlb1.getTotalLun();
		//			LunboRandomUtil rl = new LunboRandomUtil(totalLun);
		//			// 准备好返回的maps
		//			Map<Integer, List<Integer>> maps = new HashMap<Integer, List<Integer>>();
		//			Iterator<Entry<Integer, MLunbo>> itr = castLunMap.entrySet().iterator();
		//			while (itr.hasNext()) {
		//				Entry<Integer, MLunbo> ent = itr.next();
		//				int key = ent.getKey();
		//				MLunbo mlb = ent.getValue();
		//				maps.put(key, rl.getRandomList(mlb.getLun()));
		//			}
		//			System.out.println(r1 + "," + r2 + "," + r3 + maps);
		//		}
	}

}
