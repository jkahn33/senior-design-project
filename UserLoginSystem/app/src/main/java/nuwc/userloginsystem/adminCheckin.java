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

import nuwc.userloginsystem.objects.ValidateWrapper;
import nuwc.userloginsystem.util.RequestUtil;

public class adminCheckin extends AppCompatActivity {

    String password;
    String extension;

    TextView directions;
    EditText passwordBox;
    EditText extensionBox;
    Button submitBut;
    Button cancel;

    public void checkinScreen(){

        Intent sub = new Intent(adminCheckin.this, checkinBarcode.class);
        sub.putExtra("adminExt", extension);
        adminCheckin.this.startActivity(sub);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_checkin);


        submitBut = (Button) findViewById(R.id.submitBut2);
        passwordBox = (EditText) findViewById(R.id.passwordBox2);
        extensionBox = (EditText) findViewById(R.id.barcodeBox2);
        directions = (TextView) findViewById(R.id.commandBox2);
        cancel = (Button) findViewById(R.id.btnAdminLoginCancel);

        submitBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //get the password and extension from admin
                password = passwordBox.getText().toString();
                extension = extensionBox.getText().toString();

                try {
                    verifyAdmin();
                }
                catch(JSONException e){
                    Log.e("EXCPEPTION", e.toString());
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent sub = new Intent(adminCheckin.this, checkoutOptions.class);
                adminCheckin.this.startActivity(sub);
            }
        });
    }
    public void verifyAdmin() throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JSONObject body = new JSONObject();

        body.put("ext", extension);
        body.put("password", password);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/validateAdmin",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            ValidateWrapper success = mapper.readValue(response.toString(), ValidateWrapper.class);
                            verifyResponse(success.isSuccess());
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
    public void verifyResponse(boolean success){
        if(success){

            checkinScreen();
        }
        else{
            showError("Extension or password is incorrect.");
        }
    }
    public void showError(String message){
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
