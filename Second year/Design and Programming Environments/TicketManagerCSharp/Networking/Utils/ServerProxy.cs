using Networking.DTOS;
using Networking.Requests;
using Networking.Responses;
using Services;
using Services.Exceptions;
using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using TicketManagerCSharp.domain;

namespace Networking.Utils
{
    public class ServerProxy : IServer
    {
        private string host;
        private int port;

        private IObserver client;
        private NetworkStream stream;

        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;

        public ServerProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
                Console.WriteLine("Sent request " + request);
            }
            catch (Exception e)
            {
                throw new BasketException("Error sending object " + e.Message);
            }
        }

        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    response = responses.Dequeue();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }

        public void addEvent(int id, string game_name, double game_price, int free_seats)
        {
            EventDTO eventDTO = new EventDTO(id, game_name, game_price, free_seats);
            sendRequest(new AddEventRequest(eventDTO));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new BasketException(err.Message);
            }
        }

        public void addTicket(int id_game, int reserved_seats, string client_name)
        {
            TicketDTO ticketDTO = new TicketDTO(0, id_game, reserved_seats, 0.0, client_name);
            sendRequest(new AddTicketRequest(ticketDTO));
            Response response = readResponse();

            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new BasketException(err.Message);
            }
        }

        public void deleteEvent(int id)
        {
            sendRequest(new DeleteRequest(id));
            Response response = readResponse();
            if (response is OkResponse)
            {
                Console.WriteLine("Operation done with succes:");
            }
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new BasketException(err.Message);
            }
        }

        public void deleteTicket(int id)
        {
            sendRequest(new DeleteTicketRequest(id));
            Response response = readResponse();
            if (response is OkResponse)
            {
                Console.WriteLine("Operation done with succes:");
            }
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new BasketException(err.Message);
            }
        }

        public IList<Event> getEvents()
        {
            sendRequest(new GetAllEventsRequest());
            Response response = readResponse();
            if (response is GetAllEventsResponse)
            {
                List<Event> rez = new List<Event>();
                foreach (EventDTO eventDTO in ((GetAllEventsResponse)response).Events)
                {
                    rez.Add(DTOUtils.getFromDTO(eventDTO));
                }
                return rez;
            }
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new BasketException(err.Message);
            }
            return null;
        }

        public IList<Ticket> getTickets()
        {
            sendRequest(new GetAllTicketsRequest());
            Response response = readResponse();
            if (response is GetAllTicketsResponse)
            {
                List<Ticket> rez = new List<Ticket>();
                foreach (TicketDTO ticketDTO in ((GetAllTicketsResponse)response).Tickets)
                {
                    rez.Add(DTOUtils.getFromDTO(ticketDTO));
                }
                return rez;
            }

            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new BasketException(err.Message);
            }
            return null;
        }

        public void login(User user, IObserver client)
        {
            initializeConnection();
            UserDTO pdto = DTOUtils.getDTO(user);
            sendRequest(new LoginRequest(pdto));
            Response response = readResponse();
            if (response is OkResponse)
            {
                this.client = client;
                return;
            }
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new BasketException(err.Message);
            }
        }

        public void logout(User user)
        {
            UserDTO pdto = DTOUtils.getDTO(user);
            sendRequest(new LogoutRequest(pdto));
            Response response = readResponse();
            closeConnection();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new BasketException(err.Message);
            }
        }

        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (response is UpdateResponse)
                    {
                        handleUpdate((UpdateResponse)response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue((Response)response);
                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }
            }
        }

        private void handleUpdate(UpdateResponse update)
        {
            if (update is NotifyResponse)
            {
                List<Event> rez = new List<Event>();
                foreach (EventDTO eventDTO in ((NotifyResponse)update).Events)
                {
                    rez.Add(DTOUtils.getFromDTO(eventDTO));
                }

                try
                {
                    client.update(rez);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
        }

        public void updateEvent(int id, string game_name, double game_price, int free_seats)
        {
            EventDTO eventDTO = new EventDTO(id, game_name, game_price, free_seats);
            sendRequest(new UpdateEventRequest(eventDTO));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new BasketException(err.Message);
            }
        }

        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
    }
}