package Domain;

import java.util.ArrayList;
import java.util.Collections;

public class BubbleSort extends AbstractSorter {

    @Override
    public void sort(ArrayList<Integer> vector) {
        int n = vector.size();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (vector.get(j - 1) > vector.get(j)) {
                    Collections.swap(vector, j - 1, j);
                }
            }
        }
    }
}
