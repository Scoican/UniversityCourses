using System;
using TicketManagerCSharp.domain;

namespace Networking.DTOS
{
    [Serializable]
    public class EventDTO
    {
        private int id;
        private string gameName;
        private double gamePrice;
        private int freeSeats;
        private GameState gameState;

        public EventDTO(int id, string gameName, double gamePrice, int freeSeats)
        {
            this.id = id;
            this.gameName = gameName;
            this.gamePrice = gamePrice;
            this.freeSeats = freeSeats;
            if (this.freeSeats > 0)
            {
                gameState = GameState.AVAILABLE;
            }
            else
            {
                gameState = GameState.SOLD_OUT;
            }
        }

        public int Id { get => id; set => id = value; }
        public string GameName { get => gameName; set => gameName = value; }
        public double GamePrice { get => gamePrice; set => gamePrice = value; }

        public int FreeSeats
        {
            get
            {
                return freeSeats;
            }
            set
            {
                this.freeSeats = value;
                if (this.freeSeats > 0)
                {
                    this.gameState = GameState.AVAILABLE;
                }
                else
                {
                    this.gameState = GameState.SOLD_OUT;
                }
            }
        }

        public GameState GameState { get => gameState; set => gameState = value; }
    }
}