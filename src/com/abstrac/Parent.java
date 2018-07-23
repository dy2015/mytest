package com.abstrac;

public abstract class Parent implements Peplo {
	String name;
	int age = 6;
	
	
	static int get1(int a) {
		return a;
	}
    
	abstract int get1(int a,int b);
	public Parent(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void getMessage() {
	};
}
