package com.jdk8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestForeach {

	public static void main(String[] args) {

		List<User> users = new ArrayList<User>();
		users.add(new User("a", 1));
		users.add(new User("c", 3));
		users.add(new User("b", 2));
		Optional<User> avalibleScheduleDO = users.stream().filter(u -> "b".equals(u.getName()) || "c".equals(u.getName())).findFirst();
		System.out.println("===========");
	}

}
