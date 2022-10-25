package com.zerone.scms.daoyuexingchen;

public class FourTest {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Produce());
        Thread thread2 = new Thread(new Produce());
        Thread thread3 = new Thread(new Produce());
        Thread thread4 = new Thread(new Produce());
        Thread thread5 = new Thread(new Produce());
        Thread thread6 = new Thread(new Produce());
        Thread thread7 = new Thread(new Produce());
        Thread thread8 = new Thread(new Produce());
        Thread thread9 = new Thread(new Produce());
        Thread thread10 = new Thread(new Produce());
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
    }


}
