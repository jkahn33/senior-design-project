using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace USWRIC_Admin_Application
{
    class Messages
    {
        public int Id { get; private set; }
        public string Message { get; private set; }
        public string MessageEndDate { get; private set; }

        public Messages(int id, string message, string messageEndDate)
        {
            Id = id;
            Message = message;
            MessageEndDate = messageEndDate;
        }
    }
}
