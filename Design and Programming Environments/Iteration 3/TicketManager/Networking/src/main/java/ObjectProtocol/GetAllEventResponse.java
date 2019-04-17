package ObjectProtocol;

import DTOPackage.EventDTO;

public class GetAllEventResponse implements Response{
    private EventDTO[] events;
    protected GetAllEventResponse(EventDTO[] events){
        this.events=events;
    }
    public EventDTO[] getEvents(){
        return events;
    }
}

