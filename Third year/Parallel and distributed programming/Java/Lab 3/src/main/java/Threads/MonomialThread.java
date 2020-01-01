package Threads;

import Objects.DoubleLinkedList;
import Objects.Monomial;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MonomialThread extends Thread {
    private final DoubleLinkedList linkedList;
    private int min;
    private int max;

    public MonomialThread(DoubleLinkedList linkedList, int min, int max) {
        this.linkedList = linkedList;
        this.min = min;
        this.max = max;
    }

    @Override
    public void run() {
        super.run();
        for (int i = min; i < max; i++) {
            File file = new File("C:\\Users\\Scoican\\Desktop\\Work\\University Code\\Third year\\Parallel and distributed programming\\Java\\Lab 3\\src\\main\\resources\\polynomial" + (i + 1) + ".txt");
            Scanner sc;
            try {
                sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] elem = line.split(",");
                    Monomial monomial = new Monomial(Integer.parseInt(elem[0]), Integer.parseInt(elem[1]));
                    linkedList.insertSync(monomial);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
