package nuwc.userloginsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nuwc.userloginsystem.objects.BreakoutReservations;
import nuwc.userloginsystem.objects.PrinterReservations;
import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.util.AdapterPrintList;
import nuwc.userloginsystem.util.RandNameGen;
import nuwc.userloginsystem.util.RequestUtil;

public class myPrintList extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterPrintList adapter;
    FastScroller fastScroller;
    ArrayList<PrinterReservations> reservations = new ArrayList<>();
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
            if (extras == null) {
                employeeExt = null;
                reserveType = null;
                eventName = null;
            } else {
                Log.d("NULLCHECK", "NULLNOTNULL");
                employeeExt = extras.getString("employee");
                reserveType = extras.getString("reserveType");
                eventName = extras.getString("eventName");


            }
        } else {
            Log.d("NULLCHECK", "NOTNULL");
            employeeExt = (String) savedInstanceState.getSerializable("employee");
            reserveType = (String) savedInstanceState.getSerializable("reserveType");
            eventName = (String) savedInstanceState.getSerializable("eventName");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new AdapterPrintList(reservations, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        if(reserveType.equals("printer")) {
            try {
                fillPrintList();
            } catch (JSONException e) {
                Log.e("ERROR", e.toString());
            }
        }
        else{
            try {
                fillBreakoutList();
            } catch (JSONException e) {
                Log.e("ERROR", e.toString());
            }
        }

        calendarButton = findViewById(R.id.calendarButton);

        layout = findViewById(R.id.eventList);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("reserveType", reserveType + "");
                try {
                    if (eventName.equals("breakout")) {
                        Intent myIntent = new Intent(myPrintList.this, BreakoutReservation.class);
                        myPrintList.this.startActivity(myIntent);

                    } else {
                        Intent myIntent = new Intent(myPrintList.this, Reservations.class);
                        myPrintList.this.startActivity(myIntent);

                    }
                }
                catch(NullPointerException e){
                    Intent myIntent = new Intent(myPrintList.this, PickReservationType.class);
                    myPrintList.this.startActivity(myIntent);
                }
            }
        });
    }

    public void fillPrintList() throws JSONException {
        Log.d("PRINTL", "Inside fill list");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JSONObject body = new JSONObject();
        body.put("string", employeeExt);

        JSONArray array = new JSONArray();
        array.put(body);

        Log.d("Test", "test");

        Log.d("TESTYO", array.toString());

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/getPrinterReservationsId",
                array,
                response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        List<PrinterReservations> listToGet = mapper.readValue(response.toString(), new TypeReference<List<PrinterReservations>>(){});
                        reservations.clear();

                        reservations.addAll(listToGet);

                        adapter.notifyDataSetChanged();
                    }
                    catch(Exception e){
                        Log.d("EXCEPTION", e.toString());
                    }
                },
                error -> {
                    Intent myIntent = new Intent(this, Reservations.class);
                    this.startActivity(myIntent);
                    //Log.d("ERROR", error.getMessage());

                    //showError(error.getMessage());
                }
        );

        requestQueue.add(request);
    }

    public void fillBreakoutList() throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JSONObject body = new JSONObject();
        body.put("string", employeeExt);

        JSONArray array = new JSONArray();
        array.put(body);

        Log.d("Test", "test");

        Log.d("TESTYO", array.toString());

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/getBreakoutReservationsId",
                array,
                response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        List<BreakoutReservations> listToGet = mapper.readValue(response.toString(), new TypeReference<List<BreakoutReservations>>(){});
                        reservations.clear();

                        for(BreakoutReservations res : listToGet){
                            reservations.add(new PrinterReservations(res.getName(), res.getId()));
                        }

                        //reservations.addAll(listToGet);

                        adapter.notifyDataSetChanged();
                    }
                    catch(Exception e){
                        Log.d("EXCEPTION", e.toString());
                    }
                },
                error -> {
                    Intent myIntent = new Intent(this, Reservations.class);
                    this.startActivity(myIntent);
                    //Log.d("ERROR", error.getMessage());

                    //showError(error.getMessage());
                }
        );

        requestQueue.add(request);
    }

    private void showError(String message){
        Context context = this;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
