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

/**
 * Created by Vinny on 11/5/18.
 */
public class newUser extends AppCompatActivity{

    String firstName;
    String lastname;
    String badgeString;
    String eIDString;
    int badge;
    int eID;

    ImageButton submit;

    EditText nameText;
    EditText lastNameText;
    EditText badgeText;
    EditText idText;

    TextView welcomeUser;

    savedUsers users = new savedUsers();

    public static newUser userInsta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        userInsta = this;
        submit = (ImageButton) findViewById(R.id.submit);

        nameText = (EditText) findViewById(R.id.nameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        badgeText = (EditText) findViewById(R.id.badgeText);
        idText = (EditText) findViewById(R.id.idText);

        welcomeUser = (TextView) findViewById(R.id.welcomeUser);








        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //save first and last name
                firstName = nameText.getText().toString();
                lastname = nameText.getText().toString();
                //save badge and employee id
                badgeString= badgeText.getText().toString();
                badge=Integer.parseInt(badgeString);
                eIDString= idText.getText().toString();
                eID=Integer.parseInt(eIDString);
                //make editTexts disappear
                nameText.setVisibility(View.INVISIBLE);
                lastNameText.setVisibility(View.INVISIBLE);
                badgeText.setVisibility(View.INVISIBLE);
                idText.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.INVISIBLE);
                //confirm user creation
                welcomeUser.setText("Welcome " + firstName + "!");

            }
        });


    }

}
