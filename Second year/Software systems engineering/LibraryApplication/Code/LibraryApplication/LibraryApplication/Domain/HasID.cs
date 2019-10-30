namespace LibraryApplication.Domain
{
    public interface IHasId<T>
    {
        T Id { get; set; }
    }
}