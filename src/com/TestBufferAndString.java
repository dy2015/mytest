package com;


public class TestBufferAndString {

	static class Student {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Student [name=" + name + "]";
		}

	}

	public static void stringReplace(String text, String temp) {
		text = text.replace(temp, "L");
		// System.out.println(text);
	}

	public static void bufferReplace(StringBuffer text, String temp) {
		text = text.replace(text.indexOf(temp), text.indexOf(temp) + temp.length(), "#to#");
		// System.out.println(text);
	}

	public static void bufferBulidReplace(StringBuilder text, String temp) {
		text = text.replace(text.indexOf(temp), text.indexOf(temp) + temp.length(), "#to#");
		// System.out.println(text);
	}

	public static void main(String[] args) {
		/**
		 * int j = 0; for(int i = 0;i<100;i++) { j=j++; } System.out.println(j+
		 * "  " );
		 */
		for (int j = 0; j < 10; j++) {
			int num = 500000;
			String temp = "#to#";
			String textString = new String("java welcome #to# chaina");
			StringBuffer textBuffer = new StringBuffer("java welcome #to# chaina");
			StringBuilder textBufferBulid = new StringBuilder("java welcome #to# chaina");
			double start = System.currentTimeMillis();
			for (int i = 0; i < num; i++) {
				stringReplace(textString, temp);
			}
			System.out.println("第一个耗时(String)：" + (System.currentTimeMillis() - start)/1000);
			start = System.currentTimeMillis();
			for (int i = 0; i < num; i++) {
				bufferReplace(textBuffer, temp);
			}
			System.out.println("第二个耗时(StringBuffer)：" + (System.currentTimeMillis() - start)/1000);
			start = System.currentTimeMillis();
			for (int i = 0; i < num; i++) {
				bufferBulidReplace(textBufferBulid, temp);
			}
			System.out.println("第三个耗时(StringBuilder)：" + (System.currentTimeMillis() - start)/1000);
			
			
			for (int i = 0; i < num*10; i++) {
				textBuffer.append("java welcome #to# chaina");
			}
			for (int i = 0; i < num*10; i++) {
				textBufferBulid.append("java welcome #to# chaina");
			}
			start = System.currentTimeMillis();
			textBuffer.toString();
			System.out.println("第四个耗时(StringBuffer转String)：" + (System.currentTimeMillis() - start)/1000);
			
			start = System.currentTimeMillis();
			textBufferBulid.toString();
			System.out.println("第五个耗时(StringBuilder转String)：" + (System.currentTimeMillis() - start)/1000);
			
			System.out.println("====================================="+j);
		}
	}
}
