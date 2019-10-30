package Domain;

import java.util.ArrayList;
import java.util.List;

public class Chromosome {
    private Double fitness;
    private List<Integer> representation;

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public List<Integer> getRepresentation() {
        return representation;
    }

    public void setRepresentation(List<Integer> representation) {
        this.representation = representation;
    }

    public Chromosome(Double fitness, List<Integer> representation) {
        this.fitness = fitness;
        this.representation = representation;
    }

    public Chromosome(int nr){
        representation=new ArrayList<>();
        for (int i = 0; i < nr; i++) {
            representation.add(0);
        }
    }

    public Chromosome(){
        this.representation=new ArrayList<>();
    }

    public void calculateFitness(Item items){
        double weight=0d;
        double value=0d;
        for (int i = 0; i < representation.size(); i++) {
            weight+=items.getWeight().get(i)* representation.get(i);
            value+=items.getValue().get(i)* representation.get(i);
        }
        if(weight>items.getCapacity()){
            value -=7*(weight-items.getCapacity());
            fitness=-value;
        }
        fitness=value;

    }


}
