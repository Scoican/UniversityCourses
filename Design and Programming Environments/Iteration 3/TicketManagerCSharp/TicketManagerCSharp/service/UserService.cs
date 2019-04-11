using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.repository;

namespace TicketManagerCSharp.service
{
    public class UserService
    {
        private UserRepository userRepository;

        public UserService(UserRepository userRepository)
        {
            this.userRepository = userRepository;
        }

        public User LogIn(string username, string password)
        {
            User user = userRepository.FindOne(username);
            if (user == null && user.Password==password)
                throw new ValidationException("Username sau parola incorecte! \n");
            else
                return user;
        }
    }
}
