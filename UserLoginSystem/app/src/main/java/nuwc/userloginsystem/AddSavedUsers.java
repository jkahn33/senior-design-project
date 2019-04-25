package nuwc.userloginsystem;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nuwc.userloginsystem.objects.Users;
import nuwc.userloginsystem.util.RandNameGen;
import nuwc.userloginsystem.util.RequestUtil;

public class AddSavedUsers extends Thread {
    String name; // name of thread
    public ArrayList<Users> userList;
    public ArrayList<String> nameList;
    Thread t;

    AddSavedUsers(String threadname) {
        name = threadname;
        userList =  new ArrayList<>();
        t = new Thread(this, name);
        t.start();
    }

    public void run() {
            RandNameGen nameGen = new RandNameGen();
            Log.d("addUser","User added");

            for(Users u : userList){
                nameList.add(u.getName());
                Log.d("Names",u.getName());
            }
//            for(int i = 0; i < 200; i ++){
//                userList.add(nameGen.randomIdentifier());
//                Log.d("names",userList.get(i));
//
//            }
            Collections.sort(nameList);

        Log.d("ThreadStatus",name + " exiting.");
    }

    public ArrayList<String> getUsers(){
        return nameList;
    }

    public void setUserList(ArrayList<Users> userList){
        Log.d("USERS", "USER LIST SET");
        this.userList = userList;
    }
//
//    private void showError(String message){
//        Context context = this;
//        AlertDialog.Builder builder;
//        builder = new AlertDialog.Builder(context);
//        builder.setTitle("Error")
//                .setMessage(message)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }

}
