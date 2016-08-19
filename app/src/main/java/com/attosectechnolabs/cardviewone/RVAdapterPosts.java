package com.attosectechnolabs.cardviewone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dev on 18-Aug-16.
 */
public class RVAdapterPosts extends RecyclerView.Adapter<RVAdapterPosts.ViewHolder> {

    Context context;

  //  public List<GetDataAdapter> getDataAdapter1;
    public List<GetDataAdapter> getDataAdapter;

    public RVAdapterPosts(List<GetDataAdapter> getDataAdapter, Context context) {
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        holder.PostTextTV.setText(getDataAdapter1.getThreadID());

    }

    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }


    /*============================ Inner Class ====================================== */

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ThreadIDTV;
        public TextView UserNameTV;
        public TextView ThreadTextTV;
        public TextView PostTextTV;

        public ImageView LikeIV,CommentIV,FlagIV;

        public ViewHolder(View itemView) {

            super(itemView);

            ThreadIDTV =  (TextView) itemView.findViewById(R.id.ThreadIDPost);
            UserNameTV =  (TextView) itemView.findViewById(R.id.UserNamePost);
            ThreadTextTV =  (TextView)  itemView.findViewById(R.id.ThreadTopicPost);
            PostTextTV = (TextView) itemView.findViewById(R.id.PostByUser);

            LikeIV = (ImageView) itemView.findViewById(R.id.like_post);
           // CommentIV = (ImageView) itemView.findViewById(R.id.comment);
            FlagIV = (ImageView) itemView.findViewById(R.id.flag_post);



        }
    }

}