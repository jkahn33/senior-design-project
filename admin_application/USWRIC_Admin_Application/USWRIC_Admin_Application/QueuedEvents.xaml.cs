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
using System.Net.Http;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using USWRIC_Admin_Application.objects;

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for QueuedEvents.xaml
    /// </summary>
    public partial class QueuedEvents : Window
    {
        public QueuedEvents()
        {
            InitializeComponent();
            FillGrid();
        }

        public async void FillGrid()
        {
            var response = await Globals.GetHttpClient().GetAsync(Globals.GetBaseUrl() + "/getFutures");
            var responseString = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                List<Future> futureList = JsonConvert.DeserializeObject<List<Future>>(responseString);

                ColumnDefinition futureCol = new ColumnDefinition();
                ColumnDefinition startCol = new ColumnDefinition();
                ColumnDefinition endCol = new ColumnDefinition();
                ColumnDefinition checkboxCol = new ColumnDefinition();
                ColumnDefinition editCol = new ColumnDefinition();

                futureGrid.ColumnDefinitions.Add(futureCol);
                futureGrid.ColumnDefinitions.Add(startCol);
                futureGrid.ColumnDefinitions.Add(endCol);
                futureGrid.ColumnDefinitions.Add(checkboxCol);
                futureGrid.ColumnDefinitions.Add(editCol);

                foreach (Future future in futureList)
                {
                    RowDefinition currRow = new RowDefinition
                    {
                        Height = new GridLength(50)
                    };
                    futureGrid.RowDefinitions.Add(currRow);
                }
                for (int i = 0; i < futureList.Count; i++)
                {
                    Future future = futureList.ElementAt(i);
                    TextBlock messageBlock = new TextBlock
                    {
                        Name = "FutureBlock_" + future.Id,
                        Text = future.FutureMessage,
                        FontFamily = new FontFamily("Arial Black"),
                        Foreground = new SolidColorBrush(Colors.White)
                    };
                    Grid.SetRow(messageBlock, i);
                    Grid.SetColumn(messageBlock, 0);
                    futureGrid.Children.Add(messageBlock);

                    TextBlock startBlock = new TextBlock
                    {
                        Name = "StartBlock_" + future.Id,
                        Text = "Starts: " + future.StartDate,
                        FontFamily = new FontFamily("Arial Black"),
                        Foreground = new SolidColorBrush(Colors.White)
                    };
                    Grid.SetRow(startBlock, i);
                    Grid.SetColumn(startBlock, 1);
                    futureGrid.Children.Add(startBlock);

                    TextBlock endBlock = new TextBlock
                    {
                        Name = "EndBlock_" + future.Id,
                        Text = "Ends: " + future.EndDate,
                        FontFamily = new FontFamily("Arial Black"),
                        Foreground = new SolidColorBrush(Colors.White)
                    };
                    Grid.SetRow(endBlock, i);
                    Grid.SetColumn(endBlock, 1);
                    futureGrid.Children.Add(endBlock);

                    CheckBox deleteBox = new CheckBox
                    {
                        Name = "chkBox_" + future.Id
                    };
                    Grid.SetRow(deleteBox, i);
                    Grid.SetColumn(deleteBox, 2);
                    futureGrid.Children.Add(deleteBox);

                    Button editButton = new Button
                    {
                        Content = "Edit"
                    };
                    Grid.SetRow(editButton, i);
                    Grid.SetColumn(editButton, 3);
                    futureGrid.Children.Add(editButton);
                }
            }

        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {

        }
    }
}
