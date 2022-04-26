package cn.xlystar.interview;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: ConcurrentHashMapTest
 * @Author: 99847
 * @Description: ConcurrentHashMap
 * @Date: 2022/3/10 18:35
 * @Version: 1.0
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 2);
        map.get(1);
        new ConcurrentHashMapTest().validUtf8(new int[]{250,145,145, 145, 145});
    }

    public boolean validUtf8(int[] data) {
        int len = data.length;
        int index = 0;
        while (index < len) {
            int num = data[index];
            int has = 0;
            for (int i = 7; i > 0; i--) {
                if ((num >> i & 1) != 1) {
                    break;
                }
                has++;
            }

            if (has == 1) {
                return  false;
            }
            if (--has + index >= len) {
                return false;
            }
            while (has-- > 0) {
                index++;
                if ((data[index] >> 7 & 1) == 1 && (data[index] >> 6 & 1) == 0) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    //[230,136,145]
    public List<List<Integer>> threeSum(int[] nums) {
        int len = nums.length;
        if (len < 3) {
            return new ArrayList<>();
        }

        Arrays.sort(nums);
        int index = 0;
        ArrayList<List<Integer>> list = new ArrayList<>();
        while (index < len - 2) {
            if (index > 0 && nums[index] == nums[index - 1]) {
                index++;
                continue;
            }
            ArrayList<Integer> oneList = new ArrayList<>();
            int l = index + 1;
            int r = len - 1;
            while (l < len - 1) {
                if (l > index + 1 && nums[l] == nums[l - 1]) {
                    l++;
                    continue;
                }
                while (r > l && nums[index] + nums[l] + nums[r] > 0) {
                    r--;
                }
                if (r > l && nums[index] + nums[l] + nums[r] == 0) {
                    oneList.add(nums[index]);
                    oneList.add(nums[l]);
                    oneList.add(nums[r]);
                    list.add(oneList);
                }
                l++;
            }
            index++;
        }
        return list;
    }
}
