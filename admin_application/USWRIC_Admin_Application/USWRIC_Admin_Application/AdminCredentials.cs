using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace USWRIC_Admin_Application.objects
{
    class ResponseObject
    {
        public bool Success { get; private set; }
        public string Message { get; private set; }

        public ResponseObject(bool success, string message)
        {
            Success = success;
            Message = message;
        }
    }
}
