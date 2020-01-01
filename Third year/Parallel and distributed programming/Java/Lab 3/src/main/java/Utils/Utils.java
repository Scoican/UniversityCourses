package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static void generatePolynomials(int numberOfPolynomials, int minCoefficient, int maxCoefficient, int maxExponent) throws IOException {

        for (int i = 1; i <= numberOfPolynomials; i++) {
            FileWriter fileWriter = new FileWriter("src/main/resources/polynomial" + i + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String polynomial = getPolynomial(minCoefficient, maxCoefficient, maxExponent);

            bufferedWriter.write(polynomial);
            bufferedWriter.close();
        }

    }

    private static String getPolynomial(int minCoefficient, int maxCoefficient, int maxExponent) {
        StringBuilder polynomial = new StringBuilder();
        for (int i = maxExponent; i >= 0; i--) {
            if (Math.random() >= 0.25) {
                polynomial.append(getRandomInt(minCoefficient, maxCoefficient)).append(",").append(i).append("\n");
            }
        }
        return String.valueOf(polynomial);
    }

    private static int getRandomInt(int minCoefficient, int maxCoefficient) {
        return (int) (Math.random() * ((maxCoefficient - minCoefficient) + 1)) + minCoefficient;
    }

    public static boolean compareFiles(String file_name1, String file_name2) throws IOException {

        try {
            FileReader file1 = new FileReader(file_name1);
            FileReader file2 = new FileReader(file_name2);

            BufferedReader inputReader1 = new BufferedReader(file1);
            BufferedReader inputReader2 = new BufferedReader(file2);

            List<String> list1 = new ArrayList<>();
            List<String> list2 = new ArrayList<>();

            String nextLine;
            while ((nextLine = inputReader1.readLine()) != null) {
                List<String> strings = Arrays.asList(nextLine.split(" *"));
                list1.addAll(strings);
            }
            list1.sort(String::compareTo);

            while ((nextLine = inputReader2.readLine()) != null) {
                List<String> strings = Arrays.asList(nextLine.split(" *"));
                list2.addAll(strings);
            }
            list2.sort(String::compareTo);

            return list1.equals(list2);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid files!");
        }
        return false;
    }
}
