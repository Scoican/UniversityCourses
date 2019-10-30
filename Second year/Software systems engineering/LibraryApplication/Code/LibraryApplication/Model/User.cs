using System.Collections.Generic;

namespace Model
{
    public enum UserType
    {
        SUBSCRIBER, ADMIN
    }

    [System.Serializable]
    public class User : IHasId<string>
    {
        private string username;
        private string password;
        private UserType userType;

        public User(string username, string password, UserType userType)
        {
            this.username = username;
            this.password = password;
            this.userType = userType;
        }

        public string Id
        {
            get => this.username;
            set => this.username = value;
        }

        public string Password
        {
            get => password;
            set => password = value;
        }

        public UserType UserType
        {
            get => userType;
            set => userType = value;
        }

        public override bool Equals(object obj)
        {
            return obj is User user &&
                   username == user.username &&
                   password == user.password;
        }

        public override string ToString()
        {
            return "Username: " + this.username + "\nPassword: " + this.password + "\nType: " + this.userType;
        }

        public override int GetHashCode()
        {
            var hashCode = 324382885;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(username);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(password);
            hashCode = hashCode * -1521134295 + userType.GetHashCode();
            return hashCode;
        }
    }
}