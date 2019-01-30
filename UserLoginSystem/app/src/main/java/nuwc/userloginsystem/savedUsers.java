package nuwc.userloginsystem;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import static android.content.ContentValues.TAG;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;

import nuwc.userloginsystem.util.RecycleViewAdapter;

/**
 * Created by Vinny on 11/5/18.
 */
public class savedUsers extends AppCompatActivity{


    static Context ctx;

    ScrollView buttonView;
    LinearLayout buttonLayout;
    String name;
    ArrayList<String> mNames = new ArrayList<>();

    int userCount = 10;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //List<Users> listOfUsers = null;

        

        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_users);


        ctx = this;
        for(int i = 0; i < 20; i++){
            if(i == 0){
                mNames.add(i,"Vinny");

            }else if ( i == 1){
                mNames.add(i,"Bhargav");

            }else if ( i == 2){
                mNames.add(i,"Alex");

            }else if ( i == 3){
                mNames.add(i,"Jacob");

            }else if ( i == 4){
                mNames.add(i,"Nassan");

            }else if ( i == 5){
                mNames.add(i,"Gus");

            }else{
                mNames.add(i,"Vinny");
            }

        }




        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecycleViewAdapter adapter = new RecycleViewAdapter(mNames,this);
        FastScroller fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerView);



        addUser(userCount);



    }

    public void addUser(int count) {





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
