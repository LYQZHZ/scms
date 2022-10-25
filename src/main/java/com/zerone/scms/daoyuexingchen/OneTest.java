package com.zerone.scms.daoyuexingchen;

import java.util.*;

public class OneTest {
    public static void main(String[] args) {
        Date date = new Date();
        One one = new One();
        Yuan yuan = new Yuan();
        Map map = new HashMap();
//        优化后的
        System.out.println(one.func(10, map));
//        优化前的
        System.out.println(yuan.func(10));
    }
}

class One {
    int func(int n, Map map) {
        if (n <= 4) {
            return n;
        } else if (map.containsKey(n)) {
            return (int) map.get(n);
        } else {
            return func(n - 4, map) * func(n - 1, map) - func(n - 3, map) * func(n - 2, map);
        }
    }
}

class Yuan {
    int func(int n) {
        if (n <= 4) {
            return n;
        } else {
            return func(n - 4) * func(n - 1) - func(n - 3) * func(n - 2);
        }
    }
}
