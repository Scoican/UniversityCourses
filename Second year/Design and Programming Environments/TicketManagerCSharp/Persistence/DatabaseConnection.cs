using System;
using System.Data.SQLite;

namespace Persistence
{
    public static class DatabaseConnection
    {
        public static SQLiteConnection connection;

        public static SQLiteConnection getConnection()
        {
            String connectionString = "Data Source= C:\\Users\\Scoican\\Desktop\\Facultate\\Medii de proiectare si programare\\Laborator\\TicketManager\\TicketManager.db; Version = 3";
            connection = new SQLiteConnection(connectionString);
            return connection;
        }
    }
}