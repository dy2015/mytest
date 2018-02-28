package com.leetcode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Pattern;

public class StackTest {

	public static void main(String[] args) {
		String[] tokens = { "4", "13", "5", "/", "+" };
		System.out.println(evalRPN(tokens));
		String[] tokens2 = { "2", "1", "+", "3", "*" };
		System.out.println(evalRPN(tokens2));
		String[] tokens3 = { "4", "*", "5", "+", "3" };
		System.out.println(evalRPN(tokens3));
		String[] tokens4 = { "*", "5", "2", "+", "3" };
		System.out.println(evalRPN(tokens4));
		String[] tokens5 = { "3", "-4", "+" };
		System.out.println(evalRPN(tokens5));
		String[] tokens6 = { "3", "4", "-" };
		System.out.println(evalRPN(tokens6));
		// System.out.println(isNumeric("4"));
	}

	public static int evalRPN(String[] tokens) {
		Stack<Integer> array = new Stack<Integer>();
		Queue<String> queue = new LinkedList<String>();
		for (String temp : tokens) {
			if (isNumeric(temp)) {// 是数字
				if (array.size() > 1 && queue.size() > 0) {// 说明已经有两个数
					handle(array, queue.poll());
				}
				array.push(Integer.valueOf(temp));
			} else {
				queue.add(temp);
				if (array.size() > 1 && queue.size() > 0) {// 说明已经有两个数
					handle(array, queue.poll());
				}
			}
		}

		if (array.size() > 1 && queue.size() > 0) {// 说明已经有两个数
			handle(array, queue.poll());
		}
		return array.pop();
	}

	private static void handle(Stack<Integer> array, String tag) {
		int sum = 0;
		int after = array.pop();
		int before = array.pop();
		if ("+".equals(tag)) {
			sum += before + after;
		} else if ("-".equals(tag)) {
			sum += before - after;
		} else if ("*".equals(tag)) {
			sum += before * after;
		} else if ("/".equals(tag)) {
			sum += before / after;
		}
		array.push(sum);
	}

	private static boolean isNumeric(String str) {
			Pattern pattern = Pattern.compile("-[0-9]+|[0-9]*");
			return pattern.matcher(str).matches();
	}
}
