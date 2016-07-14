package com;

public class CommonTest {

	public static void main(String[] args) {
		String ss = "function svdsp_clk() {}#_SS_#<div></div>";
		String[] aa = ss.split("#_SS_#");
		System.out.println(aa.length);
		
		String temp=ss.replace("clk()", "aaaaa");
		System.out.println("temp = "+temp);
	}
}
