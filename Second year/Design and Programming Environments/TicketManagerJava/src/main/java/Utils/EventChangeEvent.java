package Utils;

import Domain.Event;

public class EventChangeEvent implements EventUtils{
    private EventType type;
    private Event newData, oldData;


    public EventChangeEvent(EventType type, Event newData, Event oldData) {
        this.type = type;
        this.newData = newData;
        this.oldData = oldData;
    }

    public EventChangeEvent(EventType type,Event newData){
        this.type = type;
        this.newData = newData;
    }

    public EventType getType(){
        return type;
    }

    public Event getNewData(){
        return newData;
    }

    public Event getOldData(){
        return oldData;
    }
}
