package com.example.mobihealthapis.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.GeneralFunctions.StaticData;
import com.example.mobihealthapis.Interface.PatientInterface;
import com.example.mobihealthapis.Models.Advice;
import com.example.mobihealthapis.Models.DiagnosticTests;
import com.example.mobihealthapis.R;

import java.util.List;

public class MultipleAdviceAdapter extends RecyclerView.Adapter<MultipleAdviceAdapter.MyViewHolder>{

    Context context;
    List<Advice> AdviceList;
    PatientInterface patientInterface;

    public MultipleAdviceAdapter(Context context, List<Advice> AdviceList) {
        this.context = context;
        this.AdviceList = AdviceList;
        this.patientInterface = (PatientInterface)context;
        hasStableIds();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.multiple_symptom_list_layout, parent, false);
        return new MultipleAdviceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Advice d = AdviceList.get(position);

        holder.tv_symptoms_number_dialog.setText((position+1)+".");

        holder.tv_symptoms_dialog.setText(""+d.getAdvice_data());
        holder.tv_symptoms_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientInterface.OnPatientClick(position, StaticData.Adapter_identifier.advice_add);
            }
        });
    }

    @Override
    public int getItemCount() {
        return AdviceList.size();
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
