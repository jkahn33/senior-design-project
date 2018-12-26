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

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for Homepage.xaml
    /// </summary>
    public partial class Homepage : Window
    {
        public Homepage()
        {
            InitializeComponent();
        }

        private void BtnStatistics_Click(object sender, RoutedEventArgs e)
        {

        }

        private void BtnEdit_Click(object sender, RoutedEventArgs e)
        {
            EditAdmin editAdmin = new EditAdmin();
            editAdmin.Show();
            this.Close();
        }

        private void BtnUserAdminMgmt_Click(object sender, RoutedEventArgs e)
        {
            UserAdminMgmt userAdminMgmt = new UserAdminMgmt();
            userAdminMgmt.Show();
            this.Close();
        }

        private void BtnDisplayMgmt_Click(object sender, RoutedEventArgs e)
        {

        }
    }
}