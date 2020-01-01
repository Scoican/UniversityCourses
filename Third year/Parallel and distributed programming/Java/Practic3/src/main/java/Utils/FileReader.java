package Utils;

import Structures.Edge;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    public static List<Edge> readFile() {
        List<Edge> edges = new ArrayList<>();
        BufferedReader readerFile;
        try {
            File file = new File("C:\\Users\\Scoican\\Desktop\\Work\\University Code\\Third year\\Parallel and distributed programming\\Java\\Practic3\\src\\main\\resources\\Edges.txt");

            readerFile = new BufferedReader(new java.io.FileReader(file));

            String lineFromFile = readerFile.readLine();

            while (lineFromFile != null) {
                String[] numbers = lineFromFile.split(" ");
                edges.add(new Edge(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]), Integer.parseInt(numbers[2])));
                lineFromFile = readerFile.readLine();
            }
            readerFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return edges;
    }
}
