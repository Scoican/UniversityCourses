using System.Collections.Generic;
using System.Linq;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.repository;
using TicketManagerCSharp.utils;

namespace TicketManagerCSharp.service
{
    public class EventService
    {
        private EventRepository eventRepository;
        private List<utils.IObserver<EventChangeEvent>> Observers;

        public EventService(EventRepository eventRepository)
        {
            this.eventRepository = eventRepository;
            Observers = new List<utils.IObserver<EventChangeEvent>>();
        }

        public void AddObserver(utils.IObserver<EventChangeEvent> e)
        {
            Observers.Add(e);
        }

        public void NotifyObservers(EventChangeEvent t)
        {
            Observers.ForEach(x => x.Update(t));
        }

        public void RemoveObserver(utils.IObserver<EventChangeEvent> e)
        {
            Observers.Remove(e);
        }

        public void UpdateMeci(int id, string game_name, double game_price, int free_seats)
        {
            Event ev = new Event(id, game_name, game_price, free_seats);
            eventRepository.Update(id, ev);
            NotifyObservers(new EventChangeEvent(ChangeEventType.UPDATE, ev));
        }

        public Event FindOne(int id)
        {
            return eventRepository.FindOne(id);
        }

        public IList<Event> FindAll()
        {
            return eventRepository.FindAll().ToList();
        }
    }
}