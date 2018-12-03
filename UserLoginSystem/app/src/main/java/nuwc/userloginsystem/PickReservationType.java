package nuwc.userloginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PickReservationType extends AppCompatActivity {

    Button printerRes;
    Button breakoutRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_reservation_type);

        printerRes = (Button) findViewById(R.id.printerRes);
        breakoutRes = (Button) findViewById(R.id.breakoutRes);



        printerRes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(PickReservationType.this, Reservations.class);
                PickReservationType.this.startActivity(myIntent);
            }
        });
        breakoutRes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(PickReservationType.this, BreakoutReservation.class);
                PickReservationType.this.startActivity(myIntent);
            }
        });


    }

}
