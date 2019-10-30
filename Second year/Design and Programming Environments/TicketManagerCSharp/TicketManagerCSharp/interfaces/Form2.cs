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
using basketLabC.Domain;
using basketLabC.Validator;
using basketLabC.Utils;

namespace basketLabC
{
    public partial class Form2 : Form
    {
        EchipaService EchipaService;
        MeciService MeciService;
        BiletService BiletService;
        OrganizatorService OrganizatorService;
        
        Form1 form1;

        public Form2(OrganizatorService organizatorService,EchipaService echipaService,MeciService meciService, BiletService biletService)
        {
            EchipaService = echipaService;
            MeciService = meciService;
            BiletService = biletService;
            OrganizatorService = organizatorService;
            InitializeComponent();
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            form1 = new Form1(OrganizatorService, EchipaService, MeciService, BiletService);
            Hide();
            form1.ShowDialog();
            Close();
        }

        
        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                int id = Convert.ToInt32(textBox1.Text.Split(':')[0]);
                string nume = textBox2.Text;
                int nr_loc = Convert.ToInt32(numericUpDown1.Value);
                double pret = Convert.ToDouble(label6.Text.Split(' ')[0]);
                BiletService.SaveBilet(id, nume, pret, nr_loc);
                MessageBox.Show("Purchase complete! ");
                showMeci(null);
                load();
            }
            catch(ValidationException msg)
            {
                MessageBox.Show(msg.Message);
            }

        }


        private void showMeci(MeciDTO meciDTO)
        {
            if (meciDTO == null)
            {
                textBox1.Text = "";
                textBox2.Text = "";
                label6.Text = "0 lei";
            }
            else
                textBox1.Text = meciDTO.ID + ":" + meciDTO.NumeMeci;

        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            MeciDTO m = (MeciDTO)dataGridView1.CurrentRow.DataBoundItem;
            Console.Write(m.NumeMeci);
            textBox1.Text = m.ID.ToString() + ":" + m.NumeMeci;
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
            List<MeciDTO> meciuri = GetMeciDTO();
            var list = new BindingList<MeciDTO>(meciuri);
            dataGridView1.DataSource = list;
        }

        private List<MeciDTO> GetMeciDTO()
        {
            return MeciService.FindAllMeci().Select(x => new MeciDTO(x.ID, EchipaService.FindEchipa(x.ID_echipaA).Nume + " vs " + 
                EchipaService.FindEchipa(x.ID_echipaB).Nume , x.Etapa, x.Nr_locuri, x.PretMeci)).ToList();
        }

        public void update(MeciChangeEvent e)
        {
            load();
        }

        private void Form2_Load(object sender, EventArgs e)
        {
            load();
            dataGridView1.Columns[0].Visible = false;
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            MeciDTO m = (MeciDTO)dataGridView1.CurrentRow.DataBoundItem;
            Console.Write(m.NumeMeci);
            textBox1.Text = m.ID.ToString() + ":" + m.NumeMeci;
        }

        private void numericUpDown1_ValueChanged_1(object sender, EventArgs e)
        {
            if (!textBox1.Text.Equals(""))
            {
                int value = Convert.ToInt32(numericUpDown1.Value);
                int id = Convert.ToInt32(textBox1.Text.Split(':')[0]);
                Meci m = MeciService.FindMeci(id);
                label6.Text = (value * m.PretMeci).ToString() + " lei";
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            showMeci(null);
        }
    }
}
