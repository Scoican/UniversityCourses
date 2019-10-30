namespace LibraryApplication.Domain
{
    public class CopyDTO : IHasId<int>
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Author { get; set; }
        public int Year { get; set; }

        public CopyDTO(int id, string name, string author, int year)
        {
            Id = id;
            Name = name;
            Author = author;
            Year = year;
        }
    }
}