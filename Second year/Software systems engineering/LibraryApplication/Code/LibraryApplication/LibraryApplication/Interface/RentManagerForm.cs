using LibraryApplication.Domain;
using LibraryApplication.Service;
using LibraryApplication.Validator;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Windows.Forms;

namespace LibraryApplication.Interface
{
    public partial class RentManagerForm : Form
    {
        private AdminService adminService;
        private User selectedSubscriber;
        private Book selectedBook;
        private UserManagerForm userManagerForm;
        private BookManagerForm bookManagerForm;

        public RentManagerForm(AdminService adminService, User selectedSubscriber, UserManagerForm userManagerForm)
        {
            this.adminService = adminService;
            this.selectedSubscriber = selectedSubscriber;
            this.userManagerForm = userManagerForm;
            this.selectedBook = null;
            this.bookManagerForm = null;
            InitializeComponent();
        }

        public RentManagerForm(AdminService adminService, Book selectedBook, BookManagerForm bookManagerForm)
        {
            this.adminService = adminService;
            this.selectedBook = selectedBook;
            this.bookManagerForm = bookManagerForm;
            this.selectedSubscriber = null;
            this.userManagerForm = null;
            InitializeComponent();
        }

        private void RentManagerForm_Load(object sender, EventArgs e)
        {
            IEnumerable<Rental> rents = adminService.GetRentals();
            if (selectedBook == null)
            {
                List<Rental> rentals = new List<Rental>();
                foreach (Rental rental in rents)
                {
                    if (selectedSubscriber.Id == rental.Owner)
                    {
                        rentals.Add(rental);
                    }
                }
                PopulateBookList(rentals);
            }
            if (selectedSubscriber == null)
            {
                List<Rental> rentals = new List<Rental>();
                foreach (Rental rental in rents)
                {
                    Copy copy = adminService.FindCopy(rental.Book);
                    if (selectedBook.Id == copy.BookId)
                    {
                        rentals.Add(rental);
                    }
                }
                PopulateUserList(rentals);
            }
        }

        public void PopulateBookList(List<Rental> rentals)
        {
            listView.Clear();
            listView.Columns.Add("Id");
            listView.Columns.Add("Book name");
            listView.Columns.Add("Author");
            listView.Columns.Add("Year");
            foreach (Rental rental in rentals)
            {
                Copy copy = adminService.FindCopy(rental.Book);
                Book book = adminService.FindBook(copy.BookId);
                string[] row = { rental.Id.ToString(), book.Name, book.Author, book.Year.ToString() };
                var listViewItem = new ListViewItem(row);
                listView.Items.Add(listViewItem);
            }
            listView.AutoResizeColumns(ColumnHeaderAutoResizeStyle.ColumnContent);
            listView.AutoResizeColumns(ColumnHeaderAutoResizeStyle.HeaderSize);
        }

        public void PopulateUserList(List<Rental> rentals)
        {
            listView.Clear();
            listView.Columns.Add("Id");
            listView.Columns.Add("Username");
            foreach (Rental rental in rentals)
            {
                User user = adminService.FindUser(rental.Owner);
                string[] row = { rental.Id.ToString(), user.Id };
                var listViewItem = new ListViewItem(row);
                listView.Items.Add(listViewItem);
            }
            listView.AutoResizeColumns(ColumnHeaderAutoResizeStyle.ColumnContent);
            listView.AutoResizeColumns(ColumnHeaderAutoResizeStyle.HeaderSize);
        }

        private void ReturnBookButton_Click(object sender, EventArgs e)
        {
            try
            {
                string id = listView.SelectedItems[0].SubItems[0].Text;
                Debug.WriteLine(id);
                adminService.ReturnBook(int.Parse(id));
                RentManagerForm_Load(this, null);
            }
            catch (ValidationException msg)
            {
                MessageBox.Show(msg.Message);
            }
            catch (System.ArgumentOutOfRangeException)
            {
                MessageBox.Show("Please choose a book");
            }
        }
    }
}