package nuwc.userloginsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class equipmentCheckout extends AppCompatActivity {

    private Button manualButton;
    private Button cancel;

    public void init() {

        manualButton = (Button)findViewById(R.id.manualButton);
        cancel = (Button) findViewById(R.id.btnCheckoutChoiceCancel);

        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prev = getIntent();

                Intent next = new Intent(equipmentCheckout.this, checkoutBarcode.class);
                next.putExtra("adminExt", prev.getStringExtra("adminExt"));
                equipmentCheckout.this.startActivity(next);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancel = new Intent(equipmentCheckout.this, checkoutOptions.class);
                equipmentCheckout.this.startActivity(cancel);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_checkout);
        init();
    }
}
