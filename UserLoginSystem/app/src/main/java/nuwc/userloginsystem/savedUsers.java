package nuwc.userloginsystem;

import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.objects.Users;
import nuwc.userloginsystem.util.RequestUtil;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;

/**
 * Created by Vinny on 11/5/18.
 */
public class savedUsers extends AppCompatActivity{


    static Context ctx;

    RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    FastScroller fastScroller;

    TextView welcomeUser;
    String signedIn = null;
    String name;
    Button goBack;

    private ArrayList<Users> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_users);
        welcomeUser = (TextView) findViewById(R.id.welcomeUser);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        goBack = (Button) findViewById(R.id.goBack);

        adapter = new RecycleViewAdapter(userList,this,recyclerView,fastScroller,welcomeUser,goBack);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerView);

        getUserList();

        ctx = this;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                name= null;
                signedIn= null;


            } else {
                name = extras.getString("name");
                signedIn = extras.getString("signedIn");
                recyclerView.setVisibility(View.INVISIBLE);
                fastScroller.setVisibility(View.INVISIBLE);
                welcomeUser.setText("Welcome " + name + "!");
            }
        } else {
            name= (String) savedInstanceState.getSerializable("name");
            signedIn= (String) savedInstanceState.getSerializable("signedIn");
        }
    }

    public void verifyResponse(ResponseObject response){
        if(response.isSuccess()){
            //confirm user creation
            Log.d("RESPONSE", "creating welcome user");
            welcomeUser.setText("Welcome " + response.getMessage() + "!");
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else{
            showError(response.getMessage());
        }
    }
    public void getUserList(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();


        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                RequestUtil.BASE_URL + "/printAllUsers",
                null,
                response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        List<Users> responseList = mapper.readValue(response.toString(), new TypeReference<List<Users>>(){});
                        userList.addAll(responseList);
                        fillNamesList();
                    }
                    catch(Exception e){
                        Log.e("EXCEPTION", e.toString());
                        showError("Unknown error. Please check logs.");
                    }
                },
                error -> {
                    Log.e("ERROR", error.getMessage());
                    showError("Request error. Please check connection.");
                }
        );

        requestQueue.add(request);
    }
    public void fillNamesList(){
        Collections.sort(userList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        adapter.notifyDataSetChanged();
    }

    private void showError(String message){
        Context context = this;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {})
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public static Context getContex(){
        return ctx;
    }
    public String getFirstName(){
        return this.name;
    }
    public void setFirstName(String firstName){
        this.name = firstName;
    }
}