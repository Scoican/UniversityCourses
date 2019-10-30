using LibraryApplication.Domain;
using LibraryApplication.Repository;
using LibraryApplication.Utils;
using LibraryApplication.Validator;
using System.Collections.Generic;

namespace LibraryApplication.Service
{
    public class UserService : IObservable<IEvent>
    {
        public readonly UserRepository userRepository;
        public readonly BookRepository bookRepository;
        public readonly RentalRepository rentalRepository;
        public readonly CopyRepository copyRepository;
        public List<IObserver<IEvent>> Observers;

        public UserService(UserRepository userRepository, BookRepository bookRepository, CopyRepository copyRepository, RentalRepository rentalRepository)
        {
            this.userRepository = userRepository;
            this.bookRepository = bookRepository;
            this.copyRepository = copyRepository;
            this.rentalRepository = rentalRepository;
            Observers = new List<IObserver<IEvent>>();
        }

        public User LogIn(string username, string password)
        {
            User user = null;
            foreach (User u in userRepository.GetAll())
            {
                if (u.Id == username && u.Password == password)
                {
                    user = u;
                }
            }
            if (user == null)
                throw new ValidationException("Username or password incorrect! \n");
            return user;
        }

        public void AddObserver(IObserver<IEvent> e)
        {
            Observers.Add(e);
        }

        public void NotifyObservers(IEvent t)
        {
            Observers.ForEach(x => x.Update(t));
        }

        public void RemoveObserver(IObserver<IEvent> e)
        {
            Observers.Remove(e);
        }
    }
}