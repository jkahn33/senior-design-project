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
    /// Interaction logic for EditAdmin.xaml
    /// </summary>
    public partial class EditAdmin : Page
    {
        public EditAdmin()
        {
            InitializeComponent();
            FillInfo();
        }
        public async void FillInfo()
        {
            HttpClient client = Globals.GetHttpClient();

            JObject o = new JObject();
            o.Add("oldExt", Globals.BadgeId);

            var response = await client.PostAsync(
                    Globals.GetBaseUrl() + "/getAdmin",
                    new StringContent(o.ToString(), Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                ReturnAdmin returnAdmin = JsonConvert.DeserializeObject<ReturnAdmin>(responseString);

                txtEditName.Text = returnAdmin.Name;
                txtEditBadgeId.Text = returnAdmin.FiveDigExt;
            }
        }

        private async void BtnEditSubmit_Click(object sender, RoutedEventArgs e)
        {
            if (txtEditBadgeId.Text.Length != 5)
            {
                MessageBox.Show("Badge Id must be 5 digits.",
                "Error",
                MessageBoxButton.OK,
                MessageBoxImage.Error);
            }
            else
            {
                MessageBoxResult boxResult = MessageBox.Show("Are you sure you want to make these changes?",
                    "Confirm",
                    MessageBoxButton.YesNo,
                    MessageBoxImage.Question);

                if (boxResult == MessageBoxResult.Yes)
                {
                    JObject o = new JObject();
                    o.Add("oldExt", Globals.BadgeId);
                    o.Add("name", txtEditName.Text);
                    o.Add("ext", txtEditBadgeId.Text);

                    var response = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/editAdmin",
                        new StringContent(o.ToString(), Encoding.UTF8, "application/json"));

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
                            MessageBox.Show("Successfully changed information.", "Success", MessageBoxButton.OK, MessageBoxImage.Information);

                            this.NavigationService.Navigate(new Homepage());
                        }
                    }
                }
            }
        }

        private void BtnEditCancel_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new Homepage());
        }

        private void BtnChangePassword_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new ChangePassword());
        }
    }
}
