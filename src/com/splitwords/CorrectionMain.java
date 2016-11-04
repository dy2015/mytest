package com.splitwords;

import java.util.HashMap;
import java.util.Map;

public class CorrectionMain {
	private final static String inputWord = "阿里巴巴";// 输入的搜索词组
	private static String[] standardWord = null;// 正确的标准词库
	private final static Map<String, String> inputWordMap = new HashMap<>();// 输入的搜索词组
	private final static Map<String, String> standardWordMap = new HashMap<>();// 正确的标准词库

	public static void main(String[] args) {
		if (inputWord != null && !"".equals(inputWord)) {
			inputWordMap.put(inputWord, PinYinUtil.CN2Spell(inputWord));
			for (String key : inputWordMap.keySet()) {
				standardWord = LoadWordLibrary.Load(standardWordMap);
				if (standardWord != null) {
					String[] result = PhraseCorrection.start(key, standardWord, inputWordMap, standardWord, standardWordMap);
					System.out.println("输入的搜索词组：" + key);
					System.out.println("===========================");
					for (int i = 0; i < result.length; i++) {
						System.out.println("纠错后的推荐词组：(第" + (i + 1) + "位)：" + result[i]);
					}
				} else {
					System.out.println("没有标准词库，请传入标准词库.");
				}
			}
		} else {
			System.out.println("请输入检索词组.");
		}
	}

}
