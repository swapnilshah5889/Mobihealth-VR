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
import com.example.mobihealthapis.Models.Med;
import com.example.mobihealthapis.Models.Medicine;
import com.example.mobihealthapis.R;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MyViewHolder> {

    Context context;
    List<Medicine> medicineList;
    PatientInterface patientInterface;

    public MedicineAdapter(Context context,List<Medicine> medicineList) {
        this.context = context;
        this.medicineList=medicineList;
        this.patientInterface= (PatientInterface)context;
        hasStableIds();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_list_layout,parent , false);
        return new MedicineAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Medicine obj = medicineList.get(position);
        holder.tv_medname.setText(obj.getName());
        holder.tv_genericname.setText(obj.getGenericname());

        holder.tv_num.setText((position+1)+".");
        double arr[]= obj.getDailytimings();
        holder.tv_medfreq.setText((int)arr[0]+" "+(int)arr[1]+" "+(int)arr[2]+" "+obj.getAfbf()+" "+ obj.getDuration()+"/"+obj.getFrequency());
        holder.ll_deleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientInterface.OnPatientClick(position, StaticData.Adapter_identifier.medicine_delete);
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //TextView tv_chip_data;
        TextView tv_medname,tv_num,tv_genericname,tv_medfreq;
        LinearLayout ll_deleted;


        public MyViewHolder(@NonNull View view) {
            super(view);
          //  tv_chip_data = view.findViewById(R.id.tv_chip_data);
            tv_medname=view.findViewById(R.id.tv_medname);
            tv_genericname=view.findViewById(R.id.tv_genericname);
            tv_medfreq = view.findViewById(R.id.tv_medfreq);
            tv_num = view.findViewById(R.id.tv_num);
            ll_deleted=view.findViewById(R.id.ll_deleted);
        }
    }

}
