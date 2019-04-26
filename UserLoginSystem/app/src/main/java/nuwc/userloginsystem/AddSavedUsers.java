package nuwc.userloginsystem;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import nuwc.userloginsystem.util.RandNameGen;

public class AddSavedUsers extends Thread {
    String name; // name of thread
    ArrayList<String> users;
    Thread t;

    AddSavedUsers(String threadname) {
        name = threadname;
        users =  new ArrayList<>();
        t = new Thread(this, name);
        t.start();
    }

    public void run() {
            RandNameGen nameGen = new RandNameGen();
            Log.d("addUser","User added");

//            for(Users u : userList){
//                mNames.add(u.getName());
//                Log.d("Names",u.getName());
//            }
            for(int i = 0; i < 200; i ++){
                users.add(nameGen.randomIdentifier());
                Log.d("names",users.get(i));

            }
            Collections.sort(users);

        Log.d("ThreadStatus",name + " exiting.");
    }

    public ArrayList getUsers(){
        return users;
    }

}
