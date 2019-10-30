
import Exceptions.BasketException;
import Interfaces.IObserver;
import Interfaces.IServer;
import ModelDomain.Event;
import ModelDomain.Ticket;
import ModelDomain.User;
import Repository.EventRepository;
import Repository.TicketRepository;
import Repository.UserRepository;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl implements IServer {
    private TicketRepository ticketRepository;
    private EventRepository eventRepository;
    private UserRepository userRepository;

    private Map<String, IObserver> loggedPers;
    private List<IObserver> observers = new ArrayList<>();


    public ServerImpl(UserRepository userRepository, EventRepository eventRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;

        loggedPers = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(User person, IObserver client) throws BasketException {
        User user = userRepository.findOne(person.getId());
        if (user != null) {
            if (loggedPers.get(user.getId()) != null) {
                throw new BasketException("User already logged in!");
            }
            loggedPers.put(person.getId(), client);
        } else {
            throw new BasketException("Authentication error!");
        }
    }

    @Override
    public void logout(User person) throws BasketException {
        loggedPers.remove(person.getId());
    }

    @Override
    public Iterable<Ticket> getTickets() throws BasketException {
        return ticketRepository.findAll();
    }

    @Override
    public Iterable<Event> getEvents() throws BasketException {
        return eventRepository.findAll();
    }

    @Override
    public void addEvent(Integer id, String gameName, Double gamePrice, Integer freeSeats) throws BasketException {
        Event newEvent = new Event(id, gameName, gamePrice, freeSeats);
        this.eventRepository.save(newEvent);
        notifyObservers();
    }

    @Override
    public void deleteEvent(Integer id) throws BasketException {
        this.eventRepository.delete(id);
        notifyObservers();
    }

    @Override
    public void updateEvent(Integer id, String gameName, Double gamePrice, Integer freeSeats) throws BasketException {
        Event newEvent = new Event(id, gameName, gamePrice, freeSeats);
        this.eventRepository.update(id, newEvent);
        notifyObservers();
    }

    @Override
    public void addTicket(Integer idGame, Integer reservedSeats, String clientName) throws BasketException {
        Event newEvent = eventRepository.findOne(idGame);
        Ticket ticket = new Ticket(idGame, reservedSeats, clientName, newEvent.getGamePrice() * reservedSeats);

        if (newEvent.getFreeSeats() - ticket.getReservedSeats() >= 0) {
            newEvent.setFreeSeats(newEvent.getFreeSeats() - ticket.getReservedSeats());
            updateEvent(newEvent.getId(), newEvent.getGameName(), newEvent.getGamePrice(), newEvent.getFreeSeats());

            ticketRepository.save(ticket);
        }
    }

    @Override
    public void deleteTicket(Integer id) throws BasketException {
        Ticket ticket = ticketRepository.findOne(id);
        Event newEvent = eventRepository.findOne(ticket.getId_game());
        newEvent.setFreeSeats(newEvent.getFreeSeats() + ticket.getReservedSeats());
        ticketRepository.delete(id);
        updateEvent(newEvent.getId(), newEvent.getGameName(), newEvent.getGamePrice(), newEvent.getFreeSeats());
    }

    @Override
    public Ticket getTicket(Integer id) throws BasketException {
        return ticketRepository.findOne(id);
    }

    @Override
    public Event getEvent(Integer id) throws BasketException {
        return eventRepository.findOne(id);
    }

    private synchronized void notifyObservers() {
        for (IObserver Client : loggedPers.values()) {
            if (Client != null) {
                System.out.println("Notifying [" + Client.toString());
                try {
                    Client.update((List<Event>) getEvents());
                } catch (BasketException | RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
