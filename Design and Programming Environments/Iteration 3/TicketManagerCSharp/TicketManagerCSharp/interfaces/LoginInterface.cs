using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using TicketManagerCSharp.service;

namespace TicketManagerCSharp
{
    public partial class LoginInterface : Form
    {
        private AdminService service;
        IDictionary<String, string> props;
        public LoginInterface(IDictionary<String, string> props)
        {
            InitializeComponent();
            this.service = new AdminService(props);
            this.props = props;
        }


        private void LoginButton_Click(object sender, EventArgs e)
        {
            try
            {
                String username = usernameTextBox.Text;
                String password = passwordTextBox.Text;
                if (service.checkPassword(username, password))
                {
                    GUIcs eventInterface = new GUIcs(this.props);
                    eventInterface.Show();
                    this.Hide();
                    
                }
                else throw new Exception("Wrong password or username");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void LoginInterface_Load(object sender, EventArgs e)
        {

        }
    }
}
