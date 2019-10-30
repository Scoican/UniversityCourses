package NumberOperations;

import ThreadOperations.AddThread;
import ThreadOperations.ClassificationThread;
import Utils.Utils;
import ThreadOperations.SimpleAddThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddNumbers {

    /**
     * Method that calculates the total sum of two given numbers, sequentially
     *
     * @param firstNumber  - first number
     * @param secondNumber - second number
     * @return The sum of the two numbers
     */
    public static List<Integer> SequentialAdd(List<Integer> firstNumber, List<Integer> secondNumber) {
        List<Integer> totalSum = new ArrayList<>();
        int carryFlag = 0;
        int digitSum;
        final long startSerial = System.nanoTime();
        if (firstNumber.size() > secondNumber.size()) {
            for (int i = 0; i < secondNumber.size(); i++) {
                digitSum = firstNumber.get(i) + secondNumber.get(i) + carryFlag;
                if (digitSum >= 10) {
                    carryFlag = 1;
                } else {
                    carryFlag = 0;
                }
                digitSum = digitSum % 10;
                totalSum.add(digitSum);
            }
            for (int j = secondNumber.size(); j < firstNumber.size(); j++) {
                digitSum = firstNumber.get(j) + carryFlag;
                if (digitSum >= 10) {
                    carryFlag = 1;
                } else {
                    carryFlag = 0;
                }
                digitSum = digitSum % 10;
                totalSum.add(digitSum);
            }
            if (carryFlag == 1) {
                totalSum.add(carryFlag);
            }
        } else {
            for (int i = 0; i < firstNumber.size(); i++) {
                digitSum = firstNumber.get(i) + secondNumber.get(i) + carryFlag;
                if (digitSum >= 10) {
                    carryFlag = 1;
                } else {
                    carryFlag = 0;
                }
                digitSum = digitSum % 10;
                totalSum.add(digitSum);
            }
            for (int j = firstNumber.size(); j < secondNumber.size(); j++) {
                digitSum = secondNumber.get(j) + carryFlag;
                if (digitSum >= 10) {
                    carryFlag = 1;
                } else {
                    carryFlag = 0;
                }
                digitSum = digitSum % 10;
                totalSum.add(digitSum);
            }
            if (carryFlag == 1) {
                totalSum.add(carryFlag);
            }
        }
        final long endSerial = System.nanoTime();

        StringBuilder serialTime = new StringBuilder();
        serialTime.append("Serial time for sum: ");
        serialTime.append(endSerial - startSerial).append(" nanoseconds");
        try {
            Utils.writeTimes(serialTime.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalSum;
    }

    /**
     * Method that calculates the total sum of two given numbers, using two sets of threads
     * One set classifies the carry flags and the second set computes the sum
     *
     * @param firstNumber     - first number
     * @param secondNumber    - second number
     * @param numberOfThreads - number of threads to be used
     * @param printFlag       - condition for printing times
     * @return The sum of the two numbers
     * @throws InterruptedException for thread exceptions
     */
    public static List<Integer> ParallelClassificationAdd(List<Integer> firstNumber, List<Integer> secondNumber, int numberOfThreads, boolean printFlag) throws InterruptedException {
        int size = Math.max(firstNumber.size(), secondNumber.size()) + 1;

        int[] numberSum = new int[size];
        int start = 0;
        int dim = size / numberOfThreads;
        int end = size / numberOfThreads;
        int rest = size % numberOfThreads;

        final long startParallel = System.nanoTime();
        final long startCreateThreads = System.nanoTime();

        Thread[] threadsClass = new ClassificationThread[numberOfThreads];
        Thread[] threads = new AddThread[numberOfThreads];
        int[] carry = new int[numberOfThreads];

        for (int index = 0; index < numberOfThreads; index++) {
            if (rest > 0) {
                end++;
                rest--;
            }

            threadsClass[index] = new ClassificationThread(start, end, firstNumber, secondNumber, carry, index);
            threadsClass[index].start();

            threads[index] = new AddThread(start, end, firstNumber, secondNumber, numberSum, carry, index);
            start = end;
            end = end + dim;
        }

        for (int index = 0; index < numberOfThreads; index++) {
            threadsClass[index].join();
        }

        for (int index = 0; index < numberOfThreads; index++) {
            if (index > 0 && carry[index] == 2 && carry[index - 1] == 1) {
                carry[index] = 1;
            } else if (index > 0 && carry[index] == 2 && carry[index - 1] == 0) {
                carry[index] = 0;
            }
            threads[index].start();
        }

        final long endCreateThreads = System.nanoTime();

        for (int index = 0; index < numberOfThreads; index++) {
            threads[index].join();
        }

        final long endParallel = System.nanoTime();

        if (carry[numberOfThreads - 1] == 1) {
            numberSum[size - 1] = 1;
        }

        List<Integer> numberSumList = new ArrayList<>();
        for (int i : numberSum) {
            numberSumList.add(i);
        }

        if (numberSumList.get(size - 1) == 0) {
            numberSumList.remove(size - 1);
        }

        if (printFlag) {
            StringBuilder parallelTime = new StringBuilder();
            //parallelTime.append("Parallel time for classification sum: ");
            //parallelTime.append(endParallel - startParallel).append(" nanoseconds").append("\n");
            //parallelTime.append("Overhead time for classification sum: ");
            //parallelTime.append(endCreateThreads - startCreateThreads).append(" nanoseconds").append("\n");
            parallelTime.append("Effective Parallel time for classification sum: ");
            parallelTime.append((endParallel - startParallel) - (endCreateThreads - startCreateThreads)).append(" nanoseconds");
            try {
                Utils.writeTimes(parallelTime.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return numberSumList;
    }

    /**
     * Method that calculates the total sum of two given numbers, using one set of threads
     *
     * @param firstNumber     - first number
     * @param secondNumber    - second number
     * @param numberOfThreads - number of threads to be used
     * @return The sum of the two numbers
     * @throws InterruptedException for thread exceptions
     */
    public static List<Integer> ParallelOptimisedAdd(List<Integer> firstNumber, List<Integer> secondNumber, int numberOfThreads) throws InterruptedException {
        int size = Math.max(firstNumber.size(), secondNumber.size());
        int[] numberSum = new int[size];

        int start = 0;
        int dim = size / numberOfThreads;
        int end = size / numberOfThreads;
        int rest = size % numberOfThreads;
        int[] carry = new int[numberOfThreads + 1];
        carry[0] = 0;

        final long startParallel = System.nanoTime();
        final long startCreateThreads = System.nanoTime();
        Thread[] threads = new SimpleAddThread[numberOfThreads];

        for (int index = 0; index < numberOfThreads; index++) {
            carry[index + 1] = -1;

            if (rest > 0) {
                end++;
                rest--;
            }

            threads[index] = new SimpleAddThread(start, end, firstNumber, secondNumber, numberSum, index, carry);
            threads[index].start();

            start = end;
            end = end + dim;
        }
        final long endCreateThreads = System.nanoTime();

        for (int index = 0; index < numberOfThreads; index++) {
            threads[index].join();
        }
        final long endParallel = System.nanoTime();

        List<Integer> numberSumList = new ArrayList<>();
        for (int i : numberSum) {
            numberSumList.add(i);
        }

        if ((carry[numberOfThreads] == 2 && carry[numberOfThreads - 1] == 1) || carry[numberOfThreads] == 1) {
            numberSumList.add(1);
        }

        StringBuilder parallelTime = new StringBuilder();
        //parallelTime.append("Parallel time for optimised sum: ");
        //parallelTime.append(endParallel - startParallel).append(" nanoseconds").append("\n");
        //parallelTime.append("Overhead time for optimised sum: ");
        //parallelTime.append(endCreateThreads - startCreateThreads).append(" nanoseconds").append("\n");
        parallelTime.append("Effective Parallel time for optimised sum: ");
        parallelTime.append((endParallel - startParallel) - (endCreateThreads - startCreateThreads)).append(" nanoseconds").append("\n");
        try {
            Utils.writeTimes(parallelTime.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numberSumList;
    }

}
