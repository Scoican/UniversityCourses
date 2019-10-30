import Jama.Matrix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        FileRead trainFile = new FileRead("hard_train.txt",true);
        FileRead testFile = new FileRead("hard_test.txt",true);
        DataSet trainDataSet = trainFile.readVector();
        DataSet testDataSet = testFile.readVector();
        main.leastSquare_test(trainDataSet,testDataSet,"hard_result_Scoican.txt","hard_coef_Scoican.txt");
        main.gradient_test(trainDataSet,testDataSet,0.00005,1000,"hard_result_Scoican.txt","hard_coef_Scoican.txt");
    }

    public void gradient_test(DataSet trainData,DataSet testData,Double rate,Integer epochs,String filename,String coefFile){
        Vector<Double> coefficients = GradientMethod.gradient(trainData,rate,epochs);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(coefFile,true))) {
            bw.write("Gradient:\n");
            for(Double c : coefficients)
                bw.write(c + " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename,true))) {
            Vector<Double> errorsFromTest = errorList(coefficients,testData);
            Double quadAvg = quadraticAvg(errorsFromTest);
            bw.write(quadAvg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leastSquare_test(DataSet trainData, DataSet testData, String filename, String coefFile){
        Matrix leastSquare = LeastSquaresMethod.leastSquareWrapper(trainData);
        Vector<Double> coefficients = LeastSquaresMethod.getCoefficients(leastSquare);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(coefFile))) {
            bw.write("LeastSquare:\n");
            for(Double c : coefficients)
                bw.write(c + " ");
            bw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Vector<Double> errorsFromTest = errorList(coefficients,testData);
            Double quadAvg = quadraticAvg(errorsFromTest);
            bw.write(quadAvg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Double quadraticAvg(Vector<Double> errorList){

        double quadraticSum = 0.0;
        for(Double err : errorList){
            quadraticSum += Math.pow(err,2);
        }
        return Math.sqrt(quadraticSum / errorList.size());
    }

    private static Vector<Double> errorList(Vector<Double> w, DataSet testDataset){
        Vector<Double> errors = new Vector<>();
        Matrix dataSetCopy = testDataset.getMatrix();
        Matrix result = testDataset.getResults();
        for(int i=0; i < testDataset.getNrExamples();i++) {
            double suma = 0.0;
            for(int j = 0; j < w.size(); j++)
                suma += w.get(j) * dataSetCopy.get(i,j);
            double err = result.get(i,0) - suma;
            errors.add(i,err);
        }
        return errors;
    }

}
