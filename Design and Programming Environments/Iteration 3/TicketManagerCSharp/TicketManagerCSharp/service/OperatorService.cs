using System;
using System.Collections.Generic;
using log4net;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.repository;

namespace TicketManagerCSharp.service
{
    class OperatorService
    {
        private TicketRepository ticketRepository;
        private EventRepository eventRepository;
        private static readonly ILog log = LogManager.GetLogger("ServiceAdmin");

        public OperatorService(IDictionary<String, string> props)
        {
            log.Info("Creating OperatorService");
            this.ticketRepository = new TicketRepository(props);
            this.eventRepository = new EventRepository(props);
            
        }

        public void BuyTicket(string buyerName, int seats, int eventId)
        {
            try
            {
                Event game = eventRepository.FindOne(eventId);
                if (game.Seats== 0)
                    throw new RepositoryException("SOLD OUT!");
                if (game.Seats - seats < 0)
                {
                    throw new RepositoryException("Not enough tickets!");
                }
                game.Seats -=seats;
                eventRepository.Update(eventId,game);
            }
            catch (Exception ex)
            {
                throw new RepositoryException(ex.Message);
            }
        }
    }
}