package cn.xlystar.interview;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @ClassName: LinkedList
 * @Author: 99847
 * @Description: 双向链表
 * @Date: 2022/3/9 14:35
 * @Version: 1.0
 */
public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        // 添加到队列尾部 linkLast
        list.add(1);

        /**
         * Queue 方法
         *
         */
        // 调用 add 方法，本质:linkLast(e)，从尾部开始添加
        list.offer(1);
        // 移除头部元素, unlinkFirst
        list.poll();
        /**
         * Stack 方法
         *
         */
        Stack<Object> stack = new Stack<>();
        // 调用 addFirst(e), 本质调用 linkFirst(e)
        list.push(2);
        // 查看头部 f 元素
        list.peek();
        // 本质调用 unlinkFirst, 移除头部元素
        list.pop();

        /**
         * Queue 与 Stack 对比
         * 很显然:
         *      1. Queue 先进先出， Stack 先进后出
         *      2. 本质上，Queue 与 Stack 在添加元素时，添加的位置不一样，Queue是从尾部添加；Stack从头部添加
         * 所以，从链表本质上理解：
         *      1. peek 返回头部的元素
         *      2. add 从尾部添加
         *      3. push 从头部添加
         *      4. offer 从尾部添加
         *      5. remove 从头部移除;与 poll()不同之处在于: empty时, remove()抛出异常
         *      5. poll 从头部移除
         *      6. pop 从头部移除元素
         *
         */
    }
}

