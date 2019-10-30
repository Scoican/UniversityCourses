namespace TicketManagerCSharp.domain
{
    public interface HasId<T>
    {
        T Id { get; set; }
    }
}