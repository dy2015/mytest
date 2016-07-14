package com;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

public class TestConcurrentMap {

	public static void main(String[] args) {
		ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("a", "1");
		map.put("b", "2");
		System.out.println(map.put("c", "3"));
        System.out.println("结果是:"+map.putIfAbsent("a", "4"));
        System.out.println("按key取值结果是:"+map.get("a"));
        System.out.println("替换结果1是:"+map.replace("a", "5"));
        System.out.println("替换结果2是:"+map.replace("a", "5", "6"));
        System.out.println("按key取值结果是:"+map.get("a"));
        
        AtomicReference<Integer> at= new AtomicReference<Integer>();
        at.set(1);
        System.out.println(at.get());
        System.out.println(at.compareAndSet(1, 2));
        System.out.println(at.get());
        System.out.println(at.weakCompareAndSet(1, 3));
        System.out.println(at.get());
        
	}

}
