using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TicketManagerCSharp.domain
{
    class EventDTO
    {
        public int Id { get; set; }
        public string GameName { get; set; }
        public int FreeSeats { get; set; }
        public double Price { get; set; }
        public GameState GameState { get; set; }

        public EventDTO(int id, string gameName, int freeSeats, double price)
        {
            Id = id;
            GameName = gameName;
            FreeSeats = freeSeats;
            Price = price;
            if (FreeSeats > 0)
            {
                GameState = GameState.AVAILABLE;
            }
            else
            {
                GameState = GameState.SOLD_OUT;
            }
        }

        public EventDTO(string gameName, int freeSeats, double price)
        {
            GameName = gameName;
            FreeSeats = freeSeats;
            Price = price;
            if (FreeSeats > 0)
            {
                GameState = GameState.AVAILABLE;
            }
            else
            {
                GameState = GameState.SOLD_OUT;
            }
        }
    }
}
