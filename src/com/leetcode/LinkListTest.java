package com.leetcode;

public class LinkListTest {

	public static void main(String[] args) {
		LinkListTest test = new LinkListTest();
		LinkListTest.ListNode head = test.new ListNode(8);
		LinkListTest.ListNode node1 = test.new ListNode(1);
		LinkListTest.ListNode node2 = test.new ListNode(3);
		LinkListTest.ListNode node3 = test.new ListNode(2);
		LinkListTest.ListNode node4 = test.new ListNode(5);
		head.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		System.out.println("初始链表顺序：");
		print(head);
		LinkListTest.ListNode begin = test.sortList(head);
		System.out.println("排序后链表顺序：");
		print(begin);

	}

	public static void print(ListNode head) {
		while (head.next != null) {
			System.out.print(head.val + " ");
			head = head.next;
		}
		System.out.println(head.val);
	}

	public ListNode sortList(ListNode head) {
		if (head == null || head.next == null)
			return head;
		ListNode p1 = head.next;
		ListNode p2 = head;

		while (p1 != null && p1.next != null) {
			p2 = p2.next;
			p1 = p1.next.next;
		}

		ListNode right = sortList(p2.next);
		p2.next = null;
		ListNode left = sortList(head);
		return compare(left, right);
	}

	public ListNode compare(ListNode left, ListNode right) {
		ListNode head = new ListNode(-1);
		ListNode p = head;
		while (left != null && right != null) {
			if (left.val < right.val) {
				p.next = left;
				left = left.next;
			} else {
				p.next = right;
				right = right.next;
			}
			p = p.next;
		}
		if (left == null)
			p.next = right;
		if (right == null)
			p.next = left;
		return head.next;
	}

	class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}
	}
}
