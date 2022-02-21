package cn.xlystar.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName: ImplementCallable
 * @Author: 99847
 * @Description: 实现Callable
 * @Date: 2022/2/19 11:45
 * @Version: 1.0
 */
public class ImplementCallable implements Callable<String> {
    private static Integer i = 0;
    @Override
    public String call() throws Exception {
        try {
            i.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "返回值：执行ing";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> stringFutureTask = new FutureTask<>(new ImplementCallable());
        new Thread(stringFutureTask).start();
        Thread.sleep(1000);

        i.notify();
        System.out.println(stringFutureTask.get());
        System.out.println("主线程执行");
    }
}
