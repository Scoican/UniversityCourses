using LibraryApplication.Domain;
using LibraryApplication.Service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Windows.Forms;

namespace LibraryApplication.Interface
{
    public partial class SubscriberForm : Form
    {
        private AdminService adminService;
        private SubscriberService subscriberService;
        private User subscriberLogged;

        public SubscriberForm(AdminService adminService, SubscriberService subscriberService, User subscriberLogged)
        {
            this.adminService = adminService;
            this.subscriberService = subscriberService;
            this.subscriberLogged = subscriberLogged;
            InitializeComponent();
        }

        private void SubscriberFrom_Load(object sender, EventArgs e)
        {
            BookGridView.AllowUserToAddRows = false;
            PopulateView();
            BookGridView.Columns["Id"].Visible = false;
            this.BorrowButton.Enabled = false;
            this.Text = subscriberLogged.Id;
        }

        public void PopulateView()
        {
            List<Copy> copies = adminService.GetCopies().ToList();
            BindingList<CopyDTO> list = new BindingList<CopyDTO>();
            foreach (Copy copy in copies)
            {
                if (copy.State == "AVAILABLE")
                {
                    Book book = adminService.FindBook(copy.BookId);
                    list.Add(new CopyDTO(copy.Id, book.Name, book.Author, book.Year));
                }
            }
            BookGridView.DataSource = list;
        }

        private void LogoutButton_Click(object sender, EventArgs e)
        {
            LoginForm loginForm = new LoginForm(adminService, subscriberService);
            Hide();
            loginForm.ShowDialog();
            Close();
        }

        private void BookGridView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            this.BorrowButton.Enabled = true;
        }

        private void BorrowButton_Click(object sender, EventArgs e)
        {
            CopyDTO copy = (CopyDTO)BookGridView.CurrentRow.DataBoundItem;
            subscriberService.BorrowBook(this.subscriberLogged.Id, copy.Id);
            PopulateView();
        }
    }
}