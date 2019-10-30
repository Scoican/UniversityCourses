using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using basketLabC.Service;
using basketLabC.Validator;

namespace basketLabC
{
    public partial class Form1 : Form
    {
        OrganizatorService Service;
        EchipaService EchipaService;
        MeciService MeciService;
        BiletService BiletService;
        Form2 form2;


        public Form1(OrganizatorService service,EchipaService echipaService,MeciService meciService,BiletService biletService)
        {
            Service = service;
            EchipaService = echipaService;
            MeciService = meciService;
            BiletService = biletService;
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            string username, password;
            username = textBox2.Text;
            password = textBox3.Text;
            try
            {
                if (Service.LogIn(username, password) != null)
                {
                    form2 = new Form2(Service,EchipaService,MeciService,BiletService);
                    Hide();
                    form2.ShowDialog();
                    Close();
                }
                
                    
            }
            catch(ValidationException msg)
            {
                textBox2.Text = "";
                textBox3.Text = "";
                MessageBox.Show(msg.Message);
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            textBox3.Text = "";
            textBox3.PasswordChar = '*';
            textBox3.MaxLength = 20;
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }
    }
}
