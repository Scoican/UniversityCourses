package FileOperations;

import Utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TxtFileOperations implements FileOperations {
    @Override
    public void fileGenerator(String fileName, Integer size, Integer min, Integer max) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            StringBuilder generatedNumber;
            for (int i = 0; i < size; i++) {
                generatedNumber = Utils.generateNumber(ThreadLocalRandom.current().nextInt(min, max + 1));
                writer.println(generatedNumber);
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean fileComparator(String fileName1, String fileName2) {
        BufferedReader readerFile1;
        BufferedReader readerFile2;
        try {
            readerFile1 = new BufferedReader(new FileReader(fileName1));
            readerFile2 = new BufferedReader(new FileReader(fileName2));

            int linesFromFile1 = 0;
            while (readerFile1.readLine() != null) linesFromFile1++;

            int linesFromFile2 = 0;
            while (readerFile2.readLine() != null) linesFromFile2++;

            if (linesFromFile1 != linesFromFile2) {
                return false;
            } else {
                readerFile1 = new BufferedReader(new FileReader(fileName1));
                readerFile2 = new BufferedReader(new FileReader(fileName2));

                String lineFromFile1 = readerFile1.readLine();

                while (lineFromFile1 != null) {
                    String lineFromFile2 = readerFile2.readLine();
                    if (!lineFromFile1.equals(lineFromFile2)) {
                        return false;
                    }
                    lineFromFile1 = readerFile1.readLine();
                }
            }
            readerFile1.close();
            readerFile2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Method that reads all the numbers in a given file
     *
     * @param fileName - given file name
     * @return List of strings that contain the numbers in the file
     */
    public List<String> readNumbers(String fileName) {
        List<String> numbers = new ArrayList<>();
        BufferedReader readerFile;
        try {
            readerFile = new BufferedReader(new FileReader(fileName));

            String lineFromFile = readerFile.readLine();

            while (lineFromFile != null) {
                numbers.add(lineFromFile);
                lineFromFile = readerFile.readLine();
            }
            readerFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    /**
     * Method that writes a given number in a given file
     *
     * @param fileName - given file name
     * @param number   - given number
     */
    public void writeNumber(String fileName, StringBuilder number) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            writer.println(number);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

