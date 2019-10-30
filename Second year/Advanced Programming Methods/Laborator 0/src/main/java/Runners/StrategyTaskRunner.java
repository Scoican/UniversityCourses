package Runners;

import main.java.Domain.Container;
import main.java.Domain.Strategy;
import main.java.Domain.Task;
import main.java.Domain.TaskContainerFactory;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
        TaskContainerFactory c= TaskContainerFactory.getInstance();
        this.container = c.createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
        Task t=this.container.remove();
        t.execute();
    }

    @Override
    public void executeAll() {
        while(this.container.isEmpty()){
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task t) {
        this.container.add(t);
    }

    @Override
    public boolean hasTask() {
        return this.container.isEmpty();
    }
}
