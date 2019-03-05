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
using System.Windows.Shapes;
using Newtonsoft.Json;
using USWRIC_Admin_Application.objects;

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for EquipmentUsage.xaml
    /// </summary>
    public partial class EquipmentUsage : Page
    {
        public DataTable StatsTable { get; private set; }
        public EquipmentUsage()
        {
            FillGrid();
        }

        private async void FillGrid()
        {
            StatsTable = new DataTable();

            StatsTable.Columns.Add("Equipment Name");
            StatsTable.Columns.Add("Barcode");
            StatsTable.Columns.Add("Amount Used");
            StatsTable.Columns.Add("Date Added");
            StatsTable.Columns.Add("Last Checked Out");

            var response = await Globals.GetHttpClient().GetAsync(Globals.GetBaseUrl() + "/equipmentUsage");
            var responseString = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                List<EquipmentUsageResponse> equipmentList = JsonConvert.DeserializeObject<List<EquipmentUsageResponse>>(responseString);
                foreach (EquipmentUsageResponse equipment in equipmentList)
                {
                    StatsTable.Rows.Add(new object[] { equipment.EquipmentName, equipment.Barcode, equipment.AmtUsed, equipment.DateAdded, equipment.LastCheckedOut });
                }
            }

            InitializeComponent();
            StatsGrid.DataContext = StatsTable.DefaultView;
        }
    }
}
