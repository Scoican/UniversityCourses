using System.Collections.Generic;
using System.Data.SQLite;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.repository;

namespace Persistence
{
    public class TicketRepository
    {
        public Ticket Save(Ticket entity)
        {
            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();
            var select = "insert into Tickets(id_game,reserved_seats,price,client_name) values (@id_game,@reserved_seats,@price,@client_name)";
            using (var comm = new SQLiteCommand(select, connection))
            {
                var idGameParam = comm.CreateParameter();
                idGameParam.ParameterName = "@id_game";
                idGameParam.Value = entity.IdGame;
                comm.Parameters.Add(idGameParam);

                var reservedSeatsParam = comm.CreateParameter();
                reservedSeatsParam.ParameterName = "@reserved_seats";
                reservedSeatsParam.Value = entity.ReservedSeats;
                comm.Parameters.Add(reservedSeatsParam);

                var priceParam = comm.CreateParameter();
                priceParam.ParameterName = "@price";
                priceParam.Value = entity.Price;
                comm.Parameters.Add(priceParam);

                var clientNameParam = comm.CreateParameter();
                clientNameParam.ParameterName = "@client_name";
                clientNameParam.Value = entity.ClientName;
                comm.Parameters.Add(clientNameParam);

                int affectedRows = comm.ExecuteNonQuery();
                if (affectedRows != 0)
                {
                    string sql = @"select last_insert_rowid()";
                    int lastId = -1;
                    using (var cmd2 = new SQLiteCommand(sql, connection))
                    {
                        lastId = int.Parse(cmd2.ExecuteScalar().ToString());
                    }
                    Ticket bil = new Ticket(lastId, entity.IdGame, entity.ReservedSeats, entity.Price, entity.ClientName);
                    connection.Close();
                    return bil;
                }
                else
                {
                    connection.Close();
                    return null;
                }
            }
        }

        public Ticket Find(int id)
        {
            if (id < 0)
            {
                return null;
            }
            SQLiteConnection connection = DatabaseConnection.getConnection();
            string select = "select id_game,reserved_seats,price,client_name from Tickets where id=@id";
            connection.Open();
            using (var cmd = new SQLiteCommand(select, connection))
            {
                var param = cmd.CreateParameter();
                param.ParameterName = "@id";
                param.Value = id;
                cmd.Parameters.Add(param);

                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        Ticket ticket = new Ticket(int.Parse(reader["id_game"].ToString()), int.Parse(reader["reserved_seats"].ToString()), double.Parse(reader["price"].ToString()), (string)reader["client_name"]);
                        connection.Close();
                        return ticket;
                    }
                    else
                    {
                        connection.Close();
                        return null;
                    }
                }
            }
        }

        public void Delete(int id)
        {
            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();

            string select = "Ddelete from Tickets where id=@id";
            using (var comm = new SQLiteCommand(select, connection))
            {
                var param1 = comm.CreateParameter();
                param1.ParameterName = "@id";
                param1.Value = id;

                comm.Parameters.Add(param1);
                comm.ExecuteNonQuery();
                connection.Close();

                var rez = comm.ExecuteNonQuery();
                if (rez == 0)
                {
                    throw new RepositoryException("Error:Can't delete Ticket!");
                }
            }
        }

        public List<Ticket> getALl()
        {
            List<Ticket> tickets = new List<Ticket>();
            var querry = "SELECT * FROM Tickets";
            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();
            using (var cmd = new SQLiteCommand(querry, connection))
            {
                using (var reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        Ticket ticket = new Ticket(int.Parse(reader["id_game"].ToString()), int.Parse(reader["reserved_seats"].ToString()), double.Parse(reader["price"].ToString()), (string)reader["client_name"]);
                        tickets.Add(ticket);
                    }
                    connection.Close();
                    return tickets;
                }
            }
        }
    }
}