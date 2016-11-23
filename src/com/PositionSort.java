package com;

public class PositionSort {
	private static final int PAGESIZE = 10;// 每页的商品个数

	/**
	 * 
	 * @param ad 广告位id
	 * @param special 人工商品id
	 * @param general 普通商品id
	 * @param pageNO 当前页码
	 */
	public static String[] sort(int[][] ad, int[][] special, int[] general,int pageNO) {
		String[] list = new String[PAGESIZE];// 当前页的排序结果
		int[] m = new int[0];//留到下一页的人工商品id
		int[] tempM = new int[0];//保留上一页的剩下的人工商品id
		int[] antiM=new int[0];//数组m的反
		int size=0;//临时变量
		int index=0;//普通商品已插入的id个数
		int tempIndex=0;//普通商品已插入的id个数:临时变量
		int n2=0;//数组m中不为0，且小于数组ad中id最小值的个数
		int n3=0;//数组ad中id最小值 - 当前页开始值
		int n4=0;//数组special中id小于数组ad中id最小值的个数
		int n5=0;//数组special中,发生冲突的id个数
		int n6=0;//数组m中可插入到当前页的id个数
		for (int i = 1; i <= pageNO; i++) {
			size=n2=n3=n4=n5=n6=0;
			tempM = new int[m.length];
			for(int j=0;j<m.length;j++){
				tempM[j]=m[j];
			}
			if (ad[i - 1].length + m.length < PAGESIZE) {// 表示当前页还有空白位置，留给当前页的人工商品
				int n1 = i * PAGESIZE - ad[i - 1][0] - ad[i - 1].length + 1;// 广告位id最小值到页末之间的空白位置个数
				for (int j = 0; j < m.length; j++) {
					if (m[j] < ad[i - 1][0]) {
						n2++;
					}
				}			
				if(n2>0){
					n3=ad[i - 1][0]-((i-1)*PAGESIZE+1);
				}
				for (int j = 0; j < special[i-1].length; j++) {
					if (special[i-1][j] < ad[i - 1][0]) {
						n4++;
					}
				}
				size=special[i-1].length-n1+n2-n3;
				if(ad[i-1].length+m.length+n4<PAGESIZE){
					n5=size-n4;
				}else if(ad[i-1].length+m.length+n4==PAGESIZE){
					n5=size;
				}else{
					n5=special[i-1].length-1;
				}
			} else {
				n5 = special[i - 1].length;
				int tempLen=m.length-PAGESIZE + ad[i - 1].length;
				if (tempLen > 0) {
					n6 = tempLen;
				} else {
					n6 = 0;
				}
			}
			
			int[] temp=new int[n6];
			for(int j=m.length-1,v=0;j>=0&&v<=n6-1;j--){
				temp[v++]=m[j];
			}
			if(n5<0){
				n5=0;
			}
			m=new int[n5+n6];
			for(int j=special[i-1].length-1,h=m.length-1;h>=n6;h--,j--){
				m[h]=special[i-1][j];
			}
			for(int j=0;j<temp.length;j++){
				m[j]=temp[j];
			}
			int len=special[i-1].length-m.length;
			if(len<0){
				len=0;
			}
		    antiM=new int[len];
			int indexAntiM=0;
			for (int j = 0; j < special[i - 1].length && indexAntiM < len; j++) {
				if (m.length==0||special[i - 1][j] < m[0]) {
					antiM[indexAntiM++] = special[i - 1][j];
				}
			}
			tempIndex = index;
			int tempSize =PAGESIZE - ad[i - 1].length - tempM.length -antiM.length;
			if (tempSize > 0) {
				index += tempSize;
			}
		}//end--for
		
	
		/*************************当前页各类商品的具体位置信息----开始处理******************************/
		for (int i=0;i<ad[pageNO - 1].length;i++) {
			list[ad[pageNO - 1][i] - (pageNO - 1) * PAGESIZE-1]="a" + ad[pageNO - 1][i];// 表示广告位
		}
		if (tempM.length > 0) {// 表示上一页剩余的人工商品,那么当前页的就应该以上一页优先级大于当前页人工商品优先级排序
			for (int i = 0,j=0; i < list.length&&j<tempM.length; i++) {
				if(list[i]==null){
					list[i] = "s" + tempM[j++];
				}
			}
			for (int i = 0,j=0; i < list.length&&j<antiM.length; i++) {
				if(list[i]==null){
					list[i] = "s" + antiM[j++];
				}
			}			
		}else{//当前页的人工商品按原有指定位置存放
			for (int i = 0; i < antiM.length; i++) {
				list[antiM[i] - 1] = "s" + antiM[i];// 表示当前页的人工商品
			}
		}
		for (int i = 0; i < list.length; i++) {
			if (list[i] != null) {
				continue;
			}
			list[i] = "p" + general[tempIndex++];// 表示普通商品
		}
		/*************************当前页各类商品的具体位置信息----处理结束******************************/
		return list;
	}

	public static void main(String[] args) {
		int pageNo = 6;// 当前页码
		int[][] ad = { { 7, 8, 9, 10 }, { 11, 12, 13, 20 }, { 23, 24, 25, 27, 28, 29, 30 }, { 32, 33, 36, 39, 40 }, { 41, 43, 45, 46, 47, 48, 49, 50 }, { 51 } };
		int[][] special = { { 5, 7, 8, 9, 10 }, { 11, 12, 13, 18 }, { 21, 22, 24, 25, 27 }, { 31, 32, 37, 38 }, { 41, 43, 45 }, { 52, 53, 54 } };
		int[] general = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		System.out.println("原始数据如下：");
		for (int i = 0; i < ad.length; i++) {
			System.out.print("第" + (i + 1) + "页商品id分布:");
			System.out.print("广告位:");
			for (int j = 0; j < ad[i].length; j++) {
				System.out.print(ad[i][j] + ",");
			}
			System.out.print("                 ");
			System.out.print("人工的:");
			for (int j = 0; j < special[i].length; j++) {
				System.out.print(special[i][j] + ",");
			}
			System.out.println();
		}
		System.out.print("普通商品位置:");
		for (int i = 0; i < general.length; i++) {
			System.out.print(general[i] + ",");
		}
		System.out.println();
		System.out.println("处理后结果:");
		for (int i = 0; i < pageNo; i++) {
			String[] id = sort(ad, special, general, i + 1);
			System.out.println("第" + (i + 1) + "页的各类商品位置信息(a表示的是广告位，s表示的是人工商品，p表示的是普通商品)：");
			for (String s : id) {
				System.out.print(s + ",");
			}
			System.out.println();
		}
	}

}
