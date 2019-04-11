using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TicketManagerCSharp.domain
{
    public class Ticket:HasId<int>
    {
        private int id;
        private int idGame;
        private int reservedSeats;
        private double price;
        private string clientName;

        public Ticket(int id, int idGame, int reservedSeats,double price,string clientName)
        {
            this.id = id;
            this.idGame = idGame;
            this.reservedSeats = reservedSeats;
            this.price = price;
            this.clientName = clientName;
        }

        public Ticket(int idGame, int reservedSeats, double price, string clientName)
        {
            this.idGame = idGame;
            this.reservedSeats = reservedSeats;
            this.price = price;
            this.clientName = clientName;
        }

        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        public int IdGame
        {
            get { return idGame; }
            set { idGame = value; }
        }

        public int ReservedSeats
        {
            get { return reservedSeats; }
            set { reservedSeats = value; }
        }

        public string ClientName
        {
            get { return clientName; }
            set { clientName = value; }
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
                   id == ticket.id;
        }


        public override string ToString()
        {
            return "Ticket{" +
                "id=" + id +
                ", event=" + idGame +
                ", reserved seats=" + reservedSeats +
                ", price=" + price +
                ", client name="+clientName +
                '}';
        }

        public override int GetHashCode()
        {
            var hashCode = -756315988;
            hashCode = hashCode * -1521134295 + id.GetHashCode();
            hashCode = hashCode * -1521134295 + idGame.GetHashCode();
            hashCode = hashCode * -1521134295 + reservedSeats.GetHashCode();
            hashCode = hashCode * -1521134295 + price.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(clientName);
            hashCode = hashCode * -1521134295 + Id.GetHashCode();
            hashCode = hashCode * -1521134295 + IdGame.GetHashCode();
            hashCode = hashCode * -1521134295 + ReservedSeats.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(ClientName);
            hashCode = hashCode * -1521134295 + Price.GetHashCode();
            return hashCode;
        }
    }
}
