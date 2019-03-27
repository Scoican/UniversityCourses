using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TicketManagerCSharp.domain
{
    class User:HasId<int>
    {
        private int id;
        private string username;
        private string password;

        public User(int id, string username, string password)
        {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        public User(string username, string password)
        {
            this.username = username;
            this.password = password;
        }

        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        public string Username
        {
            get { return username; }
            set { username = value; }
        }

        public string Password
        {
            get { return password; }
            set { password = value; }
        }

        public override bool Equals(object obj)
        {
            var user = obj as User;
            return user != null &&
                   id == user.id &&
                   username == user.username &&
                   password == user.password;
        }

        public override int GetHashCode()
        {
            var hashCode = -1343893607;
            hashCode = hashCode * -1521134295 + id.GetHashCode();
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(username);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(password);
            return hashCode;
        }

        public override string ToString()
        {
            return "User{" +
                            "id=" + id +
                            ", username='" + username + '\'' +
                            ", password='" + password + '\'' +
                            '}';
        }
    }
}
