package com;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sun.javafx.webkit.KeyCodeMap.Entry;

public class LunboUtil {
	private static final DecimalFormat df = new DecimalFormat("#.00");
	private static final int DEFAULT_CARONUM = 10;//默认总轮数正常情况不会用到
	private static final int MAX_PRIOR=10000;//最高优先级

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

		if(lcm>MAX_CARONUM){
			//Ec.ADS.log("The caronum is too big,caronum:"+lcm, new Exception(), Ec.DIM_COMMON_ERR);
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
	//list->castId->index1,index2
	//优先级排序，空位第一，其它按轮数差值排序，差值越大优先级越高,list中每个元素都是map,为了排优先级
	public static List<Map<Integer,List<Integer>>> remainIdLun(Map<Integer,List<Integer>> castIdLun,Map<Integer,int[]> castIdProbNumAll,int cyc,int lcm,boolean big){

		List<Map<Integer,List<Integer>>> idIndexsList=new ArrayList<Map<Integer,List<Integer>>>();
		Iterator<Integer> it = castIdLun.keySet().iterator();
		Map<Integer,Integer> idPrior=new HashMap<Integer,Integer>();
		Map<Integer,List<Integer>> idIndexs=new HashMap<Integer,List<Integer>>();
		while(it.hasNext()){
			int caId=it.next();

			List<Integer> indexs=castIdLun.get(caId);
			idIndexs.put(caId, indexs);
			if(indexs==null||indexs.isEmpty()){
				it.remove();
			}
			if(caId==0){
				idPrior.put(caId, MAX_PRIOR);
				continue;
			}

			int[] prob=castIdProbNumAll.get(caId);
			int curLun = lcm/cyc*indexs.size();  
			int origLun = lcm/prob[1]*prob[0];
			int dVal=Math.abs(curLun-origLun);
			//保证空位优先级最大
			if(idPrior.get(0)!=null && dVal>=idPrior.get(0)){
				idPrior.put(0,idPrior.get(0)+100);
			}
			if(big){
				//当前概率与原概率相比大的留下，说明可以移出去，其它周期一定会有比原概率小的,删除不符合条件的
				if(curLun>origLun){
					idPrior.put(caId, dVal);
				}else{
					it.remove();
				}
			}else{
				//当前概率与原概率相比小的留下，说明可以移进来,删除不符合条件的
				if(curLun<origLun){
					idPrior.put(caId, dVal);
				}else{
					it.remove();
				}
			}
		}
		List<Integer> priorList =new ArrayList<Integer>();
		for(Integer pri:idPrior.values()){
			priorList.add(pri);
		}
		Collections.sort(priorList);
		for(Integer pri:priorList){
			for(Integer caId:idPrior.keySet()){
				if(idPrior.get(caId)==pri){
					Map<Integer,List<Integer>> curIdIndexs = new HashMap<Integer,List<Integer>>(); 
					curIdIndexs.put(caId, idIndexs.get(caId));
					idIndexsList.add(0,curIdIndexs);
					break;
				}
			}
		}
		return idIndexsList;
	}



	public static Map<String,int[]> calCaroTac(Map<String,List<int[]>> posCaroNum){
		Map<String,int[]> posSeqs = new HashMap<String,int[]>();
		for(String pos:posCaroNum.keySet()){
			List<int[]> caros = posCaroNum.get(pos);
			List<Integer> nums = new ArrayList<Integer>();
			Map<Integer,int[]> castIdProbNumAll=new HashMap<Integer,int[]>();
			for(int[] arr:caros){
				if(arr!=null && arr.length==3){
					nums.add(arr[1]);
				}
				castIdProbNumAll.put(arr[2], new int[]{arr[0],arr[1]});

			}
			int lcm = calLCM(nums);
			int[] playArr = new int[lcm];
			List<Integer> distList = distNum(null,0,lcm-1);
			//周期插空仍未填上，总周期内加上
			//castId->startIndex,endIndex,seqs(剩余)
			Map<Integer,List<int[]>> overplusSeq=new HashMap<Integer,List<int[]>>();
			for(int[] arr:caros){
				if(arr!=null && arr.length==3){
					int caroNum = arr[0];
					int totalCaroNum = arr[1];
					int castId = arr[2];
					//step length
					int step=(totalCaroNum%caroNum==0?totalCaroNum/caroNum:totalCaroNum/caroNum+1);
					step=totalCaroNum;
					int times = lcm/totalCaroNum;
					//CUR_TIME_OK:
					for(int i =0;i<times;i++){
						int index=i*totalCaroNum;
						int count=0;
						int end = (i+1)*totalCaroNum;


						List<Integer> delIndex=new ArrayList<Integer>();
						for(int k:distList){
							if(caroNum==count){
								break;
							}
							if(playArr[k]==0 && caroNum >count && k>=index&& k<end){
								playArr[k]=castId;
								count++;
								delIndex.add(k);
							}
						}
						distList.removeAll(delIndex);


						//冲突移位
						if(count!=caroNum){
							//remember current lun
							//Map<Integer,Integer> castIdLun=null;
							Map<Integer,List<Integer>> castIdLun=new HashMap<Integer,List<Integer>>();
							//only for reset
							int[] playArrBack = playArr.clone();
							for(int m=index;m<end;m++){
								if(castIdLun.get(playArr[m])==null){
									castIdLun.put(playArr[m], new ArrayList<Integer>());
								}
								castIdLun.get(playArr[m]).add(m);
							}

							List<Map<Integer,List<Integer>>> idIndexsList=remainIdLun(castIdLun,castIdProbNumAll,totalCaroNum,lcm,true);
							//其它周期找符合移位条件的，比原概率小的空位
							for(int n =0;n<times;n++){
								//跳过当前周期
								if(n==i){
									continue;
								}
								int cindex=n*totalCaroNum;
								int cend = (n+1)*totalCaroNum;
								//remember current lun
								Map<Integer,List<Integer>> ncastIdLun=new HashMap<Integer,List<Integer>>();
								for(int s=cindex;s<cend;s++){
									if(ncastIdLun.get(playArr[s])==null){
										ncastIdLun.put(playArr[s], new ArrayList<Integer>());
									}
									ncastIdLun.get(playArr[s]).add(s);
								}

								List<Map<Integer,List<Integer>>> nIdIndexsList =remainIdLun(ncastIdLun,castIdProbNumAll,totalCaroNum,lcm,false);
								AGAIN:
									if(!nIdIndexsList.isEmpty()){
										//优先级从高到低
										int id1=idIndexsList.get(0).keySet().iterator().next();
										List<Integer> indexList1=idIndexsList.get(0).get(id1);

										for(int m=0;m<nIdIndexsList.size();m++){
											int id2=nIdIndexsList.get(m).keySet().iterator().next();
											if(id2!=0){
												break;
											}
											List<Integer> indexList2=nIdIndexsList.get(m).get(id2);
											//swap
											playArr[indexList1.get(0)]=castId;
											playArr[indexList2.get(0)]=id1;
											//随机序列减去
											if(id2==0){
												Iterator<Integer> it = distList.iterator(); 
												while(it.hasNext()){
													if(indexList2.get(0)==it.next()){
														it.remove();
														break;
													}
												}
											}
											indexList1.remove(0);
											indexList2.remove(0);
											count++;
											if(count==caroNum){
												break;
											}
											idIndexsList=remainIdLun(castIdLun,castIdProbNumAll,totalCaroNum,lcm,true);
											nIdIndexsList =remainIdLun(ncastIdLun,castIdProbNumAll,totalCaroNum,lcm,false);
											break AGAIN;
										}


									}
								/*
									AGAIN:
										if(!ncastIdLun.isEmpty()){
											for(int caId:castIdLun.keySet()){
												for(int k=index;k<end;k++){
													//可移
													if(playArr[k]==caId){
														//找出空位
														for(int s=cindex;s<cend;s++){
															//优先交换空位
															if(caId!=castId && ncastIdLun.get(playArr[s])!=null){
																playArr[s]=caId;
																playArr[k]=castId;
																count--;
																//随机序列减去
																if(playArr[s]==0){
																	Iterator<Integer> it = distList.iterator(); 
																	while(it.hasNext()){
																		if(s==it.next()){
																			it.remove();
																			break;
																		}
																	}
																}

																//每次都要校验是否还满足
																if(count==caroNum){
																	break CUR_TIME_OK;
																}

																//移进的加1
																if(ncastIdLun.get(caId)==null){
																	ncastIdLun.put(caId, 1);
																}else{
																	ncastIdLun.put(caId, ncastIdLun.get(caId)+1);
																}


																//移出的相应减1，新添加对应元素加1
																if(castIdLun.get(caId)==1){
																	castIdLun.remove(caId);
																}else{
																	castIdLun.put(caId, ncastIdLun.get(caId)-1);
																}

																if(castIdLun.get(castId)==null){
																	castIdLun.put(castId, 1);
																}else{
																	castIdLun.put(castId, ncastIdLun.get(castId)+1);
																}

																remainIdLun(castIdLun,castIdProbNumAll,totalCaroNum,lcm,true);
																remainIdLun(ncastIdLun,castIdProbNumAll,totalCaroNum,lcm,false);
																break AGAIN;
															}
														}

													}
												}
											}
										}
								 */
							}
						}
					}
				}
			}

			if(!overplusSeq.isEmpty()){
				for(int castId:overplusSeq.keySet()){
					List<int[]> list=overplusSeq.get(castId);
					for(int[] arr:list){
						int startIndex=arr[0];
						int endIndex=arr[1];
						int seqs=arr[2];
						for(int i=startIndex;i<endIndex;i++){
							if(seqs==0){
								break;
							}
							if(playArr[i]==0 && distList.contains(i)){
								playArr[i]=castId;
								seqs--;
							}
						}
					}

				}
			}


			posSeqs.put(pos, playArr);
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
		return posSeqs;

	}

	public static Map<String,int[]> calCaroTacNow(Map<String,List<int[]>> posCaroNum){
		Map<String,int[]> posSeqs = new HashMap<String,int[]>();
		for(String pos:posCaroNum.keySet()){
			List<int[]> caros = posCaroNum.get(pos);
			List<Integer> nums = new ArrayList<Integer>();
			Map<Integer,int[]> castIdProbNumAll=new HashMap<Integer,int[]>();
			for(int[] arr:caros){
				if(arr!=null && arr.length==3){
					nums.add(arr[1]);
				}
				castIdProbNumAll.put(arr[2], new int[]{arr[0],arr[1]});
			}
			int lcm = calLCM(nums);
			int[] playArr = new int[lcm];
			//计数，最后插空
			Map<Integer,Integer> remainId=new HashMap<Integer,Integer>();
			for(int[] arr:caros){
				if(arr!=null && arr.length==3){
					int caroNum = arr[0];
					int totalCaroNum = arr[1];
					int castId = arr[2];
					int times = lcm/totalCaroNum;
					//CUR_TIME_OK:
					for(int i =0;i<times;i++){
						int index=i*totalCaroNum;
						int count=0;
						int end = (i+1)*totalCaroNum;

						for(int j=index;j<end;j++){
							if(caroNum==count){
								break;
							}
							if(playArr[j]==0){
								playArr[j]=castId;
								count++;
							}
						}
						//冲突移位
						//必须是与当前周期有交叉的才可以
						if(caroNum!=count){
							MOVE_OK:
								for(int k=index;k<end;k++){
									if(caroNum==count){
										break MOVE_OK;
									}
									int[] prob=castIdProbNumAll.get(playArr[k]);
									int curTotalCaroNum=prob[1];
									int curCyc=k/curTotalCaroNum;
									int curStartIndex=curCyc*curTotalCaroNum;
									int curEndIndex=(curCyc+1)*curTotalCaroNum;
									if(curStartIndex<end&&curEndIndex>end){
										MOVE_NEXT:
											for(int n=end;n<curEndIndex;n++){
												if(playArr[n]==0){
													playArr[n]=playArr[k];
													playArr[k]=castId;
													count++;
													break MOVE_NEXT;
												}
											}
									}
								}
						}
						if(caroNum!=count){
							if(remainId.get(castId)==null){
								remainId.put(castId, caroNum-count);
							}else{
								remainId.put(castId,remainId.get(castId)+caroNum-count);
							}
						}

					}
				}
			}
			for(int id:remainId.keySet()){
				int count=remainId.get(id);
				for(int i=0;i<playArr.length;i++){
					if(playArr[i]==0){
						playArr[i]=id;
						count--;
					}
					if(count==0){
						break;
					}

				}
			}
			posSeqs.put(pos, playArr);

		}
		return posSeqs;
	}

	// 计算投放对应的轮播序列
	public static Map<Integer, List<Integer>> calLunboSeq(List<int[]> caros){
		//castId->seqs
		Map<Integer, List<Integer>> castLunboSeqMap = new HashMap<Integer, List<Integer>>();
		List<Integer> nums = new ArrayList<Integer>();
		Map<Integer,int[]> castIdProbNumAll=new HashMap<Integer,int[]>();
		for(int[] arr:caros){
			if(arr!=null && arr.length==3){
				nums.add(arr[1]);
			}
			castIdProbNumAll.put(arr[2], new int[]{arr[0],arr[1]});
		}
		int lcm = calLCM(nums);
		int[] playArr = new int[lcm];
		//计数，最后插空
		Map<Integer,Integer> remainId=new HashMap<Integer,Integer>();
		for(int[] arr:caros){
			if(arr!=null && arr.length==3){
				int caroNum = arr[0];
				int totalCaroNum = arr[1];
				int castId = arr[2];
				int times = lcm/totalCaroNum;
				//CUR_TIME_OK:
				for(int i =0;i<times;i++){
					int index=i*totalCaroNum;
					int count=0;
					int end = (i+1)*totalCaroNum;

					for(int j=index;j<end;j++){
						if(caroNum==count){
							break;
						}
						if(playArr[j]==0){
							playArr[j]=castId;
							count++;
						}
					}
					//冲突移位
					//必须是与当前周期有交叉的才可以
					if(caroNum!=count){
						MOVE_OK:
							for(int k=index;k<end;k++){
								if(caroNum==count){
									break MOVE_OK;
								}
								int[] prob=castIdProbNumAll.get(playArr[k]);
								int curTotalCaroNum=prob[1];
								int curCyc=k/curTotalCaroNum;
								int curStartIndex=curCyc*curTotalCaroNum;
								int curEndIndex=(curCyc+1)*curTotalCaroNum;
								if(curStartIndex<end&&curEndIndex>end){
									MOVE_NEXT:
										for(int n=end;n<curEndIndex;n++){
											if(playArr[n]==0){
												playArr[n]=playArr[k];
												playArr[k]=castId;
												count++;
												break MOVE_NEXT;
											}
										}
								}
							}
					}
					if(caroNum!=count){
						if(remainId.get(castId)==null){
							remainId.put(castId, caroNum-count);
						}else{
							remainId.put(castId,remainId.get(castId)+caroNum-count);
						}
					}

				}
			}
		}

		for(int id:remainId.keySet()){
			int count=remainId.get(id);
			for(int i=0;i<playArr.length;i++){
				if(playArr[i]==0){
					playArr[i]=id;
					count--;
				}
				if(count==0){
					break;
				}

			}
		}

		for(int i=0;i<playArr.length;i++){
			if(castLunboSeqMap.get(playArr[i])!=null){
				castLunboSeqMap.put(playArr[i], new ArrayList<Integer>());
			}
			castLunboSeqMap.get(playArr[i]).add(i+1);
		}

		return castLunboSeqMap;
	}


	//放大周期
	public static void changeArr(List<Integer[]> caros,int lcm){
		List<Integer[]>  newArrs=new ArrayList<Integer[]>();
		if(caros!=null&&caros.size()>1){
			for(Integer[] caro:caros){
				Integer[] newArr = new Integer[lcm];
				int caroNum = caro[0];
				int totalCaroNum = caro[1];
				int castId = caro[2];
				for(int j =0;j<totalCaroNum;j++){
				}
			}
		}
	}


	//distribute number
	public static List<Integer> distNum(List<Integer> distList,int startIndex,int endIndex){
		if(distList==null){
			distList = new ArrayList<Integer>();
		}
		int range = endIndex-startIndex;
		if(range==0){
			distList.add(startIndex);
			return distList;
		}
		if(range==1){
			distList.add(startIndex);
			distList.add(endIndex);
			return distList;
		}
		distList.add(startIndex);
		distList.add(endIndex);

		startIndex=startIndex+1;
		endIndex=endIndex-1;
		range = endIndex-startIndex;
		if(range==0){
			distList.add(startIndex);
			return distList;
		}
		if(range==1){
			distList.add(startIndex);
			distList.add(endIndex);
			return distList;
		}
		int midIndex=(startIndex+endIndex)/2;
		distList.add(midIndex);

		distList=distNum(distList,midIndex+1,endIndex);
		distList=distNum(distList,startIndex,midIndex-1);

		return distList;
	}

	public static void printInfo(List<int[]> origNum,int[] seq){
		StringBuilder info=new StringBuilder();
		info.append("origNum:[");
		for(int i =0;i<origNum.size();i++){
			int[] arr = origNum.get(i);
			info.append(arr[0]);
			info.append("/");
			info.append(arr[1]);
			if(i!=origNum.size()-1){
				info.append(",");
			}
		}
		info.append("] seq:[");
		for(int i =0;i<seq.length;i++){
			info.append(seq[i]);
			if(i!=seq.length-1){
				info.append(",");
			}
		}
		info.append("]");
		System.out.println(info.toString());
	}

	public static boolean checkCaroTac(Map<String,List<int[]>> origNums,Map<String,int[]> caroNums){
		boolean result=true;
		for(String pos:origNums.keySet()){
			List<int[]> origNum = origNums.get(pos);
//			System.out.println("广告位(="+pos+")长度="+origNum.size());
			int[] seq = caroNums.get(pos);
			if(origNum==null ||origNum.size()==0|| seq==null||seq.length==0){
//				System.err.println("The data must is null.");
//				result=false;
				continue;
			} 
			int lcm = seq.length;
			ERROR://当探测到一个概率不能满足时则终止探测
				for(int[] arr:origNum){
					if(arr!=null && arr.length==3){
						int caroNum = arr[0];
						int totalCaroNum = arr[1];
						int castId = arr[2];
						int times = lcm/totalCaroNum;
						for(int i =0;i<times;i++){
							int startIndex=i*totalCaroNum;
							int endIndex=(i+1)*totalCaroNum;
							int count=0;
							for(int j=startIndex;j<endIndex;j++){
								if(seq[j]==castId){
									count++;
								}
							}
							if(count!=caroNum){
								result=false;
								printInfo(origNum,seq);
								break ERROR;
							}
						}
					}
				}
		}
		return result;
	}

	//按分母排序
	public static void sortCaroByDeno(Map<String,List<int[]>> posCaroNum){
		Iterator<String> itp=posCaroNum.keySet().iterator();
		while(itp.hasNext()){
			String pos=itp.next();
			List<int[]> origNum = posCaroNum.get(pos);
			if(origNum.size()>3){
				itp.remove();
				continue;
			}
			List<Integer> prioList=new ArrayList<Integer>();
			Map<Integer,Integer> idTotal=new HashMap<Integer,Integer>();

			Map<Integer,int[]> idCaroNum=new HashMap<Integer,int[]>();
			for(int[] arr:origNum){
				idTotal.put(arr[2], arr[1]);
				prioList.add(arr[1]);
				idCaroNum.put(arr[2],arr);
			}
			//分母从小到大排序
			Collections.sort(prioList);

			List<int[]> newNum = new ArrayList<int[]>();
			for(int i=prioList.size()-1;i>=0;i--){
				Iterator<Integer> it = idTotal.keySet().iterator();
				while(it.hasNext()){
					int id=it.next();
					if(prioList.get(i)==idTotal.get(id)){
						newNum.add(0,idCaroNum.get(id));
						it.remove();
						break;
					}
				}
			}
			posCaroNum.put(pos, newNum);
		}
	}


	//按百分比排序
	public static void sortCaroNum(Map<String,List<int[]>> posCaroNum){
		for(String pos:posCaroNum.keySet()){
			List<int[]> origNum = posCaroNum.get(pos);
			List<Double> prioList=new ArrayList<Double>();
			for(int[] arr:origNum){
				double caroNum = arr[0];
				double totalCaroNum = arr[1];
				prioList.add(Double.parseDouble(df.format(caroNum/totalCaroNum)));
			}
			Collections.sort(prioList);
			int leng=prioList.size();
			Map<Double,Integer> prioMap=new HashMap<Double,Integer>();
			for(int i =0;i<leng;i++){
				prioMap.put(prioList.get(i), leng-i-1);
			}
			List<int[]> newNum = new ArrayList<int[]>();
			for(int[] arr:origNum){
				double caroNum = arr[0];
				double totalCaroNum = arr[1];
				Double key=Double.parseDouble(df.format(caroNum/totalCaroNum));
				newNum.add(prioMap.get(key), arr);
			}
			posCaroNum.put(pos, newNum);
		}
	}


	public static void main(String[] arugs) {
		//position -> [TotalCaroNum]
		Map<String,List<int[]>> posCaroNum = new HashMap<String,List<int[]>>();
		List<int[]> caros = new ArrayList<int[]>();
		//		caros.add(new int[]{1,6,65});
		//		caros.add(new int[]{1,7,66});
		//		caros.add(new int[]{3,14,67});
		//		caros.add(new int[]{5,21,69});
		//		caros.add(new int[]{1,21,68});

		caros.add(new int[]{7,15,11});
		caros.add(new int[]{10,21,22});
		caros.add(new int[]{2,35,33});
		//		caros.add(new int[]{7,25,67});

		//		1/10,3/10,7/25,8/25

		posCaroNum.put("3", caros);
		sortCaroByDeno(posCaroNum);
		long time1 = System.currentTimeMillis();
		for(int i=0;i<1;i++){
			Map<String,int[]> caroNums = calCaroTacNow(posCaroNum);
			boolean  result=checkCaroTac(posCaroNum, caroNums);
		}
		long time2 = System.currentTimeMillis();
		System.out.println(time2-time1);
		System.out.println("---------------"+time1);
		System.out.println("---------------"+time2);
		double a=1;
		double b=3;

		double sum1 =1/(double)3;
		sum1=Double.parseDouble(df.format(sum1));
		double sum2 =a/b;
		sum2=Double.parseDouble(df.format(sum2));
		System.out.println(sum2);

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
