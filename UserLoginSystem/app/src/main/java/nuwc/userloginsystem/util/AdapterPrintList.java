package nuwc.userloginsystem.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nuwc.userloginsystem.R;
import nuwc.userloginsystem.Reservations;
import nuwc.userloginsystem.ReserveOptionsBreakout;
import nuwc.userloginsystem.editReservButton;
import nuwc.userloginsystem.myPrintList;
import nuwc.userloginsystem.objects.PrinterReservations;
import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.reserveOptionsPrint;

import static android.content.ContentValues.TAG;

public class AdapterPrintList extends RecyclerView.Adapter<AdapterPrintList.ViewHolder>{

    private ArrayList<PrinterReservations> aReservations = new ArrayList<>();
    private Context aContext;

    public AdapterPrintList(ArrayList<PrinterReservations> reservations, Context context){
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
        try {
            holder.reservationName.setText(aReservations.get(position).getJobDescription());
            holder.printId = aReservations.get(position).getId();
        }catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }

    }


    @Override
    public int getItemCount() {
        if(aReservations != null){
            return aReservations.size();
        }
        return 0;
    }

    public String getSectionTitle(int position) {
        return getItem(position).substring(0, 1);
    }

    private String getItem(int position) { return aReservations.get(position).getJobDescription(); }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView backPlate;
        TextView reservationName;
        ConstraintLayout parentLayout;
        int printId;

        Button edit;
        Button delete;

        Context ctx;
        public ViewHolder(View itemView) {
            super(itemView);

            ctx = itemView.getContext();
            backPlate = itemView.findViewById(R.id.backPlate);
            parentLayout = itemView.findViewById(R.id.reservationPlate);
            reservationName = itemView.findViewById(R.id.reservationName);
            delete = itemView.findViewById(R.id.deleteButton);

            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    parentLayout.setVisibility(View.GONE);

                    try {
                        Log.d("THEID", Integer.toString(printId));
                        deleteById(printId, ctx);
                    }
                    catch(JSONException e){
                        Log.e("ERROR", e.toString());
                    }

                    Intent myIntent = new Intent(ctx, myPrintList.class);
                    myIntent.putExtra("eventName",reservationName.getText());
                    ctx.startActivity(myIntent);

                }
            });
        }
    }

    public void deleteById(int printId, Context ctx) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.start();

        JSONObject body = new JSONObject();

        body.put("string", Integer.toString(printId));

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                RequestUtil.BASE_URL + "/deletePrinterById",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            ResponseObject responseObject = mapper.readValue(response.toString(), ResponseObject.class);
                            if(responseObject.isSuccess()){
                                Intent myIntent = new Intent(ctx, Reservations.class);
                                ctx.startActivity(myIntent);
                            }
                        }
                        catch(Exception e){
                            Log.d("EXCEPTION", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.getMessage());

                    }
                }
        );

        requestQueue.add(request);
    }

    public void removeAt(int position) {
        aReservations.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, aReservations.size());
    }
}
