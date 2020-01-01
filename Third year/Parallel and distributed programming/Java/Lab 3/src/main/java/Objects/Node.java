package Objects;

public class Node {
    private Monomial data;
    private Node next, prev;

    Node(Monomial data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public void setNext(Node n) {
        next = n;
    }

    public void setPrev(Node p) {
        prev = p;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setData(Monomial d) {
        data = d;
    }

    public Monomial getData() {
        return data;
    }

    public void addCoefficient(int coefficient) {
        int oldCoefficient = this.data.getCoefficient();
        this.data.setCoefficient(oldCoefficient + coefficient);
    }

    public boolean isCoefficientZero() {
        return this.data.getCoefficient() == 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
