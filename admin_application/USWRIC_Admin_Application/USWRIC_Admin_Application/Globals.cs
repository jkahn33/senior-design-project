using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;

namespace USWRIC_Admin_Application
{
    class Globals
    {
        private static readonly HttpClient client = new HttpClient();
        private static readonly string baseUrl = "http://localhost:8080/windows";

        public static string BadgeId { get; set; }

        public static HttpClient GetHttpClient()
        {
            return client;
        }
        public static string GetBaseUrl()
        {
            return baseUrl;
        }
        
    }
}
