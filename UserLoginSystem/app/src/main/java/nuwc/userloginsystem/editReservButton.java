package nuwc.userloginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;



public class editReservButton  extends AppCompatActivity {

    Button delete;
    ConstraintLayout parentLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reservation_button);

        delete = (Button) findViewById(R.id.deleteButton);
        parentLayout = (ConstraintLayout) findViewById(R.id.reservationPlate);
    }


}
