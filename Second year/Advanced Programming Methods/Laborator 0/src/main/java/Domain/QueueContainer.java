package main.java.Domain;


public class QueueContainer extends SuperContainer {


    public QueueContainer(){
        super();
    }
    @Override
    public Task remove() {
        return tasks.remove(0);
    }

}
