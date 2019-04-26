package nuwc.userloginsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_print_list);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                employeeExt= null;
                reserveType= null;
                Log.d("NULLCHECK", "NULLNULL");
            } else {
                Log.d("NULLCHECK", "NULLNOTNULL");
                employeeExt= extras.getString("employee");
                reserveType= extras.getString("reserveType");


            }
        } else {
            Log.d("NULLCHECK", "NOTNULL");
            employeeExt= (String) savedInstanceState.getSerializable("employee");
            reserveType= (String) savedInstanceState.getSerializable("reserveType");
        }

        try {
            fillList();
        }
        catch(JSONException e){
            Log.e("ERROR", e.toString());
        }


        //has to be called AFTER RecyclerView.setAdapter()
    }

    public void addReserves() {
        RandNameGen nameGen = new RandNameGen();
        Log.d("addUser","User added");

//            for(int i = 0; i < 20; i ++){
//                reservations.add(nameGen.randomIdentifier());
//                Log.d("names",reservations.get(i));
//
//            }
//        Collections.sort(reservations);

    }

    public void sendList(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //pass sorted list of reservation ids
        adapter = new AdapterPrintList(reservations,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void fillList() throws JSONException {
        Log.d("PRINTL", "Inside fill list");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JSONObject body = new JSONObject();

        body.put("string", employeeExt);
        JSONArray array = null;
        try{
             array = new JSONArray(body.toString());
        }
        catch(Exception e){
            Log.e("ERROR", e.toString());
        }

        Log.d("Test", "test");

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/getPrinterReservationsId",
                array,
                response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        reservations = mapper.readValue(response.toString(), new TypeReference<List<PrinterReservations>>(){});
                        sendList();
                    }
                    catch(Exception e){
                        Log.d("EXCEPTION", e.toString());
                    }
                },
                error -> {
                    Log.d("ERROR", error.getMessage());

                    showError(error.getMessage());
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
