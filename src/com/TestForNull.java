package com;

import java.util.ArrayList;
import java.util.List;

public class TestForNull {

	public static void main(String[] args) {
		List<Integer> ids = new ArrayList<>();
		for (Integer id : ids) {
			if (id != null) {
				System.out.println("id:" + id);
			}
		}
	}
}
