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
    /// Interaction logic for UserAdminMgmt.xaml
    /// </summary>
    public partial class UserAdminMgmt : Page
    {
        public UserAdminMgmt()
        {
            InitializeComponent();
        }

        private void BtnNewAdmin_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new NewAdmin());
        }

        private void BtnRemoveUser_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new RemoveUser());
        }

        private void BtnRemoveAdmin_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new RemoveAdmin());
        }
        private void BtnUserAdminMgmtCancel_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new Homepage());
        }
    }
}
