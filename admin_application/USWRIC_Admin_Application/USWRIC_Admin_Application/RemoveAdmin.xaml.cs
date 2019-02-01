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
    /// Interaction logic for RemoveAdmin.xaml
    /// </summary>
    public partial class RemoveAdmin : Window
    {
        public RemoveAdmin()
        {
            InitializeComponent();
        }

        private async void BtnRemoveAdminSubmit_Click(object sender, RoutedEventArgs e)
        {
            JObject validPassObject = new JObject
            {
                { "string", txtRemoveAdminId.Text }
            };

            var response = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/getAdminName",
                        new StringContent(validPassObject.ToString(), Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                ResponseObject responseObject = JsonConvert.DeserializeObject<ResponseObject>(responseString);
                if (responseObject.Success)
                {
                    MessageBoxResult result = MessageBox.Show("Are you sure you want to remove " + responseObject.Message + " (badge ID " + txtRemoveAdminId.Text + ") as an administrator?",
                        "Confirm",
                        MessageBoxButton.YesNo,
                        MessageBoxImage.Question);

                    if (result == MessageBoxResult.Yes)
                    {
                        SendDeletion();
                    }
                }
                else
                {
                    MessageBoxResult result = MessageBox.Show("Admin with badge ID " + txtRemoveAdminId.Text + " does not exist.",
                       "Error",
                       MessageBoxButton.OK,
                       MessageBoxImage.Error);
                }
            }
        }

        private void BtnRemoveAdminCancel_Click(object sender, RoutedEventArgs e)
        {
            UserAdminMgmt userAdminMgmt = new UserAdminMgmt();
            userAdminMgmt.Show();
            this.Close();
        }

        private async void SendDeletion()
        {
            JObject validPassObject = new JObject
            {
                { "string", txtRemoveAdminId.Text }
            };

            var response = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/removeAdmin",
                        new StringContent(validPassObject.ToString(), Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                ResponseObject responseObject = JsonConvert.DeserializeObject<ResponseObject>(responseString);
                if (responseObject.Success)
                {
                    MessageBox.Show("Successfully removed administrator.", "Success", MessageBoxButton.OK, MessageBoxImage.Information);
                    Homepage homepage = new Homepage();
                    homepage.Show();
                }
                else
                {
                    MessageBox.Show(responseObject.Message, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }
    }
}
