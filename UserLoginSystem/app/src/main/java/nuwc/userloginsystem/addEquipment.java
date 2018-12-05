package nuwc.userloginsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
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


public class addEquipment extends AppCompatActivity {

    String equipName;
    String barcode;

    EditText barcodeBox;
    EditText equipmentNameBox;
    TextView equipmentSuccessBox;

    Button submitButton;
    Button addEquipCancel;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);

        submitButton = (Button) findViewById(R.id.submitButton2);
        barcodeBox = (EditText) findViewById(R.id.barcodeBox);
        equipmentNameBox = (EditText) findViewById(R.id.equipmentName);
        equipmentSuccessBox = (TextView) findViewById(R.id.commandBox);
        addEquipCancel = (Button) findViewById(R.id.btnAddEquipCancel);
        back = (Button) findViewById(R.id.btnAddEquipHome);
        back.setVisibility(View.INVISIBLE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //get the barcode from user
                barcode = barcodeBox.getText().toString();
                equipName = equipmentNameBox.getText().toString();

                try {
                    sendEquipment();
                }
                catch(JSONException e){
                    showError("JSON formatting error.");
                    Log.e("EXCEPTION", e.toString());
                }
            }
        });
        addEquipCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent cancelIntent = new Intent(addEquipment.this, checkoutOptions.class);
                addEquipment.this.startActivity(cancelIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent cancelIntent = new Intent(addEquipment.this, checkoutOptions.class);
                addEquipment.this.startActivity(cancelIntent);
            }
        });
    }
    public void sendEquipment() throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JSONObject body = new JSONObject();

        body.put("barcode", barcode);
        body.put("equipmentName", equipName);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/newEquipment",
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
    public void verifyResponse(ResponseObject response){
        if(response.isSuccess()){
            //make editTexts disappear
            barcodeBox.setVisibility(View.INVISIBLE);
            equipmentNameBox.setVisibility(View.INVISIBLE);
            addEquipCancel.setVisibility(View.INVISIBLE);
            submitButton.setVisibility(View.INVISIBLE);

            back.setVisibility(View.VISIBLE);

            //homeButton.setVisibility(View.VISIBLE);

            //confirm user request
            equipmentSuccessBox.setText("Equipment successfully added");
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
