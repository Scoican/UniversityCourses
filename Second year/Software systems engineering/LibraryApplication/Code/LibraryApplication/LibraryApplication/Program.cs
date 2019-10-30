using LibraryApplication.Repository;
using LibraryApplication.Service;
using System;
using System.Windows.Forms;

namespace LibraryApplication
{
    internal static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        private static void Main()
        {
            //test();
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            DatabaseContext db = new DatabaseContext();
            UserRepository userRepository = new UserRepository(db);
            RentalRepository rentalRepository = new RentalRepository(db);
            BookRepository bookRepository = new BookRepository(db);
            CopyRepository copyRepository = new CopyRepository(db);
            AdminService adminService = new AdminService(userRepository, bookRepository, copyRepository, rentalRepository);
            SubscriberService subscriberService = new SubscriberService(userRepository, bookRepository, copyRepository, rentalRepository);
            Application.Run(new LoginForm(adminService, subscriberService));
        }

        private static void test()
        {
            DatabaseContext db = new DatabaseContext();
            UserRepository userRepository = new UserRepository(db);
            RentalRepository rentalRepository = new RentalRepository(db);
            BookRepository bookRepository = new BookRepository(db);
        }
    }
}