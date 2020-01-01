package BinaryTree;

import java.util.Stack;

public class BinaryTree {

    private Node root;

    public Node getRoot() {
        return root;
    }

    public void add(String key, Integer value) {
        root = addRecursive(root, key, value);
    }

    private Node addRecursive(Node current, String key, int value) {

        if (current == null) {
            return new Node(key, value);
        }

        if (key.compareTo(current.key) < 0) {
            current.left = addRecursive(current.left, key, value);
        } else if (key.compareTo(current.key) > 0) {
            current.right = addRecursive(current.right, key, value);
        }

        return current;
    }

    public boolean containsKey(String key) {
        return containsNodeRecursive(root, key);
    }

    private boolean containsNodeRecursive(Node current, String key) {
        if (current == null) {
            return false;
        }

        if (key.equals(current.key)) {
            return true;
        }

        return key.compareTo(current.key) < 0
                ? containsNodeRecursive(current.left, key)
                : containsNodeRecursive(current.right, key);
    }


    public boolean containsValue(Integer value) {
        Stack<Node> stack = new Stack<Node>();
        Node current = this.root;
        boolean found = false;
        if (root != null)
            stack.push(current);
        while (!stack.isEmpty()) {
            current = stack.pop();
            if (current.value.equals(value))
                found = true;

            if (current.right != null)
                stack.push(current.right);

            if (current.left != null)
                stack.push(current.left);
        }

        return found;
    }

    public Node findValue(String key) {
        Stack<Node> stack = new Stack<Node>();
        Node current = this.root;
        stack.push(current);
        while (!stack.isEmpty()) {
            current = stack.pop();
            if (current.key.equals(key))
                return current;

            if (current.right != null)
                stack.push(current.right);

            if (current.left != null)
                stack.push(current.left);
        }

        return null;
    }

}




