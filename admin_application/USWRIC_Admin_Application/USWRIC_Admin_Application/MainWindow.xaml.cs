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

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private async void BtnLoginSubmit_Click(object sender, RoutedEventArgs e)
        {
            HttpClient client = Globals.GetHttpClient();

            //var values = new Dictionary<string, string>
            //{
            //   { "ext", txtBadgeId.Text },
            //   { "password", txtPass.Password }
            //};

            //string myJson = "{\"ext\": \"" + txtBadgeId.Text + "\",\"password\":\"" + txtPass.Password + "\"}";

            objects.AdminCredentials adminCredentials = new objects.AdminCredentials();
            adminCredentials.ext = txtBadgeId.Text;
            adminCredentials.password = txtPass.Password;

            string json = JsonConvert.SerializeObject(adminCredentials);

            using (client)
            {
                var response = await client.PostAsync(
                    "http://localhost:8080/windows/validateAdmin",
                     new StringContent(json, Encoding.UTF8, "application/json"));

                var responseString = await response.Content.ReadAsStringAsync();

                System.Diagnostics.Debug.WriteLine(responseString);



            }
        }
    }
}
