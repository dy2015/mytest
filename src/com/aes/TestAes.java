package com.aes;

public class TestAes {

	public static void main(String[] args) {
		String content = "本行业全局本行业全局本行业全局本行业全局本行业全局本行业全局本行业全局本行业全局本行业全局业全局本行业全局本行业全局本行业全局本行业全局本行业全局本行业全局";
		String password = "12345678";
		// 加密
		System.out.println("加密前：" + content);
		byte[] encryptResult = AesService.encrypt(content, password);
		String enStr = new String(encryptResult);
		System.out.println("加密后：" + enStr);
		// 解密
		byte[] decryptResult = AesService.decrypt(encryptResult, password);
		String decStr = new String(decryptResult);
		System.out.println("解密后：" + decStr);
		System.out.println(content.length());
		System.out.println(enStr.length());
		System.out.println(decStr.length());
	}

}
