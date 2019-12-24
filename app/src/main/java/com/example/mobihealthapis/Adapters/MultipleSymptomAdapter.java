package com.example.mobihealthapis.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.Models.Issues;
import com.example.mobihealthapis.R;

import java.util.List;

public class MultipleSymptomAdapter   extends RecyclerView.Adapter<MultipleSymptomAdapter.MyViewHolder>{

    Context context;
    List<Issues.Data> IssueList;


    public MultipleSymptomAdapter(Context context, List<Issues.Data> issueList) {
        this.context = context;
        IssueList = issueList;
        hasStableIds();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.multiple_symptom_list_layout, parent, false);
        return new MultipleSymptomAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Issues.Data d = IssueList.get(position);

        holder.tv_symptoms_number_dialog.setText((position+1)+".");

        holder.tv_symptoms_dialog.setText(""+d.getIssues());
    }

    @Override
    public int getItemCount() {
        return IssueList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tv_symptoms_dialog,tv_symptoms_number_dialog;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_symptoms_number_dialog = view.findViewById(R.id.tv_symptoms_number_dialog);
            tv_symptoms_dialog = view.findViewById(R.id.tv_symptoms_dialog);

        }
    }


}
