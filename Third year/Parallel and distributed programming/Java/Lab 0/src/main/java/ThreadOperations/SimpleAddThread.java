package ThreadOperations;

import Utils.Utils;

import java.util.List;

public class SimpleAddThread extends Thread {
    private int start;
    private int end;
    private List<Integer> bigNumber1;
    private List<Integer> bigNumber2;
    private int[] bigNumberSum;
    private int[] carry;
    private int noThread;

    /**
     * Constructor
     *
     * @param start         start position for the numbers
     * @param end           end position for the numbers
     * @param bigNumber1:   number to be added
     * @param bigNumber2:   number to be added
     * @param bigNumberSum: the sum of the two numbers
     * @param noThread:     current thread number
     * @param carry:        carry array
     *                      The array can have the following values:
     *                      -1:  Not yet calculated
     *                      0:  The carry is 0
     *                      1:  The carry is 1(the sum must be recalculated and it starts with carry value 1)
     *                      2:  The thread has "carry-potential"
     *                      A carry that comes from another thread can make this thread have a carry flag value of 1
     */
    public SimpleAddThread(int start, int end, List<Integer> bigNumber1, List<Integer> bigNumber2, int[] bigNumberSum, int noThread, int[] carry) {
        this.start = start;
        this.end = end;
        this.bigNumber1 = bigNumber1;
        this.bigNumber2 = bigNumber2;
        this.bigNumberSum = bigNumberSum;
        this.noThread = noThread;
        this.carry = carry;
    }

    @Override
    public void run() {
        super.run();

        int digitSum, c = 0;
        boolean potentialCarry = true;

        for (int index = start; index < end; index++) {
            digitSum = Utils.getDigit(bigNumber1, index) + Utils.getDigit(bigNumber2, index) + c;
            bigNumberSum[index] = digitSum % 10;
            c = digitSum / 10;
            if (digitSum % 10 != 9) {
                potentialCarry = false;
            }
        }
        if (potentialCarry) {
            carry[noThread + 1] = 2;
        } else {
            carry[noThread + 1] = c;
        }

        waitUntilAllThreadsAreFinished();

        setCarry();

        if (carry[noThread] == 1) {
            c = 1;
            for (int index = start; index < end; index++) {
                digitSum = bigNumberSum[index] + c;
                bigNumberSum[index] = digitSum % 10;
                c = digitSum / 10;
                if (c == 0) break;
            }
        }
    }

    /**
     * Update the carry flag values on the threads with carry-potential
     * <p>
     * Checks if the thread has carry-potential
     * If true, the value of the carry flag is updated with the closest value of 1 or 0, found on the left of him
     */
    private void setCarry() {
        if (carry[noThread] == 2) {
            int index = noThread - 1;
            do {
                carry[noThread] = carry[index];
                index--;
            } while (carry[noThread] == 2);
        }
    }

    /**
     * Checks if all the carry flag values have been calculated
     * The value of the first carry flag is ignored, since it's always 0
     */
    private void waitUntilAllThreadsAreFinished() {
        int index = 1;

        while (true) {
            while (index <= noThread) {
                if (carry[index] == -1) {
                    index = 1;
                } else {
                    index++;
                }
            }
            break;
        }
    }
}
