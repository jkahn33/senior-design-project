package nuwc.userloginsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nuwc.userloginsystem.objects.Users;
import nuwc.userloginsystem.util.AdapterPrintList;
import nuwc.userloginsystem.util.RandNameGen;
import nuwc.userloginsystem.util.RecycleViewAdapter;

public class myPrintList extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterPrintList adapter;
    FastScroller fastScroller;
    ArrayList<String> reservations = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_print_list);

        addUser();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new AdapterPrintList(reservations,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()




    }

    public void addUser() {
        RandNameGen nameGen = new RandNameGen();
        Log.d("addUser","User added");

            for(int i = 0; i < 20; i ++){
                reservations.add(nameGen.randomIdentifier());
                Log.d("names",reservations.get(i));

            }
        Collections.sort(reservations);

    }

}
