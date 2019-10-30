using Networking.DTOS;
using System;

namespace Networking.Requests
{
    [Serializable]
    public class AddTicketRequest : Request
    {
        private TicketDTO ticketDTO;

        public AddTicketRequest(TicketDTO ticketDTO)
        {
            this.ticketDTO = ticketDTO;
        }

        public TicketDTO TicketDTO { get => ticketDTO; set => ticketDTO = value; }
    }
}