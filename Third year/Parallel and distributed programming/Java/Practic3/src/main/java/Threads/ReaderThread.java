package Threads;

import Structures.Edge;
import Structures.Graph;

import java.util.List;

public class ReaderThread extends Thread {
    public int start;
    public int finish;
    public final List<Edge> edgeList;
    public Graph graph;

    public ReaderThread(int start, int finish, List<Edge> edgeList, Graph graph) {
        this.start = start;
        this.finish = finish;
        this.edgeList = edgeList;
        this.graph = graph;
    }

    @Override
    public void run() {
        super.run();
        Edge currentEdge;
        synchronized (edgeList) {
            for (int i = start; i < finish; i++) {
                currentEdge = edgeList.get(i);
                if (graph.checkNode(currentEdge.getFirstNode()) && graph.checkNode(currentEdge.getSecondNode())) {
                    if (graph.checkEdge(currentEdge.getFirstNode(), currentEdge.getFirstNode())) {
                        graph.updateEdge(currentEdge.getFirstNode(), currentEdge.getSecondNode(), currentEdge.getWeight());
                    } else {
                        graph.insertEdge(currentEdge.getFirstNode(), currentEdge.getSecondNode(), currentEdge.getWeight());
                    }
                }
            }
        }
    }
}
