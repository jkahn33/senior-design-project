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

public class equipmentCheckout extends AppCompatActivity {

    public Button manualButton;

    public void init() {

        manualButton = (Button)findViewById(R.id.manualButton);
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next = new Intent(equipmentCheckout.this, barcodeScreen.class);
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
