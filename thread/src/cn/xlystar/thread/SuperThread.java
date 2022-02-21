package cn.xlystar.thread;

/**
 * @ClassName: SuperThread
 * @Author: 99847
 * @Description: 创建一个thread
 * @Date: 2022/2/19 11:40
 * @Version: 1.0
 */
public class SuperThread extends Thread {
    @Override
    public void run() {

        System.out.println("继承thread， 创建线程1，执行ing");
    }

    public static void main(String[] args) throws InterruptedException {
        new SuperThread().start();
        System.out.println("主线程执行");
        Thread.sleep(1000);
    }
}
