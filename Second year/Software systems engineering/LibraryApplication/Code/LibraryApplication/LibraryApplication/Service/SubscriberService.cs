using LibraryApplication.Domain;
using LibraryApplication.Repository;
using LibraryApplication.Utils;
using LibraryApplication.Validator;

namespace LibraryApplication.Service
{
    public class SubscriberService : UserService
    {
        public SubscriberService(UserRepository userRepository, BookRepository bookRepository, CopyRepository copyRepository, RentalRepository rentalRepository) : base(userRepository, bookRepository, copyRepository, rentalRepository)
        {
        }

        public void BorrowBook(string user, int bookCopyId)
        {
            Copy copy = copyRepository.Find(bookCopyId);
            if (copy == null)
            {
                throw new ValidationException("The selected book doesnt exist! \n");
            }

            Book book = bookRepository.Find(copy.BookId);
            book.Quantity--;
            bookRepository.Update(book);

            Rental rental = new Rental(user, bookCopyId);
            rentalRepository.Save(rental);

            copy.State = "BORROWED";
            copyRepository.Update(copy);

            NotifyObservers(new RentalChangeEvent(RentalChangeEventType.BORROW, rental));
            NotifyObservers(new BookChangeEvent(BookChangeEventType.UPDATE, book));
        }
    }
}