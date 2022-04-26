package cn.xlystar;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @ClassName: AtomicInteger
 * @Author: 99847
 * @Description: 原子操作
 * @Date: 2022/2/21 10:29
 * @Version: 1.0
 */
public class AtomicIntegerTest {
    volatile int i = 0;
    public static void main(String[] args) {
        atomicInteger();
    }

    public static void atomicInteger() {
        AtomicMarkableReference<Integer> markR = new AtomicMarkableReference<>(1, true);
        System.out.println(markR.compareAndSet(1, 2, false, true));
        System.out.println(markR.compareAndSet(1, 2, true, false));

        AtomicStampedReference<Integer> stampR = new AtomicStampedReference<>(1,1);
        System.out.println(stampR.compareAndSet(1, 2, 1, 1));

        AtomicIntegerFieldUpdater<AtomicIntegerTest> fieldIntegerU = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerTest.class, "i");
        AtomicIntegerTest atomicIntegerTest = new AtomicIntegerTest();
        System.out.println(fieldIntegerU.compareAndSet(atomicIntegerTest,2,3));

    }
}


