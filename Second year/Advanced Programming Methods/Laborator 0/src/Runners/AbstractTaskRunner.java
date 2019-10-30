package Runners;

import Domain.Task;

public abstract class AbstractTaskRunner implements TaskRunner {
    TaskRunner taskRunner;

    AbstractTaskRunner(TaskRunner runner) {
        this.taskRunner = runner;
    }
    @Override
    public abstract void executeOneTask();

    @Override
    public abstract void executeAll();

    @Override
    public void addTask(Task t) {
        taskRunner.addTask(t);
    }

    @Override
    public boolean hasTask() {
        return taskRunner.hasTask();
    }
}
