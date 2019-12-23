package com.example.mobihealthapis.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.Interface.PatientInterface;
import com.example.mobihealthapis.Models.Patient;
import com.example.mobihealthapis.R;

import java.util.List;
import java.util.StringTokenizer;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.MyViewHolder> {

    Context context;
    List<Patient.Data> PatientList;
    PatientInterface patientInterface;

    public PatientsAdapter(Context context, List<Patient.Data> patientList) {
        this.context = context;
        this.PatientList = patientList;
        this.patientInterface = (PatientInterface)context;
        hasStableIds();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_list_layout, parent, false);
        return new PatientsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {

        final Patient.Data p = PatientList.get(position);
        holder.tv_serial_no_patient_list.setText(""+(position+1));

        String name = "";
        String temp1 = p.getFname();
        StringTokenizer st = new StringTokenizer(temp1," ");

        //Log.e("Tokens ",p.getFname()+" | "+st.countTokens());
        if(st.countTokens() > 1){

            name +=st.nextToken();
            String temp = st.nextToken().toString().toUpperCase();
            name += " "+temp.charAt(0);
            holder.tv_patient_fname.setText(name);
        }
        else{
            holder.tv_patient_fname.setText(p.getFname());
        }

        holder.tv_patient_id.setText(""+p.getPatientId());

        holder.tv_patient_number.setText(""+p.getContactNumber());





        holder.ll_patient_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientInterface.OnPatientClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return PatientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img_profile;
        LinearLayout ll_patient_main;
        TextView tv_serial_no_patient_list,tv_patient_id,tv_booking_type,tv_patient_fname,tv_patient_number;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tv_serial_no_patient_list = view.findViewById(R.id.tv_serial_no_patient_list);
            tv_patient_id = view.findViewById(R.id.tv_patient_id);
            tv_booking_type = view.findViewById(R.id.tv_booking_type);
            tv_patient_fname = view.findViewById(R.id.tv_patient_fname);
            tv_patient_number = view.findViewById(R.id.tv_patient_number);
            ll_patient_main = view.findViewById(R.id.ll_patient_main);

        }
    }

}
