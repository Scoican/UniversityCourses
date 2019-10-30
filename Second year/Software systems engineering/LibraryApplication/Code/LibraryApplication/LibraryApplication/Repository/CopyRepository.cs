using LibraryApplication.Domain;
using System.Collections.Generic;
using System.Data.Entity.Migrations;
using System.Linq;

namespace LibraryApplication.Repository
{
    public class CopyRepository : IRepository<Copy, int>
    {
        private readonly DatabaseContext DataContext;

        public CopyRepository(DatabaseContext db)
        {
            this.DataContext = db;
        }

        public void Delete(int id)
        {
            Copy copy = DataContext.Copies.Find(id);
            DataContext.Copies.Remove(copy);
            DataContext.SaveChanges();
        }

        public Copy Find(int id)
        {
            return DataContext.Copies.Find(id);
        }

        public IEnumerable<Copy> GetAll()
        {
            var query = from copy in DataContext.Copies select copy;
            return query.AsEnumerable();
        }

        public void Save(Copy entity)
        {
            DataContext.Copies.Add(entity);
            DataContext.SaveChanges();
        }

        public void Update(Copy entity)
        {
            DataContext.Copies.AddOrUpdate(entity);
            DataContext.SaveChanges();
        }
    }
}