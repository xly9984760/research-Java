package cn.xlystar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

// 泛型
public class Main {


    public static void main(String[] args) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(181);
        list.add(180);
        list.add(165);
        list.add(168);
        list.add(178);
        System.out.println(new Main().lengthOfMaxQueue(list));

    }

    public int lengthOfMaxQueue(ArrayList<Integer> nums) {
        // write code here

        if (nums == null || nums.size() == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        int max = 1;
        for (int i = 0; i < nums.size(); i++) {
            if (i == 0) stack.push(i);

            while (!stack.isEmpty() &&nums.get(stack.peek()) >= nums.get(i)) {
                stack.pop();
            }
            stack.push(i);
            max = Math.max(max,stack.size());
        }
        return max;
    }

}

