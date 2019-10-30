using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.utils;

namespace TicketManagerCSharp.repository
{
    public class EventRepository : IRepository<int, Event>
    {
        private static readonly ILog log = LogManager.GetLogger("EventRepository");

        private IDictionary<String, string> props;

        public EventRepository(IDictionary<String, string> props)
        {
            log.Info("Creating EventRepository...");
            this.props = props;
        }

        public void Delete(int id)
        {
            log.InfoFormat("Entering Delete with new value {0}...", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Events where id=@id";
                IDbDataParameter idParam = comm.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = id;
                comm.Parameters.Add(idParam);
                var rez = comm.ExecuteNonQuery();
                if (rez == 0)
                {
                    throw new RepositoryException("Error:Can't delete Event!");
                }
                log.InfoFormat("Exiting Delete");
            }
        }

        public IEnumerable<Event> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.InfoFormat("Entering findAll...");
            IList<Event> events = new List<Event>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,game_name,game_price,free_seats from Events";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idE = dataR.GetInt32(0);
                        string name = dataR.GetString(1);
                        double gamePrice = dataR.GetDouble(2);
                        int seats = dataR.GetInt32(3);
                        Event e = new Event(idE, name, gamePrice, seats);
                        events.Add(e);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return events;
        }

        public Event FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,game_name,game_price,free_seats from Events where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idE = dataR.GetInt32(0);
                        string name = dataR.GetString(1);
                        double gamePrice = dataR.GetDouble(2);
                        int seats = dataR.GetInt32(3);
                        Event e = new Event(idE, name, gamePrice, seats);
                        log.InfoFormat("Exiting findOne with value{0}", e);
                        return e;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public void Save(Event entity)
        {
            log.InfoFormat("Entering Save with new value {0}...", entity);
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Events(game_name,game_price,free_seats) values (@game_name,@game_price,@free_seats)";
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

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    throw new RepositoryException("Error:Can't add event!");
                }
                log.InfoFormat("Exiting Save");
            }
        }

        public int Size()
        {
            log.InfoFormat("Calculating Events database Size...");
            IDbConnection con = DBUtils.getConnection(props);
            int size = -1;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from Events";

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

        public void Update(int id, Event entity)
        {
            log.InfoFormat("Entering Update with value {0}", id);
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Events set game_name=@game_name,game_price=@game_price,free_seats=@free_seats where id=@id";
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

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    throw new RepositoryException("Error:Can't update Event!");
                }
                log.InfoFormat("Exiting Update");
            }
        }
    }
}