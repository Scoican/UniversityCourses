package Domain;

import java.util.ArrayList;

public class StackContainer extends SuperContainer{

    public StackContainer(){
        super();
    }
    @Override
    public Task remove() {
        return tasks.remove(tasks.size()-1);
    }

}
