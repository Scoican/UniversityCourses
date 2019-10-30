public class Main {
    public static void main(String[] args) {

        FileRead trainFile=new FileRead("medium_cardio01_train.txt",true);
        FileRead testFile=new FileRead("medium_cardio01_test.txt",true);

        DataSet dataSetTrain = trainFile.readVector();
        DataSet dataSetTest = testFile.readVector();

        StringBuilder result=new StringBuilder();

        LogisticRegression logisticReg = new LogisticRegression(dataSetTrain,dataSetTest);
        result.append(logisticReg.run(0.0005, 10000)).append("\n");

        EvolutionAlgorithm evolution = new EvolutionAlgorithm(dataSetTrain,dataSetTest);
        result.append(evolution.run(500,50));

        FileRead.writeToFile("mediumScoican.txt",result);
    }
}
