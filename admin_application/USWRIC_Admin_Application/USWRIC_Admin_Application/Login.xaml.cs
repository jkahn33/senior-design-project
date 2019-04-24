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
using Newtonsoft.Json.Linq;

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for Login.xaml
    /// </summary>
    public partial class Login : Page
    {
        public Login()
        {
            InitializeComponent();
        }
        private async void BtnLoginSubmit_Click(object sender, RoutedEventArgs e)
        {
            HttpClient client = Globals.GetHttpClient();

            JObject o = new JObject();
            o.Add("ext", txtBadgeId.Text);
            o.Add("password", txtPass.Password);

            try
            {
                var response = await client.PostAsync(
                        Globals.GetBaseUrl() + "/validateAdmin",
                         new StringContent(o.ToString(), Encoding.UTF8, "application/json"));

                var responseString = await response.Content.ReadAsStringAsync();
                if (response.IsSuccessStatusCode)
                {
                    Globals.BadgeId = txtBadgeId.Text;
                    this.NavigationService.Navigate(new Homepage());
                }
                else
                {
                    MessageBox.Show("Incorrect Badge Id or Password", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
            catch (HttpRequestException)
            {
                MessageBox.Show("Server Connection Refused", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
