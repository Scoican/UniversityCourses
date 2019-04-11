package Service;

import Domain.Event;
import Domain.Ticket;
import Repository.EventRepository;
import Repository.TicketRepository;
import Utils.DataChanged;
import Utils.Observable;
import Utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class TicketService implements Observable<DataChanged> {

    private List<Observer<DataChanged>> observers=new ArrayList<>();
    private TicketRepository ticketRepository;
    private EventRepository eventRepository;

    public TicketService(TicketRepository ticketRepository,EventRepository eventRepository){
        this.ticketRepository=ticketRepository;
        this.eventRepository=eventRepository;
    }

    public void save(Integer idGame,Integer reservedSeats,Double price,String clientName){
        Ticket ticket=new Ticket(idGame,reservedSeats, clientName, price);
        Event game=eventRepository.findOne(idGame);
        game.setFreeSeats(game.getFreeSeats()-reservedSeats);
        eventRepository.update(idGame,game);
        ticketRepository.save(ticket);

    }

    public void delete(Integer integer){
        Ticket ticket=ticketRepository.findOne(integer);
        Event game=eventRepository.findOne(ticket.getId_game());
        game.setFreeSeats(game.getFreeSeats()+ticket.getReservedSeats());
        eventRepository.update(game.getId(),game);
        ticketRepository.delete(integer);
    }

    public void update(Integer integer,Integer idGame,Integer reservedSeats,Double price,String clientName){
        Ticket ticket=new Ticket(idGame,reservedSeats, clientName, price);
        ticketRepository.update(integer,ticket);
    }

    public Ticket findOne(Integer integer){
        return ticketRepository.findOne(integer);
    }

    public int sizeRepo(){
        return ticketRepository.size();
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
