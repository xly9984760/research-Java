package cn.xlystar;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName: BlockingQueueTest
 * @Author: 99847
 * @Description: 阻塞队列
 * @Date: 2022/2/20 22:14
 * @Version: 1.0
 */
public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 基于ReentrantLock保证线程安全，根据Condition实现队列满时的阻塞
         *
         * ArrayBlockingQueue 和 LinkedBlockingQueue重要区别：
         * 1. ArrayBlockingQueue需要指定容量，LinkedBlockingQueue不需要 默认Integer最大值
         * 2. ArrayBlockingQueue 内部读写使用通一把 lock 创建条件队列。LinkedBlockingQueue 内部读写分别用一把 lock；
         * 3. ArrayBlockingQueue 不销毁对象，通过takenIndex标识；LinkedBlockingQueue会摧毁对象Node
         * 从执行上来说 LinkedBlockingQueue 并发度更高
         */
        ArrayBlockingQueue<Integer> list = new ArrayBlockingQueue<>(1);
        list.put(1);
        System.out.println(list.take());

        LinkedBlockingQueue<Integer> linkedlist = new LinkedBlockingQueue<>(); // 默认长度Integer.MAX_VALUE，容易OutofMemroy
        linkedlist.put(1);
        System.out.println(linkedlist.take());
        /**
         * 要求入队的对象必须要实现Delayed接口,而Delayed集成自Comparable接口
         * 工作原理：队列内部会根据时间优先级进行排序。延迟类线程池周期执行。
         */
        DelayQueue<Delayed> delayeds = new DelayQueue<>();
    }
}
