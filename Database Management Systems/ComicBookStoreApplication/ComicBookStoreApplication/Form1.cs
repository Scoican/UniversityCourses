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

        private void Form1_Load(object sender, EventArgs e)
        {
            SqlConnection conn = new SqlConnection(connectionString);
            try
            {
                ds = new DataSet();
                conn.Open();
                SqlDataAdapter parentAdapter = new SqlDataAdapter("SELECT * FROM ComicBooks", conn);
                childAdapter = new SqlDataAdapter("SELECT * FROM Artists", conn);
                cb = new SqlCommandBuilder(childAdapter);
                parentAdapter.Fill(ds, "ComicBooks");
                childAdapter.Fill(ds, "Artists");
                BindingSource parentBS = new BindingSource();
                childBS = new BindingSource();
                parentBS.DataSource = ds.Tables["ComicBooks"];
                dataGridViewParent.DataSource = parentBS;
                DataColumn pk = ds.Tables["ComicBooks"].Columns["id"];
                DataColumn fk = ds.Tables["Artists"].Columns["id"];
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
                childAdapter.Update(ds, "Artists");
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
                childAdapter.Update(ds, "Artists");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}


