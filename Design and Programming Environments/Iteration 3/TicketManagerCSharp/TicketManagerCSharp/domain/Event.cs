using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace TicketManagerCSharp.domain
{
    class Event:HasId<int>
    {
        private int id;
        private string name;
        private int seats;


        public Event(int id, string name, int seats)
        {
            this.id = id;
            this.name = name;
            this.seats = seats;
        }

        public Event(string name, int seats)
        {
            this.name = name;
            this.seats = seats;
        }

        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        public int Seats
        {
            get { return seats; }
            set { seats = value; }
        }

        public override bool Equals(object obj)
        {
            var @event = obj as Event;
            return @event != null &&
                   id == @event.Id &&
                   name == @event.Name &&
                   seats == @event.Seats;
        }

        public override int GetHashCode()
        {
            var hashCode = 2031959382;
            hashCode = hashCode * -1521134295 + id.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(name);
            hashCode = hashCode * -1521134295 + seats.GetHashCode();
            return hashCode;
        }

        public override string ToString()
        {
            return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", seats=" + seats +
                '}';
        }
    }
}
