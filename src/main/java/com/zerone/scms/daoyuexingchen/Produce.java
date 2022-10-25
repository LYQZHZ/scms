package com.zerone.scms.daoyuexingchen;

import static java.lang.Thread.sleep;

public class Produce implements Runnable {
    public static int stack = 0;//临界资源
    public static boolean lock = true;//互斥锁

    @Override
    public void run() {
        try {
            push();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void push() throws InterruptedException {
        if (get_lock()) {
            lock = false;
            if (full(stack)) {
                stack += 1;
                System.out.println("向临界资源添加完毕！");
                sleep(2000);//该句用来模拟互斥锁锁死的状态，注释掉可模拟临界资源已满的状态
            } else {
                System.out.println("临界资源已满！不可继续添加！");
            }
        } else {
            System.out.println("资源正在占用不可访问！");
        }
        release_lock();

    }

    boolean full(int stack) {
        if (stack == 3) {
            return false;
        }
        return true;
    }

    boolean get_lock() {
        return lock;
    }

    void release_lock() {
        lock = true;
    }

}
