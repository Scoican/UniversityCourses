using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace LibraryApplication.Domain
{
    [System.Serializable]
    [Table("Books")]
    public class Book : IHasId<int>
    {
        [Key]
        public int Id { get; set; }

        public string Name { get; set; }
        public string Author { get; set; }
        public int Year { get; set; }
        public int Quantity { get; set; }

        public Book(int id, string name, string author, int year, int quantity)
        {
            this.Id = id;
            this.Name = name;
            this.Author = author;
            this.Year = year;
            this.Quantity = quantity;
        }

        public Book(string name, string author, int year, int quantity)
        {
            this.Name = name;
            this.Author = author;
            this.Year = year;
            this.Quantity = quantity;
        }

        public Book()
        {
        }

        public override bool Equals(object obj)
        {
            return obj is Book book &&
                   Name == book.Name &&
                   Author == book.Author &&
                   Year == book.Year;
        }

        public override int GetHashCode()
        {
            var hashCode = 425686816;
            hashCode = hashCode * -1521134295 + Id.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Name);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Author);
            hashCode = hashCode * -1521134295 + Year.GetHashCode();
            hashCode = hashCode * -1521134295 + Quantity.GetHashCode();
            return hashCode;
        }

        public override string ToString()
        {
            return this.Id.ToString() + " " + this.Name + " " + this.Quantity.ToString();
        }
    }
}