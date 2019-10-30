using LibraryApplication.Domain;

namespace LibraryApplication.Utils
{
    public enum BookChangeEventType
    {
        ADD, UPDATE, DELETE
    }

    public class BookChangeEvent : IEvent
    {
        private BookChangeEventType Type { get; set; }
        private Book NewData { get; set; }
        private Book OldData { get; set; }

        public BookChangeEvent(BookChangeEventType type, Book newData, Book oldData)
        {
            Type = type;
            NewData = newData;
            OldData = oldData;
        }

        public BookChangeEvent(BookChangeEventType type, Book newData)
        {
            Type = type;
            NewData = newData;
        }
    }
}