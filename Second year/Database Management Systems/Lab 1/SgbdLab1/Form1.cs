using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Windows.Forms;

namespace SgbdLab1
{
    public partial class Form1 : Form
    {


        //pt modele
        SqlConnection sqlConnection;//= new SqlConnection("Data Source=DESKTOP-I9RLVTA;Initial Catalog=LudwkDB;Integrated Security=True");
        SqlDataAdapter SqlDataAdapter = new SqlDataAdapter();
        DataSet ds = new DataSet();
        BindingSource bs = new BindingSource();
        //pt produse
        SqlConnection sqlConnection2; //= new SqlConnection("Data Source=DESKTOP-I9RLVTA;Initial Catalog=LudwkDB;Integrated Security=True"); 
        SqlDataAdapter SqlDataAdapter2 = new SqlDataAdapter();
        DataSet ds2 = new DataSet();
        BindingSource bs2 = new BindingSource();
        //parametrii-liste
        List<string> t2Columns;
        List<string> tipuriDatet2Columns;
        List<string> parametriiSql;
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            //Buton conectare- se executa primul

            //initilizare conexiuni
            String cs = ConfigurationManager.ConnectionStrings["conn"].ConnectionString;
            SqlConnection sqlConn = new SqlConnection(cs);
            sqlConnection = sqlConn;
            sqlConnection2 = sqlConn;

            //creare textbox in panel
            List<string> t2ColumnsInput = new List<string>(ConfigurationManager.AppSettings["campuriT2"].Split(','));
            t2Columns = t2ColumnsInput;
            List<string> tipuriDatet2ColumnsInput = new List<string>(ConfigurationManager.AppSettings["tipuriDateCampuriT2"].Split(','));
            tipuriDatet2Columns = tipuriDatet2ColumnsInput;
            List<string> parametriiSqlInput = new List<string>(ConfigurationManager.AppSettings["parametriiSql"].Split(','));
            parametriiSql = parametriiSqlInput;
            foreach (string column in t2Columns)
            {
                Label lbl = new Label();
                lbl.Text = column;
                flowLayoutPanel1.Controls.Add(lbl);

                TextBox txt = new TextBox();               
                txt.Name = "textBox"+column;
                flowLayoutPanel1.Controls.Add(txt);

                Label lb2 = new Label();
                lb2.Text = "";
                flowLayoutPanel1.Controls.Add(lb2);
            }

            //conectare
            string selectModeleCommandString = ConfigurationSettings.AppSettings["selectElemsT1"];
            
            SqlDataAdapter.SelectCommand = new SqlCommand(selectModeleCommandString, sqlConnection);
            ds.Clear();
            SqlDataAdapter.Fill(ds);//populare, mai multe tabele, cu virgula
            dataGridView1.DataSource = ds.Tables[0];
            bs.DataSource = ds.Tables[0];
            sqlConnection.Close();     
        }

