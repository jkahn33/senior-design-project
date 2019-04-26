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

                futureGrid.ColumnDefinitions.Add(futureCol);
                futureGrid.ColumnDefinitions.Add(startCol);
                futureGrid.ColumnDefinitions.Add(endCol);
                futureGrid.ColumnDefinitions.Add(checkboxCol);

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
                    Grid.SetColumn(endBlock, 2);
                    futureGrid.Children.Add(endBlock);

                    CheckBox deleteBox = new CheckBox
                    {
                        Name = "chkBox_" + future.Id
                    };
                    Grid.SetRow(deleteBox, i);
                    Grid.SetColumn(deleteBox, 3);
                    futureGrid.Children.Add(deleteBox);
                }
            }

        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            List<int> deletionList = new List<int>();
            foreach (var checkBox in futureGrid.Children
                .OfType<CheckBox>()
                .Where(cb => (bool)cb.IsChecked))
            {
                deletionList.Add(Convert.ToInt32(checkBox.Name.Split('_')[1]));
            }
            int[] deletionArr = deletionList.ToArray<int>();
            var json = JsonConvert.SerializeObject(new
            {
                ids = deletionArr
            });
            //shows loading bar and disables page content
            WorkingBar.Visibility = Visibility.Visible;
            futureGrid.IsEnabled = false;
            var response = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/deleteFuturesById",
                        new StringContent(json, Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();
            //hides loading bar and renables content
            WorkingBar.Visibility = Visibility.Hidden;
            futureGrid.IsEnabled = true;
            if (response.IsSuccessStatusCode)
            {
                ResponseObject responseObject = JsonConvert.DeserializeObject<ResponseObject>(responseString);
                if (responseObject.Success)
                {
                    MessageBox.Show("Successfully deleted events.", "Success", MessageBoxButton.OK, MessageBoxImage.Information);
                    this.Close();
                }
                else
                {
                    MessageBox.Show(responseObject.Message,
                                    "Error",
                                    MessageBoxButton.OK,
                                    MessageBoxImage.Error);
                }
            }
            else
            {
                MessageBox.Show(response.StatusCode.ToString(),
                                    "Error",
                                    MessageBoxButton.OK,
                                    MessageBoxImage.Error);
            }
        }
    }
}
