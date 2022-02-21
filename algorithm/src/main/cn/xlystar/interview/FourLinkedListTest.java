package cn.xlystar.interview;

/**
 * @ClassName: FourLinkedList
 * @Author: 99847
 * @Description:
 * @Version: 1.0
 */
public class FourLinkedListTest {

    public static void main(String[] args) {
        FourLinkedList list = new FourLinkedList(3, 3);
        list.setNode(2, 2, 2);
        list.setNode(1, 1, 1);
        list.setNode(3, 3, 3);
        System.out.println(list.getValue(2, 2));
        list.print();
    }
}

class FourLinkedList<T> {

    private int width, height;
    private Node root;

    FourLinkedList() {
    }

    FourLinkedList(int width, int height) {
        this.width = width;
        this.height = height;
        root = init(width, height);
    }

    void print() {
        Node row, col;
        col = root;
        for (int i = 0; i < height; i++) {
            row = col;
            for (int j = 0; j < width; j++) {
                System.out.print(row.value + " ");
                row = row.right;
            }
            col = col.down;
            System.out.println();
        }
    }

    Node init(int width, int height) {
        if (width <= 0 || height <= 0) return null;
        root = new Node();
        Node row = root;
        for (int i = 0; i < width - 1; i++) {
            row.right = new Node();
            row.right.left = row;
            row = row.right;
        }

        row = root;
        Node row2, row1;
        for (int i = 0; i < height - 1; i++) {
            // 高度 height 的链
            row.down = new Node();
            row1 = row;
            row2 = row1.down;
            for (int j = 0; j < width - 1; j++) {
                row2.right = new Node();
                // 两行上下连接
                row2.up = row1;
                row2.right.up = row1.right;
                row1.right.down = row2.right;
                // 两列节点左右链接
                row2.right.left = row2;

                row1 = row1.right;
                row2 = row2.right;
            }
            row = row.down;
        }
        return root;
    }

    T getValue(int row, int col) {
        Node node = getNode(row, col);
        return node == null ? null : (T) node.value;
    }

    Node getNode(int row, int col) {
        Node temp = root;
        for (int i = 0; i < row - 1; i++) {
            temp = temp.right;
        }
        for (int i = 0; i < col - 1; i++) {
            temp = temp.down;
        }
        return temp;
    }

    void setNode(int row, int col, T value) {
        Node node = getNode(row, col);
        if (node != null) {
            node.value = value;
        }
    }

    Node reset(int width, int height) {
        this.width = width;
        this.height = height;
        return init(width, height);
    }

    class Node<T> {
        T value;
        Node left;
        Node right;
        Node up;
        Node down;
    }

}

