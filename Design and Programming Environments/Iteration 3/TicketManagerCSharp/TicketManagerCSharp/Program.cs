using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;
using TicketManagerCSharp.domain;
using log4net.Config;
using TicketManagerCSharp.repository;
using System.Windows.Forms;

namespace TicketManagerCSharp
{
    class Program
    {
        static void Main(string[] args)
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            IDictionary<String, string> props = new SortedList<String, String>();

            props.Add("ConnectionString", utils.DBUtils.GetConnectionStringByName("TicketManager"));

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LoginInterface(props));

        }
    }
}
