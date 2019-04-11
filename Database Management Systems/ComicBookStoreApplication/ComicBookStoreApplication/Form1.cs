using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;


namespace ComicBookStoreApplication
{

    public partial class Form1 : Form
    {
        BindingSource childBS;
        private DataSet ds;
        private SqlDataAdapter childAdapter;
        private SqlCommandBuilder cb;
        private string connectionString = "Server=DESKTOP-O7DL3PI\\SQLEXPRESS;Database=ComicBookStore;Integrated Security=true";


        public Form1()
        {
            InitializeComponent();
        }
        SqlConnection conn;
        private void Form1_Load(object sender, EventArgs e)
        {
            conn = new SqlConnection(connectionString);
            try
            {
                ds = new DataSet();
                conn.Open();
                SqlDataAdapter parentAdapter = new SqlDataAdapter("SELECT * FROM Staff", conn);
                childAdapter = new SqlDataAdapter("SELECT * FROM Tranzactions", conn);
                cb = new SqlCommandBuilder(childAdapter);
                parentAdapter.Fill(ds, "Staff");
                childAdapter.Fill(ds, "Tranzactions");
                BindingSource parentBS = new BindingSource();
                childBS = new BindingSource();
                parentBS.DataSource = ds.Tables["Staff"];
                dataGridViewParent.DataSource = parentBS;
                DataColumn pk = ds.Tables["Staff"].Columns["id"];
                DataColumn fk = ds.Tables["Tranzactions"].Columns["staffMember"];
                DataRelation relation = new DataRelation("FK_ComicBooks_Artists", pk, fk);
                ds.Relations.Add(relation);
                childBS.DataSource = parentBS;
                childBS.DataMember = "FK_ComicBooks_Artists";
                dataGridViewChild.DataSource = childBS;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                conn.Close();
            }
        }

        private void DataGridViewChild_SelectionChanged(object sender, EventArgs e)
        {
            try
            {
                childAdapter.Update(ds, "Tranzactions");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void EngineForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            try
            {
                childAdapter.Update(ds, "Tranzactions");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }


        private void updateButton_Click(object sender, EventArgs e)
        {
            cb = new SqlCommandBuilder(childAdapter);
            try
            {
                childAdapter.Update(ds, "Tranzactions");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                conn.Close();
            }
        }
    }
}


