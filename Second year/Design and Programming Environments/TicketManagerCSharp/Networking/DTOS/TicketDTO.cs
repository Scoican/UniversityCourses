using System;

namespace Networking.DTOS
{
    [Serializable]
    public class TicketDTO
    {
        private int id;
        private int idGame;
        private int reservedSeats;
        private double price;
        private string clientName;

        public TicketDTO(int id, int idGame, int reservedSeats, double price, string clientName)
        {
            this.id = id;
            this.idGame = idGame;
            this.reservedSeats = reservedSeats;
            this.price = price;
            this.clientName = clientName;
        }

        public int Id { get => id; set => id = value; }
        public int IdGame { get => idGame; set => idGame = value; }
        public int ReservedSeats { get => reservedSeats; set => reservedSeats = value; }
        public double Price { get => price; set => price = value; }
        public string ClientName { get => clientName; set => clientName = value; }
    }
}