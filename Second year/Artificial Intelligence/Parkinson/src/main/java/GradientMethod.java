import Jama.Matrix;

import java.util.Random;
import java.util.Vector;

public class GradientMethod {
    public static Vector<Double> gradient(DataSet dataset, Double rate, Integer nrEpochs){
        Vector<Double> w = new Vector<>();
        Matrix output = dataset.getResults();
        Matrix dataSetCopy = dataset.getMatrix();
        for(int i=0;i<dataset.getNrAttributes();i++)
            w.add(i,new Random().nextDouble());
        for(int i=0;i<nrEpochs;i++){
            for(int j=0;j<dataset.getNrExamples();j++){
                double sum = 0.0;
                for(int k=0;k<w.size();k++)
                    sum += w.get(k) * dataSetCopy.get(j,k);
                double error = sum - output.get(j,0);
                for(int k=0;k<w.size();k++)
                    w.set(k,w.get(k) - rate * error * dataSetCopy.get(j,k));
            }
        }
        return w;
    }
}
