using System;
using System.Collections.Generic;
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
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Net.Http;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using USWRIC_Admin_Application.objects;

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for DisplayManagement.xaml
    /// </summary>
    public partial class DisplayManagement : Page
    {
        public DisplayManagement()
        {
            InitializeComponent();
        }

        private async void BtnMessageSubmit_Click(object sender, RoutedEventArgs e)
        {
            if (Int32.TryParse(txtMessageDays.Text, out int duration))
            {
                JObject validPassObject = new JObject
                {
                    { "message", txtAdminMessage.Text },
                    { "duration", duration },
                    { "adminID", Globals.BadgeId }
                };

                var response = await Globals.GetHttpClient().PostAsync(
                            Globals.GetBaseUrl() + "/newMessage",
                            new StringContent(validPassObject.ToString(), Encoding.UTF8, "application/json"));

                var responseString = await response.Content.ReadAsStringAsync();

                if (response.IsSuccessStatusCode)
                {
                    ResponseObject responseObject = JsonConvert.DeserializeObject<ResponseObject>(responseString);
                    if (responseObject.Success)
                    {
                        //txtAdminMessage.Text = "";
                        //txtMessageDays.Text = "";
                        MessageBox.Show("Successfully added message.", "Success", MessageBoxButton.OK, MessageBoxImage.Information);
                    }
                    else
                    {
                        MessageBox.Show(responseObject.Message,
                                "Error",
                                MessageBoxButton.OK,
                                MessageBoxImage.Error);
                    }
                }
                else
                {
                    MessageBox.Show("HTTP error code " + response.StatusCode,
                                "Error",
                                MessageBoxButton.OK,
                                MessageBoxImage.Error);
                }
            }
            else
            {
                MessageBox.Show("Message duration must be whole number.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void BtnBack_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new Homepage());
        }

        private void BtnQueuedEvents_Click(object sender, RoutedEventArgs e)
        {

        }

        private void BtnEventSubmit_Click(object sender, RoutedEventArgs e)
        {
            
        }
    }
}
