package cn.xlystar;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: ThreadSignal
 * @Author: 99847
 * @Description: 线程间通信
 * 轮流打印 ABC 100次
 * @Date: 2022/2/19 14:31
 * @Version: 1.0
 */
public class ThreadSignal {

    public static Object lock = new Object();
    public static ReentrantLock lock2 = new ReentrantLock(true);
    public static volatile int state = 0;

    public static void main(String[] args) {
        roundPrint();
    }

    /**
     * 线程A,B,C，分别轮流打印100次
     */
    public static void roundPrint() {
        Thread a = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    while (state != 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(i + 1 + ":");
                    print(Thread.currentThread().getName());
                    state = 1;
                    lock.notifyAll();
                }
            }
        }, "A");
        Thread b = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    while (state != 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    print(Thread.currentThread().getName());
                    state = 2;
                    lock.notifyAll();
                }
            }
        }, "B");
        Thread c = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    while (state != 2) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    print(Thread.currentThread().getName());
                    System.out.println();
                    state = 0;
                    lock.notifyAll();
                }
            }
        }, "C");

        a.start();
        b.start();
        c.start();
    }


    /**
     * 线程A,B,C，分别轮流打印100次
     */
    public static void roundPrint2() {
        Thread a = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    while (state != 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(i + 1 + ":");
                    print(Thread.currentThread().getName());
                    state = 1;
                    lock.notifyAll();
                }
            }
        }, "A");
        Thread b = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    while (state != 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    print(Thread.currentThread().getName());
                    state = 2;
                    lock.notifyAll();
                }
            }
        }, "B");
        Thread c = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    while (state != 2) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    print(Thread.currentThread().getName());
                    System.out.println();
                    state = 0;
                    lock.notifyAll();
                }
            }
        }, "C");

        a.start();
        b.start();
        c.start();
    }
    public static void print(String s) {
        System.out.print(s);
    }
}
