using Services;
using System;
using System.Collections.Generic;
using TicketManagerCSharp.domain;

namespace Client
{
    public class ClientCtrl : IClient, IObserver
    {
        public event EventHandler<UserEventArgs> updateEventHandler;

        private readonly IServer server;
        private User currentUser;
        private TicketInterfaceForm parentForm;

        public ClientCtrl(IServer server)
        {
            this.server = server;
            currentUser = null;
        }

        public void setForm(TicketInterfaceForm parentForm)
        {
            this.parentForm = parentForm;
        }

        public void login(string username, string pass)
        {
            User user = new User(username, pass);
            server.login(user, this);
            Console.WriteLine("Login succeeded ....");
            this.currentUser = user;
        }

        public void logout()
        {
            Console.WriteLine("Ctrl logout");
            server.logout(currentUser);
            currentUser = null;
        }

        public IList<Event> getEvents()
        {
            return server.getEvents();
        }

        public IList<Ticket> getTickets()
        {
            return server.getTickets();
        }

        public void addTicket(int id_game, int reserved_seats, string client_name)
        {
            server.addTicket(id_game, reserved_seats, client_name);
        }

        public void addEvent(int id, string game_name, double game_price, int free_seats)
        {
            server.addEvent(id, game_name, game_price, free_seats);
        }

        public void deleteTicket(int id)
        {
            server.deleteTicket(id);
        }

        public void updateEvent(int id, string game_name, double game_price, int free_seats)
        {
            server.updateEvent(id, game_name, game_price, free_seats);
        }

        public void deleteEvent(int id)
        {
            server.deleteEvent(id);
        }

        protected virtual void OnUserEvent(UserEventArgs e)
        {
            if (updateEventHandler == null) return;
            updateEventHandler(this, e);
            Console.WriteLine("Update Event called");
        }

        public void update(List<Event> events)
        {
            parentForm.userUpdate(events);
        }
    }
}