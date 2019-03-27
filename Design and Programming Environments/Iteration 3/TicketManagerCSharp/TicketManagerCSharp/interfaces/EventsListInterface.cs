using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using TicketManagerCSharp.utils;

namespace TicketManagerCSharp
{
    public partial class GUIcs : Form
    {
        IDictionary<String, string> props;

        public GUIcs(IDictionary<String, string> props)
        {
            InitializeComponent();
            this.props = props;
            AddColumnIntoTable();
            RefreshTable();
            
        }

        private void sellButton_Click(object sender, EventArgs e)
        {
            int eventId = Int32.Parse(eventsDataGrid.SelectedRows[0].Cells[0].Value.ToString());
            TicketInterface ticketInterface = new TicketInterface(props,eventId,this);
            ticketInterface.Show();
        }

        

        public void RefreshTable()
        {
            try
            {
                eventsDataGrid.Rows.Clear();
                eventsDataGrid.Refresh();
                IDbConnection cs = DBUtils.getConnection(props);
                using (var comm = cs.CreateCommand())
                {
                    var comanda2 = cs.CreateCommand();
                    comm.CommandText = "select Events.id,Events.name,Events.seats,Tickets.price from Events inner join Tickets on Events.id = Tickets.event order by Events.seats desc";
                    using (var dataR = comm.ExecuteReader())
                    {
                        int i = 0;
                        while (dataR.Read())
                        {
                            eventsDataGrid.Rows.Add(new object[]
                            {
                                dataR.GetValue(dataR.GetOrdinal("Id")),
                                dataR.GetValue(dataR.GetOrdinal("Name")),
                                dataR.GetValue(dataR.GetOrdinal("Price")),
                                dataR.GetValue(dataR.GetOrdinal("Seats")),
                            });

                            if (eventsDataGrid.Rows[i].Cells[3].Value.ToString() == "0")
                            {
                                eventsDataGrid.Rows[i].Cells[3].Value = "SOLD OUT";
                                DataGridViewCellStyle style = new DataGridViewCellStyle();
                                style.BackColor = Color.Red;
                                style.Font = new Font(eventsDataGrid.Font, FontStyle.Bold);
                                style.ForeColor = Color.White;
                                eventsDataGrid.Rows[i].Cells[3].Style = style;
                            }
                            i++;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void AddColumnIntoTable()
        {
            eventsDataGrid.Columns.Add("Id", "Id");
            eventsDataGrid.Columns.Add("Name", "Name of the event");
            eventsDataGrid.Columns.Add("Price", "Price of the event");
            eventsDataGrid.Columns.Add("Seats", "Available seats");
            eventsDataGrid.Columns["Id"].Visible = false;
        }
    }
}
