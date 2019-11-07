package BinaryTree;

public class Node {

    String key;
    Integer value;
    Node left;
    Node right;

    Node(String key, Integer value) {
        this.key = key;
        this.value = value;
        right = null;
        left = null;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
