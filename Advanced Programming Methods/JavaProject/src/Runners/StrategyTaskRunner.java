package Runners;

import Domain.Container;
import Domain.Strategy;
import Domain.Task;
import Domain.TaskContainerFactory;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
        TaskContainerFactory c=TaskContainerFactory.getInstance();
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
