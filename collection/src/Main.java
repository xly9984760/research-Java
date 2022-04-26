import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int[] ints = {1};
        ArrayList<Integer> max = new Main().getMax(6, 10, new int[3][] {
            {
                1, 2, 4, 3
            },2, 3, 2, 2,{
                3, 4, 1, 4
            }
        })
    }


    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * <p>
     * 求在魔王苏醒之前，可以获取到价值最高的宝物列表；
     *
     * @param packageSize  int整型 背包容量
     * @param wakeTime     int整型 魔王睡醒时长
     * @param treasureInfo int整型二维数组 宝物信息数组；宝物信息数组；[[宝物id, 宝物距离, 宝物重量, 宝物价值]]
     * @return int整型一维数组
     */
    class Node {
        int id;
        int place;
        int weight;
        int value;

        Node(int id, int place, int weight, int value) {
            this.id = id;
            this.place = place;
            this.weight = weight;
            this.value = value;
        }

    }

    int res = 0;

    public int[] getTreasures(int packageSize, int wakeTime, int[][] treasureInfo) {
        // write code here
        // 结果
        ArrayList<Integer> resList = new ArrayList<>();
        // 根据距离洞口的时间排序
        Node[] nodeList = new Node[treasureInfo.length];
        for (int i = 0; i < treasureInfo.length; i++) {
            nodeList[i] = new Node(treasureInfo[i][0], treasureInfo[i][1], treasureInfo[i][2], treasureInfo[i][3]);
        }
        Arrays.sort(nodeList, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.place - o2.place;
            }
        });
        // 是否访问过
        boolean[] bo = new boolean[treasureInfo.length];

        resList = getMax(resList, packageSize, wakeTime, nodeList, bo, treasureInfo.length - 1, res, 0, 0);
        int[] resArr = new int[resList.size()];
        for (int i = 0; i < resList.size(); i++) {
            resArr[i] = resList.get(i);
        }
        Arrays.sort(resArr);
        return resArr;
    }

    private ArrayList<Integer> getMax(ArrayList<Integer> resList, int packageSize, int wakeTime, Node[] nodeList, boolean[] bo, int index, int value, int curPlace, int prePlace) {
        if (curPlace + prePlace > wakeTime) {
            return null;
        }

        if (index < 0) {
            if (value > res) {
                return resList;
            }
        }

        if (nodeList[index].weight <= packageSize) {
            if (curPlace == 0) {
                getMax(resList, packageSize, wakeTime, nodeList, bo, index - 1, value, curPlace, 0);
                resList.add(nodeList[index].id);
                getMax(resList, packageSize, wakeTime, nodeList, bo, index, value + nodeList[index].weight, curPlace + 2 * nodeList[index].place + 1, nodeList[index].place);
                resList.remove(resList.size() - 1);
            }


        }
        if (wakeTime >= curPlace + prePlace - nodeList[index].place + 1) {
            getMax(resList, packageSize, wakeTime, nodeList, bo, index - 1, value, curPlace, prePlace);
            getMax(resList, packageSize, wakeTime, nodeList, bo, index - 1, value + nodeList[index].weight, curPlace + prePlace + 1 - nodeList[index].place, nodeList[index].place);
        }
        return resList;
    }


}


