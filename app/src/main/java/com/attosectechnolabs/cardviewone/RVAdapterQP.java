package com.attosectechnolabs.cardviewone;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dev on 22-Aug-16.
 */

public class RVAdapterQP extends RecyclerView.Adapter<RVAdapterQP.ViewHolder> {

    public Context parContext;
    public Context context;
    public String sQP_Code;
    public LinearLayout QP_Code_layout;
    public SQLiteDatabase db;
    public List<GetDataAdapter> getDataAdapter;

    public RVAdapterQP(List<GetDataAdapter> getDataAdapter, Context context) {

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public RVAdapterQP.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.qp_card,
                parent, false);
        ViewHolder viewHolder = new RVAdapterQP.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        holder.QP_Id.setText(getDataAdapter1.getQP_Id().toString());
        holder.QP_Code.setText(getDataAdapter1.getQP_Code());
        sQP_Code = holder.QP_Code.getText().toString();

       System.out.println("RVAdapter on bind view ="+sQP_Code);
    }

    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView QP_Id,QP_Code;
        public Button submit;

        public ViewHolder(View itemView) {
            super(itemView);

            QP_Id = (TextView) itemView.findViewById(R.id.QP_Id);
            QP_Code = (TextView) itemView.findViewById(R.id.QP_Code);
            QP_Code_layout = (LinearLayout) itemView.findViewById(R.id.QP_Code_layout);


            // onclick listener on card view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sQP_Code = QP_Code.getText().toString();
                    Context ctx = view.getContext();
                    // start new activity for thread with title
                    Intent intent,intent1;
                    intent = new Intent(ctx,QuestionPaper.class);
                    // start activity
                    intent.putExtra("QP_Code1", sQP_Code);
                    System.out.println(sQP_Code);
                    ctx.startActivity(intent);
                }
            });
        }
    }



    }
