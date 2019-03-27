using System;
using System.Collections.Generic;
using System.Windows.Forms;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.service;

namespace TicketManagerCSharp
{
    public partial class TicketInterface : Form
    {
        
        private OperatorService service;
        IDictionary<String, string> props;
        private int eventId;
        private GUIcs gui;
        public TicketInterface(IDictionary<String, string> props,int eventId,GUIcs gui)
        {
            InitializeComponent();
            this.service = new OperatorService(props);
            this.eventId = eventId;
            this.props = props;
            this.gui = gui;
        }

        private void ticketSellButton_Click(object sender, EventArgs e)
        {
            try
            {
                String buyer = buyerTextBox.Text;
                int seats = Decimal.ToInt32(nrTicketsNumeric.Value);
                service.BuyTicket(buyer,seats,eventId);
                gui.RefreshTable();
                this.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
