package Service;

import Domain.Event;
import Repository.EventRepository;
import Utils.DataChanged;
import Utils.Observable;
import Utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class EventService implements Observable<DataChanged> {

    private List<Observer<DataChanged>> observers=new ArrayList<>();
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository){
        this.eventRepository=eventRepository;
    }

    public void save(String name,Integer seats){
        Event event=new Event(name,seats);
        eventRepository.save(event);
    }

    public void delete(Integer integer){
        eventRepository.delete(integer);
    }

    public void update(Integer integer,String name,Integer seats){
        Event event=new Event(name,seats);
        eventRepository.update(integer,event);
    }

    public Event findOne(Integer integer){
        return eventRepository.findOne(integer);
    }

    public Iterable<Event> findAll(){
        return eventRepository.findAll();
    }
    @Override
    public void addObserver(Observer<DataChanged> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<DataChanged> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(DataChanged t) {
        observers.forEach(o->o.update(t));
    }
}
