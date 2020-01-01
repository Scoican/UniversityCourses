package Objects;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DoubleLinkedList {
    private Node start;
    private int size;

    public DoubleLinkedList() {
        start = null;
        size = 0;
    }

    public boolean isEmpty() {
        return start == null;
    }

    public int getSize() {
        return size;
    }

    public void insert(Monomial monomial) {
        Node newNode = new Node(monomial, null, null);
        Node temporary, pointer;
        boolean inserted = false;
        if (this.start == null) {
            this.start = newNode;
        } else if (monomial.getExponent() > start.getData().getExponent()) {
            newNode.setNext(start);
            start.setPrev(newNode);
            start = newNode;
        } else if (monomial.getExponent() == start.getData().getExponent()) {
            start.addCoefficient(monomial.getCoefficient());

            if (start.isCoefficientZero()) {
                delete(start);
            }
            return;
        } else {
            temporary = start;
            pointer = start.getNext();
            while (pointer != null) {
                if (monomial.getExponent() < temporary.getData().getExponent() &&
                        monomial.getExponent() > pointer.getData().getExponent()) {
                    temporary.setNext(newNode);
                    newNode.setPrev(temporary);
                    newNode.setNext(pointer);
                    pointer.setPrev(newNode);
                    inserted = true;
                    break;
                }

                if (monomial.getExponent() == pointer.getData().getExponent()) {
                    pointer.addCoefficient(monomial.getCoefficient());
                    inserted = true;
                    size--;
                    if (pointer.isCoefficientZero()) {
                        delete(pointer);
                    }
                    break;
                }

                temporary = pointer;
                pointer = temporary.getNext();
            }
            if (!inserted) {
                temporary.setNext(newNode);
                newNode.setPrev(temporary);
            }
        }
        size++;
    }

    private void delete(Node node) {
        if (start == node) {
            start = start.getNext();
            start.setPrev(null);
        } else {
            Node pointer = start.getNext();
            while (pointer != node) {
                pointer = pointer.getNext();
            }

            if (pointer == null) {
                System.out.println("Node not found!");
                return;
            }

            Node next = pointer.getNext();
            Node previous = pointer.getPrev();

            previous.setNext(next);

            if (next != null) {
                next.setPrev(previous);
            }
        }
        size--;
    }

    public void insertSync(Monomial value) {
        Node newNode = new Node(value, null, null);
        int valueExponent = value.getExponent();

        Node currentNode, nextNode, prevNode;

        prevNode = start;
        currentNode = start.getNext();
        nextNode = start.getNext().getNext();

        int currentExponent;
        int nextExponent;

        while (true) {
            synchronized (prevNode) {
                synchronized (currentNode) {
                    synchronized (nextNode) {
                        currentExponent = currentNode.getData().getExponent();
                        nextExponent = nextNode.getData().getExponent();
                    }
                    if (currentExponent > valueExponent && valueExponent > nextExponent) {
                        currentNode.setNext(newNode);
                        newNode.setPrev(currentNode);
                        newNode.setNext(nextNode);
                        nextNode.setPrev(newNode);
                        size++;
                        break;
                    }

                    if (valueExponent == currentExponent) {
                        currentNode.addCoefficient(value.getCoefficient());
                        if (currentNode.isCoefficientZero()) {
                            delete(currentNode);
                        }
                        break;
                    }

                    if (nextNode.getNext() == null) {
                        if (nextNode.getData().getExponent() == valueExponent) {
                            nextNode.addCoefficient(value.getCoefficient());
                            if (nextNode.isCoefficientZero()) {
                                delete(nextNode);
                            }
                        } else {
                            nextNode.setNext(newNode);
                            newNode.setPrev(nextNode);
                            size++;
                        }
                        break;
                    }
                    prevNode = currentNode;
                    currentNode = nextNode;
                    nextNode = nextNode.getNext();
                }
            }
        }
    }

    public void writeInFile(String filename) throws IOException {
        if (size != 0) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            StringBuilder stringBuilder = new StringBuilder();

            if (start.getNext() == null) {
                stringBuilder.append(start.getData());
            } else {
                Node pointer;
                stringBuilder.append(start.getData()).append("\n");
                pointer = start.getNext();
                while (pointer.getNext() != null) {
                    stringBuilder.append(pointer.getData()).append("\n");
                    pointer = pointer.getNext();
                }
                stringBuilder.append(pointer.getData());

            }
            writer.write(stringBuilder.toString());
            writer.close();
        }
    }

    public void prepare(int max) {
        Node node1 = new Node(new Monomial(-1, max + 3), null, null);
        Node node2 = new Node(new Monomial(-1, max + 2), null, null);
        Node node3 = new Node(new Monomial(-1, max + 1), null, null);
        start = node1;
        start.setNext(node2);
        node2.setPrev(start);
        node2.setNext(node3);
        node3.setPrev(node2);
    }

    public void broke() {
        start = start.getNext().getNext().getNext();
        if (start != null) {
            start.setPrev(null);
        }
    }
}
