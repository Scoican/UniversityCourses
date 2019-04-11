using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.repository;

namespace TicketManagerCSharp.service
{
    public class TicketService
    {
        private TicketRepository ticketRepository;
        private EventService eventService;

        public TicketService(TicketRepository ticketRepository, EventService eventService)
        {
            this.ticketRepository = ticketRepository;
            this.eventService = eventService;
        }

        public void SaveTicket(int id_game,int reserved_seats, double price, string client_name)
        {
            Ticket ticket = new Ticket(id_game, reserved_seats, price, client_name);
            Event ev = eventService.FindOne(id_game);
            if (ev.GameState == GameState.SOLD_OUT)
                throw new ValidationException("Event is sold out");
            else
            {
                eventService.UpdateMeci(id_game,ev.GameName,ev.GamePrice,ev.FreeSeats - reserved_seats);
                ticketRepository.Save(ticket);
            }
        }
    }
}
