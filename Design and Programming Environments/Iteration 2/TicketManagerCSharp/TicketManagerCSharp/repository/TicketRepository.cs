using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using TicketManagerCSharp.domain;
using TicketManagerCSharp.utils;

namespace TicketManagerCSharp.repository
{
    class TicketRepository : IRepository<int, Ticket>
    {
        private static readonly ILog log = LogManager.GetLogger("EventRepository");

        IDictionary<String, string> props;

        public TicketRepository(IDictionary<String, string> props)
        {
            log.Info("Creating TicketRepository...");
            this.props = props;
        }

        public void Delete(int id)
        {
            log.InfoFormat("Entering Delete with new value {0}...", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Tickets where id=@id";
                IDbDataParameter idParam = comm.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = id;
                comm.Parameters.Add(idParam);
                var rez = comm.ExecuteNonQuery();
                if (rez == 0)
                {
                    throw new RepositoryException("Error:Can't delete Ticket!");
                }
                log.InfoFormat("Exiting Delete");
            }
        }

        public IEnumerable<Ticket> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.InfoFormat("Entering findAll...");
            IList<Ticket> tickets = new List<Ticket>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,buyer,price,event from Users";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idTicket = dataR.GetInt32(0);
                        int idEvent = dataR.GetInt32(3);
                        string buyer = dataR.GetString(1);
                        double price = dataR.GetDouble(2);
                        Ticket ticket = new Ticket(idTicket, idEvent, buyer, price);
                        tickets.Add(ticket);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return tickets;
        }

        public Ticket FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,buyer,price,event, from Users where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idTicket = dataR.GetInt32(0);
                        int idEvent = dataR.GetInt32(3);
                        string buyer = dataR.GetString(1);
                        double price = dataR.GetDouble(2);
                        Ticket ticket = new Ticket(idTicket, idEvent, buyer, price);
                        log.InfoFormat("Exiting findOne with value{0}", ticket);
                        return ticket;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public void Save(Ticket entity)
        {
            log.InfoFormat("Entering Save with new value {0}...", entity);
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Tickets(buyer,price,event) values (@buyer,@price,@event)";
                var buyerParam = comm.CreateParameter();
                buyerParam.ParameterName = "@buyer";
                buyerParam.Value = entity.Buyer;
                comm.Parameters.Add(buyerParam);

                var priceParam = comm.CreateParameter();
                priceParam.ParameterName = "@price";
                priceParam.Value = entity.Price;
                comm.Parameters.Add(priceParam);

                var idEventParam = comm.CreateParameter();
                idEventParam.ParameterName = "@event";
                idEventParam.Value = entity.IdEvent;
                comm.Parameters.Add(idEventParam);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    throw new RepositoryException("Error:Can't add ticket!");
                }
                log.InfoFormat("Exiting Save");
            }
        }

        public int Size()
        {
            log.InfoFormat("Calculating Tickets database Size...");
            IDbConnection con = DBUtils.getConnection(props);
            int size = -1;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from Tickets";

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

        public void Update(int id, Ticket entity)
        {
            log.InfoFormat("Entering Update with value {0}", id);
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Userss set buyer=@buyer,price=@price,event=@event where id=@id";
                var idParam = comm.CreateParameter();
                idParam.ParameterName = "@id";
                idParam.Value = entity.Id;
                comm.Parameters.Add(idParam);

                var buyerParam = comm.CreateParameter();
                buyerParam.ParameterName = "@buyer";
                buyerParam.Value = entity.Buyer;
                comm.Parameters.Add(buyerParam);

                var priceParam = comm.CreateParameter();
                priceParam.ParameterName = "@price";
                priceParam.Value = entity.Price;
                comm.Parameters.Add(priceParam);

                var idEventParam = comm.CreateParameter();
                idEventParam.ParameterName = "@event";
                idEventParam.Value = entity.IdEvent;
                comm.Parameters.Add(idEventParam);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    throw new RepositoryException("Error:Can't update Ticket!");
                }
                log.InfoFormat("Exiting Update");
            }
        }
    }
}
