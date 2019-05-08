using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace USWRIC_Admin_Application.objects
{
    class UsersStatisticsResponse
    {
        public string Name { get; private set; }
        public string DepCode { get; private set; }
        public string LoginDateTime { get; private set; }
        public string BadgeId { get; private set; }

        public UsersStatisticsResponse(string name, string depCode, string loginDateTime, string badgeId)
        {
            Name = name;
            DepCode = depCode;
            LoginDateTime = loginDateTime;
            BadgeId = badgeId;
        }
    }
}
