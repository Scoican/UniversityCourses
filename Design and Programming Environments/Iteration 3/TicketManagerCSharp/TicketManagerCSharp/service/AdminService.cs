using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.repository;

namespace TicketManagerCSharp.service
{
    class AdminService
    {
        private UserRepository userRepository;
        private static readonly ILog log = LogManager.GetLogger("ServiceAdmin");

        public AdminService(IDictionary<String, string> props)
        {
            log.Info("Creating ServiceAdmin");
            userRepository = new UserRepository(props);
        }

        public bool checkPassword(String username, String pass)
        {
            User user = userRepository.FindOne(username);
            if (user != null)
                if (user.Password == pass)
                    return true;
            return false;
        }
    }
}
