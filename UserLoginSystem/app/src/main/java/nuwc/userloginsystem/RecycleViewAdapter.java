package nuwc.userloginsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nuwc.userloginsystem.objects.ResponseObject;
import nuwc.userloginsystem.objects.Users;
import nuwc.userloginsystem.util.RequestUtil;

import static android.content.ContentValues.TAG;

public class RecycleViewAdapter  extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> implements SectionTitleProvider {

    private ArrayList<Users> mUserNames = new ArrayList<>();
    private Context mContext;
    private RecyclerView recyclerView;
    private FastScroller fastScroller;
    private TextView welcome;
    private Button goBack;



    public RecycleViewAdapter(ArrayList<Users> userNames, Context context, RecyclerView rView, FastScroller scroller, TextView userWelcome,Button back){
        mUserNames = userNames;
        mContext = context;
        recyclerView = rView;
        fastScroller = scroller;
        welcome = userWelcome;
        goBack = back;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_user, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.userName.setText((mUserNames.get(position).getName()));
        holder.badgeId = mUserNames.get(position).getBadgeId();
    }

    @Override
    public int getItemCount() {
        return mUserNames.size();
    }

    @Override
    public String getSectionTitle(int position) {
        return getItem(position).substring(0, 1);
    }

    private String getItem(int position) {
        return mUserNames.get(position).getName();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView namePlate;
        TextView  userName;
        RelativeLayout parentLayout;
        String badgeId;


        public ViewHolder(View itemView) {
            super(itemView);

            namePlate = itemView.findViewById(R.id.namePlate);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            userName = itemView.findViewById(R.id.nameView);
            parentLayout.setOnClickListener(view -> {
                try {
                    storeLogin();
                }
                catch (JSONException e){
                    showError("Unknown error.");
                }
            });
            goBack.setOnClickListener(view -> {
                Intent myIntent = new Intent(mContext, signIn.class);
                mContext.startActivity(myIntent);

            });
        }
        private void storeLogin() throws JSONException {
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.start();

            JSONObject body = new JSONObject();

            body.put("ext", badgeId);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    RequestUtil.BASE_URL + "/storeLogin",
                    body,
                    response -> {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            Log.d("RESPONSE", response.toString());
                            ResponseObject responseObject = mapper.readValue(response.toString(), ResponseObject.class);
                            verifyResponse(responseObject);
                        }
                        catch(Exception e){
                            showError("User logging object mapping error, please check logs.");
                            Log.d("EXCEPTION", e.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("ERROR", error.getMessage());

                            showError(error.getMessage());
                        }
                    }
            );

            requestQueue.add(request);
        }
        public void verifyResponse(ResponseObject responseObject){
            if(responseObject.isSuccess()) {
                fastScroller.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                welcome.setText("Welcome " + userName.getText() + "!");
                goBack.setVisibility(View.VISIBLE);
            }
            else{
                showError(responseObject.getMessage());
            }
        }
        private void showError(String message){
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Error")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}