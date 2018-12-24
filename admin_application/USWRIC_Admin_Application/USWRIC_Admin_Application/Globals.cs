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

        public static HttpClient GetHttpClient()
        {
            return client;
        }
    }
}
