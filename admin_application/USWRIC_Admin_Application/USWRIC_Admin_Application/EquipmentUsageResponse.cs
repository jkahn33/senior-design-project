using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace USWRIC_Admin_Application.objects
{
    class EquipmentUsageResponse
    {
        public string Barcode { get; private set; }
        public string EquipmentName { get; private set; }
        public int AmtUsed { get; private set; }
        public string LastCheckedOut { get; private set; }
        public string DateAdded { get; private set; }

        public EquipmentUsageResponse(string barcode, string equipmentName, int amtUsed, string lastCheckedOut, string dateAdded)
        {
            Barcode = barcode;
            EquipmentName = equipmentName;
            AmtUsed = amtUsed;
            LastCheckedOut = lastCheckedOut;
            DateAdded = dateAdded;
        }
    }
}
