using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TicketManagerCSharp.domain
{
    class Ticket:HasId<int>
    {
        private int id;
        private int idEvent;
        private string buyer;
        private double price;

        public Ticket(int id, int idEvent, string buyer, double price)
        {
            this.id = id;
            this.idEvent = idEvent;
            this.buyer = buyer;
            this.price = price;
        }

        public Ticket(int idEvent, string buyer, double price)
        {
            this.idEvent = idEvent;
            this.buyer = buyer;
            this.price = price;
        }

        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        public int IdEvent
        {
            get { return idEvent; }
            set { idEvent = value; }
        }

        public string Buyer
        {
            get { return buyer; }
            set { buyer = value; }
        }

        public double Price
        {
            get { return price; }
            set { price = value; }
        }

        public override bool Equals(object obj)
        {
            var ticket = obj as Ticket;
            return ticket != null &&
                   id == ticket.id &&
                   idEvent == ticket.idEvent &&
                   buyer == ticket.buyer &&
                   price == ticket.price;
        }

        public override int GetHashCode()
        {
            var hashCode = 791292794;
            hashCode = hashCode * -1521134295 + id.GetHashCode();
            hashCode = hashCode * -1521134295 + idEvent.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(buyer);
            hashCode = hashCode * -1521134295 + price.GetHashCode();
            return hashCode;
        }

        public override string ToString()
        {
            return "Ticket{" +
                "id=" + id +
                ", event=" + idEvent +
                ", buyer='" + buyer + '\'' +
                ", price=" + price +
                '}';
        }
    }
}
