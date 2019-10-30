using System;

namespace Networking.DTOS
{
    [Serializable]
    public class UserDTO
    {
        private string username;
        private string password;

        public UserDTO(string username, string password)
        {
            this.username = username;
            this.password = password;
        }

        public string Username { get => username; set => username = value; }
        public string Password { get => password; set => password = value; }
    }
}