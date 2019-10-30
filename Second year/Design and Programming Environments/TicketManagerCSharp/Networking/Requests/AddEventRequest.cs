using Networking.DTOS;
using System;

namespace Networking.Requests
{
    [Serializable]
    public class AddEventRequest : Request
    {
        private EventDTO eventDTO;

        public AddEventRequest(EventDTO eventDTO)
        {
            this.eventDTO = eventDTO;
        }

        public EventDTO EventDTO { get => eventDTO; set => eventDTO = value; }
    }
}