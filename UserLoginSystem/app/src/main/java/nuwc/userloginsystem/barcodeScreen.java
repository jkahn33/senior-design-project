package nuwc.userloginsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class barcodeScreen extends AppCompatActivity {

    String barcode;

    EditText enterBox;
    TextView commandBox;

    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_screen);

        submitButton = (Button) findViewById(R.id.submitButton2);
        enterBox = (EditText) findViewById(R.id.enterBox);
        commandBox = (TextView) findViewById(R.id.commandBox);


        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //get the barcode from user
                barcode = enterBox.getText().toString();

                //make editTexts disappear
                enterBox.setVisibility(View.INVISIBLE);
                submitButton.setVisibility(View.INVISIBLE);
                //confirm user request
                commandBox.setText("Requested for approval");

            }
        });


    }
}
