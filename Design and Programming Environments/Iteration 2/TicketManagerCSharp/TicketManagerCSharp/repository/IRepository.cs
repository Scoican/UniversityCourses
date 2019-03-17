using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TicketManagerCSharp.domain;

namespace TicketManagerCSharp.repository
{
    public interface IRepository<ID, E> where E : HasId<ID>
    {
        E FindOne(ID id);

        IEnumerable<E> FindAll();

        void Save(E entity);

        void Delete(ID id);

        void Update(ID id, E entity);

        int Size();
    }
}
