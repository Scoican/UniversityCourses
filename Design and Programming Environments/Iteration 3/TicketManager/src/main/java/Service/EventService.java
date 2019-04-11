package Service;

import Domain.Event;
import Repository.EventRepository;
import Utils.*;

import java.util.ArrayList;
import java.util.List;

public class EventService implements Observable<EventChangeEvent> {

    private List<Observer<EventChangeEvent>> observers=new ArrayList<>();
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository){
        this.eventRepository=eventRepository;
    }

    public void save(String gameName,Double gamePrice,Integer freeSeats){
        Event event=new Event(gameName,gamePrice,freeSeats);
        eventRepository.save(event);
        notifyObservers(new EventChangeEvent(EventType.ADD,event));
    }

    public void delete(Integer integer){
        eventRepository.delete(integer);
        notifyObservers(new EventChangeEvent(EventType.DELETE,null));
    }

    public void update(Integer integer,String gameName,Double gamePrice,Integer freeSeats){
        Event event=new Event(gameName,gamePrice,freeSeats);
        eventRepository.update(integer,event);
        notifyObservers(new EventChangeEvent(EventType.UPDATE,event));
    }

    public Event findOne(Integer integer){
        return eventRepository.findOne(integer);
    }

    public Integer sizeRepo(){
        return eventRepository.size();
    }

    public Iterable<Event> findAll(){
        return eventRepository.findAll();
    }
    @Override
    public void addObserver(Observer<EventChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<EventChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(EventChangeEvent t) {
        observers.forEach(o->o.update(t));
    }
}
