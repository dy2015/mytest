package com.vmload;

import java.lang.invoke.MethodHandle;
import static java.lang.invoke.MethodHandles.lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class StaticLoad {
	static class Human {
		void info(int i) {
			System.out.println("我是Human"+i);
		}
	}

	static class Man extends Human {
		/**
		 * MethodType表示“方法类型”，包含了方法的返回值(第一个参数）和具体的参数（第二个及其以后的参数） 
		 */
		void print() {
			MethodType mt = MethodType.methodType(void.class,int.class);
			try {
				MethodHandle mh = lookup().findSpecial(getClass().getSuperclass(), "info", mt, getClass());
				mh.invoke(this,10);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			System.out.println("我是man");
		}
	}

	static class WoMan extends Human {
		public void print() {
			System.out.println("我是WoMan");
		}
	}

	public void sayHello(Human human) {
//		human.print();
		System.out.println(" hello Human");
	}

	public void sayHello(Man man) {
		man.print();
		System.out.println(" hello man");
	}

	public void sayHello(WoMan woman) {
		woman.print();
		System.out.println(" hello woman");
	}

	public static void main(String[] args) throws Throwable {
		Class c = StaticLoad.class;
		Method[] m = c.getMethods();
		for (Method o : m) {
			System.out.println(o.getName());
		}
		
		
		Method m1 = c.getMethod("sayHello", new Class[] { Human.class });
		Human human = new Human();
		Human man = new Man();
		Human woman = new WoMan();
		StaticLoad st = new StaticLoad();
		st.sayHello(man);
		st.sayHello(woman);

		System.out.println("============反射=================");
		StaticLoad s = new StaticLoad();
		m1.invoke(s, human);

		System.out.println("++++++++++++invokedynamic指令:动态类型++++++++++++++++");
        (new Man()).print();
	}

}
