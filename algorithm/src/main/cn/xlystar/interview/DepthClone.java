package cn.xlystar.interview;


/**
 * @ClassName: DepthClone
 * @Author: 99847
 * @Description: 深拷贝
 * @Date: 2022/3/9 15:33
 * @Version: 1.0
 */
public class DepthClone implements Cloneable {
    private String name;
    private int age;

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("cn.xlystar.interview.DepthClone");
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
