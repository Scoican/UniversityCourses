import java.util.Vector;

public class LogisticRegression {

    private DataSet dataSetTrain;
    private DataSet dataSetTest;

    public LogisticRegression(DataSet train,DataSet test){
        this.dataSetTrain = train;
        this.dataSetTest = test;
    }

    public static Vector<Double> getBiases(int nrBiases){
        Vector<Double> biases = new Vector<>(nrBiases) ;
        for(int i=0;i<nrBiases;i++)
            biases.add(i,Math.random());
        return biases;
    }

    public Vector<Double> learning(DataSet dataset,double rate,int epochs){
        int nrFeatures = dataset.getNrAttributes();
        int nrExamples = dataset.getNrExamples();
        Vector<Double> resultSet = dataset.getResultSet();
        Vector<Double> biases = getBiases(nrFeatures);
        for(int i=0;i<epochs;i++){
            for(int j=0; j<nrExamples;j++) {
                double result = Utils.calculate(biases, dataset.getColumn(j));
                double sigmoid = Utils.sigmoid(result);
                double error = sigmoid - resultSet.get(j);
                Utils.update_biases(biases,error,rate,dataset.getColumn(j));
            }
        }
        return biases;
    }

    public StringBuilder run(double rate,int epochs){
        Vector<Double> biases = learning(dataSetTrain,rate,epochs);
        Double result = Utils.calculate_accuracy(biases,dataSetTest);
        StringBuilder stringResult=new StringBuilder();
        stringResult.append("LR:\n");
        stringResult.append(result);
        return stringResult;
    }
}