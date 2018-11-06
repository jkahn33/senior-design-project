package nuwc.userloginsystem;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Vinny on 11/5/18.
 */
public class savedUsers extends AppCompatActivity{

    ScrollView buttonView;


    LinearLayout linearLayout;

    Button userButton;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_users);

        buttonView = (ScrollView) findViewById(R.id.buttonView);

        linearLayout = (LinearLayout) findViewById(R.id.linear);


         userButton = new Button(getApplicationContext());

    }



    public void addUser(String name, String lastName,int badge, int eID) {
        //userButton.setText(name);
//        userButton.setBackgroundResource(R.drawable.name_plate);
//        linearLayout.addView(userButton);
//
//
//        buttonView.addView(linearLayout);

    }
}
