package com.selectData;

import java.util.ArrayList;
import java.util.List;

public class SelData {
	public static void compute(List<Tag> tagList, List<Tag> tagListTmp, String curPageTagId) {
		for (int i = 0, len = tagList.size(); i < len; i++) {
			if (tagList.get(i).getId().equals(curPageTagId)) {
				int num = 0;
				int index = len - 1;// 标签组里，包含自身，且小于等于31个数时-----------------------------》情况1：标签组里总数小于或者等于31个数时
				if (len >= 32) {
					index = i + 15;
					if (index > len - 1) {// ----------------------------------------------------》情况2：标签组里i后面不足15个数时
						num = len - 31;// 截取i前面的数,起始点从标签组最后往前数15+15+本身，共31个地方开始截取
						index = len - 1;
					} else {// ------------------------------------------------------------------》情况3：标签组里i前面有15个数时
						num = i - 15;// 前面还差num个数
						if (num < 0) {// --------------------------------------------------------》情况4：标签组里i前面不足15个数时
							index += Math.abs(num);
							num = 0;
						}
					}
				} // end--if
				tagListTmp.addAll(tagList.subList(num, i));// 截取i前面的数
				tagListTmp.addAll(tagList.subList(i + 1, index + 1));// 截取i后面的数
				break;
			} // end--if
		} // end--for
	}

	public static void main(String[] args) {
		String curPageTagId = "18";
		// 如果不是组合标签时，随机取15个标签
		List<Tag> tagList = new ArrayList<>();
		for (int i = 1; i < 35; i++) {
			Tag tag = new Tag();
			tag.setId(String.valueOf(i));
			tagList.add(tag);
		}
		System.out.println("list标签库：--->长度=" + tagList.size());
		for (Tag t : tagList) {
			System.out.print(t.getId() + ",");
		}
		System.out.println();
		List<Tag> tagListTmp = new ArrayList<>();
		compute(tagList, tagListTmp, curPageTagId);
		System.out.println();
		System.out.println("筛选结果：--->长度=" + tagListTmp.size());
		if (tagListTmp != null) {
			for (Tag t : tagListTmp) {
				System.out.print(t.getId() + ",");
			}
		}
	}
}
