package com.splitwords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PhraseCorrection {

	private final static String inputWord = "1.8米电视机";// 输入的搜索词组
	private final static String[] standardWord = { "3米", "1.9", "2米", "柜子", "电视机", "1.6米", "电脑柜", "电脑桌", "电脑", "笔记本电脑", "电冰箱", "电视桌", "电视", "电视柜", "1.8", "米" };// 正确的标准词库
	private final static Map<String, String> inputWordMap = new HashMap<>();// 输入的搜索词组
	private final static Map<String, String> standardWordMap = new HashMap<>();// 正确的标准词库
	static {
		inputWordMap.put(inputWord, "yidianbamidianshiji");
		standardWordMap.put("3米", "sanmi");
		standardWordMap.put("1.9", "yidianjiu");
		standardWordMap.put("2米", "liangmi");
		standardWordMap.put("柜子", "guizi");
		standardWordMap.put("电视机", "dianshiji");
		standardWordMap.put("1.6米", "yidianliumi");
		standardWordMap.put("电脑柜", "diannaogui");
		standardWordMap.put("电脑桌", "diannaozhuo");
		standardWordMap.put("电脑", "diannao");
		standardWordMap.put("笔记本电脑", "bijibendiannao");
		standardWordMap.put("电冰箱", "dianbingxiang");
		standardWordMap.put("电视桌", "dianshizhuo");
		standardWordMap.put("电视", "dianshi");
		standardWordMap.put("电视柜", "dianshigui");
		standardWordMap.put("1.8", "yidianba");
		standardWordMap.put("米", "mi");
	}

	public static void main(String[] args) {
		for (String key : inputWordMap.keySet()) {
		      start(key,standardWord,inputWordMap,standardWordMap);
		}
	}
	private static String start(String word, String[] input,Map<String, String> inputWordMap, Map<String, String> standardWordMap) {
		String[] newInput = cut(word);// 拆词，并组合新的搜索词组
		// String[] newInput={"米电视","电视贵"};
		String[] newMatch = match(inputWordMap, standardWordMap, newInput);// 匹配，纠错
//		for (String s : newMatch) {
//			System.out.println(s);
//		}
		String result = combination(inputWordMap, standardWordMap, newMatch);// 纠错后的词组，重新组合
		System.out.println("输入的搜索词组："+word);
		System.out.println("===========================");
		System.out.println("纠正后的词组："+result);
		return result;
	}
	
	/**
	 * 将输入的字符串拆分成单个字符,将拆分后的字符再组词
	 * 
	 * @param word
	 *            输入的搜索词组
	 * @return result 按照输入的搜索词组，重新组合的词语
	 */
	private static String[] cut(String word) {
		String[] cutInputWord = new String[word.length()];
		int len = word.length();
		for (int i = 0; i < len; i++) {
			cutInputWord[i] = word.substring(i, i + 1);
		}
		List<String> list = new ArrayList<>();
		// 将拆分后的字符组词
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len - i; j++) {
				// System.out.println(word.substring(j,j+i)+cutInputWord[j+i]);
				list.add(word.substring(j, j + i) + cutInputWord[j + i]);
			}
		} // end--for
			// for (int i = 1; i < len; i++) {
			// for (int j = 0; j < len - i+1; j++) {
			// for (int k = j+1; k < len - i+1; k++) {
			// System.out.println(word.substring(j,j+i)+cutInputWord[k+i-1]);
			// //list.add(word.substring(j, j + i) + cutInputWord[j + i]);
			// }
			// }
			// } // end--for

		len = list.size();
		String[] result = new String[len];
		for (int i = 0; i < len; i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	/**
	 * 将新组合的词语,与正确的标准词库进行匹配，匹配时，以输入的词语长度，来作为纠词的限制条件 匹配时，相同长度的词语进行纠错.然后留下纠错后的词语
	 * 
	 * @param input
	 *            输入新组合的词语
	 * @return result 返回纠错后的词组
	 */
	private static String[] match(Map<String, String> inputWordMap, Map<String, String> standardWordMap, String[] input) {
		Set<String> set = new HashSet<>();
		for (int i = 0; i < input.length; i++) {
			String[] newInput = cut(input[i]);
			boolean tag = false;

			for (int j = 0; j < standardWord.length; j++) {
				tag = false;
				if (input[i].length() != standardWord[j].length()) {
					continue;
				}
				for (int v = input[i].length(); v > 0; v--) {// 搜索词有小于等于n个字符可以和标准词组匹配成功，就可以留下这个标准词组,且留下匹配度最高的
					for (int x = newInput.length - 1; x >= 0; x--) {
						if (newInput[x].length() != v) {
							continue;
						}
						if (standardWord[j].contains(newInput[x])) {
							set.add(standardWord[j]);
							tag = true;
							break;

						}
					} // end--for
					if (tag) {
						break;
					}
				} // end--for
			} // end--for
		} // end--for
		int size = set.size();
		String[] result = new String[size];
		Iterator<String> iterator = set.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			result[index++] = iterator.next();
		}
		return optimization(inputWordMap, standardWordMap, result);
	}

	/**
	 * 将新组合的词语,与正确的标准词库进行匹配，匹配时，以输入的词语长度，来作为纠词的限制条件 匹配时，相同长度的词语进行纠错.然后留下纠错后的词语
	 * 
	 * @param input
	 *            输入新组合的词语
	 * @return result 返回纠错后的词组
	 */
	private static String combination(Map<String, String> inputWordMap, Map<String, String> standardWordMap, String[] input) {
		boolean tag = true;
		String result = "";
		String resultPinyYin = "";
		for (String key : inputWordMap.keySet()) {
			String temp = key;
			resultPinyYin = inputWordMap.get(key);
			for (String s : input) {
				if (key.indexOf(s) == 0) {
					result += s;
					resultPinyYin = resultPinyYin.substring(standardWordMap.get(s).length());
					key = key.substring(s.length());
				}
				if (s.length() == key.length()) {
					if (resultPinyYin.contains(standardWordMap.get(s))) {
						result += s;
						if (result.length() >= temp.length()) {
							tag = false;// 表示已经纠正全部词语
							break;
						}
						resultPinyYin = resultPinyYin.substring(standardWordMap.get(s).length());
					}
				}
			} // end--for
			if (tag) {
				for (String s : input) {
					if (resultPinyYin.contains(standardWordMap.get(s))) {
						result += s;
						break;
					}
				}
			}
		} // end--for
		return result;
	}

	private static String[] optimization(Map<String, String> inputWordMap, Map<String, String> standardWordMap, String[] input) {
		for (String key : inputWordMap.keySet()) {
			String[] newInput = null;
			List<String> list = new ArrayList<>();
			for (String s : input) {
				if (key.indexOf(s) == 0) {
					list.add(s);
				} else {
					newInput = cut(s);// 拆词，并组合新的搜索词组
					for (int i = newInput.length - 1; i >= 0; i--) {
						if (key.contains(newInput[i])) {
							list.add(s);
							break;
						}
					}
				}
			} // end--for
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String s : list) {
				map.put(s, s.length());
			}
			List<String> tempList = SortMapByValue.sort(map);
			List<String> resultList = new ArrayList<>();
			for (String s : tempList) {
				if (inputWordMap.get(key).contains(standardWordMap.get(s))) {
					resultList.add(s);
				}
			} // end--for

			int size = resultList.size();
			String[] result = new String[size];
			for (int i = 0; i < size; i++) {
				result[i] = resultList.get(i);
			}
			return result;
		}
		return null;
	}

}
