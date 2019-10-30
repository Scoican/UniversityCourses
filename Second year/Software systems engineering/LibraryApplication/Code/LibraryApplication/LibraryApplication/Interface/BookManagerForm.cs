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
    public partial class BookManagerForm : Form
    {
        private SubscriberService subscriberService;
        private AdminService adminService;
        private User user;

        public BookManagerForm(AdminService adminService, SubscriberService subscriberService, User user)
        {
            this.subscriberService = subscriberService;
            this.adminService = adminService;
            this.user = user;
            InitializeComponent();
        }

        private void BookManagerForm_Load(object sender, EventArgs e)
        {
            this.Text = user.Id;
            for (int i = DateTime.Now.Year; i > 1700; i--)
            {
                yearComboBox.Items.Add(i);
            }
            this.AddButton.Enabled = false;
            this.DeleteButton.Enabled = false;
            this.UpdateButton.Enabled = false;
            this.ReturnBookButton.Enabled = false;
            PopulateView();
        }

        private void ReturnButton_Click(object sender, EventArgs e)
        {
            AdminMenuForm adminMenu = new AdminMenuForm(adminService, subscriberService, user);
            Hide();
            adminMenu.ShowDialog();
            Close();
        }

        private void ClearFields()
        {
            NameTextBox.Text = "";
            AuthorTextBox.Text = "";
            yearComboBox.Text = "";
        }

        public void PopulateView()
        {
            List<Book> books = adminService.GetBooks().ToList();
            BindingList<Book> list = new BindingList<Book>(books);
            BookGridView.DataSource = list;
        }

        private void BookGridView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            Book selectedBook = (Book)BookGridView.CurrentRow.DataBoundItem;
            this.DeleteButton.Enabled = true;
            this.UpdateButton.Enabled = true;
            this.ReturnBookButton.Enabled = true;
            NameTextBox.Text = selectedBook.Name;
            AuthorTextBox.Text = selectedBook.Author;
            yearComboBox.Text = selectedBook.Year.ToString();
            QuantityNumeric.Value = selectedBook.Quantity;
        }

        private void AddButton_Click(object sender, EventArgs e)
        {
            string name = NameTextBox.Text;
            string author = AuthorTextBox.Text;
            int year = int.Parse(yearComboBox.Text);
            int quantity = (int)QuantityNumeric.Value;
            try
            {
                adminService.AddBook(name, author, year, quantity);
                PopulateView();
                MessageBox.Show("Book added with succes!");
            }
            catch (ValidationException msg)
            {
                MessageBox.Show(msg.Message);
            }
            ClearFields();
        }

        private void DeleteButton_Click(object sender, EventArgs e)
        {
            Book selectedBook = (Book)BookGridView.CurrentRow.DataBoundItem;
            try
            {
                adminService.DeleteBook(selectedBook.Id);
                PopulateView();
                MessageBox.Show("Book deleted with succes");
            }
            catch (ValidationException msg)
            {
                MessageBox.Show(msg.Message);
            }
            ClearFields();
        }

        private void UpdateButton_Click(object sender, EventArgs e)
        {
            Book selectedBook = (Book)BookGridView.CurrentRow.DataBoundItem;
            string name = NameTextBox.Text;
            string author = AuthorTextBox.Text;
            int year = int.Parse(yearComboBox.Text);
            try
            {
                adminService.UpdateBook(selectedBook.Id, name, author, year);
                PopulateView();
                MessageBox.Show("Book data updated!");
            }
            catch (ValidationException msg)
            {
                MessageBox.Show(msg.Message);
            }
            ClearFields();
        }

        private void ReturnBookButton_Click(object sender, EventArgs e)
        {
            Book selectedBook = (Book)BookGridView.CurrentRow.DataBoundItem;
            RentManagerForm rentManager = new RentManagerForm(adminService, selectedBook, this);
            rentManager.ShowDialog();
        }

        private void NameTextBox_TextChanged(object sender, EventArgs e)
        {
            this.AddButton.Enabled = true;
        }
    }
}