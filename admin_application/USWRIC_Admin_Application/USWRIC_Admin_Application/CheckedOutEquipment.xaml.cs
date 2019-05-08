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
    /// Interaction logic for CheckedOutEquipment.xaml
    /// </summary>
    public partial class CheckedOutEquipmentPage : Page
    {
        public DataTable CheckedOutEquipmentTable { get; private set; }
        public CheckedOutEquipmentPage()
        {
            FillGrid();
        }

        private async void FillGrid()
        {
            CheckedOutEquipmentTable = new DataTable();

            CheckedOutEquipmentTable.Columns.Add("User");
            CheckedOutEquipmentTable.Columns.Add("Checkout Admin");
            CheckedOutEquipmentTable.Columns.Add("Checkout Date");
            CheckedOutEquipmentTable.Columns.Add("Barcode");
            CheckedOutEquipmentTable.Columns.Add("Equipment");

            var response = await Globals.GetHttpClient().GetAsync(Globals.GetBaseUrl() + "/getCheckedOutEquipment");
            var responseString = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                List<CheckedOutEquipment> equipmentList = JsonConvert.DeserializeObject<List<CheckedOutEquipment>>(responseString);
                foreach (CheckedOutEquipment equip in equipmentList)
                {
                    CheckedOutEquipmentTable.Rows.Add(new object[] { equip.UserName, equip.AdminName, equip.CheckoutDate, equip.Barcode, equip.EquipmentName });
                }
            }

            InitializeComponent();
            CheckedOutEquipmentGrid.DataContext = CheckedOutEquipmentTable.DefaultView;
        }

        private void CheckedOutBack_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new StatisticsHome());
        }
    }
}
