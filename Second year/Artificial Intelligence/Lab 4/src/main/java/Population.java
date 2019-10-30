import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Population {

    private List<Chromosome> population;
    private int populationSize;

    public Population(int popSize, DataSet data){
        populationSize = popSize;
        population = new ArrayList<>(popSize);
        for(int i=0;i<popSize;i++)
            population.add(new Chromosome(data));
    }

    public Population(Population pop){
        this.population = pop.getPopulation();
        this.populationSize = pop.getPopulationSize();
    }

    public Chromosome getBest(){
        Chromosome minimum = population.get(0);
        for(Chromosome p : population){
            if(Math.abs(p.getFitness()) < Math.abs(minimum.getFitness())){
                minimum = p;
            }
        }
        return minimum;
    }

    public List<Chromosome> getPopulation(){

        return this.population;
    }

    public int getPopulationSize(){

        return this.populationSize;
    }

    public void addGeneration(Chromosome x){

        population.add(x);
        populationSize ++;
    }

    public Vector<Chromosome> selection(){

        Vector<Chromosome> pair = new Vector<>();
        Chromosome first_best = population.get(0);
        Chromosome second_best = population.get(1);
        for(Chromosome p : population){
            if(p.getFitness() < first_best.getFitness()){
                second_best = first_best;
                first_best = p;
            }
            else if(p.getFitness() < second_best.getFitness())
                second_best = p;

        }
        pair.add(0,first_best);
        pair.add(1,second_best);
        return pair;

    }
}
