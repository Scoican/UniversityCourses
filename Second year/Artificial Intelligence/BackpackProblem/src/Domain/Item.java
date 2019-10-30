package Domain;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private Integer totalItems;
    private List<Double> weight;
    private List<Double> value;
    private Integer capacity;
    Item(Integer totalItems,List<Double> weight, List<Double> value, Integer capacity){
        this.totalItems=totalItems;
        this.weight=new ArrayList<>(weight);
        this.value=new ArrayList<>(value);
        this.capacity=capacity;
    }

    public List<Double> getWeight() {
        return weight;
    }

    public void setWeight(List<Double> weight) {
        this.weight = weight;
    }

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }


    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }
}
