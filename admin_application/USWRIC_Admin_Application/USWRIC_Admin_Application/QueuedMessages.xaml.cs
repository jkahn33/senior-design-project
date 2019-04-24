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
using System.Net.Http;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using USWRIC_Admin_Application.objects;

namespace USWRIC_Admin_Application
{
    /// <summary>
    /// Interaction logic for QueuedMessages.xaml
    /// </summary>
    public partial class QueuedMessages : Window
    {
        public QueuedMessages()
        {
            InitializeComponent();
            FillMessages();
        }
        public async void FillMessages()
        {
            var response = await Globals.GetHttpClient().GetAsync(Globals.GetBaseUrl() + "/getMessages");
            var responseString = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                List<Messages> messageList = JsonConvert.DeserializeObject<List<Messages>>(responseString);
                ColumnDefinition messageCol = new ColumnDefinition();
                ColumnDefinition durationCol = new ColumnDefinition();
                ColumnDefinition checkboxCol = new ColumnDefinition();
                ColumnDefinition editCol = new ColumnDefinition();

                messagesGrid.ColumnDefinitions.Add(messageCol);
                messagesGrid.ColumnDefinitions.Add(durationCol);
                messagesGrid.ColumnDefinitions.Add(checkboxCol);
                messagesGrid.ColumnDefinitions.Add(editCol);
                
                foreach (Messages message in messageList)
                {
                    RowDefinition currRow = new RowDefinition
                    {
                        Height = new GridLength(50)
                    };
                    messagesGrid.RowDefinitions.Add(currRow);
                }
                for (int i = 0; i < messageList.Count; i++)
                {
                    Messages message = messageList.ElementAt(i);
                    TextBlock messageBlock = new TextBlock
                    {
                        Name = "MessageBlock_" + message.Id,
                        Text = message.Message,
                        FontFamily = new FontFamily("Arial Black"),
                        Foreground = new SolidColorBrush(Colors.White)
                    };
                    Grid.SetRow(messageBlock, i);
                    Grid.SetColumn(messageBlock, 0);
                    messagesGrid.Children.Add(messageBlock);

                    TextBlock durationBlock = new TextBlock
                    {
                        Name = "DurationBlock_" + message.Id,
                        Text = "Until: " + message.MessageEndDate,
                        FontFamily = new FontFamily("Arial Black"),
                        Foreground = new SolidColorBrush(Colors.White)
                    };
                    Grid.SetRow(durationBlock, i);
                    Grid.SetColumn(durationBlock, 1);
                    messagesGrid.Children.Add(durationBlock);

                    CheckBox deleteBox = new CheckBox
                    {
                        Name = "chkBox_" + message.Id
                    };
                    Grid.SetRow(deleteBox, i);
                    Grid.SetColumn(deleteBox, 2);
                    messagesGrid.Children.Add(deleteBox);

                    Button editButton = new Button
                    {
                        Content = "Edit"
                    };
                    Grid.SetRow(editButton, i);
                    Grid.SetColumn(editButton, 3);
                    messagesGrid.Children.Add(editButton);
                }
            }

        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            List<int> deletionList = new List<int>();
            foreach (var checkBox in messagesGrid.Children
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
            messagesGrid.IsEnabled = false;
            var response = await Globals.GetHttpClient().PostAsync(
                        Globals.GetBaseUrl() + "/deleteMessagesById",
                        new StringContent(json, Encoding.UTF8, "application/json"));

            var responseString = await response.Content.ReadAsStringAsync();
            //hides loading bar and renables content
            WorkingBar.Visibility = Visibility.Hidden;
            messagesGrid.IsEnabled = true;
            if (response.IsSuccessStatusCode)
            {
                ResponseObject responseObject = JsonConvert.DeserializeObject<ResponseObject>(responseString);
                if (responseObject.Success)
                {
                    FillMessages();
                    MessageBox.Show("Successfully deleted messages.", "Success", MessageBoxButton.OK, MessageBoxImage.Information);
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
