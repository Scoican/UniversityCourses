using Networking.DTOS;
using System;

namespace Networking.Requests
{
    [Serializable]
    public class LoginRequest : Request
    {
        private UserDTO user;

        public LoginRequest(UserDTO user)
        {
            this.user = user;
        }

        public UserDTO User { get => user; set => user = value; }
    }
}