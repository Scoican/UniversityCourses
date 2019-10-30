using System.Collections.Generic;
using TicketManagerCSharp.domain;

namespace Services
{
    public interface IServer
    {
        void login(User user, IObserver client);

        void logout(User user);

        IList<Ticket> getTickets();

        IList<Event> getEvents();

        void addEvent(int id, string game_name, double game_price, int free_seats);

        void deleteEvent(int id);

        void updateEvent(int id, string game_name, double game_price, int free_seats);

        void addTicket(int id_game, int reserved_seats, string client_name);

        void deleteTicket(int id);
    }
}