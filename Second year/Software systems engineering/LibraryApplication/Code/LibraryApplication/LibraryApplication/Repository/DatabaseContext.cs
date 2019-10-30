using LibraryApplication.Domain;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;
using System.Data.SQLite;

namespace LibraryApplication
{
    public class DatabaseContext : DbContext
    {
        public DatabaseContext() : base(new SQLiteConnection()
        {
            ConnectionString = new SQLiteConnectionStringBuilder()
            {
                DataSource = "C:\\Users\\Scoican\\Desktop\\Facultate\\Ingineria sistemelor soft\\LibraryApplication\\Code\\LibraryApplication\\LibraryDatabase.db",
                ForeignKeys = true
            }.ConnectionString
        }, true)
        { }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Conventions.Remove<PluralizingTableNameConvention>();
            base.OnModelCreating(modelBuilder);
        }

        public DbSet<User> Users { get; set; }

        public DbSet<Rental> Rentals { get; set; }

        public DbSet<Book> Books { get; set; }

        public DbSet<Copy> Copies { get; set; }
    }
}