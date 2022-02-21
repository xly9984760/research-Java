package cn.xlystar;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @ClassName: UnSafeTest
 * @Author: 99847
 * @Description: 获取 UnSafe 实例
 * Unsafe提供的API大致可分为内存操作、CAS、Class相关、对象操作、线程调度、系统 信息获取、内存屏障、数组操作等几类
 * @Date: 2022/2/21 10:44
 * @Version: 1.0
 */
public class UnSafeTest {
    public static void main(String[] args) {
        Unsafe unsafe = reflectGetUnsafe();
        if (unsafe.getClass() == Unsafe.class) {
            System.out.println(unsafe);
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }

    public static Unsafe reflectGetUnsafe() {
        try {

            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
