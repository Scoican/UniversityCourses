import java.util.Vector;

public class EvolutionAlgorithm {

    private DataSet dataSetTrain;
    private DataSet dataSetTest;

    public EvolutionAlgorithm(DataSet train, DataSet test){
        dataSetTrain = train;
        dataSetTest = test;
    }

    private Chromosome learning(int popSize, int nrSteps){

        Population population = new Population(popSize,dataSetTrain);
        Population lastPopulation = new Population(population);

        while(nrSteps > 0){
            Population newPopulation = new Population(population);
            for(int j=0;j<population.getPopulationSize();j++){
                Vector<Chromosome> pair = population.selection();
                Chromosome offspring = pair.get(0).CrossOver(pair.get(1));
                Chromosome offspringMutant = offspring.mutation();
                newPopulation.addGeneration(offspringMutant);
            }
            nrSteps --;
            lastPopulation = new Population(newPopulation);
        }

        return lastPopulation.getBest();

    }

    public StringBuilder run(int popSize, int nrSteps){
        Chromosome biases = learning(popSize,nrSteps);
        double result = Utils.calculate_accuracy(biases.getGeneration(),dataSetTest);
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("EA:\n");
        stringBuilder.append(result);
        return stringBuilder;
    }

}
