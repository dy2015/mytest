package com.abstrac;

public class Child extends Parent {

	public Child(String name) {
		super(name);
	}

	@Override
	public String toString() {
		return "Child [name=" + name + ", age=" + age + "]";
	}

	@Override
	int get1(int a, int b) {
		// TODO Auto-generated method stub
		return 0;
	}

}
