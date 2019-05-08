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
using System.Windows.Shapes;
using System.Net.Http;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using USWRIC_Admin_Application.objects;

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for NewPi.xaml
    /// </summary>
    public partial class NewPi : Window
    {
        public NewPi()
        {
            InitializeComponent();
        }

        private async void BtnNewPiSubmit_Click(object sender, RoutedEventArgs e)
        {
            JObject validPassObject = new JObject
            {
                { "user", txtUser.Text },
                { "ip", txtIp.Text },
                { "password", txtPass.Password }
            };

            var response = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/addPi",
                        new StringContent(validPassObject.ToString(), Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                ResponseObject responseObject = JsonConvert.DeserializeObject<ResponseObject>(responseString);

                if (!responseObject.Success)
                {
                    MessageBox.Show(responseObject.Message, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
                else
                {
                    MessageBox.Show("Successfully added new Raspberry Pi.", "Success", MessageBoxButton.OK, MessageBoxImage.Information);

                    this.Close();
                }
            }
            else
            {
                MessageBox.Show("Request error.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void BtnNewPiCancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
