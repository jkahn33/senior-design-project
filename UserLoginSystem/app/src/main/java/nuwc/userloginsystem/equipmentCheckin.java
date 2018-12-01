package nuwc.userloginsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class equipmentCheckin extends AppCompatActivity {

    public Button manualButton2;

    public void init2() {

        manualButton2 = (Button)findViewById(R.id.manualButton2);
        manualButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent next2 = new Intent(equipmentCheckin.this, checkinBarcode.class);
                startActivity(next2);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_checkin);
        init2();
    }
}
