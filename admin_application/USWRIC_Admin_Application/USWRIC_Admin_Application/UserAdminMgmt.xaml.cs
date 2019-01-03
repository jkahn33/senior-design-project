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
    public partial class UserAdminMgmt : Window
    {
        public UserAdminMgmt()
        {
            InitializeComponent();
        }

        private void BtnNewAdmin_Click(object sender, RoutedEventArgs e)
        {
            NewAdmin newAdmin = new NewAdmin();
            newAdmin.Show();
            this.Close();
        }

        private void BtnRemoveUser_Click(object sender, RoutedEventArgs e)
        {
            RemoveUser removeUser = new RemoveUser();
            removeUser.Show();
            this.Close();
        }
    }
}
