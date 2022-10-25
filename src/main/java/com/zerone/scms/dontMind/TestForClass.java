package com.zerone.scms.dontMind;

import java.lang.reflect.Method;

public class TestForClass {
    public static void main(String[] args) throws ClassNotFoundException {
        Calculator calculator = null;
        calculator = new Calculator();
//        Class clazz = calculator.getClass();
//        Class clazz = Class.forName("com.zerone.scms.dontMind.Calculator");
        Class clazz = Calculator.class;
        System.out.println(clazz.getName());
        System.out.println(clazz.getPackage());
        Method[] method = clazz.getMethods();
        for (int i = 0; i < method.length; i++) {
            System.out.println("方法" + i + ":" + method[i].getName());
        }

    }
}
