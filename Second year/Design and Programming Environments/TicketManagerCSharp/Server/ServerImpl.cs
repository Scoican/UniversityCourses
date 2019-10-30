using Persistence;
using Services;
using Services.Exceptions;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TicketManagerCSharp.domain;


namespace Server
{
    internal class ServerImpl : IServer
    {
        private UserRepository repositoryUser;
        private TicketRepository repositoryTicket;
        private EventRepository repositoryEvent;
        private readonly IDictionary<String, IObserver> loggedClients;

        public ServerImpl(UserRepository repositoryUser, TicketRepository repositoryTicket, EventRepository repositoryEvent)
        {
            this.repositoryUser = repositoryUser;
            this.repositoryTicket = repositoryTicket;
            this.repositoryEvent = repositoryEvent;
            loggedClients = new Dictionary<String, IObserver>();
        }

        public void addEvent(int id, string game_name, double game_price, int free_seats)
        {
            Event ev = new Event(id, game_name, game_price, free_seats);
            this.repositoryEvent.Save(ev);
            notify();
        }

        public void addTicket(int id_game, int reserved_seats, string client_name)
        {
            Event ev = repositoryEvent.Find(id_game);
            Ticket ticket = new Ticket(1, reserved_seats, reserved_seats * ev.GamePrice, client_name);
            if (ev.FreeSeats - ticket.ReservedSeats >= 0)
            {
                ev.FreeSeats = ev.FreeSeats - ticket.ReservedSeats;
                this.repositoryTicket.Save(ticket);
                updateEvent(ev.Id, ev.GameName, ev.GamePrice, ev.FreeSeats);
            }
        }

        public void deleteEvent(int id)
        {
            repositoryEvent.Delete(id);
            notify();
        }

        public void deleteTicket(int id)
        {
            Ticket ticket = repositoryTicket.Find(id);
            Event ev = repositoryEvent.Find(ticket.IdGame);
            ev.FreeSeats = ev.FreeSeats + ticket.ReservedSeats;
            repositoryTicket.Delete(id);
            updateEvent(ev.Id, ev.GameName, ev.GamePrice, ev.FreeSeats);
        }

        public IList<Event> getEvents()
        {
            return repositoryEvent.getALl();
        }

        public IList<Ticket> getTickets()
        {
            return repositoryTicket.getALl();
        }

        public void login(User user, IObserver client)
        {
            User currentUser = repositoryUser.FindOne(user.Id);
            if (currentUser.Id != null)
            {
                if (loggedClients.ContainsKey(currentUser.Id))
                    throw new BasketException("User already logged in");
                loggedClients[currentUser.Id] = client;
            }
            else
            {
                throw new BasketException("Authentication error");
            }
        }

        public void logout(User user)
        {
            IObserver localClient = loggedClients[user.Id];
            if (localClient == null)
                throw new BasketException("User " + user.Id + " is not logged in.");
            loggedClients.Remove(user.Id);
        }

        public void updateEvent(int id, string game_name, double game_price, int free_seats)
        {
            Event ev = new Event(id, game_name, game_price, free_seats);
            repositoryEvent.Update(id, ev);
            notify();
        }

        private void notify()
        {
            foreach (var obs in this.loggedClients.Values)
            {
                //Console.WriteLine("Impl Notified " + username);
                //this.loggedClients[username].update((List<Meci>)getMeciuri());
                Task.Run(() => obs.update((List<Event>)getEvents()));
            }
        }
    }
}