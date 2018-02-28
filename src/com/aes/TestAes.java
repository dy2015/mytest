package com.aes;

public class TestAes {

	public static void main(String[] args) {
		String content = "test";
		String password = "12345678";
		// 加密
		System.out.println("加密前：" + content);
		byte[] encryptResult = AesService.encrypt(content, password);
		System.out.println("加密后：" + new String(encryptResult));
		// 解密
		byte[] decryptResult = AesService.decrypt(encryptResult, password);
		System.out.println("解密后：" + new String(decryptResult));

	}

}
