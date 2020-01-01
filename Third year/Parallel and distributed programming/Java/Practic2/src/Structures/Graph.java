package Structures;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            nodes.add(new Node(i));
        }
    }

    public boolean checkNode(Integer index) {
        for (Node node : nodes) {
            if (node.getIndex().equals(index)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEdge(Integer firstNodeIndex, Integer secondNodeIndex) {
        if (!checkNode(firstNodeIndex) || checkNode(secondNodeIndex)) {
            return false;
        }
        for (Edge edge : edges) {
            if (edge.getFirstNode().equals(firstNodeIndex) && edge.getSecondNode().equals(secondNodeIndex)) {
                return false;
            }
        }
        return true;
    }

    public void insertNode(Integer index) {
        nodes.add(index, new Node(index));
    }

    public void insertEdge(Integer firstNodeIndex, Integer secondNodeIndex, Integer weight) {
        if (checkEdge(firstNodeIndex, secondNodeIndex)) {
            edges.add(new Edge(firstNodeIndex, secondNodeIndex, weight));
        }
    }

    public void updateEdge(Integer firstNodeIndex, Integer secondNodeIndex, Integer weight) {
        if (checkEdge(firstNodeIndex, secondNodeIndex)) {
            for (Edge edge : edges) {
                if (edge.getFirstNode().equals(firstNodeIndex) && edge.getSecondNode().equals(secondNodeIndex)) {
                    edge.setWeight(edge.getWeight() + weight);
                }
            }
        }
    }

    public void printGraph() {
        for (int i = 1; i <= 100; i++) {
            System.out.println(edges.get(i));
        }
    }
}
