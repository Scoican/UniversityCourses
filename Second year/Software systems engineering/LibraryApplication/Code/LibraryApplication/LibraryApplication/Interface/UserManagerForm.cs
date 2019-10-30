using LibraryApplication.Domain;
using LibraryApplication.Service;
using LibraryApplication.Validator;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Windows.Forms;

namespace LibraryApplication.Interface
{
    public partial class UserManagerForm : Form
    {
        private SubscriberService subscriberService;
        private AdminService adminService;
        private User user;

        public UserManagerForm(AdminService adminService, SubscriberService subscriberService, User user)
        {
            this.subscriberService = subscriberService;
            this.adminService = adminService;
            this.user = user;
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {
        }

        private void ClearFields()
        {
            UsernameTextBox.Text = "";
            PasswordTextBox.Text = "";
            TypeComboBox.Text = "";
            CNPTextBox.Text = "";
            AdressTextBox.Text = "";
            PhoneTextBox.Text = "";
        }

        private void AddUserButton_Click(object sender, EventArgs e)
        {
            string username = UsernameTextBox.Text;
            string password = PasswordTextBox.Text;
            string userType = TypeComboBox.Text;
            string cnp = CNPTextBox.Text;
            string adress = AdressTextBox.Text;
            string phone = PhoneTextBox.Text;

            try
            {
                adminService.AddUser(username, password, userType, cnp, adress, phone);
                PopulateView();
                MessageBox.Show("User added with succes!");
            }
            catch (ValidationException msg)
            {
                MessageBox.Show(msg.Message);
            }
            ClearFields();
        }

        private void DeleteUserButton_Click(object sender, EventArgs e)
        {
            string username = UsernameTextBox.Text;
            try
            {
                if (this.user == adminService.FindUser(username))
                {
                    MessageBox.Show("Can't delete yourself!");
                    return;
                }
                adminService.DeleteUser(username);
                PopulateView();
                MessageBox.Show("User deleted with succes!");
            }
            catch (ValidationException msg)
            {
                MessageBox.Show(msg.Message);
            }
            ClearFields();
        }

        private void UpdateUserButton_Click(object sender, EventArgs e)
        {
            string username = UsernameTextBox.Text;
            string password = PasswordTextBox.Text;
            string userType = TypeComboBox.Text;
            string cnp = CNPTextBox.Text;
            string adress = AdressTextBox.Text;
            string phone = PhoneTextBox.Text;

            try
            {
                adminService.UpdateUser(username, password, userType, cnp, adress, phone);
                PopulateView();
                MessageBox.Show("User data updated!");
            }
            catch (ValidationException msg)
            {
                MessageBox.Show(msg.Message);
            }
            ClearFields();
        }

        private void UserManagerFrom_Load(object sender, EventArgs e)
        {
            this.Text = user.Id;
            this.AddUserButton.Enabled = false;
            this.DeleteUserButton.Enabled = false;
            this.UpdateUserButton.Enabled = false;
            this.BookViewerButton.Enabled = false;
            PopulateView();
        }

        private void ReturnButton_Click(object sender, EventArgs e)
        {
            AdminMenuForm adminMenu = new AdminMenuForm(adminService, subscriberService, user);
            Hide();
            adminMenu.ShowDialog();
            Close();
        }

        public void PopulateView()
        {
            List<User> users = adminService.GetUsers().ToList();
            BindingList<User> list = new BindingList<User>(users);
            UserGridView.DataSource = list;
        }

        private void UserGridView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            User user = (User)UserGridView.CurrentRow.DataBoundItem;
            UsernameTextBox.Text = user.Id;
            PasswordTextBox.Text = user.Password;
            TypeComboBox.Text = user.UserType;
            CNPTextBox.Text = user.Cnp;
            AdressTextBox.Text = user.Adress;
            PhoneTextBox.Text = user.Phone;
            this.BookViewerButton.Enabled = true;
        }

        private void UsernameTextBox_TextChanged(object sender, EventArgs e)
        {
            if (PasswordTextBox.Text != "")
            {
                AddUserButton.Enabled = true;
            }
            DeleteUserButton.Enabled = true;
        }

        private void PasswordTextBox_TextChanged(object sender, EventArgs e)
        {
            if (UsernameTextBox.Text != "")
            {
                AddUserButton.Enabled = true;
                UpdateUserButton.Enabled = true;
            }
        }

        private void BookViewerButton_Click(object sender, EventArgs e)
        {
            User user = (User)UserGridView.CurrentRow.DataBoundItem;
            RentManagerForm rentManager = new RentManagerForm(adminService, user, this);
            rentManager.ShowDialog();
        }
    }
}