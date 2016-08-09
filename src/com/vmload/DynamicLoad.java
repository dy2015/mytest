package com.vmload;

public class DynamicLoad {
	static class Human {
		public void sayHello() {
			System.out.println(" hello Human");
		}
	}

	static class Man extends Human {
		@Override
		public void sayHello() {
			System.out.println(" hello man");
		}
	}

	static class WoMan extends Human {
		@Override
		public void sayHello() {
			System.out.println(" hello woman");
		}
	}

	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new WoMan();
		man.sayHello();
		woman.sayHello();
	}

}
