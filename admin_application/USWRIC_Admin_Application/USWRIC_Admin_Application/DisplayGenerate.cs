using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TheArtOfDev.HtmlRenderer.WPF;
using System.Windows.Media.Imaging;
using System.IO;
using System.Windows;
using System.Diagnostics;

namespace USWRIC_Admin_Application.util
{
    class DisplayGenerate
    {
        public void GenerateAdminMessagePage()
        {
            string html_string = @"
<head>
  <h1>USWRIC Important Info</h1>
</head>
<body>
    <p> TEST TEST TEST TEST TES TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST</p>
    <hr>
    <p> TEST TEST TEST TEST TES TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST</p>
    <hr>
    <p> TEST TEST TEST TEST TES TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST</p>
    <hr>
    <p> TEST TEST TEST TEST TES TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST</p>
    <hr>
    <p> TEST TEST TEST TEST TES TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST</p>
    <hr>
    <p> TEST TEST TEST TEST TES TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST</p>
    <hr>
    <p> TEST TEST TEST TEST TES TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST</p>
    <hr>
    <p> TEST TEST TEST TEST TES TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST</p>
    <hr>            
    <p> TEST TEST TEST TEST TES TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST</p>
</body>
";
            
            BitmapFrame image = HtmlRender.RenderToImage(html_string);
            var encoder = new PngBitmapEncoder();
            encoder.Frames.Add(image);
            using (FileStream stream = new FileStream(@"C:\Users\jgkah\Desktop\Test.png", FileMode.OpenOrCreate))
                encoder.Save(stream);

            MessageBox.Show("Success", "Error", MessageBoxButton.OK, MessageBoxImage.Information);
        }
    }
}