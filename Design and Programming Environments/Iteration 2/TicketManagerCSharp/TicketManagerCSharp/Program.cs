using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;
using TicketManagerCSharp.domain;
using log4net.Config;
using TicketManagerCSharp.repository;

namespace TicketManagerCSharp
{
    class Program
    {
        static void Main(string[] args)
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            IDictionary<String, string> props = new SortedList<String, String>();

            props.Add("ConnectionString", utils.DBUtils.GetConnectionStringByName("TicketManager"));

            EventRepository eventRepo = new EventRepository(props);
            TicketRepository ticketRepo = new TicketRepository(props);
            UserRepository userRepo = new UserRepository(props);

            Console.WriteLine("Dimensiune baza de date: " + eventRepo.Size());
            Console.WriteLine("Dimensiune baza de date: " + ticketRepo.Size());
            Console.WriteLine("Dimensiune baza de date: " + userRepo.Size());

        }
    }
}
