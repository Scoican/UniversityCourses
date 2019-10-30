using Networking.DTOS;
using System;
using System.Collections.Generic;

namespace Networking.Responses
{
    [Serializable]
    public class GetAllTicketsResponse : Response
    {
        private IList<TicketDTO> tickets;

        public GetAllTicketsResponse(IList<TicketDTO> tickets)
        {
            this.tickets = tickets;
        }

        public IList<TicketDTO> Tickets { get => tickets; set => tickets = value; }
    }
}