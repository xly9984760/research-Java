import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName: ListTest
 * @Author: 99847
 * @Description: list
 * @Date: 2022/2/22 13:29
 * @Version: 1.0
 */
public class ListTest {

    public static void main(String[] args) {


        HashSet<Object> objects = new HashSet<>();
        TreeSet<Object> objects1 = new TreeSet<>();

        // ArrayList  可以手动加同步机制实现安全
        ArrayList<Object> list = new ArrayList<>();
        list.add(new Object());
        // 安全ArrayList
        Collections.synchronizedList(new ArrayList<>());
        // 安全ArrayList 不推荐
        Vector<Object> ver = new Vector<>();
        ver.add(new Object());
        // 安全ArrayList
        CopyOnWriteArrayList<Object> objects2 = new CopyOnWriteArrayList<>();
        objects2.add(1);

        HashMap<Integer, Integer> hashmap = new HashMap<>();
        Integer integer = hashmap.get(1);
        hashmap.put(1,1);
        hashmap.remove(1);



    }


}
