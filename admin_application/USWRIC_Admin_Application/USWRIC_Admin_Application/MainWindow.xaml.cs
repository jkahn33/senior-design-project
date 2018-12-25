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
using USWRIC_Admin_Application.objects;

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

            JObject o = new JObject();
            o.Add("ext", txtBadgeId.Text);
            o.Add("password", txtPass.Password);

            var response = await client.PostAsync(
                    Globals.GetBaseUrl() + "/validateAdmin",
                     new StringContent(o.ToString(), Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                Globals.BadgeId = txtBadgeId.Text;

                Homepage homepage = new Homepage();
                homepage.Show();
                this.Close();
            }
            else
            {
                MessageBox.Show("Incorrect Badge Id or Password", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
