using System.Collections.Generic;
using TicketManagerCSharp.domain;

namespace Services
{
    public interface IObserver
    {
        void update(List<Event> events);
    }
}