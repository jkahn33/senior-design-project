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
using System.Data;

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for EquipmentUsage.xaml
    /// </summary>
    public partial class EquipmentUsage : Page
    {
        public EquipmentUsage()
        {
            InitializeComponent();

            FillGrid();
        }
        private void FillGrid()
        {
            DataTable table = new DataTable();

            // create "fixed" columns
            table.Columns.Add("id");
            table.Columns.Add("image");

            // create custom columns
            table.Columns.Add("Name1");
            table.Columns.Add("Name2");

            // add one row as an object array
            table.Rows.Add(new object[] { 123, "image.png", "Foo", "Bar" });
        }
    }
}
