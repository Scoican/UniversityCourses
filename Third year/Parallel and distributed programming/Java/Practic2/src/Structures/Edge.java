package Structures;

public class Edge {
    private Integer firstNode;
    private Integer secondNode;
    private Integer weight;

    public Integer getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(Integer firstNode) {
        this.firstNode = firstNode;
    }

    public Integer getSecondNode() {
        return secondNode;
    }

    public void setSecondNode(Integer secondNode) {
        this.secondNode = secondNode;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Edge(Integer firstNode, Integer secondNode, Integer weight) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return firstNode + "/" + secondNode + "/" + weight;
    }
}
