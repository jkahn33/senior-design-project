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
    /// Interaction logic for NewAdmin.xaml
    /// </summary>
    public partial class NewAdmin : Window
    {
        public NewAdmin()
        {
            InitializeComponent();
        }

        private void BtnNewSubmit_Click(object sender, RoutedEventArgs e)
        {
            if(txtNewName.Text.Length == 0)
            {
                MessageBox.Show("Name must not be empty.",
                            "Error",
                            MessageBoxButton.OK,
                            MessageBoxImage.Error);
            }
            else if(txtNewId.Text.Length != 5)
            {
                MessageBox.Show("Badge Id must be five digits.",
                            "Error",
                            MessageBoxButton.OK,
                            MessageBoxImage.Error);
            }
            else if(!txtNewPass.Password.Equals(txtNewPassConfirm.Password))
            {
                MessageBox.Show("Passwords do not match.",
                            "Error",
                            MessageBoxButton.OK,
                            MessageBoxImage.Error);
            }
            else if(txtNewPass.Password.Length == 0)
            {
                MessageBoxResult result = MessageBox.Show("Password is empty. Are you sure you want to set a blank password?",
                            "Confirm",
                            MessageBoxButton.YesNo,
                            MessageBoxImage.Question);

                if(result == MessageBoxResult.Yes)
                {
                    SendNewAdmin();
                }
            }
            else
            {
               SendNewAdmin();
            }
        }

        private void BtnNewCancel_Click(object sender, RoutedEventArgs e)
        {
            UserAdminMgmt userAdminMgmt = new UserAdminMgmt();
            userAdminMgmt.Show();
            this.Close();
        }

        private async void SendNewAdmin()
        {
            JObject o = new JObject
            {
                { "name", txtNewName.Text },
                { "ext", txtNewId.Text },
                { "password", txtNewPass.Password }
            };

            var response = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/newAdmin",
                        new StringContent(o.ToString(), Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                ResponseObject responseObject = JsonConvert.DeserializeObject<ResponseObject>(responseString);
                if (responseObject.Success)
                {
                    MessageBox.Show("Successfully created new administrator.", "Success", MessageBoxButton.OK, MessageBoxImage.Information);
                    Homepage homepage = new Homepage();
                    homepage.Show();
                    this.Close();
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
    }
}
