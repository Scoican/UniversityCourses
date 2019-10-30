package ObjectProtocol;

import ModelDomain.Event;


import java.util.List;

public class NotifyResponse implements Response,UpdateResponse{
    List<Event> events;

    public NotifyResponse(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
