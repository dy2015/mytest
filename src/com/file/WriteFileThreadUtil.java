package com.file;

/**
 * 功能描述：独占锁文件内容追加写入操作
 **/

public class WriteFileThreadUtil implements Runnable {
	private String filePath = "";
	private String content = "";
	private boolean add = false;

	public WriteFileThreadUtil(String filePath, String content, boolean add) {
		super();
		this.filePath = filePath;
		this.content = content;
		this.add = add;
	}

	@Override
	public void run() {
		OpearFileUtil.excute(filePath, content, add);
	}

}