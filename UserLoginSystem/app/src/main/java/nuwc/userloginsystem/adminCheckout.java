package nuwc.userloginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class adminCheckout extends AppCompatActivity {

    String password;
    String extension;

    TextView directions;
    EditText passwordBox;
    EditText extensionBox;
    Button submitBut;

    public void checkoutScreen(){

        submitBut = (Button)findViewById(R.id.submitBut);
        submitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sub = new Intent(adminCheckout.this, equipmentCheckout.class);
                adminCheckout.this.startActivity(sub);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_checkout);


        submitBut = (Button) findViewById(R.id.submitBut);
        passwordBox = (EditText) findViewById(R.id.passwordBox);
        extensionBox = (EditText) findViewById(R.id.extensionBox);
        directions = (TextView) findViewById(R.id.commandBox);

        submitBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //get the password and extension from admin
                password = passwordBox.getText().toString();
                extension = extensionBox.getText().toString();

            }
        });

        checkoutScreen();

    }
}
