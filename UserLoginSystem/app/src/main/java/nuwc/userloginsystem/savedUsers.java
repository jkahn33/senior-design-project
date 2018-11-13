package nuwc.userloginsystem;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Vinny on 11/5/18.
 */
public class savedUsers extends AppCompatActivity{


    static Context ctx;

    ScrollView buttonView;
    LinearLayout buttonLayout;
    String name;

    int userCount = 10;

    public static savedUsers savedInsta;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_users);

        savedInsta = new savedUsers();

        ctx = this;

        buttonView = (ScrollView) findViewById(R.id.buttonView);
        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);


        addUser(userCount);



    }

    public void addUser(int count) {

        for(int i = 0; i < count; i ++){
            Button userButton = new Button(this);

            userButton.setText("Vinny");
            userButton.setTag(i);
            userButton.setTextSize(40);
            userButton.setBackgroundResource(R.drawable.name_plate);

            buttonLayout.addView(userButton);

        }



    }
    public static Context getContex(){
            return ctx;
    }
    public String getFirstName(){
        return this.name;
    }
    public void setFirstName(String firstName){
        this.name = firstName;
    }



}
