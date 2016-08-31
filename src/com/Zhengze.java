package com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Zhengze {
	private static Pattern regex_vid = Pattern.compile("\\w+X(.+).html?");

	public static void main(String[] args) {
		String s1 = "http://v.youku.com/v_show/id_XMTU5MzY3NjgzNg==.html";
		String s2 = "http://wdnzj.youku.com/main.html";
		String s3 = "http://r1.ykimg.com/material/0A03/201604/0408/106746/400-300.jpg";
		Matcher m1 = regex_vid.matcher(s1);
		if (m1.find()) {
			System.out.println("m1-----" + s1);
		}
		Matcher m2 = regex_vid.matcher(s2);
		if (m2.find()) {
			System.out.println("m2-----" + s2);
		}
		StringBuilder s=new StringBuilder("abdcf");
		System.out.println(s.replace(3, s.length()-1, "2"));
		System.out.println(s);
	}

}
