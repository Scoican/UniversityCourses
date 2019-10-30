using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace LibraryApplication.Domain
{
    [System.Serializable]
    [Table("Copies")]
    public class Copy : IHasId<int>
    {
        [Key]
        public int Id { get; set; }

        public int BookId { get; set; }
        public string State { get; set; }

        public Copy(int id, int bookId, string state)
        {
            Id = id;
            BookId = bookId;
            State = state;
        }

        public Copy(int bookId, string state)
        {
            BookId = bookId;
            State = state;
        }

        public Copy()
        {
        }
    }
}