using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace USWRIC_Admin_Application.objects
{
    class CheckedOutEquipment
    {
        public string UserName { get; private set; }
        public string AdminName { get; private set; }
        public string CheckoutDate { get; private set; }
        public string Barcode { get; private set; }
        public string EquipmentName { get; private set; }

        public CheckedOutEquipment(string userName, string adminName, string checkoutDate, string barcode, string equipmentName)
        {
            UserName = userName;
            AdminName = adminName;
            CheckoutDate = checkoutDate;
            Barcode = barcode;
            EquipmentName = equipmentName;
        }
    }
}
