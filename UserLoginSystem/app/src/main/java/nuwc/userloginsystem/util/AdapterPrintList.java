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
import nuwc.userloginsystem.myPrintList;
import nuwc.userloginsystem.objects.ReservationWrapper;
import nuwc.userloginsystem.objects.ResponseObject;

public class AdapterPrintList extends RecyclerView.Adapter<AdapterPrintList.ViewHolder>{

    private ArrayList<ReservationWrapper> aReservations = new ArrayList<>();
    private Context aContext;
    private String empExt;

    public AdapterPrintList(String empExt, ArrayList<ReservationWrapper> reservations, Context context){
        this.aReservations = reservations;
        this.aContext = context;
        this.empExt = empExt;
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
            holder.reservationName.setText(aReservations.get(position).getName());
            holder.resId = aReservations.get(position).getId();
            holder.start.setText(aReservations.get(position).getDate().toString());
            holder.resType = aReservations.get(position).getResType();
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

    private String getItem(int position) { return aReservations.get(position).getName(); }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView backPlate;
        TextView reservationName;
        TextView start;
        ConstraintLayout parentLayout;
        int resId;
        String resType;

        Button edit;
        Button delete;

        Context ctx;
        public ViewHolder(View itemView) {
            super(itemView);

            ctx = itemView.getContext();
            backPlate = itemView.findViewById(R.id.backPlate);
            parentLayout = itemView.findViewById(R.id.reservationPlate);
            reservationName = itemView.findViewById(R.id.reservationName);
            start = itemView.findViewById(R.id.startTime);
            delete = itemView.findViewById(R.id.deleteButton);

            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    parentLayout.setVisibility(View.GONE);

                    try {
                        deleteById(resId, ctx, resType, getAdapterPosition());
                    }
                    catch(JSONException e){
                        Log.e("ERROR", e.toString());
                    }

                    Intent myIntent = new Intent(ctx, myPrintList.class);
                    myIntent.putExtra("employee", empExt);
                    myIntent.putExtra("eventName",reservationName.getText());
                    myIntent.putExtra("reserveType", resType);
                    ctx.startActivity(myIntent);

                }
            });
        }
    }

    public void deleteById(int printId, Context ctx, String type, int position) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.start();

        JSONObject body = new JSONObject();

        body.put("string", Integer.toString(printId));

        JsonObjectRequest request;

        if(type.equals("printer")) {
            request = new JsonObjectRequest(
                    Request.Method.POST,
                    RequestUtil.BASE_URL + "/deletePrinterById",
                    body,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                ResponseObject responseObject = mapper.readValue(response.toString(), ResponseObject.class);
                                if (responseObject.isSuccess()) {
                                    removeAt(position);
                                }
                                else{
                                    showError(responseObject.getMessage(), ctx);
                                }
                            } catch (Exception e) {
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
        }
        else{
            request = new JsonObjectRequest(
                    Request.Method.POST,
                    RequestUtil.BASE_URL + "/deleteBreakoutById",
                    body,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                ResponseObject responseObject = mapper.readValue(response.toString(), ResponseObject.class);
                                if (responseObject.isSuccess()) {
                                    Log.d("POSITION", Integer.toString(position));
                                    removeAt(position);
                                }
                                else{
                                    showError(responseObject.getMessage(), ctx);
                                }
                            } catch (Exception e) {
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
        }

        requestQueue.add(request);
    }

    private void showError(String message, Context ctx){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void removeAt(int position) {
        aReservations.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, aReservations.size());
    }
}
