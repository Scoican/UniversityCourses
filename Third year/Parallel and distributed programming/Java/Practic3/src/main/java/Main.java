import Structures.Edge;
import Structures.Graph;
import Threads.ReaderThread;
import Utils.FileReader;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Graph graph=new Graph();
        List<Edge> edges = FileReader.readFile();
        ReaderThread[] readerThreads = new ReaderThread[2];

        int start = 0;
        int end = edges.size()/2;

        for(int i=0;i<2;i++){
            readerThreads[i] = new ReaderThread(start,end,edges,graph);
            start=end;
            end = edges.size();
        }

        for(int i=0;i<2;i++){
            readerThreads[i].start();
        }

        for(int i=0;i<2;i++){
            readerThreads[i].join();
        }

        graph.printGraph();
    }
}