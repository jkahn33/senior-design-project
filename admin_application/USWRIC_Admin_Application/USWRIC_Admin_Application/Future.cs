using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace USWRIC_Admin_Application
{
    class Future
    {
        public int Id { get; private set; }
        public string FutureMessage { get; private set; }
        public string StartDate { get; private set; }
        public string EndDate { get; private set; }

        public Future(int id, string futureMessage, string startDate, string endDate)
        {
            Id = id;
            FutureMessage = futureMessage;
            StartDate = startDate;
            EndDate = endDate;
        }
    }
}
