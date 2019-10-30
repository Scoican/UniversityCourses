import Domain.Chromosome;
import Domain.FileRead;
import Domain.Item;
import Operations.DFS;
import Operations.EvaluativeAlgorithm;
import Operations.Greedy;
import Operations.HillClimbing;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String fileToRead = "hard_02_knapsack.txt";
        String fileGreedy = "FileGreedy.txt";
        String fileDFS = "FileDFS.txt";
        String fileHC = "FileHC.txt";
        String fileEA = "FileEA.txt";
        obtainResult(fileToRead, fileGreedy, fileDFS, fileHC, fileEA);
    }

    private static void obtainResult(String fileToRead, String fileGreedy, String fileDFS, String fileHC, String fileEA) {
        FileRead fr = new FileRead(fileToRead);
        Item items = fr.readData();
        fr.writeToFile(fileGreedy, createStringGreedy(items));
        fr.writeToFile(fileDFS, createStringDFS(items));
        fr.writeToFile(fileHC, createStringHillClimbing(items));
        fr.writeToFile(fileEA, createStringEvolutiveAlgorithm(items));
        //System.out.println(EvaluativeAlgorithm.evAlgol(items).getRepresentation());
    }

    private static StringBuilder createStringGreedy(Item items) {
        List<Integer> selected = Greedy.getMaxValue(items);
        StringBuilder output = new StringBuilder();
        output.append(items.getTotalItems());
        output.append("\r\n");

        for (Integer number : selected) {
            output.append(number).append(",");
        }
        output.delete(output.length() - 1, output.length());
        output.append("\r\n");

        for (int i = 0; i < items.getTotalItems(); i++) {
            if (selected.get(i) == 1) {
                output.append(items.getValue().get(i)).append(",");
            } else {
                output.append("0,");
            }
        }
        output.delete(output.length() - 1, output.length());
        output.append("\r\n");

        Double total = 0d;
        for (int i = 0; i < items.getTotalItems(); i++) {
            if (selected.get(i) == 1) {
                total += items.getValue().get(i);
            }
        }
        output.append(total);
        return output;
    }

    private static StringBuilder createStringEvolutiveAlgorithm(Item items) {
        Chromosome selected = EvaluativeAlgorithm.evAlgol(items);
        StringBuilder output = new StringBuilder();
        output.append(items.getTotalItems());
        output.append("\r\n");

        for (Integer number : selected.getRepresentation()) {
            output.append(number).append(",");
        }
        output.delete(output.length() - 1, output.length());
        output.append("\r\n");

        for (int i = 0; i < items.getTotalItems(); i++) {
            if (selected.getRepresentation().get(i) == 1) {
                output.append(items.getValue().get(i)).append(",");
            } else {
                output.append("0,");
            }
        }
        output.delete(output.length() - 1, output.length());
        output.append("\r\n");

        Double total = 0d;
        for (int i = 0; i < items.getTotalItems(); i++) {
            if (selected.getRepresentation().get(i) == 1) {
                total += items.getValue().get(i);
            }
        }
        output.append(total);
        return output;
    }

    private static StringBuilder createStringHillClimbing(Item items) {
        Chromosome selected = HillClimbing.hc(items);
        StringBuilder output = new StringBuilder();
        output.append(items.getTotalItems());
        output.append("\r\n");

        for (Integer number : selected.getRepresentation()) {
            output.append(number).append(",");
        }
        output.delete(output.length() - 1, output.length());
        output.append("\r\n");

        for (int i = 0; i < items.getTotalItems(); i++) {
            if (selected.getRepresentation().get(i) == 1) {
                output.append(items.getValue().get(i)).append(",");
            } else {
                output.append("0,");
            }
        }
        output.delete(output.length() - 1, output.length());
        output.append("\r\n");

        Double total = 0d;
        for (int i = 0; i < items.getTotalItems(); i++) {
            if (selected.getRepresentation().get(i) == 1) {
                total += items.getValue().get(i);
            }
        }
        output.append(total);
        return output;
    }

    private static StringBuilder createStringDFS(Item items) {
        List<Integer> solution = new ArrayList<>();
        List<Integer> backpack = new ArrayList<>();

        for (int i = 0; i < items.getTotalItems(); i++) {
            solution.add(0);
        }
        DFS.dfsBack(items, backpack, solution);
        StringBuilder output = new StringBuilder();
        output.append(items.getTotalItems());
        output.append("\r\n");

        for (Integer number : solution) {
            output.append(number).append(",");
        }

        output.delete(output.length() - 1, output.length());
        output.append("\r\n");

        for (int i = 0; i < items.getTotalItems(); i++) {
            if (solution.get(i) == 1) {
                output.append(items.getValue().get(i)).append(",");
            } else {
                output.append("0,");
            }
        }
        output.delete(output.length() - 1, output.length());
        output.append("\r\n");

        Double total = 0d;
        for (int i = 0; i < items.getTotalItems(); i++) {
            if (solution.get(i) == 1) {
                total += items.getValue().get(i);
            }
        }
        output.append(total);
        return output;
    }
}