package cn.xlystar.thread;

/**
 * @ClassName: ImplementRunnable
 * @Author: 99847
 * @Description: 实现Runnable 创建线程
 * @Date: 2022/2/19 11:43
 * @Version: 1.0
 */
public class ImplementRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("实现Runnable， 创建线程2，执行ing");
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ImplementRunnable()).start();
        System.out.println("主线程执行ing");
        Thread.sleep(1000);
    }
}
