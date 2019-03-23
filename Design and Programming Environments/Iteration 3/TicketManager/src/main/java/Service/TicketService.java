package Service;

import Domain.Ticket;
import Repository.TicketRepository;
import Utils.DataChanged;
import Utils.Observable;
import Utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class TicketService implements Observable<DataChanged> {

    private List<Observer<DataChanged>> observers=new ArrayList<>();
    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository=ticketRepository;
    }

    public void save(Integer event,String buyer,Double price){
        Ticket ticket=new Ticket(event,buyer,price);
        ticketRepository.save(ticket);
    }

    public void delete(Integer integer){
        ticketRepository.delete(integer);
    }

    public void update(Integer integer,Integer event,String buyer,Double price){
        Ticket ticket=new Ticket(event,buyer,price);
        ticketRepository.update(integer,ticket);
    }

    public Ticket findOne(Integer integer){
        return ticketRepository.findOne(integer);
    }

    public Ticket findTicketWithEvent(Integer integer){
        for(Ticket t:ticketRepository.findAll()){
            if(t.getEvent()==integer){
                return t;
            }
        }
        return null;
    }

    public Iterable<Ticket> findAll(){
        return ticketRepository.findAll();
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
