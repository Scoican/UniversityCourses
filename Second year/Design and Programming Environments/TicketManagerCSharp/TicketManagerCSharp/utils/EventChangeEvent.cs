using TicketManagerCSharp.domain;

namespace TicketManagerCSharp.utils
{
    public enum ChangeEventType
    {
        ADD, UPDATE, DELETE
    }

    public class EventChangeEvent : IEvent
    {
        private ChangeEventType Type { get; set; }
        private Event NewData { get; set; }
        private Event OldData { get; set; }

        public EventChangeEvent(ChangeEventType type, Event newData, Event oldData)
        {
            Type = type;
            NewData = newData;
            OldData = oldData;
        }

        public EventChangeEvent(ChangeEventType type, Event newData)
        {
            Type = type;
            NewData = newData;
        }
    }
}