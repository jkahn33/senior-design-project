package nuwc.userloginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Vinny on 11/5/18.
 */
public class signIn extends AppCompatActivity{

    Button savedUsers;
    Button newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        savedUsers = (Button) findViewById(R.id.savedUser);
        newUser = (Button) findViewById(R.id.newUser);



        savedUsers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(signIn.this, savedUsers.class);
                signIn.this.startActivity(myIntent);
            }
        });
        newUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(signIn.this, newUser.class);
                signIn.this.startActivity(myIntent);
            }
        });
    }

}