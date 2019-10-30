package Domain;

import java.util.ArrayList;
import java.util.Collections;

public class QuickSort extends  AbstractSorter {


    private int partition(ArrayList<Integer> vector, int low, int high)
    {
        int pivot = vector.get(high);
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
            if (vector.get(j) <= pivot)
            {
                i++;
                Collections.swap(vector,i,j);
            }
        }
        Collections.swap(vector,i+1,high);
        return i+1;
    }
    
    private void quickSort(ArrayList<Integer> vector,int low,int high){
        if (low < high)
        {
            int pi = partition(vector, low, high);
            quickSort(vector, low, pi-1);
            quickSort(vector, pi+1, high);
        }
    }

    @Override
    public void sort(ArrayList<Integer> vector) {
        quickSort(vector,0,vector.size()-1);
    }
}
