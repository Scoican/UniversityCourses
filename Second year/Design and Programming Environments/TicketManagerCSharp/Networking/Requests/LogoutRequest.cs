using Networking.DTOS;
using System;

namespace Networking.Requests
{
    [Serializable]
    public class LogoutRequest : Request
    {
        private UserDTO user;

        public LogoutRequest(UserDTO user)
        {
            this.user = user;
        }

        public UserDTO User { get => user; set => user = value; }
    }
}