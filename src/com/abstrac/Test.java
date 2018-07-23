package com.abstrac;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		Parent t = new Child("a");
		t.setAge(12);
		System.out.println(t);
		Child b = new Child("b");
		if (b instanceof Parent) {
			System.out.println(1);
		} else {
			System.out.println(0);
		}

		String str = "1-3";
		Pattern p = Pattern.compile("[0-9]-[0-9]{1,3}");
		Matcher m = p.matcher(str);
		if (m.matches()) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}

}
