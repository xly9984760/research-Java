package cn.xlystar;

/**
 * @ClassName: ThreadLocalTest
 * @Author: 99847
 * @Description: ThreadLocal使用
 * @Date: 2022/2/20 13:55
 * @Version: 1.0
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {

        });
        ThreadLocal<String> local = new ThreadLocal<>();
        local.set("local");
        System.out.println(local.get());
    }
}
