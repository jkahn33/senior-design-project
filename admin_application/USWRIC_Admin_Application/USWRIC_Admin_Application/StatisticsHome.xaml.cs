﻿using System;
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
    /// Interaction logic for StatisticsHome.xaml
    /// </summary>
    public partial class StatisticsHome : Page
    {
        public StatisticsHome()
        {
            InitializeComponent();
        }

        private void BtnEqueipmentUsage_Click(object sender, RoutedEventArgs e)
        {

        }

        private void BtnCheckedOutEquipment_Click(object sender, RoutedEventArgs e)
        {

        }

        private void BtnRoomUsage_Click(object sender, RoutedEventArgs e)
        {

        }

        private void BtnStatisticsCancel_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new Homepage());
        }
    }
}
