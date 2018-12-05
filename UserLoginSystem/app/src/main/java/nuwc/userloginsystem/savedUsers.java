package nuwc.userloginsystem;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.objects.Users;
import nuwc.userloginsystem.util.RequestUtil;

/**
 * Created by Vinny on 11/5/18.
 */
public class savedUsers extends AppCompatActivity{


    static Context ctx;

    ScrollView buttonView;
    LinearLayout buttonLayout;
    String name;
    String ext;

    TextView welcomeUser;

    private List<Users> userList = null;

    public static savedUsers savedInsta;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getUserList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_users);

        savedInsta = new savedUsers();

        ctx = this;

        buttonView = (ScrollView) findViewById(R.id.buttonView);
        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        welcomeUser = (TextView) findViewById(R.id.welcomeUser);
    }

    @TargetApi(24)
    public void addUser() {
        if(userList != null) {
            userList.sort(Comparator.comparing(Users::getName));

            for (int i = 0; i < userList.size(); i++) {
                final Button userButton = new Button(this);

                userButton.setId(Integer.parseInt(userList.get(i).getFiveDigExt()));
                userButton.setText(userList.get(i).getName());
                userButton.setTag(i);
                userButton.setTextSize(30);
                userButton.setBackgroundResource(R.drawable.name_plate);

                buttonLayout.addView(userButton);

                userButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            logUser(Integer.toString(userButton.getId()));
                        }
                        catch(JSONException e){
                            showError("JSON Format Error");
                            Log.e("EXCEPTION", e.toString());
                        }
                    }
                });
            }
        }
        else{
            Log.d("ERROR", "Users list is null");
        }
    }
    public void verifyResponse(ResponseObject response){
        if(response.isSuccess()){
            //confirm user creation
            Log.d("RESPONSE", "creating welcome user");
            welcomeUser.setText("Welcome " + response.getMessage() + "!");
            buttonLayout.setVisibility(View.INVISIBLE);
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
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            userList = mapper.readValue(response.toString(), new TypeReference<List<Users>>(){});
                            addUser();
                        }
                        catch(Exception e){
                            Log.e("EXCEPTION", e.toString());
                            showError("Object mapping error. Please check logs.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());
                        showError("Unknown error. Please check logs.");

                    }
                }
        );

        requestQueue.add(request);
    }
    public void logUser(String id) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JSONObject body = new JSONObject();

        body.put("ext", id);

        ext = id;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/storeLogin",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            Log.d("RESPONSE", response.toString());
                            ResponseObject responseObject = mapper.readValue(response.toString(), ResponseObject.class);
                            verifyResponse(responseObject);
                        }
                        catch(Exception e){
                            showError("User logging object mapping error, please check logs.");
                            Log.d("EXCEPTION", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.getMessage());

                        showError(error.getMessage());
                    }
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
