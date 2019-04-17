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
    /// Interaction logic for UserSearch.xaml
    /// </summary>
    public partial class UserSearch : Page
    {
        public UserSearch()
        {
            InitializeComponent();
        }

        private async void BtnUserSearchExe_Click(object sender, RoutedEventArgs e)
        {
            JObject o = new JObject
            {
                { "string", txtSearchCriteria.Text }
            };

            var response = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/getSpecificUser",
                        new StringContent(o.ToString(), Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                SpecificUserResponse userResponse = JsonConvert.DeserializeObject<SpecificUserResponse>(responseString);
                if (userResponse.Found)
                {
                    lblDept.Visibility = Visibility.Visible;
                    lblSince.Visibility = Visibility.Visible;
                    lblEntered.Visibility = Visibility.Visible;
                    lblLastRecorded.Visibility = Visibility.Visible;

                    tblockUserName.Text = userResponse.Name;
                    tblockDept.Text = userResponse.Dept;
                    tblockUserSince.Text = userResponse.CreationDate;
                    tblockTimesEntered.Text = userResponse.Occurrences;
                    tblockLastRecorded.Text = userResponse.LastEntered;
                }
                else
                {
                    MessageBox.Show("Cannot find a user with search criteria " + txtSearchCriteria.Text,
                           "Not found",
                           MessageBoxButton.OK,
                           MessageBoxImage.Error);
                }
            }
        }
    }
}
