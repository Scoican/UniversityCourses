package ObjectProtocol;

import DTOPackage.EventDTO;

public class AddEventRequest implements Request {
    private EventDTO eventDTO;
    public AddEventRequest(EventDTO eventDTO){
        this.eventDTO=eventDTO;
    }
    public EventDTO getEventDTO(){
        return eventDTO;
    }
}
