package ThreadOperations;

import Utils.Utils;

import java.util.List;

public class AddThread extends Thread {
    private int start;
    private int end;
    private List<Integer> bigNumber1;
    private List<Integer> bigNumber2;
    private int[] bigNumberSum;
    private int noThread;
    private int[] carry;


    public AddThread(int start, int end, List<Integer> bigNumber1, List<Integer> bigNumber2, int[] bigNumberSum, int[] carry, int noThread) {
        this.start = start;
        this.end = end;
        this.bigNumber1 = bigNumber1;
        this.bigNumber2 = bigNumber2;
        this.bigNumberSum = bigNumberSum;
        this.carry = carry;
        this.noThread = noThread;
    }

    @Override
    public void run() {
        super.run();
        int digitSum, c = 0;
        if (noThread > 0) {
            c = this.carry[noThread - 1];
        }
        for (int index = start; index < end; index++) {
            digitSum = Utils.getDigit(bigNumber1, index) + Utils.getDigit(bigNumber2, index) + c;
            bigNumberSum[index] = digitSum % 10;
            c = digitSum / 10;
        }
    }
}