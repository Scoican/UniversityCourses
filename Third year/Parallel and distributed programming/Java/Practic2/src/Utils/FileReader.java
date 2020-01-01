package Utils;

import Structures.Edge;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    public static List<Edge> readFile(String fileName) {
        List<Edge> edges = new ArrayList<>();
        BufferedReader readerFile;
        try {
            readerFile = new BufferedReader(new java.io.FileReader(fileName));

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
