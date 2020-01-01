package com.example.mobihealthapis.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.GeneralFunctions.StaticData;
import com.example.mobihealthapis.Interface.PatientInterface;
import com.example.mobihealthapis.Models.Medicine;
import com.example.mobihealthapis.R;

import java.util.List;

public class MedicinePDFAdapter extends RecyclerView.Adapter<MedicinePDFAdapter.MyViewHolder> {

    Context context;
    List<Medicine> medicineList;


    public MedicinePDFAdapter(Context context, List<Medicine> medicineList) {
        this.context = context;
        this.medicineList=medicineList;
        hasStableIds();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_layout_pdf,parent , false);
        return new MedicinePDFAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Medicine obj = medicineList.get(position);

        holder.tv_num.setText((position+1)+".");
        holder.tv_medname.setText(obj.getName());
        holder.tv_genericname.setText(obj.getGenericname());

        double arr[]= obj.getDailytimings();

        holder.tv_dailytimings_pdf.setText((int)arr[0]+"-"+(int)arr[1]+"-"+(int)arr[2]+"-");
        holder.tv_afbf_pdf.setText(""+obj.getAfbf());
        holder.tv_duration_freq_pdf.setText(""+ obj.getDuration()+"/"+obj.getFrequency());

    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //TextView tv_chip_data;
        TextView tv_medname,tv_num,tv_genericname,tv_medfreq,tv_dailytimings_pdf,tv_afbf_pdf,tv_duration_freq_pdf;

        public MyViewHolder(@NonNull View view) {
            super(view);
          //  tv_chip_data = view.findViewById(R.id.tv_chip_data);
            tv_medname=view.findViewById(R.id.tv_medname_pdf);
            tv_genericname=view.findViewById(R.id.tv_genericname_pdf);
            tv_num = view.findViewById(R.id.tv_num_pdf);
            tv_dailytimings_pdf = view.findViewById(R.id.tv_dailytimings_pdf);
            tv_afbf_pdf = view.findViewById(R.id.tv_afbf_pdf);
            tv_duration_freq_pdf = view.findViewById(R.id.tv_duration_freq_pdf);
        }
    }

}
