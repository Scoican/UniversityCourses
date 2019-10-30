using Networking.DTOS;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows.Forms;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.service;

namespace Client
{
    public partial class TicketInterfaceForm : Form
    {
        private readonly ClientCtrl server;
        private LoginInterfaceForm loginInterface;

        public TicketInterfaceForm(ClientCtrl server)
        {
            this.server = server;
            InitializeComponent();
        }

        private void load()
        {
            List<Event> events = (List<Event>)server.getEvents();
            var list = new BindingList<Event>(events);
            dataGridView.DataSource = list;
        }

        public void userUpdate(List<Event> events)
        {
            this.Invoke((MethodInvoker)delegate
            {
                updateTables(events);
            });
        }

        private void updateTables(List<Event> events)
        {
            BindingList<Event> list = new BindingList<Event>(events);
            dataGridView.DataSource = events;
        }

        private void TicketInterfaceForm_Load(object sender, System.EventArgs e)
        {
            load();
            dataGridView.Columns[0].Visible = false;
        }

        private void dataGridView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            EventDTO m = (EventDTO)dataGridView.CurrentRow.DataBoundItem;
            Console.Write(m.GameName);
            textBox1.Text = m.Id.ToString() + ":" + m.GameName;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            loginInterface = new LoginInterfaceForm(server);
            server.logout();
            Hide();
            loginInterface.ShowDialog();
            Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                int id = Convert.ToInt32(textBox1.Text.Split(':')[0]);
                string name = textBox2.Text;
                int seats = Convert.ToInt32(numericUpDown1.Value);
                double price = Convert.ToDouble(label6.Text.Split(' ')[0]);
                server.addTicket(id, seats,name);
                MessageBox.Show("Purchase complete! ");
                showMeci(null);
                load();
            }
            catch (ValidationException msg)
            {
                MessageBox.Show(msg.Message);
            }
        }

        private void showMeci(EventDTO eventDTO)
        {
            if (eventDTO == null)
            {
                textBox1.Text = "";
                textBox2.Text = "";
                label6.Text = "0 lei";
            }
            else
                textBox1.Text = eventDTO.Id + ":" + eventDTO.GameName;
        }

        private void dataGridView_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            Event m = (Event)dataGridView.CurrentRow.DataBoundItem;
            textBox1.Text = m.Id.ToString() + ":" + m.GameName;
        }

        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        {

            if (!textBox1.Text.Equals(""))
            {
                int value = Convert.ToInt32(numericUpDown1.Value);
                int id = Convert.ToInt32(textBox1.Text.Split(':')[0]);
                Event m = (Event)dataGridView.CurrentRow.DataBoundItem;
                label6.Text = (Convert.ToInt32(numericUpDown1.Value) * m.GamePrice).ToString() + " lei";
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            showMeci(null);
        }

    }
}