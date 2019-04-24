package nuwc.userloginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class dayBlock  extends AppCompatActivity {

    ConstraintLayout layout;
    TextView bubb;
    TextView bubb1;
    TextView bubb2;
    TextView date;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        layout = (ConstraintLayout) findViewById(R.id.conLayout);



        bubb = (TextView) findViewById(R.id.bubb);
        bubb1 = (TextView) findViewById(R.id.bubb1);
        bubb2 = (TextView) findViewById(R.id.bubb2);
        date = (TextView) findViewById(R.id.date);






    }

    public void invisNotification(){
        bubb.setVisibility(View.INVISIBLE);
        bubb1.setVisibility(View.INVISIBLE);
        bubb2.setVisibility(View.INVISIBLE);


    }
}
