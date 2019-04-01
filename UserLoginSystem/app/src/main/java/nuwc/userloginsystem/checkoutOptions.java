package nuwc.userloginsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class checkoutOptions extends AppCompatActivity {

    public Button addEquipment;
    public Button checkoutEquipment;
    public Button checkinEquipment;
    public Button back;

    public void add(){

        addEquipment = (Button)findViewById(R.id.addEquipment);
        addEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent add = new Intent(checkoutOptions.this, /*adminAddEquip*/addEquipment.class);
                checkoutOptions.this.startActivity(add);

            }
        });
    }

    public void checkout(){

        checkoutEquipment = (Button)findViewById(R.id.checkoutEquipment);
        checkoutEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent checkout = new Intent(checkoutOptions.this, adminCheckout.class);
                checkoutOptions.this.startActivity(checkout);

            }
        });
    }


    public void checkin(){

        checkinEquipment = (Button)findViewById(R.id.checkinEquipment);
        checkinEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent checkin = new Intent(checkoutOptions.this, /*adminCheckin*/checkinBarcode.class);
                checkoutOptions.this.startActivity(checkin);

            }
        });
    }

    public void cancel(){

        back = (Button)findViewById(R.id.btnCheckoutBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent back = new Intent(checkoutOptions.this, initialScreen.class);
                checkoutOptions.this.startActivity(back);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_options);
        add();
        checkout();
        checkin();
        cancel();
    }
}
