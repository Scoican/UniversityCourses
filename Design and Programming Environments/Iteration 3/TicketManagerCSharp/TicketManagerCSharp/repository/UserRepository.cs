using System;
using System.Collections.Generic;
using TicketManagerCSharp.domain;
using log4net;
using System.Data;
using TicketManagerCSharp.utils;

namespace TicketManagerCSharp.repository
{
   class UserRepository : IRepository<int, User>
    {
        private static readonly ILog log = LogManager.GetLogger("EventRepository");

        IDictionary<String, string> props;

        public UserRepository(IDictionary<String, string> props)
        {
            log.Info("Creating UserRepository...");
            this.props = props;
        }

        public void Delete(int id)
        {
            log.InfoFormat("Entering Delete with new value {0}...", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Users where id=@id";
                IDbDataParameter idParam = comm.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = id;
                comm.Parameters.Add(idParam);
                var rez = comm.ExecuteNonQuery();
                if (rez == 0)
                {
                    throw new RepositoryException("Error:Can't delete User!");
                }
                log.InfoFormat("Exiting Delete");
            }
        }

        public IEnumerable<User> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.InfoFormat("Entering findAll...");
            IList<User> users = new List<User>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,username,password from Users";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idU = dataR.GetInt32(0);
                        string username = dataR.GetString(1);
                        string password = dataR.GetString(2);
                        User user = new User(idU, username, password);
                        users.Add(user);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return users;
        }

        public User FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,username,password from Users where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idU = dataR.GetInt32(0);
                        string username = dataR.GetString(1);
                        string password = dataR.GetString(2);
                        User user = new User(idU, username, password);
                        log.InfoFormat("Exiting findOne with value{0}", user);
                        return user;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public User FindOne(string username)
        {
            log.InfoFormat("Entering findUserByUsername with value {0}", username);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,username,password from Users where username=@username";
                IDbDataParameter paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = username;
                comm.Parameters.Add(paramUsername);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idU = dataR.GetInt32(0);
                        string name = dataR.GetString(1);
                        string password = dataR.GetString(2);
                        User user = new User(idU, name, password);
                        log.InfoFormat("Exiting findUserByUsername with value{0}", user);
                        return user;
                    }
                }
            }
            log.InfoFormat("Exiting findUserByUsername with value {0}", null);
            return null;
        }

        public void Save(User entity)
        {
            log.InfoFormat("Entering Save with new value {0}...", entity);
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Users(username,password) values (@username,@password)";
                var usernameParam = comm.CreateParameter();
                usernameParam.ParameterName = "@username";
                usernameParam.Value = entity.Username;
                comm.Parameters.Add(usernameParam);

                var passwordParam = comm.CreateParameter();
                passwordParam.ParameterName = "@password";
                passwordParam.Value = entity.Password;
                comm.Parameters.Add(passwordParam);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    throw new RepositoryException("Error:Can't add user!");
                }
                log.InfoFormat("Exiting Save");
            }
        }

        public int Size()
        {
            log.InfoFormat("Calculating Users database Size...");
            IDbConnection con = DBUtils.getConnection(props);
            int size = -1;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from Users";

                var rez = comm.ExecuteNonQuery();
                if (rez == 0)
                {
                    throw new RepositoryException("Error:Can't calculate size!");
                }

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        size = dataR.GetInt32(0);
                    }
                }
            }
            log.InfoFormat("Finished calculation!");
            return size;
        }

        public void Update(int id, User entity)
        {
            log.InfoFormat("Entering Update with value {0}", id);
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Userss set username=@username,password=@password where id=@id";
                var idParam = comm.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = entity.Id;
                comm.Parameters.Add(idParam);

                var usernameParam = comm.CreateParameter();
                usernameParam.ParameterName = "@username";
                usernameParam.Value = entity.Username;
                comm.Parameters.Add(usernameParam);

                var passwordParam = comm.CreateParameter();
                passwordParam.ParameterName = "@password";
                passwordParam.Value = entity.Password;
                comm.Parameters.Add(passwordParam);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    throw new RepositoryException("Error:Can't update User!");
                }
                log.InfoFormat("Exiting Update");
            }
        }
    }
}
