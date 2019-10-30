using Networking.DTOS;
using System;
using System.Collections.Generic;

namespace Networking.Responses
{
    [Serializable]
    public class NotifyResponse : UpdateResponse
    {
        private IList<EventDTO> events;

        public NotifyResponse(IList<EventDTO> events)
        {
            this.events = events;
        }

        public IList<EventDTO> Events { get => events; set => events = value; }
    }
}