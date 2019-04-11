using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TicketManagerCSharp.utils
{
    public interface IObserver<E> where E : IEvent
    {
        void Update(E e);
    }
}
