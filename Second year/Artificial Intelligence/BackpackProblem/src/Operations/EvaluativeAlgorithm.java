package Operations;

import Domain.Chromosome;
import Domain.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EvaluativeAlgorithm {

    public static Chromosome evAlgol(Item items) {
        List<Chromosome> populated = population(10, items.getTotalItems(), items);
        Chromosome bestChrome = populated.get(0);
        int generation = 0;
        Random random = new Random();
        while (generation < 500) {
            int r1 = random.nextInt(items.getTotalItems());
            int r2 = random.nextInt(items.getTotalItems());
            Chromosome crossed = crossover(populated.get(r1), populated.get(r2), items);
            Chromosome mutated = mutation(crossed, items, random);
            if (bestChrome.getFitness() < mutated.getFitness())
                bestChrome = mutated;
            if (populated.get(r1).getFitness() < populated.get(r2).getFitness()) {
                populated.set(r1, mutated);
            } else {
                populated.set(r2, mutated);
            }
            generation++;
            //System.out.println(generation+":"+bestChrome.getFitness()+"-"+bestChrome.getRepresentation());
        }
        return bestChrome;
    }


    private static Chromosome mutation(Chromosome crossed, Item items, Random random) {
        int r = random.nextInt(items.getTotalItems());
        return HillClimbing.mutate_pos(crossed, r, items);
    }

    private static Chromosome crossover(Chromosome parent1, Chromosome parent2, Item items) {
        Chromosome child = new Chromosome();
        List<Integer> representation = new ArrayList<>();
        for (int i = 0; i < parent1.getRepresentation().size(); i++) {
            if (i < parent1.getRepresentation().size() / 2) {
                representation.add(parent1.getRepresentation().get(i));
            } else {
                representation.add(parent2.getRepresentation().get(i));
            }
        }
        child.setRepresentation(representation);
        child.calculateFitness(items);
        return child;
    }

    private static List<Chromosome> population(int nr, Integer totalItems, Item items) {
        List<Chromosome> populated = new ArrayList<>();
        for (int i = 0; i < nr; i++) {
            Chromosome newChrome = new Chromosome(totalItems);
            newChrome.calculateFitness(items);
            populated.add(newChrome);
        }
        return populated;
    }
}
