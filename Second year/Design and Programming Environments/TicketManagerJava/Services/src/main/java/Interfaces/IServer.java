package Interfaces;
import Exceptions.BasketException;
import ModelDomain.Event;
import ModelDomain.Ticket;
import ModelDomain.User;


public interface IServer {
    void login(User person, IObserver client) throws BasketException;

    void logout(User person) throws BasketException;

    Iterable<Ticket> getTickets() throws BasketException;

    Iterable<Event> getEvents() throws BasketException;

    void addEvent(Integer id,String gameName,Double gamePrice,Integer freeSeats) throws BasketException;

    void deleteEvent(Integer id) throws BasketException;

    void updateEvent(Integer id,String gameName,Double gamePrice,Integer freeSeats) throws BasketException;

    void addTicket(Integer idGame,Integer reservedSeats,String clientName) throws BasketException;

    void deleteTicket(Integer id) throws BasketException;

    Ticket getTicket(Integer id) throws BasketException;

    Event getEvent(Integer id) throws BasketException;
}
