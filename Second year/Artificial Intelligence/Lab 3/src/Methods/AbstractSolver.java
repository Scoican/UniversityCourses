package Methods;

import matrix.Matrix;
import utils.DataSet;

import java.util.Vector;

public class AbstractSolver {
    private DataSet testSet;
    private DataSet trainSet;

    public DataSet getTestSet() {
        return testSet;
    }

    public void setTestSet(DataSet testSet) {
        this.testSet = testSet;
    }

    public DataSet getTrainSet() {
        return trainSet;
    }

    public void setTrainSet(DataSet trainSet) {
        this.trainSet = trainSet;
    }

    public AbstractSolver(DataSet testSet, DataSet trainSet) {
        this.testSet = testSet;
        this.trainSet = trainSet;
    }

    public Vector<Float> test(Matrix trainedResult){
        Vector<Float> errors=new Vector<>();
        Matrix x=this.testSet.getMatrix();
        Matrix y=this.testSet.getResultSet();
        for(int i=0;i<this.testSet.getExamples();i++){
            float sum=0;
            for(int j=0;j<trainedResult.getNrows();j++){
                sum=sum+trainedResult.getValueAt(j,0)*x.getValueAt(j,i);
            }
            float error=y.getValueAt(i,0)-sum;
            errors.add(error);
        }
        return errors;
    }

}
