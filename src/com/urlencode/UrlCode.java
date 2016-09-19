package com.urlencode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlCode {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "1355313%3A1%3A792064%2C792061%2C792040%2C%3A1476181755_1355316%3A2%3A792074%2C792071%2C%3A1476181755_1355310%3A2%3A791997%2C791971%2C%3A147618175";
		System.out.println(URLDecoder.decode(s, "UTF-8"));
	}

}
