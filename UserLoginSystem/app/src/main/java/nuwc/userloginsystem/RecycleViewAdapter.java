package nuwc.userloginsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RecycleViewAdapter  extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> implements SectionTitleProvider {

    private ArrayList<String> mUserNames = new ArrayList<>();
    private Context mContext;
    RecyclerView recyclerView;
    FastScroller fastScroller;
    TextView welcome;



    public RecycleViewAdapter(ArrayList<String> userNames, Context context, RecyclerView rView, FastScroller scroller, TextView userWelcome){
        mUserNames = userNames;
        mContext = context;
        recyclerView = rView;
        fastScroller = scroller;
        welcome = userWelcome;
    }





    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_user, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG,"onBind");

        holder.userName.setText((mUserNames.get(position)));

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
        return mUserNames.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{



        ImageView namePlate;
        TextView  userName;
        RelativeLayout parentLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            namePlate = itemView.findViewById(R.id.namePlate);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            userName = itemView.findViewById(R.id.nameView);
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    fastScroller.setVisibility(View.INVISIBLE);
                    welcome.setText("Welcome " + userName.getText() + "!");
                    recyclerView.setVisibility(View.INVISIBLE);


                }
            });

        }
    }
}