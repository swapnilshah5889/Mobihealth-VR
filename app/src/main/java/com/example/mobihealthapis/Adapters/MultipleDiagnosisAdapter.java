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
import com.example.mobihealthapis.Models.Diagnosis;
import com.example.mobihealthapis.Models.Issues;
import com.example.mobihealthapis.R;

import java.util.List;

public class MultipleDiagnosisAdapter extends RecyclerView.Adapter<MultipleDiagnosisAdapter.MyViewHolder>{

    Context context;
    List<Diagnosis.Data> DiagnosisList;
    PatientInterface patientInterface;

    public MultipleDiagnosisAdapter(Context context, List<Diagnosis.Data> DiagnosisList) {
        this.context = context;
        this.DiagnosisList = DiagnosisList;
        this.patientInterface = (PatientInterface)context;
        hasStableIds();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.multiple_symptom_list_layout, parent, false);
        return new MultipleDiagnosisAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Diagnosis.Data d = DiagnosisList.get(position);

        holder.tv_symptoms_number_dialog.setText((position+1)+".");

        holder.tv_symptoms_dialog.setText(""+d.getDiagnosis());
        holder.tv_symptoms_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientInterface.OnPatientClick(position, StaticData.Adapter_identifier.diagnosis);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DiagnosisList.size();
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
