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
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Net.Http;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using USWRIC_Admin_Application.objects;

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for PrinterUsageStatistics.xaml
    /// </summary>
    public partial class PrinterUsageStatistics : Page
    {
        public PrinterUsageStatistics()
        {
            InitializeComponent();
        }
        private async void fillStatistics()
        {
            
        }

        private void BtnBack_Click(object sender, RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new StatisticsHome());
        }
    }
}