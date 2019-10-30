namespace TicketManagerCSharp.utils
{
    public interface IObservable<E> where E : IEvent
    {
        void AddObserver(IObserver<E> e);

        void RemoveObserver(IObserver<E> e);

        void NotifyObservers(E t);
    }
}