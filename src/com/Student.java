package com;

import java.util.Map;

public class Student {
	private String name;
	private int age;
	private int tag;
	private Map<String, Integer> prioritySeq;
	private boolean flag;

	public Student() {
		super();
	}

	public Student(String name, int age, int tag) {
		this.name = name;
		this.age = age;
		this.tag = tag;
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

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public Map<String, Integer> getPrioritySeq() {
		return prioritySeq;
	}

	public void setPrioritySeq(Map<String, Integer> prioritySeq) {
		this.prioritySeq = prioritySeq;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public boolean equals(Object obj) {
		Student po = (Student) obj;
		if (this.name.equals(po.getName())) {
			return true;
		}
		return false;
	}

	public boolean compareTo(Student s) {
		if (this.name.equals(s.getName())) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", tag=" + tag + ", prioritySeq=" + prioritySeq + ", flag=" + flag + "]";
	}
}
