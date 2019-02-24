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
    /// Interaction logic for ChangePassword.xaml
    /// </summary>
    public partial class ChangePassword : Page
    {
        public ChangePassword()
        {
            InitializeComponent();
        }

        private async void BtnEditSubmit_Click(object sender, RoutedEventArgs e)
        {
            JObject validPassObject = new JObject
            {
                { "ext", Globals.BadgeId },
                { "password", txtEditPassOld.Password }
            };

            var validPass = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/validateAdmin",
                        new StringContent(validPassObject.ToString(), Encoding.UTF8, "application/json"));

            var responseString = await validPass.Content.ReadAsStringAsync();

            if (validPass.IsSuccessStatusCode && bool.Parse(responseString))
            {
                if (!txtEditPassNew.Password.Equals(txtEditPassConfirm.Password))
                {
                    MessageBox.Show("New password and confirm password do not match.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
                else
                {
                    if(txtEditPassNew.Password.Length == 0)
                    {
                        MessageBoxResult result = MessageBox.Show("New password is empty. Are you sure you want to set a blank password?", 
                            "Confirm", 
                            MessageBoxButton.YesNo, 
                            MessageBoxImage.Question);
                        if(result == MessageBoxResult.Yes)
                        {
                            SendPasswordChange();
                        }
                    }
                    else
                    {
                        MessageBoxResult result = MessageBox.Show("Are your sure you want to change your password?",
                            "Confirm",
                            MessageBoxButton.YesNo,
                            MessageBoxImage.Question);
                        if (result == MessageBoxResult.Yes)
                        {
                            SendPasswordChange();
                        }
                    }                    
                }
            }
            else
            {
                MessageBox.Show("Old password is incorrect.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
            
        }

        private void BtnEditCancel_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new Homepage());
        }

        private async void SendPasswordChange()
        {
            JObject o = new JObject
            {
                { "oldExt", Globals.BadgeId },
                { "newPass", txtEditPassNew.Password }
            };

            var response = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/editAdmin",
                        new StringContent(o.ToString(), Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                ResponseObject responseObject = JsonConvert.DeserializeObject<ResponseObject>(responseString);
                if (responseObject.Success)
                {
                    MessageBox.Show("Successfully changed password.", "Success", MessageBoxButton.OK, MessageBoxImage.Information);
                    this.NavigationService.Navigate(new Homepage());
                }

            }
        }
    }
}
