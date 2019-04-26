package nuwc.userloginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;
import java.util.Collections;

import nuwc.userloginsystem.util.AdapterPrintList;
import nuwc.userloginsystem.util.RandNameGen;

public class myPrintList extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterPrintList adapter;
    FastScroller fastScroller;
    ArrayList<String> reservations = new ArrayList<>();
    String employeeExt = null;
    String reserveType = null;
    String eventName = null;
    RelativeLayout layout;

    ImageView calendarButton;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_print_list);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                employeeExt= null;
                reserveType= null;
                eventName = null;



            } else {
                employeeExt= extras.getString("employee");
                reserveType= extras.getString("reserveType");
                eventName= extras.getString("eventName");



            }
        } else {
            employeeExt= (String) savedInstanceState.getSerializable("employee");
            reserveType= (String) savedInstanceState.getSerializable("reserveType");
            eventName= (String) savedInstanceState.getSerializable("eventName");



        }


        addReserves();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new AdapterPrintList(reservations,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        calendarButton = findViewById(R.id.calendarButton);



        layout = findViewById(R.id.eventList);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("reserveType",reserveType +"");
                if(eventName == "breakout"){
                    Intent myIntent = new Intent(myPrintList.this, BreakoutReservation.class);
                    myPrintList.this.startActivity(myIntent);

                }else{
                    Intent myIntent = new Intent(myPrintList.this, Reservations.class);
                    myPrintList.this.startActivity(myIntent);

                }


            }
        });





    }

    public void addReserves() {
        RandNameGen nameGen = new RandNameGen();
        Log.d("addUser","User added");

            for(int i = 0; i < 20; i ++){
                reservations.add(nameGen.randomIdentifier());
                Log.d("names",reservations.get(i));

            }
        Collections.sort(reservations);

    }

}
