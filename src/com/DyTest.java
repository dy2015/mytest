package com;

public class DyTest {
	private static boolean flag;
	public static void main(String[] args) {
		System.out.println("========================================");
		System.out.println("flag:"+flag);
		deffience();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		// replace();
		// zhengze();
		// splitTest();
		// replaceTest();
		// javaGetIp();
//		System.out.println(new Random().nextInt(100));
	}

	public static void deffience() {
		String[] s1 = { "1368308", "1392308", "1215697", "1415474", "1392946", "1361532", "1207320", "1371608", "1408005", "1416678", "1412353", "1287492", "1302859", "1408108", "1412367", "1412329", "1406120", "1392008", "1387112", "1313802", "1370218" };
		String[] s2 = { "1368308", "1392308", "1215697", "1415474", "1392946", "1361532", "1207320", "1371608", "1408005", "1416678", "1412353", "1287492", "1302859", "1408108", "1412367", "1412329", "1406120", "1392008", "1387112", "1313802", "1370218" };
		boolean tag = true;
		for (String ss : s1) {
			tag = true;
			for (String sss : s2) {
				if (ss.equals(sss)) {
					tag = false;
					break;
				}
			}//end--for
			if (tag) {
				System.out.println(ss);
			}
		}
	}

}
