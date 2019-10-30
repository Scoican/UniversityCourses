using System;
using System.Data.SQLite;

namespace Persistence
{
    public static class DatabaseConnection
    {
        public static SQLiteConnection connection;

        public static SQLiteConnection GetConnection()
        {
            String connectionString = "Data Source= C:\\Users\\Scoican\\Desktop\\Facultate\\Ingineria sistemelor soft\\LibraryApplication\\Code\\LibraryApplication\\LibraryDatabase.db; Version = 3";
            connection = new SQLiteConnection(connectionString);
            return connection;
        }
    }
}