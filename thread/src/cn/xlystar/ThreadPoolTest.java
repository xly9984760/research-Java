package cn.xlystar;

import org.w3c.dom.ls.LSOutput;

import java.sql.Time;
import java.util.concurrent.*;

/**
 * @ClassName: ThreadPoolTest
 * @Author: 99847
 * @Description: 线程池使用
 * Executors.DefaultFactory 创建的默认线程为用户线程(非守护线程)
 * @Date: 2022/2/21 10:58
 * @Version: 1.0
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1,1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(2));
        threadPoolExecutor.submit(() -> {
            System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
        });

        Executors.newCachedThreadPool();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.schedule(()->{
            System.out.println(System.currentTimeMillis());
        },1,TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(()->{
            System.out.println(System.currentTimeMillis());
        },10,10,TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(()->{
            System.out.println(System.currentTimeMillis());
        },10,10,TimeUnit.SECONDS);

    }
}
