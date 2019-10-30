using Networking.Utils;
using Services;
using System;
using System.Windows.Forms;

namespace Client
{
    internal class StartClient
    {
        [STAThread]
        private static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            IServer server = new ServerProxy("127.0.0.1", 55555);
            ClientCtrl ctrl = new ClientCtrl(server);
            LoginInterfaceForm win = new LoginInterfaceForm(ctrl);

            Application.Run(win);
        }
    }
}