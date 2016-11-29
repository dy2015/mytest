package com.splitwords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PhraseCorrection {

	/**
	 * ============================词组纠错开始
	 * @param word 输入的汉字词组
	 * @param input  标准词库
	 * @param inputWordMap 输入的汉字词组对应的拼音
	 * @param standardWordMap 标准词库对应的拼音
	 * @return
	 */
	public static String[] start(String word, String[] input, Map<String, String> inputWordMap, String[] standardWord, Map<String, String> standardWordMap) {
		String[] newInput = cut(word);// 拆词，并组合新的搜索词组
//		for (String s : newInput) {
//			System.out.println(s);
//		}
		String[] newMach = match(inputWordMap, standardWordMap, newInput,standardWord);// 匹配，纠错
//		for (String s : newMach) {
//			System.out.println(s);
//		}
		newInput = combination(inputWordMap, standardWordMap, newMach,newInput);// 纠错后的词组，重新组合
		return newInput;
	}

	/**
	 * 将输入的字符串拆分成单个字符,将拆分后的字符再组词
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
	 * @param input 输入新组合的词语
	 * @return result 返回纠错后的词组
	 */
	private static String[] match(Map<String, String> inputWordMap, Map<String, String> standardWordMap, String[] input, String[] standardWord) {
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
	 * @param input 输入新组合的词语
	 * @return result 返回纠错后的词组
	 */
	private static String[] combination(Map<String, String> inputWordMap, Map<String, String> standardWordMap, String[] input, String[] newInput) {
		boolean tag = true;
		List<String> list = new ArrayList<>();
		String result = "";
		String resultPinyYin = "";
		String tempOld ="";
		String temp ="";
		for (String key : inputWordMap.keySet()) {
			tempOld = key;
			temp=key;
			resultPinyYin = inputWordMap.get(key);
			for (String s : input) {
				if (key.indexOf(s) == 0) {
					list.add(s);
				}
			}
			for (String s : input) {
				if (key.indexOf(s) == 0) {
					result += s;
					resultPinyYin = resultPinyYin.substring(standardWordMap.get(s).length());
					key = key.substring(s.length());
				}else{
					if (key.length() > 0) {
						if (s.substring(0, 1).equals(key.substring(0, 1))) {
							result = s;
							if (standardWordMap.get(s).length() <= resultPinyYin.length()) {
								resultPinyYin = resultPinyYin.substring(standardWordMap.get(s).length());
								key = key.substring(s.length());
							}
							list.add(result);
						}
					}
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
						list.add(result + s);
					}
				}//end--for
				if(list.size()<=0){
					list.add(result);
				}
			}else{
				list.add(result);
			}
		} // end--for
		int size = list.size();
		
		/**优化结果：当只有一个推荐返回时，且返回词组长度小于输入的搜索词组长度，则在进行词库筛选，找出一批匹配度稍弱的词组来**/
		if (size < 2) {
			String tempStr = "";
			int count=0;
			while (tempStr.length() < temp.length()) {
				if ("".equals(tempStr)) {
					tempStr = list.get(size - 1);
				}
				temp = temp.substring(tempStr.length());
				if (tempStr.length() > 1) {
					for (String str : input) {
						if (temp.indexOf(str.substring(0, 1)) == 0) {
							list.add(tempStr + str);
						}
					} // end--for
				} else {
					list.remove(size - 1);
				}
				int tempSize = list.size();
				for (String str : input) {
					if (temp.indexOf(str.substring(0, 1)) == 0) {
						continue;
					}
					String[] ss = cut(str);
					for (String s : ss) {
						if (temp.contains(s)) {
							if (tempStr.length() > 1) {
								list.add(tempStr + str);
							} else {
								list.add(str);
							}
							break;
						}
					}
				} // end--for
				if (list.size() > 0) {
					if (tempSize > 1) {
						size = tempSize * count + 2;
					} else {
						size = tempSize;
						if (size < 1) {
							size = 1;
						}
					}
					if (size > temp.length()) {
						break;
					}
					tempStr = list.get(size - 1);
					count++;
				} else {
					break;
				}
			} // end--while
		}
		/**如果汉字纠错后长度达不到输入词组的长度，则按拼音纠错，推荐出拼音类似的词组出来，直到全部找出拼音类似的词组**/
		String tempPinYin="";
		if(list.size()>0){
			tempPinYin=PinYinUtil.CN2Spell(tempOld.substring(list.get(0).length()));
			for(String key:standardWordMap.keySet()){
				if(standardWordMap.get(key).equals(tempPinYin)){
					list.add(list.get(0)+key);
				}
			}
		}else{
			for (int i = newInput.length - 1; i >= 0; i--) {
				tempPinYin = PinYinUtil.CN2Spell(newInput[i]);
				for (String key : standardWordMap.keySet()) {
					if (tempPinYin.indexOf(standardWordMap.get(key))==0) {
						list.add(key);
					}
				}
				for (String key : standardWordMap.keySet()) {
					if (standardWordMap.get(key).equals(tempPinYin)) {
						if (key.length() < tempOld.length()) {
							if (list.size() > 0&&!list.get(0).equals(key)) {
								list.add(list.get(0) + key);
							}
						}
						list.add(key);
					}
				}
			}//end--for
		}
		
		/** 拼音纠错结束 *******/
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String s : list) {
			if (s.length() <= tempOld.length()) {
				map.put(s, s.length());
			}
		}
		List<String> resultList = SortMapByValue.sort(map);
		size = resultList.size();
		String[] resultStr = new String[size];
		for (int i = 0; i < size; i++) {
			resultStr[i] = resultList.get(i);
		}
		return resultStr;
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
			List<String> resultList = SortMapByValue.sort(map);
//			List<String> resultList = new ArrayList<>();
//			for (String s : tempList) {
////				if (inputWordMap.get(key).contains(standardWordMap.get(s))) {
//					resultList.add(s);
////				}
//			} // end--for

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