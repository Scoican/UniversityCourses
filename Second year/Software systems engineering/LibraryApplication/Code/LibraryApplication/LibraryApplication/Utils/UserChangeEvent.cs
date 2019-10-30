using LibraryApplication.Domain;

namespace LibraryApplication.Utils
{
    public enum UserChangeEventType
    {
        ADD, UPDATE, DELETE
    }

    public class UserChangeEvent : IEvent
    {
        private UserChangeEventType Type { get; set; }
        private User NewData { get; set; }
        private User OldData { get; set; }

        public UserChangeEvent(UserChangeEventType type, User newData, User oldData)
        {
            Type = type;
            NewData = newData;
            OldData = oldData;
        }

        public UserChangeEvent(UserChangeEventType type, User newData)
        {
            Type = type;
            NewData = newData;
        }
    }
}