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

public class checkoutBarcode extends AppCompatActivity {

    String barcode;
    String adminExt;
    String extension;

    EditText barcodeBox;
    EditText extensionBox;
    TextView commandBox;

    Button submitButton;
    Button cancelButton;
    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminExt = getIntent().getStringExtra("adminExt");
        setContentView(R.layout.activity_checkout_barcode_screen);

        submitButton = (Button) findViewById(R.id.submitButton2);
        barcodeBox = (EditText) findViewById(R.id.barcodeBox);
        extensionBox = (EditText) findViewById(R.id.extensionBox);
        commandBox = (TextView) findViewById(R.id.commandBox);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        homeButton = (Button) findViewById(R.id.homeButton);

        homeButton.setVisibility(View.INVISIBLE);


        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //get the barcode from user
                barcode = barcodeBox.getText().toString();
                extension = extensionBox.getText().toString();

                if(barcode.equals("")){
                    showError("Barcode must not be empty.");
                }
                else if(extension.length() != 5){
                    showError("Extension must be 5 digits");
                }
                else {
                    try {
                        sendCheckout();
                    } catch (JSONException e) {
                        Log.e("EXCEPTION", e.toString());
                    }
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent next = new Intent(checkoutBarcode.this, checkoutOptions.class);
                startActivity(next);
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent next = new Intent(checkoutBarcode.this, checkoutOptions.class);
                startActivity(next);
            }
        });
    }
    public void sendCheckout() throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JSONObject body = new JSONObject();

        body.put("barcode", barcode);
        body.put("userExt", extension);
        body.put("adminExt", adminExt);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/checkoutEquipment",
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
            barcodeBox.setVisibility(View.INVISIBLE);
            extensionBox.setVisibility(View.INVISIBLE);
            submitButton.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);

            homeButton.setVisibility(View.VISIBLE);

            //confirm user request
            commandBox.setText("Sucessfully checked out equipment.");
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
