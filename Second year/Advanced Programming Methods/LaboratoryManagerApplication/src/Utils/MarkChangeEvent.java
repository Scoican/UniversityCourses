package Utils;

import Domain.Mark;

public class MarkChangeEvent implements Event {
    private ChangeEventType type;
    private Mark newData, oldData;


    public MarkChangeEvent(ChangeEventType type, Mark newData) {
        this.type = type;
        this.newData = newData;
    }

    public MarkChangeEvent(ChangeEventType type, Mark newData, Mark oldData) {
        this.type = type;
        this.newData = newData;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Mark getNewData() {
        return newData;
    }

    public Mark getOldData() {
        return oldData;
    }
}
