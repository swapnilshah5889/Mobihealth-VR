package com.example.mobihealthapis.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.R;

public class FollowupAdapter extends RecyclerView.Adapter<FollowupAdapter.MyViewHolder> {

    Context context;

    public FollowupAdapter(Context context) {
        this.context = context;
        hasStableIds();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.followup_layout,parent , false);
        return new FollowupAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //TextView tv_chip_data;


        public MyViewHolder(@NonNull View view) {
            super(view);
          //  tv_chip_data = view.findViewById(R.id.tv_chip_data);

        }
    }

}
