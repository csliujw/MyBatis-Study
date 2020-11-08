package org.example.core.thread;

import java.util.HashMap;

public class Demo {
    final static HashMap<String, Double> hashMap = new HashMap<>();

    public static void main(String[] args) {
        // ok
        hashMap.put("1", 12.312);
        // final 变量 只能被复制一次（引用）。
//        hashMap = new HashMap<>();
//        var map = new HashMap<String, Double>();
//        // 错误 只能被初始化一次
//        hashMap = map;
    }
}
