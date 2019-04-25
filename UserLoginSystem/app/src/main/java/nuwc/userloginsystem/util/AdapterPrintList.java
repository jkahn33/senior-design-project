package nuwc.userloginsystem.util;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nuwc.userloginsystem.R;
import nuwc.userloginsystem.editReservButton;
import nuwc.userloginsystem.myPrintList;
import nuwc.userloginsystem.reserveOptionsPrint;

import static android.content.ContentValues.TAG;

public class AdapterPrintList extends RecyclerView.Adapter<AdapterPrintList.ViewHolder>{

    private ArrayList<String> aReservations = new ArrayList<>();
    private Context aContext;


    public AdapterPrintList(ArrayList<String> reservations, Context context){
        aReservations = reservations;
        aContext = context;

    }

    @Override
    public AdapterPrintList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_reservation_button, parent, false);
        AdapterPrintList.ViewHolder holder = new AdapterPrintList.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(AdapterPrintList.ViewHolder holder, int position) {
        Log.d(TAG,"onBind");

        holder.reservationName.setText(aReservations.get(position));

    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public String getSectionTitle(int position) {
        return getItem(position).substring(0, 1);
    }

    private String getItem(int position) { return aReservations.get(position); }



    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView backPlate;
        TextView reservationName;
        ConstraintLayout parentLayout;

        Button edit;
        Button delete;

        Context ctx;
        public ViewHolder(View itemView) {
            super(itemView);

            ctx = itemView.getContext();
            backPlate = itemView.findViewById(R.id.backPlate);
            parentLayout = itemView.findViewById(R.id.reservationPlate);
            reservationName = itemView.findViewById(R.id.reservationName);
            edit = itemView.findViewById(R.id.editButton);
            delete = itemView.findViewById(R.id.deleteButton);


            edit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent myIntent = new Intent(ctx, reserveOptionsPrint.class);
                    //used to send data to other layout
//                    Intent myIntent = getIntent(); // gets the previously created intent
//                    String firstKeyName = myIntent.getStringExtra("firstKeyName"); // will return "FirstKeyValue"
//                    String secondKeyName= myIntent.getStringExtra("secondKeyName");
                    myIntent.putExtra("firstKeyName","FirstKeyValue");
                    myIntent.putExtra("secondKeyName","SecondKeyValue");
                    ctx.startActivity(myIntent);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    parentLayout.setVisibility(View.GONE);
                    Log.d("Name","Plate boy");

                }
            });



        }
    }
    public void removeAt(int position) {
        aReservations.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, aReservations.size());
    }
}