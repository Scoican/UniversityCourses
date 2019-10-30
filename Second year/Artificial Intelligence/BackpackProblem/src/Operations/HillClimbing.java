package Operations;

import Domain.Chromosome;
import Domain.Item;
import com.sun.media.sound.SoftChorus;

import java.util.*;
import java.util.stream.Collectors;

public class HillClimbing {

    private static List<Chromosome> population(Integer n, Integer nr) {
        List<Chromosome> pop = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            pop.add(new Chromosome(nr));
        }
        return pop;
    }

    public static Chromosome hc(Item items) {
        Chromosome chromosome = population(1, items.getTotalItems()).get(0);
        int generation = 0;
        while (generation < 100) {
            chromosome = bestOf(chromosome, items);
            //System.out.println(generation+":"+chromosome.getFitness()+"-"+chromosome.getRepresentation());
            generation++;
        }
        return chromosome;
    }

    private static Chromosome bestOf(Chromosome chromosome, Item items) {
        List<Chromosome> populated = createPopulation(chromosome, items);
        Chromosome bestChromosome = populated.get(0);
        for (int i = 0; i < populated.size(); i++) {
            if (bestChromosome.getFitness() < populated.get(i).getFitness()) {
                bestChromosome = populated.get(i);
            }
        }
        chromosome.calculateFitness(items);
        if (bestChromosome.getFitness() < chromosome.getFitness()) {
            bestChromosome = chromosome;
        }
        return bestChromosome;
    }

    private static List<Chromosome> createPopulation(Chromosome chromosome, Item items) {
        List<Chromosome> populated = new ArrayList<>();
        for (int i = 0; i < chromosome.getRepresentation().size(); i++) {
            Chromosome mutated = mutate_pos(chromosome, i, items);
            populated.add(mutated);
        }
        return populated;
    }

    public static Chromosome mutate_pos(Chromosome chromosome, int i, Item items) {
        List<Integer> newRepresentation = new ArrayList<>(chromosome.getRepresentation());
        Collections.copy(newRepresentation, chromosome.getRepresentation());
        if (newRepresentation.get(i) == 0) {
            newRepresentation.set(i, 1);
        } else {
            newRepresentation.set(i, 0);
        }
        Chromosome newMutation = new Chromosome(chromosome.getRepresentation().size());
        newMutation.setRepresentation(newRepresentation);
        newMutation.calculateFitness(items);
        return newMutation;
    }
}
