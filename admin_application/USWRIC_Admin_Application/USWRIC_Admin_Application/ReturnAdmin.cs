﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace USWRIC_Admin_Application.objects
{
    class ReturnAdmin
    {
        public string Name { get; private set; }
        public string BadgeId { get; private set; }

        public ReturnAdmin(string name, string badgeId)
        {
            Name = name;
            BadgeId = badgeId;
        }
    }
}
