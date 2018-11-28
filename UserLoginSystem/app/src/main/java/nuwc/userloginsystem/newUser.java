package nuwc.userloginsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.util.RequestUtil;

/**
 * Created by Vinny on 11/5/18.
 */
public class newUser extends AppCompatActivity{

    String firstName;
    String lastname;
    String badgeString;
    String eIDString;
    int badge;
    int eID;

    ImageButton submit;

    EditText nameText;
    EditText lastNameText;
    EditText badgeText;
    EditText idText;

    TextView welcomeUser;

    savedUsers users = new savedUsers();

    public static newUser userInsta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        userInsta = this;
        submit = (ImageButton) findViewById(R.id.submit);

        nameText = (EditText) findViewById(R.id.nameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        badgeText = (EditText) findViewById(R.id.badgeText);
        idText = (EditText) findViewById(R.id.idText);

        welcomeUser = (TextView) findViewById(R.id.welcomeUser);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //save first and last name
                firstName = nameText.getText().toString();
                lastname = lastNameText.getText().toString();
                //save badge and employee id
                badgeString = badgeText.getText().toString();
                badge = Integer.parseInt(badgeString);
                eIDString = idText.getText().toString();
                eID = Integer.parseInt(eIDString);
                //make editTexts disappear
                nameText.setVisibility(View.INVISIBLE);
                lastNameText.setVisibility(View.INVISIBLE);
                badgeText.setVisibility(View.INVISIBLE);
                idText.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.INVISIBLE);

                try {
                    addNewUser();
                }
                catch(JSONException e){
                    Log.d("EXCEPTION", e.toString());
                }

            }
        });
    }

        public void verifyResponse(ResponseObject response){
            if(response.isSuccess()){
                //confirm user creation
                welcomeUser.setText("Welcome " + firstName + "!");
            }
            else{
                showError(response.getMessage());
            }
        }

        public void addNewUser() throws JSONException {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.start();

            JSONObject body = new JSONObject();

            body.put("name", firstName + " " + lastname);
            body.put("ext", eIDString);
            body.put("dep", badgeString);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    RequestUtil.BASE_URL + "/newUser",
                    body,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                ResponseObject responseObject = mapper.readValue(response.toString(), ResponseObject.class);
                                verifyResponse(responseObject);
                            }
                            catch(Exception e){
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
        public void showError(String message){
            Context context = this;
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context);
            builder.setTitle("Error")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            nameText.setVisibility(View.VISIBLE);
                            lastNameText.setVisibility(View.VISIBLE);
                            badgeText.setVisibility(View.VISIBLE);
                            idText.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.VISIBLE);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

}
