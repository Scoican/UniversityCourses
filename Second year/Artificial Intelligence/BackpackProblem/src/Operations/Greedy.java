package Operations;

import Domain.Item;
import Domain.Pair;


import java.util.*;


public class Greedy {
    private static Double getFitness(Integer position, Item items){
        return items.getValue().get(position)/items.getWeight().get(position);
    }

    private static Integer bestSolution(List<Integer> visited,Item items) {
        int best = -1;
        for (int i = 0; i < items.getTotalItems(); i++) {
            if(best==-1 && visited.get(i) ==0) {
                best = i;
            }
            if (best>=0 && getFitness(best, items) < getFitness(i, items) && visited.get(i) == 0) {
                best = i;
            }
        }
        return best;
    }

    private static Double getWeight(Item items, List<Integer> backpack){
        Double total=0d;
        for(int i=0;i<backpack.size();i++){
            if(backpack.get(i)==1){
                total+=items.getWeight().get(i);
            }
        }
        return total;
    }

    public static List<Integer> getMaxValue(Item items){
        List<Integer> backpack=new ArrayList<>();
        List<Integer> visited=new ArrayList<>();
        for (int i = 0; i <items.getTotalItems() ; i++) {
            backpack.add(0);
            visited.add(0);
        }
        for(int i=0;i<items.getTotalItems();i++){
            int theBest=bestSolution(visited,items);
            if(getWeight(items,backpack)+items.getWeight().get(theBest)<=items.getCapacity()){
                backpack.set(theBest,1);
            }
            visited.set(theBest,1);
        }
        return backpack;
    }

}
