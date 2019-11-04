import FileOperations.TxtFileOperations;
import NumberOperations.AddNumbers;
import NumberOperations.MultiplyNumbers;
import Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TxtFileOperations txtFile = new TxtFileOperations();
        calculate(txtFile, Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        //Result of file comparisons
        try {
            Utils.writeResult(txtFile.fileComparator("totalSumSequential.txt", "totalSumParallelClassification.txt"));
            Utils.writeResult(txtFile.fileComparator("totalSumSequential.txt", "totalSumParallelOptimised.txt"));
            Utils.writeResult(txtFile.fileComparator("totalMultiplySequential.txt", "totalMultiplyParallel.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void calculate(TxtFileOperations txtFile, int min, int max) {
        //txtFile.fileGenerator("Numbers.txt", 2, 10000, 10000);
        List<String> numbers = txtFile.readNumbers("Numbers.txt");
        List<List<Integer>> convertedNumbers = new ArrayList<>();
        StringBuilder auxiliaryStringBuilder = new StringBuilder();
        for (String number : numbers) {
            auxiliaryStringBuilder.append(number);
            convertedNumbers.add(Utils.stringToList(auxiliaryStringBuilder));
            auxiliaryStringBuilder.setLength(0);
        }

        //Total sum of numbers sequential
        txtFile.writeNumber("totalSumSequential.txt", Utils.listToString(AddNumbers.SequentialAdd(convertedNumbers.get(0), convertedNumbers.get(1))));

        //Total sum of numbers parallel classification
        try {
            txtFile.writeNumber("totalSumParallelClassification.txt", Utils.listToString(AddNumbers.ParallelClassificationAdd(convertedNumbers.get(0), convertedNumbers.get(1), 8, true)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Total sum of numbers parallel optimised
        try {
            txtFile.writeNumber("totalSumParallelOptimised.txt", Utils.listToString(AddNumbers.ParallelOptimisedAdd(convertedNumbers.get(0), convertedNumbers.get(1), 8)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Total result of number multiplication sequential
        txtFile.writeNumber("totalMultiplySequential.txt", Utils.listToString(MultiplyNumbers.SequentialMultiply(convertedNumbers.get(0), convertedNumbers.get(1))));

        //Total result of number multiplication parallel
        try {
            txtFile.writeNumber("totalMultiplyParallel.txt", Utils.listToString(MultiplyNumbers.ParallelMultiply(convertedNumbers.get(0), convertedNumbers.get(1), 8)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
