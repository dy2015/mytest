package com;

import java.util.BitSet;

public class TestBitSet {
	 private final static int ADDRESS_BITS_PER_WORD = 6;
	 private final static  long WORD_MASK = 0xffffffffffffffffL;
	public static void main(String[] args) {

		int a = 1 << 6;
		System.out.println("a=" + a);
		int b = 63 >> 6;
		System.out.println("b=" + b);
		BitSet bm = new BitSet();
		System.out.println(bm.isEmpty() + "--" + bm.size());
		bm.set(0);
		System.out.println(bm.isEmpty() + "--" + bm.size());
		bm.set(1);
		System.out.println(bm.isEmpty() + "--" + bm.size());
		System.out.println(bm.get(65));
		System.out.println(bm.isEmpty() + "--" + bm.size());
		bm.set(65);
		System.out.println(bm.isEmpty() + "--" + bm.size());
		for (int i=0;i<bm.size();i++) {
			System.out.println("第"+(i+1)+"行："+bm.isEmpty() + "--" + bm.get(i));
		}
		
		BitSet newBit = new BitSet();
		newBit.set(0);
		newBit.or(bm);
		for(int i=0;i<newBit.size();i++)
		System.out.println("i="+i+":"+newBit.nextSetBit(i)+";"+newBit.get(i));
		
		
		long[] words=new long[10];
		
		int bitIndex=63;
		int wordIndex = wordIndex(bitIndex);
		words[wordIndex] |= (1L << bitIndex);
		
		long a1=1L << bitIndex;
//		long b1=0;
		long b1=words[wordIndex];
		long c1 =a1&b1;
		long d1=WORD_MASK << bitIndex;
		long e1=Long.numberOfTrailingZeros(63);
        System.out.println(c1 != 0);
        
	}
	private static int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }
}
