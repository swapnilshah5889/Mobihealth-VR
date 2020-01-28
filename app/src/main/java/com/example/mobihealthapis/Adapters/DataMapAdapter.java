package com.example.mobihealthapis.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.R;

import java.util.List;

public class DataMapAdapter extends RecyclerView.Adapter<DataMapAdapter.MyViewHolder> {

    Context context;
    List<String> stringList;

    public DataMapAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
        hasStableIds();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_map_item_layout,parent , false);
        return new DataMapAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_chip_data.setText(stringList.get(position));

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_chip_data;


        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_chip_data = view.findViewById(R.id.tv_data_map_item);
        }


    }
}
