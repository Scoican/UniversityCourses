using System.Collections.Generic;

namespace TicketManagerCSharp.domain
{
    public class User : HasId<string>
    {
        private string username;
        private string password;

        public User(string username, string password)
        {
            this.username = username;
            this.password = password;
        }

        public string Id
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
                   username == user.username &&
                   password == user.password;
        }

        public override int GetHashCode()
        {
            var hashCode = -1343893607;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(username);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(password);
            return hashCode;
        }

        public override string ToString()
        {
            return "User{" +
                            "username='" + username + '\'' +
                            ", password='" + password + '\'' +
                            '}';
        }
    }
}