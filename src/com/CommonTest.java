package com;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonTest {

	public static void main(String[] args) {
		String ss = "function svdsp_clk() {}#_SS_#<div></div>";
		String[] aa = ss.split("#_SS_#");
		System.out.println(aa.length);
		
		String temp=ss.replace("clk()", "aaaaa");
		System.out.println("temp = "+temp);
		
		Date time = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		String str=sdf.format(time);  
		System.out.println(str);
	}
}
