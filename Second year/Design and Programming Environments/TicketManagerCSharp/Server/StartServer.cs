using log4net.Config;
using Networking.Utils;
using Persistence;
using Services;
using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Threading;

namespace Server
{
    internal class StartServer
    {
        private static void Main(string[] args)
        {

            TicketRepository ticketRepo = new TicketRepository();
            EventRepository eventRepo = new EventRepository();
            UserRepository userRepo = new UserRepository();
            IServer serviceImpl = new ServerImpl(userRepo, ticketRepo, eventRepo);

            SerialServer server = new SerialServer("127.0.0.1", 55555, serviceImpl);
            server.Start();
            Console.WriteLine("Server started ...");
            Console.ReadLine();
        }

        public class SerialServer : ConcurrentServer
        {
            private IServer server;
            private ClientObjectWorker worker;

            public SerialServer(string host, int port, IServer server) : base(host, port)
            {
                this.server = server;
                Console.WriteLine("ChatServer...");
            }

            protected override Thread createWorker(TcpClient client)
            {
                worker = new ClientObjectWorker(server, client);
                return new Thread(new ThreadStart(worker.run));
            }
        }
    }
}