using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;
using TicketManagerCSharp.domain;
using log4net.Config;
using TicketManagerCSharp.repository;
using System.Windows.Forms;
using TicketManagerCSharp.service;

namespace TicketManagerCSharp
{
    class Program
    {
        static void Main(string[] args)
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            IDictionary<String, string> props = new SortedList<String, String>();

            props.Add("ConnectionString", utils.DBUtils.GetConnectionStringByName("TicketManager"));

            EventRepository eventRepository = new EventRepository(props);
            TicketRepository ticketRepository = new TicketRepository(props);
            UserRepository userRepository = new UserRepository(props);

            EventService eventService = new EventService(eventRepository);
            TicketService ticketService = new TicketService(ticketRepository,eventService);
            UserService userService = new UserService(userRepository);

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LoginInterface(userService,eventService,ticketService));

        }
    }
}
