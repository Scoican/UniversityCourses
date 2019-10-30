using System.Collections.Generic;

namespace Model
{
    [System.Serializable]
    public class Rental : IHasId<int>
    {
        private int id;
        private string owner;
        private int book;

        public int Id { get => id; set => id = value; }
        public string Owner { get => owner; set => owner = value; }
        public int Book { get => book; set => book = value; }

        public Rental(int id, string owner, int book)
        {
            this.id = id;
            this.owner = owner;
            this.book = book;
        }

        public Rental(string owner, int book)
        {
            this.owner = owner;
            this.book = book;
        }

        public override string ToString()
        {
            return "Rental id: " + this.id + "\nOwner id: " + this.owner + "\nBook id: " + this.book.ToString();
        }

        public override bool Equals(object obj)
        {
            return obj is Rental rental &&
                   id == rental.id;
        }

        public override int GetHashCode()
        {
            var hashCode = -873282292;
            hashCode = hashCode * -1521134295 + id.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(owner);
            hashCode = hashCode * -1521134295 + book.GetHashCode();
            return hashCode;
        }
    }
}