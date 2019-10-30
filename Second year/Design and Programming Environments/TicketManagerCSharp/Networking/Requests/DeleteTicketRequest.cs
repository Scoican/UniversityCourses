using System;

namespace Networking.Requests
{
    [Serializable]
    internal class DeleteTicketRequest : Request
    {
        private int id;

        public DeleteTicketRequest(int id)
        {
            this.id = id;
        }

        public int Id { get => id; set => id = value; }
    }
}