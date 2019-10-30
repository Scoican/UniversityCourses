using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace LibraryApplication.Domain
{
    [System.Serializable]
    [Table("Users")]
    public class User : IHasId<string>
    {
        public User()
        {
        }

        public User(string id, string password, string userType, string cnp, string adress, string phone)
        {
            Id = id;
            Password = password;
            UserType = userType;
            Cnp = cnp;
            Adress = adress;
            Phone = phone;
        }

        [Key]
        public string Id { get; set; }

        public string Password { get; set; }
        public string UserType { get; set; }
        public string Cnp { get; set; }
        public string Adress { get; set; }
        public string Phone { get; set; }

        public override bool Equals(object obj)
        {
            return obj is User user &&
                   Id == user.Id &&
                   Password == user.Password;
        }

        public override string ToString()
        {
            return "Username: " + this.Id + "\nPassword: " + this.Password + "\nType: " + this.UserType;
        }

        public override int GetHashCode()
        {
            var hashCode = 324382885;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Id);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Password);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(UserType);
            return hashCode;
        }
    }
}