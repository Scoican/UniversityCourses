package NumberOperations;

import ThreadOperations.MultiplyThread;
import Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiplyNumbers {

    /**
     * Method that returns the smallest number by size
     *
     * @param firstNumber  - first number
     * @param secondNumber - second number
     * @return One of the numbers that has a smaller size
     */
    private static List<Integer> getSmallestNumber(List<Integer> firstNumber, List<Integer> secondNumber) {
        return firstNumber.size() <= secondNumber.size() ? firstNumber : secondNumber;
    }

    /**
     * Method that returns the biggest number by size
     *
     * @param firstNumber  - first number
     * @param secondNumber - second number
     * @return One of the numbers that has a bigger size
     */
    private static List<Integer> getBiggestNumber(List<Integer> firstNumber, List<Integer> secondNumber) {
        return firstNumber.size() > secondNumber.size() ? firstNumber : secondNumber;
    }

    /**
     * Method that calculates the product of two given numbers, sequentially
     *
     * @param firstNumber  - first number
     * @param secondNumber - second number
     * @return The product of the two numbers
     */
    public static List<Integer> SequentialMultiply(List<Integer> firstNumber, List<Integer> secondNumber) {
        List<Integer> result = new ArrayList<>(Collections.nCopies((firstNumber.size() + secondNumber.size()), 0));
        final long startSerial = System.nanoTime();

        for (int i = 0; i < firstNumber.size(); i++) {
            int carry = 0;
            for (int j = 0; j < secondNumber.size(); j++) {
                int sum = Utils.getDigit(firstNumber, i) * Utils.getDigit(secondNumber, j) + Utils.getDigit(result, i + j) + carry;
                carry = sum / 10;
                result.set(i + j, sum % 10);
            }
            if (carry > 0) {
                result.set(i + secondNumber.size(), Utils.getDigit(result, i + secondNumber.size()) + carry);
            }
        }
        int i = result.size() - 1;
        while (i > 0 && Utils.getDigit(result, i) == 0) {
            result.remove(i);
            i--;
        }
        if (i == -1) {
            return Collections.emptyList();
        }

        final long endSerial = System.nanoTime();
        StringBuilder serialTime = new StringBuilder();
        serialTime.append("Serial time for multiplication: ");
        serialTime.append(endSerial - startSerial).append(" nanoseconds");
        try {
            Utils.writeTimes(serialTime.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Method that calculates the product of two given numbers using threads
     *
     * @param firstNumber     - first number
     * @param secondNumber    - second number
     * @param numberOfThreads - number of threads to be used
     * @return The product of the two numbers
     * @throws InterruptedException for thread exception
     */
    public static List<Integer> ParallelMultiply(List<Integer> firstNumber, List<Integer> secondNumber, int numberOfThreads) throws InterruptedException {

        List<Integer> smallNumber = getSmallestNumber(firstNumber, secondNumber);
        List<Integer> bigNumber = getBiggestNumber(firstNumber, secondNumber);


        int minSize = smallNumber.size();
        int maxSize = bigNumber.size();

        int start = 0;
        int dim = minSize / numberOfThreads;
        int end = minSize / numberOfThreads;
        int rest = minSize % numberOfThreads;

        final long startParallel = System.nanoTime();
        final long startCreateThreads = System.nanoTime();
        Thread[] threads = new MultiplyThread[numberOfThreads];
        List<List<Integer>> results = new ArrayList<>(Collections.nCopies(numberOfThreads, new ArrayList<>()));

        for (int index = 0; index < numberOfThreads; index++) {
            if (rest > 0) {
                end++;
                rest--;
            }
            results.set(index, new ArrayList<>(Collections.nCopies(minSize + maxSize, 0)));

            threads[index] = new MultiplyThread(start, end, smallNumber, bigNumber, results.get(index));
            threads[index].start();

            start = end;
            end = end + dim;
        }
        final long endCreateThreads = System.nanoTime();

        for (int index = 0; index < numberOfThreads; index++) {
            threads[index].join();
        }
        final long endParallel = System.nanoTime();

        for (int i = 1; i < numberOfThreads; i++) {
            results.set(0, AddNumbers.ParallelClassificationAdd(results.get(0), results.get(i), numberOfThreads, false));
        }

        List<Integer> finalResult = results.get(0);

        int i = finalResult.size() - 1;
        while (i > 0 && Utils.getDigit(finalResult, i) == 0) {
            finalResult.remove(i);
            i--;
        }
        if (i == -1) {
            return Collections.emptyList();
        }

        StringBuilder parallelTime = new StringBuilder();
        //parallelTime.append("Parallel time for multiplication: ");
        //parallelTime.append(endParallel - startParallel).append(" nanoseconds").append("\n");
        //parallelTime.append("Overhead time for multiplication: ");
        //parallelTime.append(endCreateThreads - startCreateThreads).append(" nanoseconds").append("\n");
        parallelTime.append("Effective Parallel time for multiplication: ");
        parallelTime.append((endParallel - startParallel) - (endCreateThreads - startCreateThreads)).append(" nanoseconds").append("\n");
        try {
            Utils.writeTimes(parallelTime.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return finalResult;
    }
}
