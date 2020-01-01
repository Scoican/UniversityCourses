package Threads;

import Objects.DoubleLinkedList;
import Objects.Monomial;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ListThread extends Thread {
    private final DoubleLinkedList linkedList;
    private int min;
    private int max;

    public ListThread(DoubleLinkedList linkedList, int min, int max) {
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
                    synchronized (linkedList) {
                        linkedList.insert(monomial);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
