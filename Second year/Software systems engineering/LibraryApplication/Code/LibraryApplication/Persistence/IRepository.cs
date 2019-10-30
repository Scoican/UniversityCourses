using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Persistence
{
    public interface IRepository<T,ID>
    {
        void Save(T entity);
        void Delete(ID id);
        void Update(T entity);
        T Find(ID id);
        IEnumerable<T> GetAll();
    }
}
