package nuwc.userloginsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;


public class addEquipment extends AppCompatActivity {

    String equipName;
    String barcode;

    EditText barcodeBox;
    EditText equipmentName;
    TextView commandBox2;

    Button submitButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);

        submitButton2 = (Button) findViewById(R.id.submitButton2);
        barcodeBox = (EditText) findViewById(R.id.barcodeBox);
        equipmentName = (EditText) findViewById(R.id.equipmentName);
        commandBox2 = (TextView) findViewById(R.id.commandBox2);

        submitButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //get the barcode from user
                barcode = barcodeBox.getText().toString();
                equipName = equipmentName.getText().toString();

                //make editTexts disappear
                barcodeBox.setVisibility(View.INVISIBLE);
                equipmentName.setVisibility(View.INVISIBLE);
                submitButton2.setVisibility(View.INVISIBLE);
                //confirm user request
                commandBox2.setText("Equipment added");

            }
        });






    }
}
