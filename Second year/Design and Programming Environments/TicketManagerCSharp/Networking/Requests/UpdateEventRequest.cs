using Networking.DTOS;
using System;

namespace Networking.Requests
{
    [Serializable]
    internal class UpdateEventRequest : Request
    {
        private EventDTO eventDTO;

        public UpdateEventRequest(EventDTO eventDTO)
        {
            this.eventDTO = eventDTO;
        }

        public EventDTO EventDTO { get => eventDTO; set => eventDTO = value; }
    }
}