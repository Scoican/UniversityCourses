using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace LibraryApplication.Domain
{
    [System.Serializable]
    [Table("Rentals")]
    public class Rental : IHasId<int>
    {
        [Key]
        public int Id { get; set; }

        public string Owner { get; set; }
        public int Book { get; set; }

        public Rental(int id, string owner, int book)
        {
            this.Id = id;
            this.Owner = owner;
            this.Book = book;
        }

        public Rental(string owner, int book)
        {
            this.Owner = owner;
            this.Book = book;
        }

        public Rental()
        {
        }

        public override string ToString()
        {
            return "Rental id: " + this.Id + "\nOwner id: " + this.Owner + "\nBook id: " + this.Book.ToString();
        }

        public override bool Equals(object obj)
        {
            return obj is Rental rental &&
                   Id == rental.Id;
        }

        public override int GetHashCode()
        {
            var hashCode = -873282292;
            hashCode = hashCode * -1521134295 + Id.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Owner);
            hashCode = hashCode * -1521134295 + Book.GetHashCode();
            return hashCode;
        }
    }
}