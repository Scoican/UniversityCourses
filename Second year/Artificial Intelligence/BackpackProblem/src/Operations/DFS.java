package Operations;

import Domain.Item;

import java.util.*;

public class DFS {


    private static boolean valid(Item items,List<Integer> backpack,Integer capacity){
        int total=0;
        for(int i=0;i<backpack.size();i++){
            if(backpack.get(i)==1){
                total+=items.getWeight().get(i);
            }
        }
        return total<=capacity;
    }

    private static Double getValue(Item items,List<Integer> backpack){
        Double total=0d;
        for(int i=0;i<backpack.size();i++){
            if(backpack.get(i)==1){
                total+=items.getValue().get(i);
            }
        }
        return total;
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

    private static boolean solution(List<Integer> backpack,Item items){
        return backpack.size()==items.getTotalItems();
    }

    private static boolean betterSolution(List<Integer> possible,List<Integer> backpack,Item items){
        if(!solution(possible,items)){
            return true;
        }
        if(getValue(items,backpack)>getValue(items,possible)){
            return true;
        }
        if(getValue(items, backpack).equals(getValue(items, possible))){
            return getWeight(items,backpack)> getWeight(items,possible);
        }
        return  false;
    }

    public static void dfsBack(Item items, List<Integer> backpack, List<Integer> currentSolution){
        for(int i=0;i<2;i++){
            backpack.add(i);
            if(valid(items,backpack,items.getCapacity())){
                if(solution(backpack,items)){
                    if(betterSolution(currentSolution,backpack,items)){
                        currentSolution.clear();
                        currentSolution.addAll(backpack);
                    }
                }else{
                    dfsBack(items,backpack,currentSolution);
                }
            }
           backpack.remove(backpack.size()-1);
        }
    }

}
