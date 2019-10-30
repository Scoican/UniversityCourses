using LibraryApplication.Domain;
using System;

namespace LibraryApplication.Validator
{
    public class DataValidator
    {
        public static void ValidateUser(User user)
        {
            string errors = "";
            if (user.Id == "") { errors += "Username field must not be empty! \n"; }
            if (user.Password == "") { errors += "Password field must not be empty! \n"; }
            if (user.UserType != "ADMIN" || user.UserType != "SUBSCRIBER") { errors += "User type is wrong type! \n"; }
            if (user.Cnp != "") { errors += "User CNP must not be empty! \n"; }
            if (user.Adress != "") { errors += "User Adress must not be empty! \n"; }
            if (user.Phone != "") { errors += "User Phone number must not be empty! \n"; }
            if (errors != "") { throw new ValidationException(errors); }
        }

        public static void ValidateBook(Book book)
        {
            string errors = "";
            if (book.Name == "") { errors += "Name field must not be emtpy! \n"; }
            if (book.Author == "") { errors += "Author field must not be emtpy! \n"; }
            if (book.Year > DateTime.Now.Year) { errors += "Year field must be valid! \n"; }
            if (book.Quantity < 0) { errors += "Quantity field must be valid! \n"; }
            if (errors != "") { throw new ValidationException(errors); }
        }
    }
}