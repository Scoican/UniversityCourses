using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.service;
using TicketManagerCSharp.utils;
using System.Diagnostics;


namespace TicketManagerCSharp.interfaces
{
    public partial class TicketInterface : Form
    {
        UserService userService;
        EventService eventService;
        TicketService ticketService;

        LoginInterface loginInterface;

        public TicketInterface(UserService userService, EventService eventService, TicketService ticketService)
        {
            this.userService = userService;
            this.eventService = eventService;
            this.ticketService = ticketService;
            InitializeComponent();
            load();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            loginInterface = new LoginInterface(userService, eventService, ticketService);
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
                ticketService.SaveTicket(id, seats, price, name);
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

        private void dataGridView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            EventDTO m = (EventDTO)dataGridView.CurrentRow.DataBoundItem;
            Console.Write(m.GameName);
            textBox1.Text = m.Id.ToString() + ":" + m.GameName;
        }

        private void label5_Click(object sender, EventArgs e)
        {

        }

        private void label6_Click(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void load()
        {
            List<EventDTO> games = GetEventDTO();
            var list = new BindingList<EventDTO>(games);
            dataGridView.DataSource = list;
        }

        private List<EventDTO> GetEventDTO()
        {
            return eventService.FindAll().Select(x => new EventDTO(x.Id, x.GameName, x.FreeSeats, x.GamePrice)).ToList();
        }

        public void update(EventChangeEvent e)
        {
            load();
        }

        private void TicketInteface_Load(object sender, EventArgs e)
        {
            load();
            dataGridView.Columns[0].Visible = false;
        }

        private void dataGridView_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            EventDTO m = (EventDTO)dataGridView.CurrentRow.DataBoundItem;
            textBox1.Text = m.Id.ToString() + ":" + m.GameName;
        }

        private void numericUpDown1_ValueChanged(object sender, EventArgs e)
        {
            Debug.WriteLine("ceva");
            if (!textBox1.Text.Equals(""))
            {
                int value = Convert.ToInt32(numericUpDown1.Value);
                int id = Convert.ToInt32(textBox1.Text.Split(':')[0]);
                Event m = eventService.FindOne(id);
                label6.Text = (Convert.ToInt32(numericUpDown1.Value) * m.GamePrice).ToString() + " lei";
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            showMeci(null);
        }

    }
}
