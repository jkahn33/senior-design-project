package nuwc.userloginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class barcodeScreen2 extends AppCompatActivity {

    String barcode;

    EditText enterBox2;
    TextView commandBox2;

    Button submitButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_screen2);

        submitButton2 = (Button) findViewById(R.id.submitButton2);
        enterBox2 = (EditText) findViewById(R.id.enterBox2);
        commandBox2 = (TextView) findViewById(R.id.commandBox2);

        submitButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //get the barcode from user
                barcode = enterBox2.getText().toString();

                //make editTexts disappear
                enterBox2.setVisibility(View.INVISIBLE);
                submitButton2.setVisibility(View.INVISIBLE);
                //confirm user request
                commandBox2.setText("Thank you!");

            }
        });


    }
}
