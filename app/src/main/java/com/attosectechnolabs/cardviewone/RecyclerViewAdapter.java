package com.attosectechnolabs.cardviewone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    public RecyclerViewAdapter(){}
    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items,
                    parent, false);
         ViewHolder viewHolder = new ViewHolder(v);
         return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        holder.ThreadIDTV.setText(getDataAdapter1.getThreadID());
        holder.UserNameTV.setText(getDataAdapter1.getUser());
        holder.ThreadTextTV.setText(getDataAdapter1.getThread_text());

/*======= on click listener for the items under card are kept inside onBindViewHolder  ==========*/

        holder.LikeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.LikeIV.setBackgroundColor(Color.RED);

            }
        });

        holder.CommentIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context ctx = view.getContext();
                // start new activity for thread with title
                Intent intent;
                intent = new Intent(ctx,ThreadWithTitle.class);
                // start activity
                String sThreadID = holder.ThreadIDTV.getText().toString();
                if (sThreadID.isEmpty()) sThreadID ="T16081442";
                String sThreadText = holder.ThreadTextTV.getText().toString();
                intent.putExtra("ThreadID1", sThreadID);
                intent.putExtra("ThreadText1", sThreadText);
                ctx.startActivity(intent);
            }
        });

        holder.FlagIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.FlagIV.setBackgroundColor(Color.RED);

            }
        });


    }// end onBindViewHolder

    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }

    /*============================ Inner Class ====================================== */

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ThreadIDTV;
        public TextView UserNameTV;
        public TextView ThreadTextTV;

        public ImageView LikeIV,CommentIV,FlagIV;

        public ViewHolder(View itemView) {

            super(itemView);

            ThreadIDTV =  (TextView) itemView.findViewById(R.id.ThreadID);
            UserNameTV =  (TextView) itemView.findViewById(R.id.UserName);
            ThreadTextTV =  (TextView)  itemView.findViewById(R.id.thread_text);

            LikeIV = (ImageView) itemView.findViewById(R.id.like);
            CommentIV = (ImageView) itemView.findViewById(R.id.comment);
            FlagIV = (ImageView) itemView.findViewById(R.id.flag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(view.getContext(), ThreadIDTV.getText().toString(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
