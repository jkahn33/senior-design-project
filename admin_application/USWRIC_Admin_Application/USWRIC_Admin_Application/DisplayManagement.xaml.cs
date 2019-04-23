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

        private async void BtnEventSubmit_Click(object sender, RoutedEventArgs e)
        {
            string[] startVals = dteEventStart.Text.Split('/');
            string[] endVals = dteEventEnd.Text.Split('/');

            try
            {
                DateTime startDate = new DateTime(Convert.ToInt32(startVals[2]), Convert.ToInt32(startVals[0]), Convert.ToInt32(startVals[1]));
                DateTime endDate = new DateTime(Convert.ToInt32(endVals[2]), Convert.ToInt32(endVals[0]), Convert.ToInt32(endVals[1]));


                int startEndCheck = DateTime.Compare(endDate, startDate);
                int todayCheck = DateTime.Compare(startDate, DateTime.Today);

                if (todayCheck < 0)
                {
                    MessageBox.Show("Event must start on or after today, " + DateTime.Today.ToString().Split(' ')[0] + ".", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
                else if (startEndCheck < 0)
                {
                    MessageBox.Show("Event end must be after event start date.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
                else
                {
                    JObject validPassObject = new JObject
                {
                    { "future", txtFutureEvent.Text },
                    { "startDate", startDate.ToString("yyyy-MM-dd HH:mm:ss") },
                    { "endDate", endDate.ToString("yyyy-MM-dd HH:mm:ss") },
                    { "adminID", Globals.BadgeId }
                };

                    var response = await Globals.GetHttpClient().PostAsync(
                                Globals.GetBaseUrl() + "/newFuture",
                                new StringContent(validPassObject.ToString(), Encoding.UTF8, "application/json"));

                    var responseString = await response.Content.ReadAsStringAsync();
                    if (response.IsSuccessStatusCode)
                    {
                        MessageBox.Show("Success", "success", MessageBoxButton.OK, MessageBoxImage.Information);
                    }
                    else
                    {
                        MessageBox.Show(response.StatusCode.ToString(), "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                    }

                }
            }
            catch(IndexOutOfRangeException)
            {
                MessageBox.Show("Start and end date must be provided.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            
        }

        private void BtnQueuedMessages_Click(object sender, RoutedEventArgs e)
        {
            QueuedMessages messages = new QueuedMessages();
            messages.Show();
        }
    }
}
