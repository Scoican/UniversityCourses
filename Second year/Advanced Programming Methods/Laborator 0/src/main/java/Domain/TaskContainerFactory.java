package Domain;


public final class TaskContainerFactory implements Factory
{
    private static final TaskContainerFactory INSTANCE = new TaskContainerFactory();

    private TaskContainerFactory() {}

    public static TaskContainerFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Container createContainer(Strategy strategy) {
        if (strategy== Strategy.LIFO)
            return new StackContainer();
        else
            if(strategy== Strategy.FIFO)
                return new QueueContainer();
            else return null;
    }
}
