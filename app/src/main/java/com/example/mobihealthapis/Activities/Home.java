package com.example.mobihealthapis.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobihealthapis.Adapters.AdviceAdapter;
import com.example.mobihealthapis.Adapters.FollowupAdapter;
import com.example.mobihealthapis.Adapters.MedicineAdapter;
import com.example.mobihealthapis.Adapters.PatientsAdapter;
import com.example.mobihealthapis.GeneralFunctions.transitions;
import com.example.mobihealthapis.R;
import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;
import com.nex3z.flowlayout.FlowLayout;

public class Home extends AppCompatActivity {

    RelativeLayout rl_home_main;
    RecyclerView rv_main_drawer,rv_advice_list_main,rv_followup_list_main,rv_medicines_list_main;
    LinearLayout ll_activate_voice;
    RipplePulseLayout mRipplePulseLayout;

    int fixedheight,fixedheightcounter = 0;

    //Patient Details


    Boolean[] isExpanded;
    LinearLayout[] ll_expand_main,ll_expand,ll_after_expand,ll_data;
    ImageView[] img_expand;

    FlowLayout flow_symptoms,flow_diagnosis,flow_diagnostic;

    private int details = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        InitializeObjects();

    }

    private void InitializeObjects() {

        rl_home_main = findViewById(R.id.rl_home_main);

        rv_main_drawer = findViewById(R.id.rv_main_drawer);
        ll_activate_voice = findViewById(R.id.ll_activate_voice);
        //mRipplePulseLayout = findViewById(R.id.layout_ripplepulse);


        PatientDetails();

        ll_activate_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      mRipplePulseLayout.startRippleAnimation();
            }
        });

        ClickMethods();

        SetMainDrawerRecyclerView();
    }

    private void PatientDetails() {

        ll_data = new LinearLayout[details];
        ll_expand = new LinearLayout[details];
        ll_after_expand = new LinearLayout[details];
        ll_expand_main = new LinearLayout[details];
        img_expand = new ImageView[details];

        ll_expand[0] = findViewById(R.id.ll_expand_vital);
        ll_data[0] = findViewById(R.id.ll_data_vital);
        ll_expand_main[0] = findViewById(R.id.ll_vitals_expand_main);
        ll_after_expand[0] = findViewById(R.id.ll_vitals_after_expand);
        img_expand[0] = findViewById(R.id.img_expand_vital);

        ll_expand[1] = findViewById(R.id.ll_expand_symptoms);
        ll_data[1] = findViewById(R.id.ll_data_symptoms);
        ll_expand_main[1] = findViewById(R.id.ll_symptoms_expand_main);
        ll_after_expand[1] = findViewById(R.id.ll_symptoms_after_expand);
        img_expand[1] = findViewById(R.id.img_expand_symptoms);

        ll_expand[2] = findViewById(R.id.ll_expand_diagnosis);
        ll_data[2] = findViewById(R.id.ll_data_diagnosis);
        ll_expand_main[2] = findViewById(R.id.ll_diagnosis_expand_main);
        ll_after_expand[2] = findViewById(R.id.ll_diagnosis_after_expand);
        img_expand[2] = findViewById(R.id.img_expand_diagnosis);

        ll_expand[3] = findViewById(R.id.ll_expand_medicine);
        ll_data[3] = findViewById(R.id.ll_data_medicine);
        ll_expand_main[3] = findViewById(R.id.ll_medicine_expand_main);
        ll_after_expand[3] = findViewById(R.id.ll_medicine_after_expand);
        img_expand[3] = findViewById(R.id.img_expand_medicine);

        ll_expand[4] = findViewById(R.id.ll_expand_diagnostic);
        ll_data[4] = findViewById(R.id.ll_data_diagnostic);
        ll_expand_main[4] = findViewById(R.id.ll_diagnostic_expand_main);
        ll_after_expand[4] = findViewById(R.id.ll_diagnostic_after_expand);
        img_expand[4] = findViewById(R.id.img_expand_diagnostic);

        ll_expand[5] = findViewById(R.id.ll_expand_advice);
        ll_data[5] = findViewById(R.id.ll_data_advice);
        ll_expand_main[5] = findViewById(R.id.ll_advice_expand_main);
        ll_after_expand[5] = findViewById(R.id.ll_advice_after_expand);
        img_expand[5] = findViewById(R.id.img_expand_advice);

        ll_expand[6] = findViewById(R.id.ll_expand_followup);
        ll_data[6] = findViewById(R.id.ll_data_followup);
        ll_expand_main[6] = findViewById(R.id.ll_followup_expand_main);
        ll_after_expand[6] = findViewById(R.id.ll_followup_after_expand);
        img_expand[6] = findViewById(R.id.img_expand_followup);

        isExpanded = new Boolean[details];
        for(int i = 0; i < details; i++){
            if(ll_data[i].getVisibility() == View.VISIBLE)
                isExpanded[i] = true;
            else
                isExpanded[i] = false;


            final int temp = i;
            ll_expand[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     OnExpandClicked(temp);
                }
            });
        }


        SetSymptoms();

        SetDiagnosis();

        SetDiagnosticTests();

        SetAdvice();

        SetFollowUp();

        SetMedicines();
    }

    private void SetMedicines() {

        rv_medicines_list_main = findViewById(R.id.rv_medicines_list_main);
        MedicineAdapter medicineAdapter = new MedicineAdapter(this);
        rv_medicines_list_main.setAdapter(medicineAdapter);
        rv_medicines_list_main.setHasFixedSize(true);

    }

    private void SetFollowUp() {

        rv_followup_list_main = findViewById(R.id.rv_followup_list_main);
        FollowupAdapter followupAdapter = new FollowupAdapter(this);
        rv_followup_list_main.setAdapter(followupAdapter);
        rv_followup_list_main.setHasFixedSize(true);

    }

    private void SetAdvice() {
        rv_advice_list_main = findViewById(R.id.rv_advice_list_main);
        AdviceAdapter adviceAdapter = new AdviceAdapter(this);
        rv_advice_list_main.setAdapter(adviceAdapter);
        rv_advice_list_main.setHasFixedSize(true);
    }

    private void SetSymptoms() {

        flow_symptoms = findViewById(R.id.flow_symptoms);
        ViewGroup parent = (ViewGroup)rl_home_main;

        for(int i = 0; i < 6; i++){
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dda_list_layout,parent , false);
            TextView data = itemView.findViewById(R.id.tv_chip_data);
            LinearLayout ll_chip_delete = itemView.findViewById(R.id.ll_chip_delete);
            final TextView number = itemView.findViewById(R.id.tv_chip_number);
            number.setText((i+1)+".");
            data.setText("Fever with cold");
            flow_symptoms.addView(itemView);

            itemView.setClickable(true);
            ll_chip_delete.setClickable(true);
            ll_chip_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Home.this, ""+number.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    private void SetDiagnosis() {

        flow_diagnosis = findViewById(R.id.flow_diagnosis);
        ViewGroup parent = (ViewGroup)rl_home_main;

        for(int i = 0; i < 6; i++){
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dda_list_layout,parent , false);
            TextView data = itemView.findViewById(R.id.tv_chip_data);
            LinearLayout ll_chip_delete = itemView.findViewById(R.id.ll_chip_delete);
            final TextView number = itemView.findViewById(R.id.tv_chip_number);
            number.setText((i+1)+".");
            data.setText("Fever with cold");
            flow_diagnosis.addView(itemView);

            itemView.setClickable(true);
            ll_chip_delete.setClickable(true);
            ll_chip_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Home.this, ""+number.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    private void SetDiagnosticTests() {

        flow_diagnostic = findViewById(R.id.flow_diagnostic);
        ViewGroup parent = (ViewGroup)rl_home_main;

        for(int i = 0; i < 6; i++){
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dda_list_layout,parent , false);
            TextView data = itemView.findViewById(R.id.tv_chip_data);
            LinearLayout ll_chip_delete = itemView.findViewById(R.id.ll_chip_delete);
            final TextView number = itemView.findViewById(R.id.tv_chip_number);
            number.setText((i+1)+".");
            data.setText("Fever with cold");
            flow_diagnostic.addView(itemView);

            itemView.setClickable(true);
            ll_chip_delete.setClickable(true);
            ll_chip_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Home.this, ""+number.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    private void OnExpandClicked(final int temp) {

        if(fixedheightcounter == 0) {
            fixedheight = ll_expand_main[temp].getHeight();
            fixedheightcounter++;
        }

        //Toast.makeText(this, ""+temp, Toast.LENGTH_SHORT).show();

        //if not expanded then expand
        if(!isExpanded[temp]){
            transitions.expand(ll_data[temp]);
            ll_expand_main[temp].setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout
                            .LayoutParams(ll_expand_main[temp].getWidth(), ll_expand_main[temp].getHeight()/10);
                    ll_expand_main[temp].setLayoutParams(layoutParams);

                }
            }, 100);


            //Collapse other expanded views

            for(int k =0; k<details;k++){

                if(k!=temp && isExpanded[k] == true){
                    final int pos = k;
                    //ll_data[pos].setVisibility(View.INVISIBLE);
                    transitions.collapse(ll_data[pos]);
                    img_expand[pos].setImageResource(R.drawable.ic_add_white);
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ll_expand_main[pos].setVisibility(View.VISIBLE);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout
                                    .LayoutParams(ll_expand_main[pos].getWidth(), fixedheight);
                            ll_expand_main[pos].setLayoutParams(layoutParams);
                        }
                    }, 150);
                    isExpanded[pos] = !isExpanded[pos];
                }

            }

            isExpanded[temp] = !isExpanded[temp];
        }

    }


    private void ClickMethods() {


    }



    private void SetMainDrawerRecyclerView() {

        PatientsAdapter adapter = new PatientsAdapter(this);
        rv_main_drawer.setAdapter(adapter);
        rv_main_drawer.setHasFixedSize(true);

    }

   /* private boolean toggleLayout(boolean isExpanded, LinearLayout layoutExpand, ImageView img_expand) {
        if (isExpanded) {
            transitions.expand(layoutExpand);
            img_expand.setImageResource(R.drawable.ic_minus);

        } else {
            transitions.collapse(layoutExpand);
            img_expand.setImageResource(R.drawable.ic_add_white);
        }
        return isExpanded;

    }*/



    /*
    private void PatienDetailsClickMethods() {

        //Vitals
        ll_expand_vital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLayout(isVitalsExpanded,ll_data_vital,img_expand_vital);
                isVitalsExpanded = !isVitalsExpanded;
            }
        });

        ll_data_vital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLayout(isVitalsExpanded,ll_data_vital,img_expand_vital);
                isVitalsExpanded = !isVitalsExpanded;
            }
        });


        //Symptoms
        ll_expand_symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLayout(isSymptomsExpanded,ll_data_symptoms,img_expand_symptoms);
                isSymptomsExpanded = !isSymptomsExpanded;
            }
        });

        ll_data_symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLayout(isSymptomsExpanded,ll_data_symptoms,img_expand_symptoms);
                isSymptomsExpanded = !isSymptomsExpanded;
            }
        });

    }


    public void OnDetailsExpandCollapse(LinearLayout temp, Boolean isExpanded){

        final LinearLayout ll_holder = temp;
        if(fixedheightcounter == 0) {
            fixedheight = ll_holder.getHeight();
            fixedheightcounter++;
        }
        if(isExpanded){
            ll_holder.setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout
                            .LayoutParams(ll_holder.getWidth(), ll_holder.getHeight()/10);
                    ll_holder.setLayoutParams(layoutParams);

                }
            }, 100);

            Toast.makeText(this, ll_holder.getHeight()+" | "+
                    fixedheight, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, ll_holder.getHeight()+" | "+
                    fixedheight, Toast.LENGTH_SHORT).show();


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ll_holder.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout
                            .LayoutParams(ll_holder.getWidth(), fixedheight);
                    ll_holder.setLayoutParams(layoutParams);
                }
            }, 150);


        }

    }

    */
}
