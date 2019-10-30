using LibraryApplication.Domain;

namespace LibraryApplication.Utils
{
    public enum RentalChangeEventType
    {
        BORROW, RETURN
    }

    public class RentalChangeEvent : IEvent
    {
        private RentalChangeEventType Type { get; set; }
        private Rental NewData { get; set; }
        private Rental OldData { get; set; }

        public RentalChangeEvent(RentalChangeEventType type, Rental newData, Rental oldData)
        {
            Type = type;
            NewData = newData;
            OldData = oldData;
        }

        public RentalChangeEvent(RentalChangeEventType type, Rental newData)
        {
            Type = type;
            NewData = newData;
        }
    }
}