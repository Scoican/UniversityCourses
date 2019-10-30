using LibraryApplication.Domain;
using LibraryApplication.Service;
using System;
using System.Windows.Forms;

namespace LibraryApplication.Interface
{
    public partial class AdminMenuForm : Form
    {
        private AdminService adminService;
        private SubscriberService subscriberService;
        private User user;

        public AdminMenuForm(AdminService adminService, SubscriberService subscriberService, User user)
        {
            this.adminService = adminService;
            this.subscriberService = subscriberService;
            this.user = user;
            InitializeComponent();
        }

        private void AdminMenuForm_Load(object sender, EventArgs e)
        {
            this.Text = this.user.Id;
        }

        private void LogoutButton_Click(object sender, EventArgs e)
        {
            LoginForm loginForm = new LoginForm(adminService, subscriberService);
            Hide();
            loginForm.ShowDialog();
            Close();
        }

        private void UsersButton_Click(object sender, EventArgs e)
        {
            UserManagerForm userManager = new UserManagerForm(adminService, subscriberService, user);
            Hide();
            userManager.ShowDialog();
            Close();
        }

        private void BooksButton_Click(object sender, EventArgs e)
        {
            BookManagerForm bookManager = new BookManagerForm(adminService, subscriberService, user);
            Hide();
            bookManager.ShowDialog();
            Close();
        }
    }
}