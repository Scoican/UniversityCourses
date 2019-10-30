using LibraryApplication.Domain;
using LibraryApplication.Repository;
using LibraryApplication.Utils;
using LibraryApplication.Validator;
using System.Collections.Generic;

namespace LibraryApplication.Service
{
    public class AdminService : UserService
    {
        public AdminService(UserRepository userRepository, BookRepository bookRepository, CopyRepository copyRepository, RentalRepository rentalRepository) : base(userRepository, bookRepository, copyRepository, rentalRepository)
        {
        }

        public void AddUser(string username, string password, string userType, string cnp, string adress, string phone)
        {
            User newUser = new User(username, password, userType, cnp, adress, phone);
            foreach (User u in userRepository.GetAll())
            {
                if (newUser.Id == u.Id)
                {
                    throw new ValidationException("This username already exist! \n");
                }
            }
            userRepository.Save(newUser);
            NotifyObservers(new UserChangeEvent(UserChangeEventType.ADD, newUser));
        }

        public void DeleteUser(string username)
        {
            User user = userRepository.Find(username);
            if (user == null)
            {
                throw new ValidationException("The selected user doesnt exist! \n");
            }
            userRepository.Delete(user.Id);
            NotifyObservers(new UserChangeEvent(UserChangeEventType.DELETE, user));
        }

        public void UpdateUser(string username, string password, string userType, string cnp, string adress, string phone)
        {
            User user = userRepository.Find(username);
            if (user == null)
            {
                throw new ValidationException("The selected user doesnt exist! \n");
            }
            userRepository.Update(new User(username, password, userType, cnp, adress, phone));
            NotifyObservers(new UserChangeEvent(UserChangeEventType.UPDATE, user));
        }

        public User FindUser(string id)
        {
            User user = userRepository.Find(id);
            if (user == null)
            {
                throw new ValidationException("The selected user doesnt exist! \n");
            }
            else
            {
                return user;
            }
        }

        public IEnumerable<User> GetUsers()
        {
            return userRepository.GetAll();
        }

        public void AddBook(string name, string author, int year, int quantity)
        {
            Book newBook = new Book(name, author, year, quantity);
            DataValidator.ValidateBook(newBook);

            IEnumerable<Book> books = bookRepository.GetAll();
            foreach (Book book in books)
            {
                if (book.Equals(newBook) && newBook.Quantity - book.Quantity > 0)
                {
                    for (int i = 0; i < newBook.Quantity - book.Quantity; i++)
                    {
                        Copy copy = new Copy(book.Id, "AVAILABLE");
                        copyRepository.Save(copy);
                    }
                    book.Quantity = newBook.Quantity;
                    bookRepository.Update(book);
                    return;
                }
            }

            bookRepository.Save(newBook);
            for (int i = 0; i < newBook.Quantity; i++)
            {
                Copy copy = new Copy(newBook.Id, "AVAILABLE");
                copyRepository.Save(copy);
            }
            NotifyObservers(new BookChangeEvent(BookChangeEventType.ADD, newBook));
        }

        public void DeleteBook(int id)
        {
            Book book = bookRepository.Find(id);
            if (book == null)
            {
                throw new ValidationException("The selected book doesnt exist! \n");
            }
            bookRepository.Delete(id);
            NotifyObservers(new BookChangeEvent(BookChangeEventType.DELETE, book));
        }

        public void UpdateBook(int id, string name, string author, int year)
        {
            Book book = bookRepository.Find(id);
            if (book == null)
            {
                throw new ValidationException("The selected book doesnt exist! \n");
            }
            bookRepository.Update(new Book(id, name, author, year, book.Quantity));
            NotifyObservers(new BookChangeEvent(BookChangeEventType.UPDATE, book));
        }

        public Book FindBook(int id)
        {
            Book book = bookRepository.Find(id);
            if (book == null)
            {
                throw new ValidationException("The selected book doesnt exist! \n");
            }
            else
            {
                return book;
            }
        }

        public IEnumerable<Book> GetBooks()
        {
            return bookRepository.GetAll();
        }

        public void ReturnBook(int id)
        {
            Rental rental = rentalRepository.Find(id);
            Copy returnedBook = copyRepository.Find(rental.Book);
            if (returnedBook == null)
            {
                throw new ValidationException("The selected copy doesnt exist! \n");
            }
            if (rental == null)
            {
                throw new ValidationException("The selected copy doesnt exist! \n");
            }
            returnedBook.State = "AVAILABLE";
            copyRepository.Update(returnedBook);
            Book book = bookRepository.Find(returnedBook.BookId);
            book.Quantity++;
            bookRepository.Update(book);
            rentalRepository.Delete(id);
            NotifyObservers(new RentalChangeEvent(RentalChangeEventType.RETURN, rental));
            NotifyObservers(new BookChangeEvent(BookChangeEventType.UPDATE, book));
        }

        public IEnumerable<Rental> GetRentals()
        {
            return rentalRepository.GetAll();
        }

        public IEnumerable<Copy> GetCopies()
        {
            return copyRepository.GetAll();
        }

        public Copy FindCopy(int id)
        {
            Copy book = copyRepository.Find(id);
            if (book == null)
            {
                throw new ValidationException("The selected copy doesnt exist! \n");
            }
            else
            {
                return book;
            }
        }
    }
}