using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using Newtonsoft.Json;
using USWRIC_Admin_Application.objects;

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for QueuedMessages.xaml
    /// </summary>
    public partial class QueuedMessages : Window
    {
        public QueuedMessages()
        {
            InitializeComponent();
            FillMessages();
        }
        public async void FillMessages()
        {
            var response = await Globals.GetHttpClient().GetAsync(Globals.GetBaseUrl() + "/getMessages");
            var responseString = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                List<Messages> messageList = JsonConvert.DeserializeObject<List<Messages>>(responseString);
                foreach (Messages message in messageList)
                {
                    DockPanel panel = new DockPanel
                    {
                        
                    };

                    
                    
                }
            }

        }
    }
}
