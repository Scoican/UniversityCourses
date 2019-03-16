package Domain;

import java.util.ArrayList;

public abstract class SuperContainer implements Container {
    public ArrayList<Task> tasks;

    SuperContainer(){
        this.tasks = new ArrayList<>();
    }
    public abstract Task remove();

    public void add(Task task) {
        this.tasks.add(task);
    }

    public int size() {
        return this.tasks.size();
    }

    public boolean isEmpty() {
        return !this.tasks.isEmpty();
    }

}
