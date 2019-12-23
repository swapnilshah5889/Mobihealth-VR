package com.example.mobihealthapis.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.Adapters.AdviceAdapter;
import com.example.mobihealthapis.Adapters.FollowupAdapter;
import com.example.mobihealthapis.Adapters.MedicineAdapter;
import com.example.mobihealthapis.Adapters.PatientsAdapter;
import com.example.mobihealthapis.GeneralFunctions.OnSwipeTouchListener;
import com.example.mobihealthapis.GeneralFunctions.transitions;
import com.example.mobihealthapis.Interface.PatientInterface;
import com.example.mobihealthapis.Models.Patient;
import com.example.mobihealthapis.Models.Vitals;
import com.example.mobihealthapis.R;
import com.example.mobihealthapis.database_call.NetworkCall;
import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;
import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.mobihealthapis.database_call.utils_string.API_URL.VR_APIS;

public class Home extends AppCompatActivity implements PatientInterface {


    //Home
    private final int doctor_id = 40089;

    TextView tv_home_total_patients,tv_patient_draft,tv_patients_checked_in;
    List<Patient.Data> PatientsList;
    ImageView img_patient;

    RelativeLayout rl_home_main;
    RecyclerView rv_main_drawer,rv_advice_list_main,rv_followup_list_main,rv_medicines_list_main;
    LinearLayout ll_activate_voice,ll_patient_drawer;
    RipplePulseLayout mRipplePulseLayout;

    int fixedheight,fixedheightcounter = 0;

    int SelectedPatientPosition;
    Patient.Data SelectedPatient;

    //Patient Details

    LinearLayout ll_main_patient_rx;
    Boolean[] isExpanded;
    LinearLayout[] ll_expand_main,ll_expand,ll_after_expand,ll_data;
    ImageView[] img_expand;
    TextView tv_patient_id_2,tv_patient_name,tv_gender_age,tv_bmi,tv_weight,tv_height;
    LinearLayout ll_previous_rx;



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

        HomeInit();

        PatientDetails();

        ll_activate_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      mRipplePulseLayout.startRippleAnimation();
            }
        });




    }

    private void HomeInit() {

        tv_home_total_patients = findViewById(R.id.tv_home_total_patients);
        tv_patients_checked_in = findViewById(R.id.tv_patients_checked_in);
        tv_patient_draft = findViewById(R.id.tv_patient_draft);

        ll_patient_drawer = findViewById(R.id.ll_patient_drawer);

        FetchPatients();

        HomeClickMethods();
    }

    private void FetchPatients() {

        HashMap<String,String> params = new HashMap<>();

        params.put("action","getChildren");
        params.put("drId",""+doctor_id);

        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);


        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {


                try {
                    //tv_main.setText(responseStr);
                    Patient obj = new Gson().fromJson(responseStr, Patient.class);

                    if(obj.getStatus()){
                        PatientsList = new ArrayList<>();
                        PatientsList.addAll(obj.getData());

                        tv_home_total_patients.setText("Today's Patients - "+PatientsList.size());
                        tv_patients_checked_in.setText(""+PatientsList.size());
                        SetMainDrawerRecyclerView();
                        //tv_main.setText(obj.getTotal_records()+"|"+vitalList.size());
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });


    }

    private void HomeClickMethods() {



    }

    private void PatientDetails() {

        ll_main_patient_rx = findViewById(R.id.ll_main_patient_rx);
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

        tv_patient_id_2=findViewById(R.id.tv_patient_id_2);
        tv_patient_name=findViewById(R.id.tv_patient_name);
        tv_gender_age=findViewById(R.id.tv_gender_age);
        ll_previous_rx=findViewById(R.id.ll_previous_rx);

        //Vitals
        tv_weight = findViewById(R.id.tv_weight);
        tv_bmi = findViewById(R.id.tv_bmi);
        tv_height = findViewById(R.id.tv_height);





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


        PatientDetailsClickMethods();

        SetSymptoms();

        SetDiagnosis();

        SetDiagnosticTests();

        SetAdvice();

        SetFollowUp();

        SetMedicines();


    }

    private void PatientDetailsClickMethods() {

        ll_main_patient_rx.setOnTouchListener(new OnSwipeTouchListener(Home.this){

            @Override
            public void onSwipeLeft() {

                //Toast.makeText(Home.this, "Left", Toast.LENGTH_SHORT).show();
                ShowHidePatientList(0);

            }

            @Override
            public void onSwipeRight() {
                //Toast.makeText(Home.this, "Right", Toast.LENGTH_SHORT).show();
                ShowHidePatientList(1);
                super.onSwipeRight();
            }

        });
    }

    private void ShowHidePatientList(int x) {

        if(x == 1 && (ll_patient_drawer.getVisibility() == View.GONE ||
                ll_patient_drawer.getVisibility() == View.INVISIBLE))
            ll_patient_drawer.setVisibility(View.VISIBLE);
        else if(ll_patient_drawer.getVisibility() == View.VISIBLE)
            ll_patient_drawer.setVisibility(View.GONE);

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






    private void SetMainDrawerRecyclerView() {

        PatientsAdapter adapter = new PatientsAdapter(this,PatientsList);
        rv_main_drawer.setAdapter(adapter);
        rv_main_drawer.setHasFixedSize(true);

    }

    @Override
    public void OnPatientClick(int position) {
        SelectedPatientPosition = position;
        SelectedPatient = PatientsList.get(SelectedPatientPosition);
        ll_patient_drawer.setVisibility(View.GONE);
        ll_main_patient_rx.setVisibility(View.VISIBLE);
        //Toast.makeText(this, SelectedPatient.getFname()+" Selected", Toast.LENGTH_SHORT).show();

        SetPatientDetails();



    }

    private void SetPatientDetails() {

        tv_patient_name.setText(SelectedPatient.getFname());
        tv_patient_id_2.setText("ID : "+SelectedPatient.getPatientId());
        tv_gender_age.setText( SelectedPatient.getGender()+" | " + SelectedPatient.getAge());

        FetchVitals();

    }



    private void FetchVitals() {

        HashMap<String,String> params = new HashMap<>();

        params.put("action","getChildVitals");
        params.put("patient_id", ""+SelectedPatient.getPatientId());

        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);


        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {


                try {
                    //tv_main.setText(responseStr);
                    Vitals obj = new Gson().fromJson(responseStr, Vitals.class);

                    if(obj.getStatus()){
                        List<Vitals.Data> vitalList = new ArrayList<>();
                        vitalList.addAll(obj.getData());

                        SelectedPatient.setVitals(vitalList.get(0));

                        tv_height.setText(SelectedPatient.getVitals().getHeight()+" cm");
                        tv_weight.setText(SelectedPatient.getVitals().getWeight()+" k.g.");
                        tv_bmi.setText(""+SelectedPatient.getVitals().getBMI());

                        //tv_main.setText(obj.getTotal_records()+"|"+vitalList.size());
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });

    }

}
