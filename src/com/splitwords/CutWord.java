package com.splitwords;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CutWord {

	private final static String inputWord = "1.8米电视贵";// 输入的搜索词组
	private final static String[] standardWord = { "2米", "柜子", "1.8米", "电视", "电视柜" };// 正确的标准词库

	public static void main(String[] args) {
		String[] newInput = cut(inputWord);//拆词，并组合新的搜索词组
		// String[] newInput={"米电视","电视贵"};
		newInput = match(newInput);//匹配，纠错
		newInput = combination(newInput);//纠错后的词组，重新组合
		for (String s : newInput) {
			System.out.println(s);
		}
	}

	/**
	 * 将输入的字符串拆分成单个字符,将拆分后的字符再组词
	 * 
	 * @param word 输入的搜索词组
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
		for (String s : cutInputWord) {
			list.add(s);
		}
		for (int i = 1; i < len; i++) {
			for (int j = 0; j < len - i; j++) {
				// System.out.println(word.substring(j,j+i)+cutInputWord[j+i]);
				list.add(word.substring(j, j + i) + cutInputWord[j + i]);
			}
		} // end--for
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
	 * @param input 输入新组合的词语
	 * @return result 返回纠错后的词组
	 */
	private static String[] match(String[] input) {
		Set<String> set = new HashSet<>();
		for (int i = 0; i < input.length; i++) {
			String[] newInput = cut(input[i]);
			boolean tag=false;
			
			for (int j = 0; j < standardWord.length; j++) {
				tag=false;
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
							tag=true;
							break;
							
						}
					} // end--for
					if(tag){
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
		return result;
	}
	/**
	 * 将新组合的词语,与正确的标准词库进行匹配，匹配时，以输入的词语长度，来作为纠词的限制条件 匹配时，相同长度的词语进行纠错.然后留下纠错后的词语
	 * 
	 * @param input 输入新组合的词语
	 * @return result 返回纠错后的词组
	 */
	private static String[] combination(String[] input) {
		
		return null;
	}
}
