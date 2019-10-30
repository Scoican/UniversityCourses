using System.Collections.Generic;

namespace LibraryApplication
{
    public interface IRepository<T, ID>
    {
        void Save(T entity);

        void Delete(ID id);

        void Update(T entity);

        T Find(ID id);

        IEnumerable<T> GetAll();
    }
}