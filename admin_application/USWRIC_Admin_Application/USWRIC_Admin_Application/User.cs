﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace USWRIC_Admin_Application.objects
{
    class User
    {
        public string Name { get; private set; }
        public string CreationDate { get; private set; }
        public string BadgeId { get; private set; }
        public string DepCode { get; private set; }

        public User(string name, string creationDate, string badgeId, string depCode)
        {
            Name = name;
            CreationDate = creationDate;
            BadgeId = badgeId;
            DepCode = depCode;
        }
    }
}
