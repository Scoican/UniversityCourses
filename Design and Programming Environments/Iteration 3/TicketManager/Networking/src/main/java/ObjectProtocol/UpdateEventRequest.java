package ObjectProtocol;


import DTOPackage.EventDTO;

public class UpdateEventRequest implements Request{
    private EventDTO eventDTO;

    public EventDTO getEventDTO() {
        return eventDTO;
    }

    public UpdateEventRequest(EventDTO eventDTO) {
        this.eventDTO = eventDTO;
    }
}
