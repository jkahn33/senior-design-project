package nuwc.userloginsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class initialScreen extends AppCompatActivity {

    public Button loginButton;
    public Button reserveButton;
    public Button checkoutButton;

    public void login(){

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent login = new Intent(initialScreen.this, signIn.class);
                initialScreen.this.startActivity(login);

            }
        });
    }

    public void reserve(){

        reserveButton = (Button)findViewById(R.id.reserveButton);
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent reserve = new Intent(initialScreen.this, Reservations.class);
                startActivity(reserve);

            }
        });
    }

    public void checkout(){

        checkoutButton = (Button)findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent checkout = new Intent(initialScreen.this, checkoutOptions.class);
                initialScreen.this.startActivity(checkout);

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);
        login();
        reserve();
        checkout();
    }
}
