package com.attosectechnolabs.cardviewone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by dev on 25-Aug-16.
 */

public class QuestionPaperAdapter extends RecyclerView.Adapter<QuestionPaperAdapter.ViewHolder> {

    Context context;
    List<GetDataAdapter> getDataAdapter;

    public QuestionPaperAdapter(){}

    public QuestionPaperAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();

        this.getDataAdapter = getDataAdapter;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_question2,
                parent, false);

        ViewHolder viewHolder = new QuestionPaperAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QuestionPaperAdapter.ViewHolder holder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        holder.OptAnswerTV.setVisibility(GONE);
        holder.OptYourAnswerTV.setVisibility(GONE);
        holder.QuestionTV.setText(getDataAdapter1.getQuestion());
  /*      holder.OptATV.setText(getDataAdapter1.getOptA());
        holder.OptBTV.setText(getDataAdapter1.getOptB());
        holder.OptCTV.setText(getDataAdapter1.getOptC());
        holder.OptDTV.setText(getDataAdapter1.getOptD());
*/
        holder.OptARB.setText(getDataAdapter1.getOptA());
        holder.OptBRB.setText(getDataAdapter1.getOptB());
        holder.OptCRB.setText(getDataAdapter1.getOptC());
        holder.OptDRB.setText(getDataAdapter1.getOptD());

        holder.QuestionNoTV.setText(getDataAdapter1.getQuestion_id());
        holder.OptAnswerTV.setText(getDataAdapter1.getAnswer());

    }

    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView QuestionTV;
        public TextView OptATV;
        public TextView OptBTV;
        public TextView OptCTV;
        public TextView OptDTV;
        public TextView OptAnswerTV;
        public TextView OptYourAnswerTV;
        public RadioButton OptARB;
        public RadioButton OptBRB;
        public RadioButton OptDRB;
        public RadioButton OptCRB;

        public TextView QuestionNoTV;
        public ViewHolder(View itemView) {
            super(itemView);

            QuestionTV = (TextView) itemView.findViewById(R.id.QuestionTV);
            OptATV = (TextView) itemView.findViewById(R.id.AnswerA);
            OptBTV = (TextView) itemView.findViewById(R.id.AnswerB);
            OptCTV = (TextView) itemView.findViewById(R.id.AnswerC);
            OptDTV = (TextView) itemView.findViewById(R.id.AnswerD);
            OptAnswerTV = (TextView) itemView.findViewById(R.id.AnswerTV);
            OptYourAnswerTV = (TextView) itemView.findViewById(R.id.YourAnswer);

            OptARB = (RadioButton) itemView.findViewById(R.id.rb_A);
            OptBRB = (RadioButton) itemView.findViewById(R.id.rb_B);
            OptCRB = (RadioButton) itemView.findViewById(R.id.rb_C);
            OptDRB = (RadioButton) itemView.findViewById(R.id.rb_D);

            QuestionNoTV = (TextView) itemView.findViewById(R.id.QuestionNo);

        }


    }
}
