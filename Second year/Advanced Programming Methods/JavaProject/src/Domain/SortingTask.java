package Domain;

import java.util.ArrayList;

public class SortingTask extends Task {

    private ArrayList<Integer> vector;
    private AbstractSorter sorter;
    public SortingTask(String id,String description,ArrayList<Integer> vector){
        super(id,description);
        this.vector=vector;
        this.sorter = new QuickSort();
    }

    @Override
    public void execute() {
        sorter.sort(vector);
    }
}
