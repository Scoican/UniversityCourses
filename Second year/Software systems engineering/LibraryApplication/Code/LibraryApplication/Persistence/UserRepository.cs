using Model;
using System.Collections.Generic;
using System.Data.Entity.Migrations;
using System.Linq;

namespace Persistence
{
    public class UserRepository : IRepository<User, string>
    {
        private readonly DatabaseContext DataContext;

        public UserRepository(DatabaseContext db)
        {
            this.DataContext = db;
        }

        public void Delete(string id)
        {
            User user = DataContext.Users.Find(id);
            DataContext.Users.Remove(user);
            DataContext.SaveChanges();
        }

        public User Find(string id)
        {
            return DataContext.Users.Find(id);
        }

        public IEnumerable<User> GetAll()
        {
            var query = from user in DataContext.Users select user;
            return query.AsEnumerable();
        }

        public void Save(User entity)
        {
            DataContext.Users.Add(entity);
            DataContext.SaveChanges();
        }

        public void Update(User entity)
        {
            DataContext.Users.AddOrUpdate(entity);
            DataContext.SaveChanges();
        }
    }
}