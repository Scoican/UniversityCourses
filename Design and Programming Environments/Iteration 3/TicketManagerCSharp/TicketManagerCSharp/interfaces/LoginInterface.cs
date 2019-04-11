using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using TicketManagerCSharp.interfaces;
using TicketManagerCSharp.service;

namespace TicketManagerCSharp
{
    public partial class LoginInterface : Form
    
        {
        UserService userService;
        EventService eventService;
        TicketService ticketService;
        TicketInterface ticketInterface;


        public LoginInterface(UserService userService,EventService eventService, TicketService ticketService)
        {
            this.userService = userService;
            this.eventService = eventService;
            this.ticketService = ticketService;
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            string username, password;
            username = usernameTextBox.Text;
            password = passwordTextBox.Text;
            try
            {
                if (userService.LogIn(username, password) != null)
                {
                    ticketInterface = new TicketInterface(userService, eventService, ticketService);
                    Hide();
                    ticketInterface.ShowDialog();
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

        private void LoginInterface_Load(object sender, EventArgs e)
        {
            passwordTextBox.Text = "";
            passwordTextBox.PasswordChar = '*';
            passwordTextBox.MaxLength = 20;
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void textBox3_TextChanged(object sender, EventArgs e)
        {

        }
    }
}
