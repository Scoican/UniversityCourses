package Domain;

public abstract class Task {
    private String taskId;
    private String description;

    public Task(String taskId,String description){
        this.taskId=taskId;
        this.description =description;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void  execute();

    public String toString(){
        return taskId+" "+ description;
    }
}
