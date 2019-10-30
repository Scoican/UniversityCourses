namespace TicketManagerCSharp.utils
{
    public interface IObserver<E> where E : IEvent
    {
        void Update(E e);
    }
}