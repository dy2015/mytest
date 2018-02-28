package com.leetcode;

import java.text.DecimalFormat;

public class PointMaxTest {
	static DecimalFormat df = new DecimalFormat("######0.000000");
	static DecimalFormat df2 = new DecimalFormat("######0");

	public static void main(String[] args) {
		PointMaxTest t = new PointMaxTest();
		 Point point1 = new Point(3, 2);
		 Point point2 = new Point(5, 4);
		 Point point3 = new Point(7, 6);
		 Point point4 = new Point(1, 2);
		 Point point5 = new Point(9, 8);
		 Point point6 = new Point(11, 10);
		 Point[] points = { point1, point2, point3, point4, point5};
		// Point[] points = { point1 };

		// Point point1 = new Point(0, -12);
		// Point point2 = new Point(5, 2);
		// Point point3 = new Point(2, 5);
		// Point point4 = new Point(0, -5);
		// Point point5 = new Point(1, 5);
		// Point point6 = new Point(2, -2);
		// Point point7 = new Point(5, -4);
		// Point point8 = new Point(3, 4);
		// Point point9 = new Point(-2, 4);
		// Point point10 = new Point(-1, 4);
		// Point point11 = new Point(0, -5);
		// Point point12 = new Point(0, -8);
		// Point point13 = new Point(-2, -1);
		// Point point14 = new Point(0, -11);
		// Point point15 = new Point(0, -9);
		// Point[] points = { point1, point2, point3, point4, point5, point6,
		// point7, point8, point9, point10, point11, point12, point13, point14,
		// point15 };
		//
//		String s = "560,248;0,16;30,250;950,187;630,277;950,187;-212,-268;-287,-222;53,37;-280,-100;-1,-14;-5,4;-35,-387;-95,11;-70,-13;-700,-274;-95,11;-2,-33;3,62;-4,-47;106,98;-7,-65;-8,-71;-8,-147;5,5;-5,-90;-420,-158;-420,-158;-350,-129;-475,-53;-4,-47;-380,-37;0,-24;35,299;-8,-71;-2,-6;8,25;6,13;-106,-146;53,37;-7,-128;-5,-1;-318,-390;-15,-191;-665,-85;318,342;7,138;-570,-69;-9,-4;0,-9;1,-7;-51,23;4,1;-7,5;-280,-100;700,306;0,-23;-7,-4;-246,-184;350,161;-424,-512;35,299;0,-24;-140,-42;-760,-101;-9,-9;140,74;-285,-21;-350,-129;-6,9;-630,-245;700,306;1,-17;0,16;-70,-13;1,24;-328,-260;-34,26;7,-5;-371,-451;-570,-69;0,27;-7,-65;-9,-166;-475,-53;-68,20;210,103;700,306;7,-6;-3,-52;-106,-146;560,248;10,6;6,119;0,2;-41,6;7,19;30,250";
//		String[] strs = s.split(";");
//		Point[] points = new Point[strs.length];
//		for (int i = 0; i < strs.length; i++) {
//			String[] temps = strs[i].split(",");
//			Point point = new Point(Integer.valueOf(temps[0]), Integer.valueOf(temps[1]));
//			points[i] = point;
//		}

		System.out.println(t.maxPoints(points));
	}

	public int maxPoints(Point[] points) {
		int ABx;
		int ABy;
		int BCx;
		int BCy;

		if (points.length <= 2)
			return points.length;
		int max = 2;// 用来记录最大个数

		for (int i = 0; i < points.length; i++) {
			int num = 0;
			int temp = 1;

			for (int j = i + 1; j < points.length; j++) {
				ABx = points[i].x - points[j].x;
				ABy = points[i].y - points[j].y;
				if (ABx == 0 && ABy == 0)// 表示出现重复点
				{
					num++;
				} else {
					temp++;
					for (int k = j + 1; k < points.length; k++) {
						BCx = points[j].x - points[k].x;
						BCy = points[j].y - points[k].y;
						if (ABx * BCy == BCx * ABy) {// 表示两个斜率相等，转化为乘积的形式可以避免分母为0的情况
							temp++;
						}
					}
				}
				if (max < (num + temp)) {
					max = num + temp;
				}
				temp = 1;
			}

		}
		return max;
	}
}