        private void handleSelectionChanged(object sender, EventArgs e)
        {
            try
            {
                if (ds.Tables[0].Rows[dataGridView1.CurrentCell.RowIndex][0] != null)
                {
                    string selectElTab2ByIdElTab1 = ConfigurationSettings.AppSettings["selectElTab2ByIdElTab1"];
                    string idTab1 = ConfigurationSettings.AppSettings["idTab1"];                    

                    SqlDataAdapter2.SelectCommand = new SqlCommand(selectElTab2ByIdElTab1, sqlConnection2);
                    SqlDataAdapter2.SelectCommand.Parameters.Add(idTab1, SqlDbType.Int).Value = ds.Tables[0].Rows[dataGridView1.CurrentCell.RowIndex][0];
                    ds2.Clear();
                    SqlDataAdapter2.Fill(ds2);//populare, mai multe tabele, cu virgula
                    dataGridView2.DataSource = ds2.Tables[0];
                    bs2.DataSource = ds2.Tables[0];
                    sqlConnection2.Close();
                }
            }
            catch (Exception ex) { MessageBox.Show(ex.Message); }        
        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                string insertElTab2CommandString = ConfigurationSettings.AppSettings["insertElTab2CommandString"];
                SqlDataAdapter2.InsertCommand = new SqlCommand(insertElTab2CommandString , sqlConnection2);

                for(int i=0;i<t2Columns.Count(); i++)
                {
                    var dataType = tipuriDatet2Columns.ElementAt(i);
                    var parameter = parametriiSql.ElementAt(i);
                    var numeColoana = t2Columns.ElementAt(i);
                    var textBoxName = "textBox" + numeColoana;
                    string idTab1 = ConfigurationSettings.AppSettings["idTab1"];

                    TextBox currTextBox = (TextBox)Controls.Find(string.Format("textBox{0}",numeColoana),true).FirstOrDefault();

                    if (dataType.Equals("string")) { 
                        SqlDataAdapter2.InsertCommand.Parameters.Add(parameter, SqlDbType.VarChar).Value = currTextBox.Text.ToString();
                        //MessageBox.Show(currTextBox.Name.ToString());
                    }
                    if (dataType.Equals("int") && !parameter.Equals(idTab1))
                    {
                        SqlDataAdapter2.InsertCommand.Parameters.Add(parameter, SqlDbType.Int).Value = Int32.Parse(currTextBox.Text);
                        //MessageBox.Show(currTextBox.Name.ToString());
                    }
                    if (dataType.Equals("int") && parameter.Equals(idTab1))
                    {
                        SqlDataAdapter2.InsertCommand.Parameters.Add(parameter, SqlDbType.Int).Value = ds.Tables[0].Rows[dataGridView1.CurrentCell.RowIndex][0];
                        //MessageBox.Show(currTextBox.Name.ToString());
                    }
                    if (dataType.Equals("float"))
                    {
                        SqlDataAdapter2.InsertCommand.Parameters.Add(parameter, SqlDbType.Float).Value = float.Parse(currTextBox.Text);
                        //MessageBox.Show(currTextBox.Name.ToString());
                    }

                }

                ds2.Clear();
                sqlConnection2.Open();
                SqlDataAdapter2.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Added succesfull");
                sqlConnection2.Close();
                SqlDataAdapter2.Fill(ds2);
                dataGridView2.DataSource = ds2.Tables[0];
            }
            catch (Exception ex)
            { MessageBox.Show(ex.Message); sqlConnection2.Close(); }

        }

        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                var deleteCommand = ConfigurationSettings.AppSettings["deleteCommandString"];
                SqlDataAdapter2.DeleteCommand = new SqlCommand(deleteCommand, sqlConnection2);
                SqlDataAdapter2.DeleteCommand.Parameters.Add(parametriiSql.ElementAt(0),SqlDbType.Int).Value= ds2.Tables[0].Rows[dataGridView2.CurrentCell.RowIndex][0];
                ds2.Clear();
                sqlConnection2.Open();
                SqlDataAdapter2.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Deleted succesfull");
                sqlConnection2.Close();
                SqlDataAdapter2.Fill(ds2);
                dataGridView2.DataSource = ds2.Tables[0];
            }
            catch (Exception ex)
            { MessageBox.Show(ex.Message); sqlConnection2.Close(); }
        }

            private void button4_Click(object sender, EventArgs e)
        {

            try
            {
                var updateCommand = ConfigurationSettings.AppSettings["updateCommandString"];
                SqlDataAdapter2.UpdateCommand = new SqlCommand(updateCommand, sqlConnection2);

                SqlDataAdapter2.UpdateCommand.Parameters.Add(parametriiSql.ElementAt(0), SqlDbType.Int).Value = ds2.Tables[0].Rows[dataGridView2.CurrentCell.RowIndex][0];
                for (int i = 1; i < t2Columns.Count(); i++)
                {
                    var dataType = tipuriDatet2Columns.ElementAt(i);
                    var parameter = parametriiSql.ElementAt(i);
                    var numeColoana = t2Columns.ElementAt(i);
                    
                    TextBox currTextBox = (TextBox)Controls.Find(string.Format("textBox{0}", numeColoana), true).FirstOrDefault();

                    if (dataType.Equals("string"))
                    {
                        SqlDataAdapter2.UpdateCommand.Parameters.Add(parameter, SqlDbType.VarChar).Value = currTextBox.Text.ToString();
                    }
                    if (dataType.Equals("int") )
                    {
                        SqlDataAdapter2.UpdateCommand.Parameters.Add(parameter, SqlDbType.Int).Value = Int32.Parse(currTextBox.Text);
                    }                    
                    if (dataType.Equals("float"))
                    {
                        SqlDataAdapter2.UpdateCommand.Parameters.Add(parameter, SqlDbType.Float).Value = float.Parse(currTextBox.Text);
                    }

                }

                ds2.Clear();
                sqlConnection2.Open();
                SqlDataAdapter2.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show("Updated succesfull");
                sqlConnection2.Close();
                SqlDataAdapter2.Fill(ds2);
                dataGridView2.DataSource = ds2.Tables[0];
            }
            catch (Exception ex)
            { MessageBox.Show(ex.Message); sqlConnection2.Close(); }
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }
    }
}
