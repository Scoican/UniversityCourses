package Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    /**
     * Method that generates a number with of a given size
     *
     * @param size - How many digits does the number contain
     * @return Number, in string format, generated with length equal to given size
     */
    public static StringBuilder generateNumber(int size) {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int digit = ThreadLocalRandom.current().nextInt(0, 9);
            if (i == 0) {
                while (digit == 0) {
                    digit = ThreadLocalRandom.current().nextInt(0, 9);
                }
            }
            number.append(digit);
        }
        return number;
    }

    /**
     * Method that converts a given string into a list of integers
     *
     * @param line StringBuilder that contains a number
     * @return list of digits that represent a number
     */
    public static List<Integer> stringToList(StringBuilder line) {
        List<Integer> obtainedNumber = new ArrayList<>();
        line.reverse();
        for (int i = 0; i < line.length(); i++) {
            obtainedNumber.add(Integer.parseInt(String.valueOf(line.charAt(i))));
        }
        return obtainedNumber;
    }

    /**
     * Method that converts a given list into a string
     *
     * @param number List of digits that represent a number
     * @return string that represent a number
     */
    public static StringBuilder listToString(List<Integer> number) {
        StringBuilder obtainedLine = new StringBuilder();
        for (Integer digit : number) {
            obtainedLine.append(digit);
        }
        obtainedLine.reverse();
        return obtainedLine;
    }

    /**
     * Method that returns a digit from a given number from a given index
     *
     * @param number - given number
     * @param index  - given index
     * @return digit at index
     */
    public static int getDigit(List<Integer> number, int index) {
        if (index < number.size())
            return number.get(index);
        return 0;
    }

    /**
     * Method that writes in a file a given result
     *
     * @param result - result
     * @throws IOException IOException
     */
    public static void writeResult(boolean result) throws IOException {
        Files.write(Paths.get("Result.txt"), (result + " - " + Instant.now() + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    /**
     * Method that writes in a file a given string
     *
     * @param time - duration of computing
     * @throws IOException IOException
     */
    public static void writeTimes(String time) throws IOException {
        Files.write(Paths.get("Times.txt"), (time + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    /**
     * Method that adds a digit to a given position in a list of digits and returns the carry
     *
     * @param digits - List of digits
     * @param poz    - Position for where to add the given digit
     * @param digit  - given digit
     * @return carry value
     */
    public static int addDigit(List<Integer> digits, int poz, int digit) {
        int sum = digits.get(poz) + digit;
        digits.set(poz, sum % 10);
        return sum / 10;
    }
}
