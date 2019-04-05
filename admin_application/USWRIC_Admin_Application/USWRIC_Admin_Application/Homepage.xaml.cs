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
    public partial class Homepage : Page
    {
        public Homepage()
        {
            InitializeComponent();
        }

        private void BtnStatistics_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new StatisticsHome());
        }

        private void BtnEdit_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new EditAdmin());
        }

        private void BtnUserAdminMgmt_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new UserAdminMgmt());
        }

        private void BtnDisplayMgmt_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new DisplayManagement());
        }

        private void BtnLogout_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new Login());
        }
    }
}