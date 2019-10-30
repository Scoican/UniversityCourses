using System.Collections.Generic;

namespace Model
{
    [System.Serializable]
    public class Book : IHasId<int>
    {
        private int id;
        private string name;
        private string author;
        private int year;
        private int quantity;

        public int Id { get => id; set => id = value; }
        public string Name { get => name; set => name = value; }
        public string Author { get => author; set => author = value; }
        public int Year { get => year; set => year = value; }
        public int Quantity { get => quantity; set => quantity = value; }

        public Book(int id, string name, string author, int year, int quantity)
        {
            this.id = id;
            this.name = name;
            this.author = author;
            this.year = year;
            this.quantity = quantity;
        }

        public Book(string name, string author, int year, int quantity)
        {
            this.name = name;
            this.author = author;
            this.year = year;
            this.quantity = quantity;
        }

        public override bool Equals(object obj)
        {
            return obj is Book book &&
                   id == book.id &&
                   name == book.name &&
                   author == book.author &&
                   year == book.year &&
                   quantity == book.quantity;
        }

        public override int GetHashCode()
        {
            var hashCode = 425686816;
            hashCode = hashCode * -1521134295 + id.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(name);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(author);
            hashCode = hashCode * -1521134295 + year.GetHashCode();
            hashCode = hashCode * -1521134295 + quantity.GetHashCode();
            return hashCode;
        }

        public override string ToString()
        {
            return this.id.ToString() + " " + this.name + " " + this.quantity.ToString();
        }
    }
}