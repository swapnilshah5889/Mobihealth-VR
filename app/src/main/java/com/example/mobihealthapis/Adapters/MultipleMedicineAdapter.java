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
import com.example.mobihealthapis.Models.Issues;
import com.example.mobihealthapis.Models.Med;
import com.example.mobihealthapis.R;

import java.util.List;

public class MultipleMedicineAdapter extends RecyclerView.Adapter<MultipleMedicineAdapter.MyViewHolder>{

    Context context;
    List<Med.Data> MedList;
    PatientInterface patientInterface;

    public MultipleMedicineAdapter(Context context, List<Med.Data> MedList) {
        this.context = context;
        this.MedList = MedList;
        this.patientInterface = (PatientInterface)context;
        hasStableIds();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.multiple_symptom_list_layout, parent, false);
        return new MultipleMedicineAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Med.Data d = MedList.get(position);

        holder.tv_symptoms_number_dialog.setText((position+1)+".");

        holder.tv_symptoms_dialog.setText(""+d.getMedicine_name());
        holder.tv_symptoms_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientInterface.OnPatientClick(position, StaticData.Adapter_identifier.medicine_add);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MedList.size();
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
