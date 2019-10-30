using System.Collections.Generic;
using System.Data.SQLite;
using TicketManagerCSharp.domain;

namespace Persistence
{
    public class UserRepository
    {
        public User FindOne(string id)
        {
            if (id == null)
                return null;
            SQLiteConnection connection = DatabaseConnection.getConnection();
            string select = "select username,password from Users where username=@username";
            connection.Open();
            using (var cmd = new SQLiteCommand(select, connection))
            {
                var param = cmd.CreateParameter();
                param.ParameterName = "@username";
                param.Value = id;
                cmd.Parameters.Add(param);

                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        User angj = new User((string)reader["username"].ToString(), (string)reader["password"].ToString());
                        connection.Close();
                        return angj;
                    }
                    else
                    {
                        connection.Close();
                        return null;
                    }
                }
            }
        }

        public User Delete(string id)
        {
            User user = FindOne(id);
            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();
            if (user == null)
            {
                connection.Close();
                return null;
            }
            string select = "delete from Users where username=@id";
            using (var cmd = new SQLiteCommand(select, connection))
            {
                var param1 = cmd.CreateParameter();
                {
                    param1.ParameterName = "@id";
                    param1.Value = id;

                    cmd.Parameters.Add(param1);
                    cmd.ExecuteNonQuery();
                    connection.Close();
                    return user;
                }
            }
        }

        public User Save(User user)
        {
            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();
            var select = "insert into Users(username,password) values (@username,@password)";

            using (var comm = new SQLiteCommand(select, connection))
            {
                var usernameParam = comm.CreateParameter();
                usernameParam.ParameterName = "@username";
                usernameParam.Value = user.Id;
                comm.Parameters.Add(usernameParam);

                var passwordParam = comm.CreateParameter();
                passwordParam.ParameterName = "@password";
                passwordParam.Value = user.Password;
                comm.Parameters.Add(passwordParam);

                int affectedRows = comm.ExecuteNonQuery();
                if (affectedRows != 0)
                {
                    string sql = @"select last_insert_rowid()";
                    string lastId;
                    using (var cmd2 = new SQLiteCommand(sql, connection))
                    {
                        lastId = cmd2.ToString();
                    }
                    User ang = new User(lastId, user.Password);
                    connection.Close();
                    return ang;
                }
                else
                {
                    connection.Close();
                    return null;
                }
            }
        }

        public User Update(int id, User angj)
        {
            User clon = angj;
            if (id < 0 || angj == null)
            {
                return null;
            }
            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();
            string select = "update Users set password=@password where username=@id";
            using (var comm = new SQLiteCommand(select, connection))
            {
                var idParam = comm.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = angj.Id;
                comm.Parameters.Add(idParam);

                var passwordParam = comm.CreateParameter();
                passwordParam.ParameterName = "@password";
                passwordParam.Value = angj.Password;
                comm.Parameters.Add(passwordParam);

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

        public List<User> getAll()
        {
            List<User> angajati = new List<User>();
            var querry = "SELECT * FROM User";
            SQLiteConnection connection = DatabaseConnection.getConnection();
            connection.Open();
            using (var cmd = new SQLiteCommand(querry, connection))
            {
                using (var reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        User angj = new User((string)reader["username"].ToString(), (string)reader["password"].ToString());
                        angajati.Add(angj);
                    }
                    connection.Close();
                    return angajati;
                }
            }
        }
    }
}