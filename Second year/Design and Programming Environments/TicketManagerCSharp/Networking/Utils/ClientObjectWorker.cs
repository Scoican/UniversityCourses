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
    public class ClientObjectWorker : IClient, IObserver
    {
        private IServer server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;
        private string username;

        public ClientObjectWorker(IServer server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    object request = formatter.Deserialize(stream);
                    object response = handleRequest((Request)request);
                    Console.WriteLine("worker request: " + request.ToString());
                    if (response != null)
                    {
                        Console.WriteLine("worker response" + response.ToString());
                        sendResponse((Response)response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(2000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        private Response handleRequest(Request request)
        {
            if (request is LoginRequest)
            {
                Console.WriteLine("Login request ...");
                LoginRequest logReq = (LoginRequest)request;
                UserDTO udto = logReq.User;
                User user = DTOUtils.getFromDTO(udto);
                try
                {
                    lock (server)
                    {
                        server.login(user, this);
                        username = user.Id;
                    }
                    return new OkResponse();
                }
                catch (BasketException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is LogoutRequest)
            {
                Console.WriteLine("Logout request");
                LogoutRequest logReq = (LogoutRequest)request;
                UserDTO udto = logReq.User;
                User user = DTOUtils.getFromDTO(udto);
                try
                {
                    lock (server)
                    {
                        server.logout(user);
                    }
                    connected = false;
                    return new OkResponse();
                }
                catch (BasketException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is GetAllEventsRequest)
            {
                List<Event> list = (List<Event>)server.getEvents();
                List<EventDTO> events = new List<EventDTO>();
                foreach (Event ev in list)
                {
                    EventDTO eventDTO = DTOUtils.getDTO(ev);
                    events.Add(eventDTO);
                }
                return new GetAllEventsResponse(events);
            }
            if (request is AddEventRequest)
            {
                AddEventRequest saveEventRequest = (AddEventRequest)request;
                EventDTO eventDTO = saveEventRequest.EventDTO;
                Event ev = DTOUtils.getFromDTO(eventDTO);
                try
                {
                    server.addEvent(1, ev.GameName, ev.GamePrice, ev.FreeSeats);
                    return new OkResponse();
                }
                catch (BasketException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is DeleteRequest)
            {
                Console.WriteLine("Delete request ...");
                DeleteRequest deleteRequest = (DeleteRequest)request;
                int id = deleteRequest.Id;
                try
                {
                    server.deleteEvent(id);
                    return new OkResponse();
                }
                catch (BasketException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is UpdateEventRequest)
            {
                Console.WriteLine("Update request ...");
                UpdateEventRequest updateEventRequest = (UpdateEventRequest)request;
                int id = updateEventRequest.EventDTO.Id;
                Event ev = DTOUtils.getFromDTO(updateEventRequest.EventDTO);
                try
                {
                    server.addEvent(id, ev.GameName, ev.GamePrice, ev.FreeSeats);
                    return new OkResponse();
                }
                catch (BasketException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is GetAllTicketsRequest)
            {
                List<Ticket> list = (List<Ticket>)server.getTickets();
                List<TicketDTO> tickets = new List<TicketDTO>();
                foreach (Ticket ticket in list)
                {
                    TicketDTO ticketDTO = DTOUtils.getDTO(ticket);
                    tickets.Add(ticketDTO);
                }
                return new GetAllTicketsResponse(tickets);
            }
            if (request is AddTicketRequest)
            {
                AddTicketRequest addTicketRequest = (AddTicketRequest)request;
                TicketDTO ticketDTO = ((AddTicketRequest)request).TicketDTO;
                Ticket ticket = DTOUtils.getFromDTO(ticketDTO);
                try
                {
                    server.addTicket(ticket.IdGame, ticket.ReservedSeats, ticket.ClientName);
                    return new OkResponse();
                }
                catch (BasketException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is DeleteTicketRequest)
            {
                Console.WriteLine("Delete ticket request ...");
                DeleteTicketRequest deleteTicketRequest = (DeleteTicketRequest)request;
                int id = deleteTicketRequest.Id;
                try
                {
                    server.deleteTicket(id);
                    return new OkResponse();
                }
                catch (BasketException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }

            return null;
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response + " " + this.username);
            formatter.Serialize(stream, response);
            stream.Flush();
        }

        public void update(List<Event> events)
        {
            List<EventDTO> rez = new List<EventDTO>();
            foreach (Event ev in events)
            {
                rez.Add(DTOUtils.getDTO(ev));
            }

            try
            {
                sendResponse(new NotifyResponse(rez));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}