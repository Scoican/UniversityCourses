package ThreadOperations;

import Utils.Utils;

import javax.print.attribute.IntegerSyntax;
import java.util.List;

public class MultiplyThread extends Thread {
    public int start;
    public int end;
    private List<Integer> smallestNr;
    private List<Integer> biggerNr;
    private List<Integer> bigNumberResult;

    public MultiplyThread(int start, int end, List<Integer> smallestNr, List<Integer> biggerNr, List<Integer> bigNumberResult) {
        this.start = start;
        this.end = end;
        this.smallestNr = smallestNr;
        this.biggerNr = biggerNr;
        this.bigNumberResult = bigNumberResult;
    }

    @Override
    public void run() {
        super.run();

        int maxSize = biggerNr.size();
        int carry, product, multiplicand, multiplier, i, j;
        for (i = start; i < end; i++) {
            multiplier = Utils.getDigit(smallestNr, i);
            carry = 0;
            for (j = 0; j < maxSize; j++) {
                multiplicand = Utils.getDigit(biggerNr, j);
                product = multiplicand * multiplier + carry;
                carry = product / 10 + Utils.addDigit(bigNumberResult, j + i, product % 10);
            }
            if (carry != 0) {
                Utils.addDigit(bigNumberResult, j + i, carry % 10);
            }
        }
    }
}
