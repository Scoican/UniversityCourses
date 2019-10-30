using System.Collections.Generic;
using System.Data.SQLite;
using TicketManagerCSharp.domain;

namespace Persistence
{
    public class EventRepository
    {
        public Event Save(Event entity)
        {
            if (entity.Id <= 0)
            {
                return null;
            }
            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();
            var select = "insert into Events(game_name,game_price,free_seats) values (@game_name,@game_price,@free_seats)";
            using (var comm = new SQLiteCommand(select, connection))
            {
                var nameParam = comm.CreateParameter();
                nameParam.ParameterName = "@game_name";
                nameParam.Value = entity.GameName;
                comm.Parameters.Add(nameParam);

                var gamePriceParam = comm.CreateParameter();
                gamePriceParam.ParameterName = "@game_price";
                gamePriceParam.Value = entity.GamePrice;
                comm.Parameters.Add(gamePriceParam);

                var freeSeatsParam = comm.CreateParameter();
                freeSeatsParam.ParameterName = "@free_seats";
                freeSeatsParam.Value = entity.FreeSeats;
                comm.Parameters.Add(freeSeatsParam);

                int affectedRows = comm.ExecuteNonQuery();
                if (affectedRows != 0)
                {
                    string sql = @"select last_insert_rowid()";
                    int lastId = -1;
                    using (var cmd2 = new SQLiteCommand(sql, connection))
                    {
                        lastId = int.Parse(cmd2.ExecuteScalar().ToString());
                    }
                    Event mec = new Event(lastId, entity.GameName, entity.GamePrice, entity.FreeSeats);
                    connection.Close();
                    return mec;
                }
                else
                {
                    connection.Close();
                    return null;
                }
            }
        }

        public Event Find(int id)
        {
            if (id < 0)
            {
                return null;
            }
            SQLiteConnection connection = DatabaseConnection.getConnection();
            string select = "select id,game_name,game_price,free_seats from Events where id=@id";
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
                        Event entity = new Event(int.Parse(reader["id"].ToString()), (string)reader["game_name"], double.Parse(reader["game_price"].ToString()), int.Parse(reader["free_seats"].ToString()));
                        connection.Close();
                        return entity;
                    }
                    else
                    {
                        connection.Close();
                        return null;
                    }
                }
            }
        }

        public Event Delete(int id)
        {
            Event entity = Find(id);

            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();

            string select = "delete from Events where id=@id";
            using (var cmd = new SQLiteCommand(select, connection))
            {
                var param1 = cmd.CreateParameter();
                param1.ParameterName = "@Id";
                param1.Value = id;

                cmd.Parameters.Add(param1);
                cmd.ExecuteNonQuery();
                connection.Close();
                return entity;
            }
        }

        public Event Update(int id, Event entity)
        {
            Event clon = entity;

            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();
            string select = "update Events set game_name=@game_name,game_price=@game_price,free_seats=@free_seats where id=@id";
            using (var comm = new SQLiteCommand(select, connection))
            {
                var nameParam = comm.CreateParameter();
                nameParam.ParameterName = "@game_name";
                nameParam.Value = entity.GameName;
                comm.Parameters.Add(nameParam);

                var gamePriceParam = comm.CreateParameter();
                gamePriceParam.ParameterName = "@game_price";
                gamePriceParam.Value = entity.GamePrice;
                comm.Parameters.Add(gamePriceParam);

                var freeSeatsParam = comm.CreateParameter();
                freeSeatsParam.ParameterName = "@free_seats";
                freeSeatsParam.Value = entity.FreeSeats;
                comm.Parameters.Add(freeSeatsParam);

                var idParam = comm.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = id;
                comm.Parameters.Add(idParam);

                int affectedRows = comm.ExecuteNonQuery();
                if (affectedRows != 0)
                {
                    connection.Close();
                    return clon;
                }
                else
                {
                    connection.Close();
                    return null;
                }
            }
        }

        public List<Event> getALl()
        {
            List<Event> meciuri = new List<Event>();
            var querry = "SELECT * FROM Events";
            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();
            using (var cmd = new SQLiteCommand(querry, connection))
            {
                using (var reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        Event entity = new Event(int.Parse(reader["id"].ToString()), (string)reader["game_name"], double.Parse(reader["game_price"].ToString()), int.Parse(reader["free_seats"].ToString()));
                        meciuri.Add(entity);
                    }
                    connection.Close();
                    return meciuri;
                }
            }
        }
    }
}