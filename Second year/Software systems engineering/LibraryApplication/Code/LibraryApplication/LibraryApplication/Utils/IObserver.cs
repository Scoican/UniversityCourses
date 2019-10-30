namespace LibraryApplication.Utils
{
    public interface IObserver<E> where E : IEvent
    {
        void Update(E e);
    }
}