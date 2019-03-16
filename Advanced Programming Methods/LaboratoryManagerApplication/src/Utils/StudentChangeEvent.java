package Utils;

import Domain.Student;

public class StudentChangeEvent implements Event {
    private ChangeEventType type;
    private Student newData;
    private Student oldData;

    public StudentChangeEvent(ChangeEventType type,Student newData,Student oldData){
        this.type=type;
        this.newData=newData;
        this.oldData=newData;
    }

    public StudentChangeEvent(ChangeEventType type,Student newData){
        this.type=type;
        this.newData=newData;
    }

    public ChangeEventType getType(){
        return type;
    }

    public Student getNewData() {
        return newData;
    }

    public Student getOldData() {
        return oldData;
    }
}
