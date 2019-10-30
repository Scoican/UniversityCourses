using System;

namespace Services.Exceptions
{
    public class BasketException : Exception
    {
        public BasketException() : base()
        {
        }

        public BasketException(String msg) : base(msg)
        {
        }

        public BasketException(String msg, Exception ex) : base(msg, ex)
        {
        }
    }
}