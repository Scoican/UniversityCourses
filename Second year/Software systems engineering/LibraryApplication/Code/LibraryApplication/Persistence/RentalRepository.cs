using Model;
using System.Collections.Generic;
using System.Data.Entity.Migrations;
using System.Linq;

namespace Persistence
{
    public class RentalRepository : IRepository<Rental, int>
    {
        private readonly DatabaseContext DataContext;

        public RentalRepository(DatabaseContext db)
        {
            this.DataContext = db;
        }

        public void Delete(int id)
        {
            Rental rental = DataContext.Rentals.Find(id);
            DataContext.Rentals.Remove(rental);
            DataContext.SaveChanges();
        }

        public Rental Find(int id)
        {
            return DataContext.Rentals.Find(id);
        }

        public IEnumerable<Rental> GetAll()
        {
            var query = from rental in DataContext.Rentals select rental;
            return query.AsEnumerable();
        }

        public void Save(Rental entity)
        {
            DataContext.Rentals.Add(entity);
            DataContext.SaveChanges();
        }

        public void Update(Rental entity)
        {
            DataContext.Rentals.AddOrUpdate(entity);
            DataContext.SaveChanges();
        }
    }
}