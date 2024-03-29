package com.example.mobihealthapis.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.GeneralFunctions.StaticData;
import com.example.mobihealthapis.Interface.PatientInterface;
import com.example.mobihealthapis.Models.Advice;
import com.example.mobihealthapis.R;

import java.util.List;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.MyViewHolder> {

    Context context;
    List<Advice> adviceList;
    PatientInterface patientInterface;
    public AdviceAdapter(Context context,List<Advice> adviceList) {
        this.context = context;
        this.adviceList = adviceList;
        this.patientInterface=(PatientInterface)context;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Advice advice = adviceList.get(position);


        holder.tv_chip_data.setText(advice.getAdvice_data());

        holder.tv_chip_number.setText((position+1)+"");
        holder.ll_chip_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientInterface.OnPatientClick(position, StaticData.Adapter_identifier.advice);
            }
        });

    }

    @Override
    public int getItemCount() {
        return adviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_chip_data,tv_chip_number;
        LinearLayout ll_chip_delete;


        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_chip_data = view.findViewById(R.id.tv_chip_data);
            tv_chip_number = view.findViewById(R.id.tv_chip_number);
            ll_chip_delete = view.findViewById(R.id.ll_chip_delete);
        }
    }

}
