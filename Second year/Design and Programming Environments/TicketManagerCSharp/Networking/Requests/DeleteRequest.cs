using System;

namespace Networking.Requests
{
    [Serializable]
    internal class DeleteRequest : Request
    {
        private int id;

        public DeleteRequest(int id)
        {
            this.id = id;
        }

        public int Id { get => id; set => id = value; }
    }
}