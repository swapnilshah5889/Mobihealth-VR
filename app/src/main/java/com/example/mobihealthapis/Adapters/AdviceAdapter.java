package com.example.mobihealthapis.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.R;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.MyViewHolder> {

    Context context;

    public AdviceAdapter(Context context) {
        this.context = context;
        hasStableIds();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.advice_layout,parent , false);
        return new AdviceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_chip_data.setText("Take over-the-counter medications like acetaminophen and ibuprofen to reduce fever.");

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_chip_data;


        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_chip_data = view.findViewById(R.id.tv_chip_data);

        }
    }

}
