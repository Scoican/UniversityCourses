import Objects.DoubleLinkedList;
import Threads.ListThread;
import Threads.MonomialThread;
import Utils.Utils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        int numberOfPolynomials = 10;
        int numberOfThreads = 8;
        int minCoefficient = -100;
        int maxCoefficient = 100;
        int maxExponent = 10000;

        Utils.generatePolynomials(numberOfPolynomials, minCoefficient, maxCoefficient, maxExponent);

        listSynchronization(numberOfThreads, numberOfPolynomials);
        monomialSynchronization(numberOfThreads, numberOfPolynomials, maxExponent);

        System.out.println("Final result: " + Utils.compareFiles("SynchronizedListResult.txt", "SynchronizedMonomialResult.txt"));
    }

    private static void monomialSynchronization(int numberOfThreads, int numberOfPolynomials, int maxExponent) throws InterruptedException, IOException {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        MonomialThread[] threads = new MonomialThread[numberOfThreads];
        long start, finish;
        float time;
        int rest = numberOfPolynomials % numberOfThreads;

        doubleLinkedList.prepare(maxExponent);


        for (int i = 0; i < numberOfThreads; i++) {
            int numberOfFilesPerThread = numberOfPolynomials / numberOfThreads;
            if (rest > 0) {
                numberOfFilesPerThread++;
                rest--;
            }
            threads[i] = new MonomialThread(doubleLinkedList, i * numberOfFilesPerThread, i * numberOfFilesPerThread + numberOfFilesPerThread);
        }

        start = System.nanoTime();

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }

        finish = System.nanoTime();

        doubleLinkedList.broke();
        doubleLinkedList.writeInFile("SynchronizedMonomialResult.txt");

        time = (finish - start) / (float) 1_000_000;
        System.out.println("Synchronized monomial time: " + time + " ms");
    }

    private static void listSynchronization(int numberOfThreads, int numberOfPolynomials) throws InterruptedException, IOException {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        ListThread[] threads = new ListThread[numberOfThreads];

        long start, finish;
        float time;
        int rest = numberOfPolynomials % numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            int numberOfFilesPerThread = numberOfPolynomials / numberOfThreads;
            if (rest > 0) {
                numberOfFilesPerThread++;
                rest--;
            }
            threads[i] = new ListThread(doubleLinkedList, i * numberOfFilesPerThread, i * numberOfFilesPerThread + numberOfFilesPerThread);
        }

        start = System.nanoTime();

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }

        finish = System.nanoTime();

        doubleLinkedList.writeInFile("SynchronizedListResult.txt");

        time = (finish - start) / (float) 1_000_000;
        System.out.println("Synchronized list time: " + time + " ms");
    }
}
