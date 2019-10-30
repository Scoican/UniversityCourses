using LibraryApplication.Domain;
using System.Collections.Generic;
using System.Data.Entity.Migrations;
using System.Linq;

namespace LibraryApplication
{
    public class BookRepository : IRepository<Book, int>
    {
        private readonly DatabaseContext DataContext;

        public BookRepository(DatabaseContext db)
        {
            this.DataContext = db;
        }

        public void Delete(int id)
        {
            Book book = DataContext.Books.Find(id);
            DataContext.Books.Remove(book);
            DataContext.SaveChanges();
        }

        public Book Find(int id)
        {
            return DataContext.Books.Find(id);
        }

        public IEnumerable<Book> GetAll()
        {
            var query = from book in DataContext.Books select book;
            return query.AsEnumerable();
        }

        public void Save(Book entity)
        {
            DataContext.Books.Add(entity);
            DataContext.SaveChanges();
        }

        public void Update(Book entity)
        {
            DataContext.Books.AddOrUpdate(entity);
            DataContext.SaveChanges();
        }
    }
}