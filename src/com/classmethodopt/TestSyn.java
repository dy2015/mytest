package com.classmethodopt;

public class TestSyn {

	public static void main(String[] args) {
		Thread t=new Thread(new TestClassMethodIn2());
		t.start();
		Thread t1=new Thread(new TestClassMethodIn1());
		t1.start();
		
	}

}
