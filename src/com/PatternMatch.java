package com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class PatternMatch {
	private static Pattern regex_vid = Pattern.compile("\\w+X(.+).html?");

	public static void main(String[] args) {
		String urlString = " http://v.youku.com/v_show/id_XNzU0MTEyMzg4.html";
		Matcher m = regex_vid.matcher(urlString);
		if (m.find()) {
			String id = m.group(1);
			System.out.println("id=" + id);
			id = StringUtil.decodeBase64(id);
            int idd = Integer.parseInt(id) >> 2;
            System.out.println("idd=" + idd);
		}
	}

}
