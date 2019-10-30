import java.util.Random;
import java.util.Vector;

public class Chromosome {

    private DataSet dataSetTrain;
    private Integer nrAttributes;
    private Vector<Double> generation;
    private Double fitness;

    public Chromosome(DataSet dataSetTrain){
        this.dataSetTrain = dataSetTrain;
        this.nrAttributes = dataSetTrain.getNrAttributes();
        this.generation = new Vector<>();
        init_generation();
        this.fitness = calculateFitness();

    }

    public void init_generation(){
        int nrBiases = dataSetTrain.getNrAttributes();
        Random r = new Random();
        for(int i=0;i<nrBiases;i++)
            this.generation.add(i,((Math.random()* (r.nextBoolean() ? -1 : 1))));
    }

    public Double calculateFitness(){
        double difference = 0;
        Integer nrExamples = dataSetTrain.getNrExamples();
        Vector<Double> resultSet = dataSetTrain.getResultSet();
        for(int i=0;i<nrExamples;i++) {
            double result = Utils.calculate(this.generation,dataSetTrain.getColumn(i));
            double sigmoid = Utils.sigmoid(result);
            double error = sigmoid - resultSet.get(i);
            difference += Math.abs(error);
        }
        return difference;
    }

    public Vector<Double> getGeneration(){
        return this.generation;
    }

    public void setGeneration(Vector<Double> newGeneration){
        this.generation = newGeneration;
    }

    public Double getFitness(){
        return this.fitness;
    }

    public void setFitness(Double newFitness){
        this.fitness = newFitness;
    }

    public Chromosome CrossOver(Chromosome other){

        Vector<Double> xo = new Vector<>();
        Vector<Double> other_gen = other.getGeneration();

        for(int i=0; i<nrAttributes; i++) {
            xo.add(i,((generation.get(i) + other_gen.get(i))/2.0));
        }
        Chromosome offspring = new Chromosome(dataSetTrain);
        offspring.setGeneration(xo);
        offspring.setFitness(offspring.calculateFitness());

        return offspring;
    }
    public Chromosome mutation(){

        Integer nrExamples = dataSetTrain.getNrExamples();
        Vector<Double> resultSet = dataSetTrain.getResultSet();

        for(int i=0;i<nrExamples;i++){
            Vector<Double> currentCol = dataSetTrain.getColumn(i);
            Double result = Utils.calculate(generation,currentCol);
            Double calculated = Utils.sigmoid(result);
            double error = calculated - resultSet.get(i);
            Utils.update_biases(generation,error,0.005,currentCol);
        }
        return this;
    }
}
