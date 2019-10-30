using Services.Exceptions;
using System;
using System.Windows.Forms;

namespace Client
{
    public partial class LoginInterfaceForm : Form
    {
        private readonly ClientCtrl server;

        public LoginInterfaceForm(ClientCtrl server)
        {
            this.server = server;
            InitializeComponent();
        }

        private void LoginInterfaceForm_Load(object sender, EventArgs e)
        {
            passwordTextBox.Text = "";
            passwordTextBox.PasswordChar = '*';
            passwordTextBox.MaxLength = 20;
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            string username, password;
            username = usernameTextBox.Text;
            password = passwordTextBox.Text;
            try
            {
                server.login(username, password);
                TicketInterfaceForm newForm = new TicketInterfaceForm(server);
                server.setForm(newForm);
                this.Hide();
                newForm.Show();
                newForm.Text = username;
                newForm.FormClosed += new FormClosedEventHandler(delegate { Close(); });
            }
            catch (BasketException ex)
            {
                MessageBox.Show(this, "Login error" + ex.Message);
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Login error" + ex.Message);
                return;
            }
        }
    }
}