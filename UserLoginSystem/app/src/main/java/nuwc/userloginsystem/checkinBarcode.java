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

import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.util.RequestUtil;

public class checkinBarcode extends AppCompatActivity {

    String barcode;

    static EditText enterBox2;
    TextView commandBox2;

    Button submitButton2;
    Button cancel;
    Button back;
    Button buttonScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_barcode_screen);

        submitButton2 = (Button) findViewById(R.id.submitButton2);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        cancel = (Button) findViewById(R.id.btnCheckinCancel);
        enterBox2 = (EditText) findViewById(R.id.enterBox2);
        commandBox2 = (TextView) findViewById(R.id.commandBox2);
        back = (Button) findViewById(R.id.btnCheckinHome);
        back.setVisibility(View.INVISIBLE);

        submitButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //get the barcode from user
                barcode = enterBox2.getText().toString();
                try {
                    sendCheckin();
                }
                catch(JSONException e ){
                    Log.e("EXCEPTION", e.toString());
                }


            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent cancelIntent = new Intent(checkinBarcode.this, checkoutOptions.class);
                checkinBarcode.this.startActivity(cancelIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent cancelIntent = new Intent(checkinBarcode.this, checkoutOptions.class);
                checkinBarcode.this.startActivity(cancelIntent);
            }
        });

        buttonScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), scannerScreen.class));
            }
        });
    }

    public void sendCheckin() throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JSONObject body = new JSONObject();

        body.put("barcode", barcode);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/checkinEquipment",
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
    public void verifyResponse(ResponseObject response){
        if(response.isSuccess()){
            //make editTexts disappear
            enterBox2.setVisibility(View.INVISIBLE);
            submitButton2.setVisibility(View.INVISIBLE);
            back.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            //confirm user request
            commandBox2.setText("Equipment successfully checked in.");
        }
        else{
            showError(response.getMessage());
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
