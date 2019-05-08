using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace USWRIC_Admin_Application.objects
{
    class SpecificUserResponse
    {
        public bool Found { get; private set; }
        public string Name { get; private set; }
        public string Dept { get; private set; }
        public string Occurrences { get; private set; }
        public string LastEntered { get; private set; }
        public string CreationDate { get; private set; }

        public SpecificUserResponse(bool found, string name, string dept, string occurrences, string lastEntered, string creationDate)
        {
            Found = found;
            Name = name;
            Dept = dept;
            Occurrences = occurrences;
            LastEntered = lastEntered;
            CreationDate = creationDate;
        }
    }
}
