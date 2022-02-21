package cn.xlystar;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

/**
 * @ClassName: ReentrantLockTest
 * @Author: 99847
 * @Description:
 * @Date: 2022/2/20 20:14
 * @Version: 1.0
 */
public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        // ReentrantLock 采用的是CLH双向链表结构，独占式锁

        // 默认false 非公平锁
        // 通过 reentrantlock 内部类 Syn 实现同步，这个 Syn 继承了 AQS
        // FairSync 和 NonfairSync 重写了tryAcquire方法，公平非公平细节
        // 这里用了CLH的变种，先进先出的队列，阻塞机制。
        // 抢锁时，state做加法操作
        ReentrantLock lock = new ReentrantLock(true);
        // 上锁
        lock.lock();
        // 同步的代码块
        System.out.println("ReentrantLock 执行同步代码块..");
        // 解锁
        lock.unlock();

        /**
         * Semaphore 用于控制访问特定资源的线程数目。资源访问，服务限流(Hystrix里限流就有基于信号量方式)
         */
        // Semaphore 共享式锁;
        // 抢锁时，state做减法操作
        Semaphore semaphore = new Semaphore(10);
        semaphore.acquire(2);
        // 同步的代码块
        System.out.println("semaphore 执行代码块..");
        semaphore.release(2);

        /**
         * CountDownLatch这个类能够使一个线程等待其他线程完成各自的工作后再执行
         */
        // CountDownLatch 共享式锁；
        // 抢锁时，state做减法操作。
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // state 减法操作
        countDownLatch.countDown();
        // 同步的代码块
        System.out.println("countDownLatch 执行代码块..");
        // state == 0 执行
        countDownLatch.await();


        /**
         * 栅栏屏障，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程 到达屏障时，屏障才会开门，
         * 所有被屏障拦截的线程才会继续运行
         */
        // CyclicBarrier
        // 本质上是ReentrantLock的lock操作。也就是说state做加法操作。但是内部有count变量，当count=0时，放行执行任务
        CyclicBarrier cyclicBarrier = new CyclicBarrier(11, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有特工到达屏障，准备开始执行秘密任务");
            }
        });
        for (int i = 0; i < 10; i++) {
            new Thread(new CyclicBarrierTest(cyclicBarrier, i)).start();
        }
        cyclicBarrier.await();
        System.out.println("全部到达屏障....");

        /**
         * Exchanger ：当一个线程运行到exchange()方法时会阻塞，另一个线程运行到exchange()时，二者交换数据，然后执行后面的程序。
         *
         */
        Exchanger<Integer> ex = new Exchanger<>();
        for (int i = 0; i < 10; i++) {
            final int num = i;
            new Thread(()-> {
                int exchangeNum = 0;
                try {
                    exchangeNum = ex.exchange(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是线程：Thread_" + Thread.currentThread().getName() + ";我原先的数据为：" + num + " , 交换后的数据为：" + exchangeNum);
            }).start();
        }


        /**
         * 读写锁
         */
        StampedLock stampedLock = new StampedLock();
        stampedLock.readLock();
        stampedLock.unlockRead(1);

        /**
         * 原理：添加删除时,先上锁，然后将原来的数组值copy到新数组，最后进行替换。
         * 内部锁用的JVM synchronized
         */
        CopyOnWriteArrayList<Integer> writeArrayList = new CopyOnWriteArrayList<>();
        writeArrayList.add(1);
        writeArrayList.remove(1);
    }
}

class CyclicBarrierTest implements Runnable {
    private CyclicBarrier cyclicBarrier;
    private int index;

    public CyclicBarrierTest(CyclicBarrier cyclicBarrier, int index) {
        this.cyclicBarrier = cyclicBarrier;
        this.index = index;

    }

    @Override
    public void run() {
        try {
            System.out.println("index: " + index);
            index--;
            cyclicBarrier.await();
            System.out.println(111);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
