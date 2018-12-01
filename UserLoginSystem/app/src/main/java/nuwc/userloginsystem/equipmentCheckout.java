package nuwc.userloginsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class equipmentCheckout extends AppCompatActivity {

    public Button manualButton;

    public void init() {

        manualButton = (Button)findViewById(R.id.manualButton);
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prev = getIntent();

                Intent next = new Intent(equipmentCheckout.this, checkoutBarcode.class);
                next.putExtra("adminExt", prev.getStringExtra("adminExt"));
                startActivity(next);
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
