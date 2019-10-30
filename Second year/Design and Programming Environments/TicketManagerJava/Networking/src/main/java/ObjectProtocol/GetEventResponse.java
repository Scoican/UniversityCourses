package ObjectProtocol;

import ModelDomain.Event;

public class GetEventResponse implements Response{
    private Event event;
    protected GetEventResponse(Event event){
        this.event=event;
    }
    public Event getEvent(){
        return event;
    }
}
