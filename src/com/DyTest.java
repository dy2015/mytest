package com;

public class DyTest {
	private static boolean flag;
	public static void main(String[] args){
		String id="123232328798773";
		try{
		Long d=Long.valueOf(id);
		System.out.println("d="+d);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
			 
		String str="abc abc dd ";
		System.out.println(str.lastIndexOf("dd"));
		
		Integer in=new Integer(12);
		System.out.println(in);
		String ssssss=(String) in.toString();
		System.out.println(ssssss);
		
		String sss="322,320,324,346,325,323,321,327,345,340,341,344,342,343,1421835827,1425020640,1427883541,1433218285,1435125689,1436945825,1436945894,1441874967,1463562019";
		System.out.println("长度:"+sss.length());
		System.out.println("========================================");
		System.out.println("flag:"+flag);
//		deffience();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		// replace();
		// zhengze();
		// splitTest();
		// replaceTest();
		// javaGetIp();
//		System.out.println(new Random().nextInt(100));
		try {
			Student student = new Student();
			student.setFlag(1 == 3);
		} catch (Exception e) {
			System.out.println(e);
		}	
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

