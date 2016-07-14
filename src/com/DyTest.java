package com;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

public class DyTest {
	private static final double PI = 3.14;

	public static void javaGetIp() {
		try {
			// 获取计算机名
			String name = InetAddress.getLocalHost().getHostName();
			// 获取IP地址
			String ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println("计算机名：" + name);
			System.out.println("IP地址：" + ip);
		} catch (UnknownHostException e) {
			System.out.println("异常：" + e);
			e.printStackTrace();
		}
	}

	public static String getIp(String sif) {
		String x_ret = "";
		try {
			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();

				if (sif.compareToIgnoreCase(netInterface.getName()) != 0)
					continue;
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress ip = (InetAddress) addresses.nextElement();

					if ((ip != null) && (ip instanceof Inet4Address))
						return ip.getHostAddress();
				}
			}
		} catch (Exception e) {
		}

		return x_ret;
	}

	public static String getFirstValidEthIP() {
		String x_ret = null;

		String nicName = System.getProperty("nicName");

		if (nicName == null || (nicName.length() == 0)) {
			for (int i = 0; i < 3; ++i) {
				x_ret = getIp("eth" + i);
				if (x_ret != null && (x_ret.length() != 0)) {
					System.out.println("eth" + i);
					return x_ret;
				}
				x_ret = getIp("wlan" + i);
				if (x_ret != null && (x_ret.length() != 0)) {
					System.out.println("wlan" + i);
					return x_ret;
				}
				x_ret = getIp("en" + i);
				if (x_ret != null && (x_ret.length() != 0)) {
					System.out.println("en" + i);
					return x_ret;
				}
			}
		} else {
			System.out.println("nicName");
			x_ret = getIp(nicName);
		}
		return x_ret;
	}

	public void compute(int[] a) {
		double sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum = 0.5 * a[i] * a[i] * PI;
			System.out.println("面积=" + sum);
		}
	}

	public static void init() {
		int[] a = { 1, 2 };
		DyTest dy = new DyTest();
		dy.compute(a);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		System.out.println(c.get(11));
		System.out.println("============================");
		System.out.println("".equals(getFirstValidEthIP()) ? 0 : 1);
		javaGetIp();
		String cmd = "get";
		if (cmd == null || (!cmd.equals("view")) && !cmd.equals("get")) {
			System.out.println("请转入正确的 cmd参数值, view:仅预览; get:获取数据并清空！");
		} else {
			System.out.println("cmd=" + cmd);
		}
	}

	public static void replace() {
		String testStr = "abcd:;abcd";
		System.out.println("替换前状态：" + testStr);
		if (testStr.indexOf(":") != -1 || testStr.indexOf(";") != -1) {
			testStr.replace(":", "*");
			testStr.replace(";", "#");
		}
		System.out.println("替换后状态：" + testStr);
	}

	public static void zhengze() {
		String stand1 = "[a-z&&[^bc]]{1}?";
		String stand2 = "\\A[0-9]{1}?\\p{Lower}{1}?";
		String testStr = "7g";
		if (testStr.matches(stand2)) {
			System.out.println("满足正则条件！");
		} else {
			System.out.println("不满足正则条件！");
		}
	}

	public static void splitTest() {
		String s = "1%2A345";
		String[] ss = s.split("%2A");
		for (int i = 0; i < ss.length; i++) {
			System.out.println(ss[i]);
		}
		System.out.println(s.indexOf("2A"));
	}

	public static void replaceTest() {
		String s = "doucument:cum=[cum]&cum1=[cum1]";
		s = s.replace("[cum]", "http:\\123.com");
		System.out.println(s);
	}

	public static void main(String[] args) {
		 init();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		// replace();
		// zhengze();
		// splitTest();
//		replaceTest();
//		javaGetIp();
	}
}
