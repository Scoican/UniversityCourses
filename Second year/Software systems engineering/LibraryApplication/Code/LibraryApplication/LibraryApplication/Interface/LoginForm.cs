using LibraryApplication.Domain;
using LibraryApplication.Interface;
using LibraryApplication.Service;
using LibraryApplication.Validator;
using System.Windows.Forms;

namespace LibraryApplication
{
    public partial class LoginForm : Form
    {
        private AdminService adminService;
        private SubscriberService subscriberService;

        public LoginForm(AdminService adminService, SubscriberService subscriberService)
        {
            this.adminService = adminService;
            this.subscriberService = subscriberService;
            InitializeComponent();
        }

        private void LoginButton_Click(object sender, System.EventArgs e)
        {
            string username, password;
            username = this.usernameTextBox.Text;
            password = this.passwordTextBox.Text;
            try
            {
                User user = adminService.LogIn(username, password);
                if (user.UserType == "ADMIN")
                {
                    AdminMenuForm adminMenuForm = new AdminMenuForm(adminService, subscriberService, user);
                    Hide();
                    adminMenuForm.ShowDialog();
                    Close();
                }
                if (user.UserType == "SUBSCRIBER")
                {
                    SubscriberForm subscriberForm = new SubscriberForm(adminService, subscriberService, user);
                    Hide();
                    subscriberForm.ShowDialog();
                    Close();
                }
            }
            catch (ValidationException msg)
            {
                usernameTextBox.Text = "";
                passwordTextBox.Text = "";
                MessageBox.Show(msg.Message);
            }
        }

        private void LoginForm_Load(object sender, System.EventArgs e)
        {
            this.usernameTextBox.Text = "";
            this.passwordTextBox.Text = "";
            this.passwordTextBox.PasswordChar = '*';
            this.passwordTextBox.MaxLength = 30;
        }
    }
}