import java.util.Vector;

public class Utils {

    public static double calculate(Vector<Double> biases,Vector<Double> data){
        double result = 0;
        for(int i=0;i<biases.size();i++)
            result += biases.get(i) * data.get(i);
        return result;
    }

    public static double sigmoid(double value){
        return 1.0 / (1.0 + (Math.exp(value)));
    }

    public static Vector<Double> update_biases(Vector<Double> biases,double error,double rate,Vector<Double> data){
        for(int i=0;i<biases.size();i++)
            biases.set(i,biases.get(i) - rate * error * data.get(i));
        return biases;
    }

    public static double calculate_accuracy(Vector<Double> biases, DataSet dataSetTest){
        Vector<Double> originalResultSet = dataSetTest.getOriginalResultSet();
        int nrExamples = dataSetTest.getNrExamples();
        double correct = 0;

        for(int j=0;j<nrExamples;j++){
            double result = calculate(biases,dataSetTest.getColumn(j));
            double sigmoid = sigmoid(result);
            double predicted = Math.round(dataSetTest.translate(sigmoid,0,1,1,dataSetTest.getNrClasses()));
            if(predicted == originalResultSet.get(j))
                correct++;
        }
        return correct / nrExamples;
    }
}