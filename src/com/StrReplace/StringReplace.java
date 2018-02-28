package com.StrReplace;

public class StringReplace {

	public static String removeStr(String src, String str) {
		if (src == null || str == null)
			return src;
		int idx = src.indexOf(str);
		if (idx == -1)
			return src;
		int pst = 0;
		char[] cs = src.toCharArray();
		char[] rs = new char[src.length() - str.length()];
		for (int i = 0; i < cs.length; i++) {
			if (i >= idx && i < idx + str.length())
				continue;
			rs[pst] = cs[i];
			pst++;
		}
		return new String(rs);
	}

	public static String replaceStr(String src, String target, String replacement) {
		if (src == null || target == null || replacement == null)
			return src;
		int idx = src.indexOf(target);
		if (idx == -1)
			return src;
		int pst = 0;
		char[] cs = src.toCharArray();
		char[] rs = new char[src.length() - target.length() + replacement.length()];
		for (int i = 0; i < cs.length; i++) {
			if (i == idx) {
				for (char c : replacement.toCharArray()) {
					rs[pst] = c;
					pst++;
				}
				i += target.length() - 1;
				continue;
			}
			rs[pst] = cs[i];
			pst++;
		}
		return new String(rs);
	}

	public static String replaceStr2(String src, String target, String replacement) {
		if (src == null || target == null || replacement == null)
			return src;
		StringBuilder str = new StringBuilder();
		int idx = src.indexOf(target);
		if (idx == -1)
			return src;
		str.append(src.substring(0, idx)).append(replacement).append(src.substring(idx + target.length()));
		return str.toString();
	}

	/**
	 * 
	 * @param src
	 * @param target
	 * @param replacement
	 * @return
	 */

	public static String replaceAllStr(String src, String target, String replacement) {
		if (src == null || target == null || replacement == null)
			return src;
		int idx = src.indexOf(target);
		if (idx == -1)
			return src;
		int pst = 0;
		char[] cs = src.toCharArray();
		char[] rs = new char[src.length() - target.length() + replacement.length()];
		for (int i = 0; i < cs.length; i++) {
			if (i == idx) {
				for (char c : replacement.toCharArray()) {
					rs[pst] = c;
					pst++;
				}
				i += target.length() - 1;
				for (int j = i; j > i - target.length(); j--) {
					cs[j] = '|';
				}
				idx = new String(cs).indexOf(target);
				continue;
			}
			rs[pst] = cs[i];
			pst++;
		}
		return new String(rs);
	}

	public static String replaceAllStr2(String src, String target, String replacement) {
		StringBuilder str = new StringBuilder();
		int idx = src.indexOf(target);
		if (idx == -1)
			return src;
		while (idx != -1) {
			str.append(src.substring(0, idx)).append(replacement);
			src = src.substring(idx + target.length());
			idx = src.indexOf(target);
		}
		str.append(src);
		return str.toString();
	}

	public static void main(String[] args) {
		int num = 1000;
		String str = "http://yktd.m.cn.miaozhen.com/r/k=2027285&p=738xv&dx=__IPDX__&rt=2&ns=127.0.0.1&ni=__IESID__&v=__LOC__&xa=__ADPLATFORM__&mo=__OS__&m0=__OPENUDID__&m0a=__DUID__&m1=__ANDROIDID1__&m1a=__ANDROIDID__";
		// String str =
		// "http://yktd.m.cn.miaozhen.&ni=__IESID__&ni=__IESID__&ni=__IESID__&ni=__IESID__&ni=__IESID__";
		System.out.println("len=" + str.length());
		String target = "__LOC__";
		String replaceStr = "123";
		System.out.println("原始串:");
		System.out.println(str);
		for (int hi = 0; hi < 100; hi++) {
			System.out.println("================replaceAll:=======================");
			long start = System.currentTimeMillis();
			for (int i = 0; i < num; i++) {
				replaceAllStr(str, target, replaceStr);
			}
			System.out.println("新方法1耗时：" + (System.currentTimeMillis() - start));
			System.out.println(replaceAllStr(str, target, replaceStr));

			start = System.currentTimeMillis();
			for (int i = 0; i < num; i++) {
				replaceAllStr2(str, target, replaceStr);
			}
			System.out.println("新方法2耗时：" + (System.currentTimeMillis() - start));
			System.out.println(replaceAllStr2(str, target, replaceStr));

			start = System.currentTimeMillis();
			for (int i = 0; i < num; i++) {
				str.replaceAll(target, replaceStr);
			}
			System.out.println("老方法耗时：" + (System.currentTimeMillis() - start));
			System.out.println(str.replaceAll(target, replaceStr));

			System.out.println("================replace:===========================");
			start = System.currentTimeMillis();
			for (int i = 0; i < num; i++) {
				str.replace(target, replaceStr);
			}
			System.out.println("老方法耗时：" + (System.currentTimeMillis() - start));
			System.out.println(str.replace(target, replaceStr));

			System.out.println("================replace first:===========================");
			start = System.currentTimeMillis();
			for (int i = 0; i < num; i++) {
				replaceStr(str, target, "?");
			}
			System.out.println("新方法1耗时：" + (System.currentTimeMillis() - start));
			System.out.println(replaceStr(str, target, replaceStr));

			start = System.currentTimeMillis();
			for (int i = 0; i < num; i++) {
				replaceStr2(str, target, "?");
			}
			System.out.println("新方法2耗时：" + (System.currentTimeMillis() - start));
			System.out.println(replaceStr2(str, target, replaceStr));
			System.out.println("str="+str);
			start = System.currentTimeMillis();
			for (int i = 0; i < num; i++) {
				str.replaceFirst(target, replaceStr);
			}
			System.out.println("老方法耗时：" + (System.currentTimeMillis() - start));
			System.out.println(str.replaceFirst(target, replaceStr));
		}//end--for
	}
}
