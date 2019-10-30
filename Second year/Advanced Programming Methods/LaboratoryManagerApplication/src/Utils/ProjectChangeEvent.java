package Utils;

import Domain.Project;

public class ProjectChangeEvent implements Event{
    private ChangeEventType type;
    private Project newData;
    private Project oldData;

    public ProjectChangeEvent(ChangeEventType type,Project newData,Project oldData){
        this.type=type;
        this.newData=newData;
        this.oldData=newData;
    }

    public ProjectChangeEvent(ChangeEventType type,Project newData){
        this.type=type;
        this.newData=newData;
    }

    public ChangeEventType getType(){
        return type;
    }

    public Project getNewData() {
        return newData;
    }

    public Project getOldData() {
        return oldData;
    }
}
