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
    /// Interaction logic for Ba.xaml
    /// </summary>
    public partial class Ba : Window
    {
        public Ba()
        {
            InitializeComponent();
        }

        private async void BtnIdSubmit_Click(object sender, RoutedEventArgs e)
        {
            JObject validPassObject = new JObject
            {
                { "string", txtUserId.Text }
            };

            var response = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/userSearchId",
                        new StringContent(validPassObject.ToString(), Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                ResponseObject responseObject = JsonConvert.DeserializeObject<ResponseObject>(responseString);
                if (responseObject.Success)
                {

                }
                else
                {
                    MessageBox.Show(responseObject.Message, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }

        public void DisplayUserInfo()
        {

        }

        private void BtnIdCancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
