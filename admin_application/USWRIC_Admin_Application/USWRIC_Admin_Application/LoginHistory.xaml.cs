using System;
using System.Collections.Generic;
using System.Data;
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
using Newtonsoft.Json;
using USWRIC_Admin_Application.objects;

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for LoginHistory.xaml
    /// </summary>
    public partial class LoginHistory : Page
    {
        public DataTable LoginHistTable { get; private set; }
        public LoginHistory()
        {
            FillGrid();
        }

        private async void FillGrid()
        {
            LoginHistTable = new DataTable();

            LoginHistTable.Columns.Add("Name");
            LoginHistTable.Columns.Add("Badge ID");
            LoginHistTable.Columns.Add("Department");
            LoginHistTable.Columns.Add("Login");

            var response = await Globals.GetHttpClient().GetAsync(Globals.GetBaseUrl() + "/userStatistics");
            var responseString = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                List<UsersStatisticsResponse> userList = JsonConvert.DeserializeObject<List<UsersStatisticsResponse>>(responseString);
                foreach (UsersStatisticsResponse user in userList)
                {
                    LoginHistTable.Rows.Add(new object[] { user.Name, user.BadgeId, user.DepCode, user.LoginDateTime });
                }
            }

            InitializeComponent();
            LoginHistGrid.DataContext = LoginHistTable.DefaultView;
        }

        private void LoginHistBack_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new StatisticsHome());
        }
    }
}
