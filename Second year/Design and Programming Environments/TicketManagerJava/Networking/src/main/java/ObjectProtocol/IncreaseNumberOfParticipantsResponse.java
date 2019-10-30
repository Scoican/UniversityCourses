package ObjectProtocol;


import ModelDomain.Event;

public class IncreaseNumberOfParticipantsResponse implements UpdateResponse {
    private Event event;
    public  IncreaseNumberOfParticipantsResponse(Event event){
        this.event=event;
    }
    public Event getEvent(){return  event;
    }
}
