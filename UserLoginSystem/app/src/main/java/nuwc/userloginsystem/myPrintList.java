package nuwc.userloginsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_print_list);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                employeeExt= null;
                reserveType= null;


            } else {
                employeeExt= extras.getString("employee");
                reserveType= extras.getString("reserveType");


            }
        } else {
            employeeExt= (String) savedInstanceState.getSerializable("employee");
            reserveType= (String) savedInstanceState.getSerializable("reserveType");


        }

        addReserves();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new AdapterPrintList(reservations,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()




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
