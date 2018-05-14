package com.file;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 功能描述： 1、独占锁文件写入操作 2、同时返回文件原始内容 add:是否清空文件
 **/

public class OpearFileUtil {
	public static String excute(String filePath, String content, boolean clean) {
		// Calendar calstart = Calendar.getInstance();
		StringBuffer result = new StringBuffer();// 原始文件内容
		File file = new File(filePath);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			// 对文件进行加锁
			RandomAccessFile out = new RandomAccessFile(file, "rw");
			FileChannel fcout = out.getChannel();
			FileLock flout = null;
			while (true) {
				try {
					flout = fcout.tryLock();// 无参是独占锁
					break;
				} catch (Exception e) {
					System.out.println("有其他线程正在操作该文件，当前线程休眠1000毫秒！");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}

			byte[] buf = new byte[1024];
			while ((out.read(buf)) != -1) {
				result.append(new String(buf, "utf-8"));
				buf = new byte[1024];
			}
			if (clean) {
				fcout.truncate(0);
			}
			out.write(content.getBytes("utf-8"));
			flout.release();
			fcout.close();
			out.close();
			out = null;
		} catch (Exception e) {
			System.out.println(e);
		}

		// System.out.println("写文件共花了:" + (Calendar.getInstance().getTimeInMillis() -
		// calstart.getTimeInMillis()) + "毫秒");
		return result.toString();
	}
}
