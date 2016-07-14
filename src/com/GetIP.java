package com;

public class GetIP {
	public static long getIPLong(String ip) {
		long iplong = 0;
		String[] ipArray = ip.split("\\.");
		int length = ipArray.length;
		for (int i = 0; i < length; i++) {
			long temp = Long.valueOf(ipArray[i]);
			System.out.println("temp=" + temp);
			int moveTime = 24 - 8 * i;
			temp = temp << moveTime;
			System.out.println("temp2=" + temp);
			iplong += temp;
		}
		return iplong;
	}

	public static void main(String[] arges) {
		long ip = getIPLong("127.0.1.2");
		System.out.println("ip=" + ip);
	}
}
