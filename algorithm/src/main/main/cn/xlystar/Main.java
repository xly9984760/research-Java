package main.cn.xlystar;


import java.util.*;
    
/**
 * @ClassName: Main
 * @Author: 99847
 * @Description:
 * @Date: 2021/12/25 10:17
 * @Version: 1.0
 */
public class Main {
    public static void main(String[] args) {


    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Graph graph = GraphGenerator.generator(prerequisites);

        HashMap<Node, Integer> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            if (node.in == 0) queue.add(node);
            map.put(node, node.in);
        }

        boolean bo = false;
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            for (Node node : temp.outlist) {
                map.put(node, map.get(node) - 1);
                if (map.get(node) == 0) queue.add(node);
            }
        }

        for (Map.Entry<Node, Integer> node : map.entrySet()) {
            if (node.getValue() != 0) return false;
        }
        return true;
    }


}

class GraphGenerator {
    static Graph generator(int[][] arr) {
        Graph graph = new Graph();
        for (int i = 0; i < arr.length; i++) {
            int from = arr[i][0];
            int to = arr[i][1];
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }

            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge edge = new Edge(fromNode, toNode, 0);

            fromNode.out++;
            toNode.in++;
            fromNode.outlist.add(toNode);
            fromNode.edges.add(edge);
            graph.edges.add(edge);

        }
        return graph;
    }
}

class Graph {
    HashMap<Integer, Node> nodes;
    HashSet<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}

class Node {
    int value;
    int in;
    int out;
    ArrayList<Node> outlist;
    ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        outlist = new ArrayList<>();
        edges = new ArrayList<>();
    }
}

class Edge {
    int weight;
    Node from;
    Node to;

    Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
