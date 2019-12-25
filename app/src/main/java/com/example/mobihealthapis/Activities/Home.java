package com.example.mobihealthapis.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.Adapters.AdviceAdapter;
import com.example.mobihealthapis.Adapters.FollowupAdapter;
import com.example.mobihealthapis.Adapters.MedicineAdapter;
import com.example.mobihealthapis.Adapters.MultipleDiagnosisAdapter;
import com.example.mobihealthapis.Adapters.MultipleDiagnosticTestAdapter;
import com.example.mobihealthapis.Adapters.MultipleSymptomAdapter;
import com.example.mobihealthapis.Adapters.PatientsAdapter;
import com.example.mobihealthapis.GeneralFunctions.Functions;
import com.example.mobihealthapis.GeneralFunctions.OnSwipeTouchListener;
import com.example.mobihealthapis.GeneralFunctions.StaticData;
import com.example.mobihealthapis.GeneralFunctions.transitions;
import com.example.mobihealthapis.Interface.PatientInterface;
import com.example.mobihealthapis.Models.Diagnosis;
import com.example.mobihealthapis.Models.DiagnosticTests;
import com.example.mobihealthapis.Models.Issues;
import com.example.mobihealthapis.Models.Med;
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
import java.util.Locale;
import java.util.StringTokenizer;

import static com.example.mobihealthapis.GeneralFunctions.Functions.FilterDiagnosis;
import static com.example.mobihealthapis.GeneralFunctions.Functions.FilterDiagnosticTest;
import static com.example.mobihealthapis.GeneralFunctions.Functions.FilterIssues;
import static com.example.mobihealthapis.GeneralFunctions.Functions.IsInList;
import static com.example.mobihealthapis.GeneralFunctions.Functions.isNumeric;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VOICE_RECOGNITION_REQUEST_CODE;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_MULTIPLE_DIAGNOSIS;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_MULTIPLE_DIAGNOSTIC_TEST;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_MULTIPLE_SYMPTOMS;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_NOMATCH_PROMPT_DIAGNOSIS;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_NOMATCH_PROMPT_DIAGNOSTIC_TEST;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_NOMATCH_PROMPT_SYMPTOM;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.advice;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.diagnosis;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.diagnostic;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.followup;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.medicine;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.symptoms;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.vitals;
import static com.example.mobihealthapis.database_call.utils_string.API_URL.VR_APIS;

public class Home extends AppCompatActivity implements PatientInterface {


    //Home
    private final int doctor_id = 40089;

    int ExpandedDetail = -1;

    TextView tv_home_total_patients, tv_patient_draft, tv_patients_checked_in;
    List<Patient.Data> PatientsList;
    ImageView img_patient;
    TextToSpeech textToSpeech;
    RelativeLayout rl_home_main;
    RecyclerView rv_main_drawer, rv_advice_list_main, rv_followup_list_main, rv_medicines_list_main;
    LinearLayout ll_activate_voice, ll_patient_drawer;
    RipplePulseLayout mRipplePulseLayout;

    int fixedheight, fixedheightcounter = 0;

    int SelectedPatientPosition;
    Patient.Data SelectedPatient;


    List<Issues.Data> Final_Symptoms;
    List<Diagnosis.Data> Final_Diagnosis;
    List<DiagnosticTests.Data> Final_DiagnosticTests;
    List<String> Final_Advice;
    List<Med.Data> Final_Medicines;
    String temp_symptom="";
    List<Issues.Data> issuesmatched;
    List<Diagnosis.Data> diagnosismatched;
    List<DiagnosticTests.Data> diagnostictestsmatched;
    //Patient Details

    LinearLayout ll_main_patient_rx;
    Boolean[] isExpanded;
    LinearLayout[] ll_expand_main, ll_expand, ll_after_expand, ll_data;
    ImageView[] img_expand;
    TextView tv_patient_id_2, tv_patient_name, tv_gender_age, tv_bmi, tv_weight, tv_height,tv_temperature,tv_hc;
    LinearLayout ll_previous_rx;
    List<Issues.Data> Issuelist;
    List<Diagnosis.Data> DiagnosisList;
    List<DiagnosticTests.Data> DiagnosticTestList;
    FlowLayout flow_symptoms, flow_diagnosis, flow_diagnostic;

    private int details = 7;

    //Symptom Dialog
    LinearLayout ll_symptom_dialog;
    TextView tv_dialog_heading,tv_symptom_close_dialog,tv_dialog_count;
    RecyclerView rv_multiple_symptoms;

    //no match promopt
    LinearLayout ll__uni_prompt;
    TextView tv_promt_question,tv_promt_data,tv_promt_no,tv_promt_yes;


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

        DiagnosticTestList = new ArrayList<>();
        DiagnosisList = new ArrayList<>();
        Issuelist = new ArrayList<>();
        HomeInit();

        PatientDetails();

        ll_activate_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceRecognitionActivity(VOICE_RECOGNITION_REQUEST_CODE);
            }
        });
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.US);

                }


            }
        });

        PackageManager pm = getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            ll_activate_voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startVoiceRecognitionActivity(VOICE_RECOGNITION_REQUEST_CODE);
                }
            });
        } else {
            ll_activate_voice.setEnabled(false);
        }

        FetchSymptoms();
        FetchDiagnosis();
        FetchDiagnosticList();
    }

    public void startVoiceRecognitionActivity(int VOICE_REQUEST_CODE) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_REQUEST_CODE);

    }//VOICE_RECOGNITION_REQUEST_CODE


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {


            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String[] finalarr = GetRawDataFromSpeech(matches);


            if (finalarr.length > 0) {
                if (finalarr[0].equals("write") || finalarr[0].equals("right")
                        || finalarr[0].equals("set") || finalarr[0].equals("add") || finalarr[0].equals("start")) {

                    if (finalarr.length > 1) {
                        switch (finalarr[1]) {
                            case ("vital"):
                            case ("vitals"): {
                                OnExpandClicked(vitals);


                                break;
                            }
                            case ("issue"):
                            case ("issues"):
                            case ("complaints"):
                            case ("complaint"):
                            case ("symptom"):
                            case ("symptoms"): {
                                OnExpandClicked(symptoms);
                                break;
                            }

                            case ("diagnosis"): {
                                OnExpandClicked(diagnosis);

                                break;
                            }
                            case ("medicine"):
                            case ("prescription"): {
                                if (finalarr.length > 1) {

                                    OnExpandClicked(medicine);
                                }
                                break;
                            }
                            case ("diagnostic"): {
                                if (finalarr.length > 2) {
                                    if (finalarr[2].equals("test")) {
                                        OnExpandClicked(diagnostic);
                                        break;
                                    }
                                }
                                break;
                            }


                            case ("follow"): {
                                if (finalarr.length > 2) {
                                    if (finalarr[1].equals("follow") && finalarr[2].equals("up")) {
                                        OnExpandClicked(followup);
                                    }
                                }

                                break;
                            }

                            case ("advice"):
                            case ("advise"): {
                                OnExpandClicked(advice);
                                break;
                            }

                            default:
                                Toast.makeText(this, "Command not recognized", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                }
                //setting vitals when expanded
                else if (Functions.CheckData(finalarr[0], StaticData.Vitals) && ExpandedDetail == vitals) {
                    SetVitals(finalarr);
                }

                // delete
                else if(finalarr[0].equals("delete")){
                    if(finalarr.length>2)
                    {
                        //if symptoms expanded
                        if(ExpandedDetail == symptoms){
                            if(finalarr[1].equals("number") && finalarr.length>2){
                                if(isNumeric(finalarr[2]) ){
                                    int temp= Integer.parseInt(finalarr[2]);
                                    if(IsInList(temp,Final_Symptoms.size())) {
                                        temp--;
                                        DeleteSymptom(temp);
                                    }
                                }
                            }
                        }
                        //if diagnosis expanded
                        else if(ExpandedDetail == diagnosis){
                            if(finalarr[1].equals("number") && finalarr.length>2){
                                if(isNumeric(finalarr[2]) ){
                                    int temp= Integer.parseInt(finalarr[2]);
                                    if(IsInList(temp,Final_Diagnosis.size())) {
                                        temp--;
                                        DeleteDiagnosis(temp);
                                    }
                                }
                            }
                        }
                        //if diagnostic test expanded
                        else if(ExpandedDetail == diagnostic){
                            if(finalarr[1].equals("number") && finalarr.length>2){
                                if(isNumeric(finalarr[2]) ){
                                    int temp= Integer.parseInt(finalarr[2]);
                                    if(IsInList(temp,Final_DiagnosticTests.size())) {
                                        temp--;
                                        DeleteDiagnosticTest(temp);
                                    }
                                }
                            }
                        }



                        //global delete
                        if(finalarr[1].equals("symptom")){
                            if(finalarr[2].equals("number") && finalarr.length>3){
                                if(isNumeric(finalarr[3]) ){
                                   int temp= Integer.parseInt(finalarr[3]);
                                   if(IsInList(temp,Final_Symptoms.size())) {
                                       temp--;
                                       DeleteSymptom(temp);
                                   }
                               }
                            }
                        }
                        else  if(finalarr[1].equals("diagnosis")){
                            if(finalarr[2].equals("number") && finalarr.length>3){
                                if(isNumeric(finalarr[3]) ){
                                    int temp= Integer.parseInt(finalarr[3]);
                                    if(IsInList(temp,Final_Diagnosis.size())) {
                                        temp--;
                                        DeleteDiagnosis(temp);
                                    }
                                }
                            }
                        }
                        else  if(finalarr[1].equals("test")){
                            if(finalarr[2].equals("number") && finalarr.length>3){
                                if(isNumeric(finalarr[3]) ){
                                    int temp= Integer.parseInt(finalarr[3]);
                                    if(IsInList(temp,Final_DiagnosticTests.size())) {
                                        temp--;
                                        DeleteDiagnosticTest(temp);
                                    }
                                }
                            }
                        }



                    }

                }
                else if(ExpandedDetail == symptoms)
                {

                    SetSymptoms(finalarr);

                }
                else if(ExpandedDetail == diagnosis)
                {
                    SetDiagnosis(finalarr);
                }
                else if(ExpandedDetail == diagnostic)
                {
                    SetDiagnosticTests(finalarr);
                }

            }



        }
        else if(requestCode == VR_NOMATCH_PROMPT_SYMPTOM && resultCode == RESULT_OK){

            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String[] finalarr = GetRawDataFromSpeech(matches);
            if (finalarr.length > 0){

                if((finalarr[0].equals("yes")||finalarr[0].equals("accept") || finalarr[0].equals("except")) &&
                        (!temp_symptom.equals("")))
                {
                    AddSymptom(temp_symptom);
                }
                else if(finalarr[0].equals("no")||finalarr[0].equals("decline"))
                {

                }
            }
        }
        else if(requestCode == VR_MULTIPLE_SYMPTOMS && resultCode == RESULT_OK) {
            ll_symptom_dialog.setVisibility(View.GONE);
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String[] finalarr = GetRawDataFromSpeech(matches);
            if(finalarr.length>2)
            {
                if(finalarr[0].equals("select")){
                    if(finalarr[1].equals("number")){
                        if(isNumeric(finalarr[2])){
                            int pos = Integer.parseInt(finalarr[2]);
                            if(IsInList(pos,issuesmatched.size())){
                                pos--;
                                AddSymptom(issuesmatched.get(pos).getIssues());
                            }

                        }
                    }
                }
            }
        }

        else if(requestCode == VR_NOMATCH_PROMPT_DIAGNOSIS && resultCode == RESULT_OK){

            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String[] finalarr = GetRawDataFromSpeech(matches);
            if (finalarr.length > 0){

                if((finalarr[0].equals("yes")||finalarr[0].equals("accept") || finalarr[0].equals("except")) &&
                        (!temp_symptom.equals("")))
                {
                    AddDiagnosis(temp_symptom);
                }
                else if(finalarr[0].equals("no")||finalarr[0].equals("decline"))
                {

                }
            }
        }
        else if(requestCode == VR_MULTIPLE_DIAGNOSIS && resultCode == RESULT_OK) {
            ll_symptom_dialog.setVisibility(View.GONE);
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String[] finalarr = GetRawDataFromSpeech(matches);
            if(finalarr.length>2)
            {
                if(finalarr[0].equals("select")){
                    if(finalarr[1].equals("number")){
                        if(isNumeric(finalarr[2])){
                            int pos = Integer.parseInt(finalarr[2]);
                            if(IsInList(pos,diagnosismatched.size())){
                                pos--;
                                AddDiagnosis(diagnosismatched.get(pos).getDiagnosis());
                            }

                        }
                    }
                }
            }
        }

        else if(requestCode == VR_NOMATCH_PROMPT_DIAGNOSTIC_TEST && resultCode == RESULT_OK){

            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String[] finalarr = GetRawDataFromSpeech(matches);
            if (finalarr.length > 0){

                if((finalarr[0].equals("yes")||finalarr[0].equals("accept") || finalarr[0].equals("except")) &&
                        (!temp_symptom.equals("")))
                {
                    AddDiagnosticTest(temp_symptom);
                }
                else if(finalarr[0].equals("no")||finalarr[0].equals("decline"))
                {

                }
            }
        }
        else if(requestCode == VR_MULTIPLE_DIAGNOSTIC_TEST && resultCode == RESULT_OK) {
            ll_symptom_dialog.setVisibility(View.GONE);
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String[] finalarr = GetRawDataFromSpeech(matches);
            if(finalarr.length>2)
            {
                if(finalarr[0].equals("select")){
                    if(finalarr[1].equals("number")){
                        if(isNumeric(finalarr[2])){
                            int pos = Integer.parseInt(finalarr[2]);
                            if(IsInList(pos,diagnostictestsmatched.size())){
                                pos--;
                                AddDiagnosticTest(diagnostictestsmatched.get(pos).getTest_name());
                            }

                        }
                    }
                }
            }
        }



        super.onActivityResult(requestCode, resultCode, data);

    }

    private void DeleteDiagnosis(int pos) {
        flow_diagnosis.removeViewAt(pos);
        Final_Diagnosis.remove(pos);

        for (int i = 0; i < flow_diagnosis.getChildCount(); i++) {
            View view1 = flow_diagnosis.getChildAt(i);
            //if (view1.getId()>temp_pos) {
            final TextView number = view1.findViewById(R.id.tv_chip_number);
            number.setText((i + 1) + ".");
            //}
        }
    }



    private void DeleteDiagnosticTest(int pos) {
        flow_diagnostic.removeViewAt(pos);
        Final_DiagnosticTests.remove(pos);

        for (int i = 0; i < flow_diagnostic.getChildCount(); i++) {
            View view1 = flow_diagnostic.getChildAt(i);
            //if (view1.getId()>temp_pos) {
            final TextView number = view1.findViewById(R.id.tv_chip_number);
            number.setText((i + 1) + ".");
            //}
        }
    }
    private void DeleteSymptom(int pos) {

        flow_symptoms.removeViewAt(pos);
        Final_Symptoms.remove(pos);

        for (int i = 0; i < flow_symptoms.getChildCount(); i++) {
            View view1 = flow_symptoms.getChildAt(i);
            //if (view1.getId()>temp_pos) {
            final TextView number = view1.findViewById(R.id.tv_chip_number);
            number.setText((i + 1) + ".");
            //}
        }

    }




    private String[] GetRawDataFromSpeech(ArrayList<String> matches) {

        if (matches.contains("information")) {
            informationMenu();
        }


        String finaldata = matches.get(0).toLowerCase();


        StringTokenizer st = new StringTokenizer(finaldata, " ");
        String[] finalarr = new String[st.countTokens()];

        int l = 0;
        while (st.hasMoreTokens()) {
            finalarr[l] = st.nextToken();
            l++;
        }
        return finalarr;
    }

    public void informationMenu() {
        startActivity(new Intent("android.intent.action.INFOSCREEN"));
    }

    private void HomeInit() {

        tv_home_total_patients = findViewById(R.id.tv_home_total_patients);
        tv_patients_checked_in = findViewById(R.id.tv_patients_checked_in);
        tv_patient_draft = findViewById(R.id.tv_patient_draft);

        ll_patient_drawer = findViewById(R.id.ll_patient_drawer);

        Final_Symptoms = new ArrayList<>();
        Final_Diagnosis= new ArrayList<>();
        Final_DiagnosticTests = new ArrayList<>();
        Final_Advice= new ArrayList<>();
        Final_Medicines= new ArrayList<>();

        flow_symptoms = findViewById(R.id.flow_symptoms);

        FetchPatients();

        HomeClickMethods();
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

        tv_patient_id_2 = findViewById(R.id.tv_patient_id_2);
        tv_patient_name = findViewById(R.id.tv_patient_name);
        tv_gender_age = findViewById(R.id.tv_gender_age);
        ll_previous_rx = findViewById(R.id.ll_previous_rx);

        //Vitals
        tv_weight = findViewById(R.id.tv_weight);
        tv_bmi = findViewById(R.id.tv_bmi);
        tv_height = findViewById(R.id.tv_height);
        tv_temperature = findViewById(R.id.tv_temperature);
        tv_hc = findViewById(R.id.tv_hc);

        //Symptom Dialog
        ll_symptom_dialog = findViewById(R.id.ll_symptom_dialog);
        tv_dialog_heading = findViewById(R.id.tv_dialog_heading);
        tv_symptom_close_dialog = findViewById(R.id.tv_symptom_close_dialog);
        tv_dialog_count = findViewById(R.id.tv_dialog_count);
        rv_multiple_symptoms = findViewById(R.id.rv_multiple_symptoms);

        //no match prompt
        ll__uni_prompt = findViewById(R.id.ll__uni_prompt);
        tv_promt_question = findViewById(R.id.tv_promt_question);
        tv_promt_data = findViewById(R.id.tv_promt_data);
        tv_promt_no = findViewById(R.id.tv_promt_no);
        tv_promt_yes = findViewById(R.id.tv_promt_yes);



        isExpanded = new Boolean[details];
        for (int i = 0; i < details; i++) {
            if (ll_data[i].getVisibility() == View.VISIBLE)
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

       // SetSymptoms();

       // SetDiagnosis();

        //SetDiagnosticTests();

        SetAdvice();

        SetFollowUp();

        SetMedicines();


    }

    private void PatientDetailsClickMethods() {

        ll_main_patient_rx.setOnTouchListener(new OnSwipeTouchListener(Home.this) {

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

        tv_symptom_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_symptom_dialog.setVisibility(View.GONE);
            }
        });


    }


    private void ShowHidePatientList(int x) {

        if (x == 1 && (ll_patient_drawer.getVisibility() == View.GONE ||
                ll_patient_drawer.getVisibility() == View.INVISIBLE))
            ll_patient_drawer.setVisibility(View.VISIBLE);
        else if (ll_patient_drawer.getVisibility() == View.VISIBLE)
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

    private void SetSymptoms(String[] arr) {

        if(Issuelist.size()>0){

            List<String> filtered_symptopms = FilterArray(arr);

             issuesmatched = new ArrayList<>();

            for(int i = 0; i < Issuelist.size(); i++){

                if(Issuelist.get(i).getIssues().toLowerCase().contains(filtered_symptopms.get(0))){
                    issuesmatched.add(Issuelist.get(i));
                }
            }

            //More than one filters and more than one issues
            if(issuesmatched.size()>1){
                if(filtered_symptopms.size() > 1)
                    issuesmatched = FilterIssues(1,filtered_symptopms,issuesmatched);

                ShowSymptomDialog("Symptom Options",issuesmatched);
                Toast.makeText(this, ""+issuesmatched.size(), Toast.LENGTH_SHORT).show();
            }
            //no issues matched
            else if(issuesmatched.size() == 0){
                String temp = "";
                for(int i = 0; i < arr.length; i++){
                    temp+=arr[i]+" ";
                }
                ShowNoMatchPrompt(temp,"Symptom");
                Toast.makeText(this, "No Results !", Toast.LENGTH_SHORT).show();
            }
            else if (issuesmatched.size() == 1){
                AddSymptom(issuesmatched.get(0).getIssues());
                Toast.makeText(this, ""+issuesmatched.get(0).getIssues(), Toast.LENGTH_SHORT).show();
            }


            ViewGroup parent = (ViewGroup) rl_home_main;

            /*for (int i = 0; i < 6; i++) {
                final View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.dda_list_layout, parent, false);
                TextView data = itemView.findViewById(R.id.tv_chip_data);
                LinearLayout ll_chip_delete = itemView.findViewById(R.id.ll_chip_delete);
                final TextView number = itemView.findViewById(R.id.tv_chip_number);
                number.setText((i + 1) + ".");
                data.setText("Fever with cold");
                //flow_symptoms.addView(itemView);

                itemView.setClickable(true);
                ll_chip_delete.setClickable(true);
                ll_chip_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Home.this, "" + number.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }*/

        }


    }
    private void SetVitals(String[] data) {

        HashMap<String, Double> rawVitals = Functions.getVitals(data);
        if (rawVitals.size() > 0) {
            for (String key : rawVitals.keySet()) {
                Double a=0.0;
                switch (key) {
                    case "height":
                        a= rawVitals.get(key);
                        tv_height.setText(a+" CM");
                        break;
                    case "weight":
                        a= rawVitals.get(key);
                        tv_weight.setText(a+" KG");
                        break;
                    case "head":
                        a= rawVitals.get(key);
                        tv_hc.setText(a+" CM");
                        break;
                    case "temperature":
                        a= rawVitals.get(key);
                        tv_temperature.setText(a+" °");
                        break;

                }
            }
        }




    }
    private void SetDiagnosis(String[] arr) {


        if(DiagnosisList.size()>0) {
            flow_diagnosis = findViewById(R.id.flow_diagnosis);
            ViewGroup parent = (ViewGroup) rl_home_main;

            List<String> filtered_diagnosis = FilterArray(arr);

            diagnosismatched = new ArrayList<>();

            for(int i = 0; i < DiagnosisList.size(); i++){

                if(DiagnosisList.get(i).getDiagnosis().toLowerCase().contains(filtered_diagnosis.get(0))){
                    diagnosismatched.add(DiagnosisList.get(i));
                }
            }

            //More than one filters and more than one issues
            if(diagnosismatched.size()>1){
                if(filtered_diagnosis.size() > 1)
                    diagnosismatched = FilterDiagnosis(1,filtered_diagnosis,diagnosismatched);

                ShowDiagnosisDialog("Diagnosis Options",diagnosismatched);
                Toast.makeText(this, ""+diagnosismatched.size(), Toast.LENGTH_SHORT).show();
            }
            //no issues matched
            else if(diagnosismatched.size() == 0){
                String temp = "";
                for(int i = 0; i < arr.length; i++){
                    temp+=arr[i]+" ";
                }
                ShowNoMatchPrompt(temp,"Diagnosis");
                Toast.makeText(this, "No Results !", Toast.LENGTH_SHORT).show();
            }
            else if (diagnosismatched.size() == 1){
                AddDiagnosis(diagnosismatched.get(0).getDiagnosis());
                Toast.makeText(this, ""+diagnosismatched.get(0).getDiagnosis(), Toast.LENGTH_SHORT).show();
            }


        }






    }

    private void SetDiagnosticTests(String[] arr) {

        if(DiagnosticTestList.size()>0) {
            flow_diagnostic = findViewById(R.id.flow_diagnostic);
            ViewGroup parent = (ViewGroup) rl_home_main;

            List<String> filtered_diagnosis = FilterArray(arr);

            diagnostictestsmatched = new ArrayList<>();

            for(int i = 0; i < DiagnosticTestList.size(); i++){

                if(DiagnosticTestList.get(i).getTest_name().toLowerCase().contains(filtered_diagnosis.get(0))){
                    diagnostictestsmatched.add(DiagnosticTestList.get(i));
                }
            }

            //More than one filters and more than one issues
            if(diagnostictestsmatched.size()>1){
                if(filtered_diagnosis.size() > 1)
                    diagnostictestsmatched = FilterDiagnosticTest(1,filtered_diagnosis,diagnostictestsmatched);

                ShowDiagnosticTestDialog("Diagnosis Options",diagnostictestsmatched);
                Toast.makeText(this, ""+diagnostictestsmatched.size(), Toast.LENGTH_SHORT).show();
            }
            //no issues matched
            else if(diagnostictestsmatched.size() == 0){
                String temp = "";
                for(int i = 0; i < arr.length; i++){
                    temp+=arr[i]+" ";
                }
                ShowNoMatchPrompt(temp,"Diagnostic");
                Toast.makeText(this, "No Results !", Toast.LENGTH_SHORT).show();
            }
            else if (diagnostictestsmatched.size() == 1){
                AddDiagnosticTest(diagnostictestsmatched.get(0).getTest_name());
                Toast.makeText(this, ""+diagnostictestsmatched.get(0).getTest_name(), Toast.LENGTH_SHORT).show();
            }


        }

    }




    private void ShowNoMatchPrompt(final String data, String type) {

        if(type.equals("Symptom")){

            tv_promt_data.setText(type+" : "+data);
            temp_symptom=data;
            tv_promt_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll__uni_prompt.setVisibility(View.GONE);
                }
            });


            tv_promt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddSymptom(data);


                }
            });


            ll__uni_prompt.setVisibility(View.VISIBLE);
            Speak(tv_promt_question.getText().toString(),VR_NOMATCH_PROMPT_SYMPTOM,1000);


        }
        else if(type.equals("Diagnosis"))
        {
            tv_promt_data.setText(type+" : "+data);
            temp_symptom=data;
            tv_promt_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll__uni_prompt.setVisibility(View.GONE);
                }
            });


            tv_promt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddDiagnosis(data);


                }
            });


            ll__uni_prompt.setVisibility(View.VISIBLE);
            Speak("No Matches Found in Database.Do you want to add this Diagnosis?",VR_NOMATCH_PROMPT_DIAGNOSIS,1000);


        }
        else if(type.equals("Diagnostic"))
        {
            tv_promt_data.setText(type+" : "+data);
            temp_symptom=data;
            tv_promt_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll__uni_prompt.setVisibility(View.GONE);
                }
            });


            tv_promt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddDiagnosticTest(data);


                }
            });


            ll__uni_prompt.setVisibility(View.VISIBLE);
            Speak("No Matches Found in Database.Do you want to add this Diagnostic test?",VR_NOMATCH_PROMPT_DIAGNOSTIC_TEST,1000);


        }
    }




    private void AddDiagnosticTest(String data) {

        final ViewGroup parent = (ViewGroup) rl_home_main;
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dda_list_layout, parent, false);
        TextView tempdata = itemView.findViewById(R.id.tv_chip_data);
        LinearLayout ll_chip_delete = itemView.findViewById(R.id.ll_chip_delete);
        itemView.setId(Final_DiagnosticTests.size());
        ll_chip_delete.setClickable(true);
        //final int temp_pos = Final_Symptoms.size();

        final DiagnosticTests.Data dtemp = new DiagnosticTests.Data(data);
        final TextView number = itemView.findViewById(R.id.tv_chip_number);
        number.setText((Final_DiagnosticTests.size()+1) + ".");
        Final_DiagnosticTests.add(dtemp);

        ll_chip_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp_pos = itemView.getId();
                Final_DiagnosticTests.remove(dtemp);
                flow_diagnostic.removeView(itemView);
                //DeleteSymptom(temp_pos);

                for (int i = 0; i < flow_diagnostic.getChildCount(); i++) {
                    View view1 = flow_diagnostic.getChildAt(i);
                    //if (view1.getId()>temp_pos) {
                    final TextView number = view1.findViewById(R.id.tv_chip_number);
                    number.setText((i + 1) + ".");
                    //}
                }

            }
        });



        tempdata.setText(dtemp.getTest_name());
        flow_diagnostic.addView(itemView);
        ll__uni_prompt.setVisibility(View.GONE);
        ll_symptom_dialog.setVisibility(View.GONE);
        itemView.setClickable(true);
    }

    private void AddSymptom(String data) {

        final ViewGroup parent = (ViewGroup) rl_home_main;
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dda_list_layout, parent, false);
        TextView tempdata = itemView.findViewById(R.id.tv_chip_data);
        LinearLayout ll_chip_delete = itemView.findViewById(R.id.ll_chip_delete);
        itemView.setId(Final_Symptoms.size());
        ll_chip_delete.setClickable(true);
        //final int temp_pos = Final_Symptoms.size();

        final Issues.Data dtemp = new Issues.Data(data);
        final TextView number = itemView.findViewById(R.id.tv_chip_number);
        number.setText((Final_Symptoms.size()+1) + ".");
        Final_Symptoms.add(dtemp);

        ll_chip_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp_pos = itemView.getId();
                Final_Symptoms.remove(dtemp);
                flow_symptoms.removeView(itemView);
                //DeleteSymptom(temp_pos);

                for (int i = 0; i < flow_symptoms.getChildCount(); i++) {
                    View view1 = flow_symptoms.getChildAt(i);
                    //if (view1.getId()>temp_pos) {
                        final TextView number = view1.findViewById(R.id.tv_chip_number);
                        number.setText((i + 1) + ".");
                    //}
                }

            }
        });



        tempdata.setText(dtemp.getIssues());
        flow_symptoms.addView(itemView);
        ll__uni_prompt.setVisibility(View.GONE);
        ll_symptom_dialog.setVisibility(View.GONE);
        itemView.setClickable(true);
    }

    private void AddDiagnosis(String data) {

        final ViewGroup parent = (ViewGroup) rl_home_main;
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dda_list_layout, parent, false);
        TextView tempdata = itemView.findViewById(R.id.tv_chip_data);
        LinearLayout ll_chip_delete = itemView.findViewById(R.id.ll_chip_delete);
        itemView.setId(Final_Diagnosis.size());
        ll_chip_delete.setClickable(true);
        //final int temp_pos = Final_Symptoms.size();

        final Diagnosis.Data dtemp = new Diagnosis.Data(data);
        final TextView number = itemView.findViewById(R.id.tv_chip_number);
        number.setText((Final_Diagnosis.size()+1) + ".");
        Final_Diagnosis.add(dtemp);

        ll_chip_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp_pos = itemView.getId();
                Final_Diagnosis.remove(dtemp);
                flow_diagnosis.removeView(itemView);
                //DeleteSymptom(temp_pos);

                for (int i = 0; i < flow_diagnosis.getChildCount(); i++) {
                    View view1 = flow_diagnosis.getChildAt(i);
                    //if (view1.getId()>temp_pos) {
                    final TextView number = view1.findViewById(R.id.tv_chip_number);
                    number.setText((i + 1) + ".");
                    //}
                }

            }
        });



        tempdata.setText(dtemp.getDiagnosis());
        flow_diagnosis.addView(itemView);
        ll__uni_prompt.setVisibility(View.GONE);
        ll_symptom_dialog.setVisibility(View.GONE);
        itemView.setClickable(true);
    }







    private void ShowSymptomDialog(String symptom_options, List<Issues.Data> issuesmatched) {

        tv_dialog_count.setText(issuesmatched.size()+" Symptoms Matched In The Database");

        tv_dialog_heading.setText(symptom_options);

        MultipleSymptomAdapter adapter = new MultipleSymptomAdapter(this,issuesmatched);

        rv_multiple_symptoms.setAdapter(adapter);
        rv_multiple_symptoms.setHasFixedSize(true);

        ll_symptom_dialog.setVisibility(View.VISIBLE);
        Speak("Multiple matches found in database.Please select one",VR_MULTIPLE_SYMPTOMS,3000);
    }


    private void ShowDiagnosisDialog(String diagnosis_option, List<Diagnosis.Data> diagnosismatched) {

        tv_dialog_count.setText(diagnosismatched.size()+" Diagnosis Matched In The Database");

        tv_dialog_heading.setText(diagnosis_option);

        MultipleDiagnosisAdapter adapter = new MultipleDiagnosisAdapter(this,diagnosismatched);

        rv_multiple_symptoms.setAdapter(adapter);
        rv_multiple_symptoms.setHasFixedSize(true);

        ll_symptom_dialog.setVisibility(View.VISIBLE);
        Speak("Multiple matches found in database.Please select one",VR_MULTIPLE_DIAGNOSIS,3000);
    }

    private void ShowDiagnosticTestDialog(String diagnosis_option, List<DiagnosticTests.Data> diagnosismatched) {

        tv_dialog_count.setText(diagnosismatched.size()+" Diagnostic test Matched In The Database");

        tv_dialog_heading.setText(diagnosis_option);

        MultipleDiagnosticTestAdapter adapter = new MultipleDiagnosticTestAdapter(this,diagnostictestsmatched);

        rv_multiple_symptoms.setAdapter(adapter);
        rv_multiple_symptoms.setHasFixedSize(true);

        ll_symptom_dialog.setVisibility(View.VISIBLE);
        Speak("Multiple matches found in database.Please select one",VR_MULTIPLE_DIAGNOSTIC_TEST,3000);
    }






    private void FetchSymptoms() {
        HashMap<String,String> params = new HashMap<>();
        params.put("action","getIssues");
        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);
        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                try{
                    Issues obj = new Gson().fromJson(responseStr, Issues.class);
                    if(obj.isStatus()){

                        Issuelist.addAll(obj.getData());

                        //  tv_main.setText(obj.getTotal_records()+"|"+Issuelist.size());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : "+e.getMessage(), Toast.LENGTH_LONG).show();

                }

                return false;
            }
        });

    }

    private void FetchDiagnosis() {

        HashMap<String,String> params = new HashMap<>();
        params.put("action","getDiagnosis");
        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);
        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                try{
                    Diagnosis obj = new Gson().fromJson(responseStr, Diagnosis.class);
                    if(obj.isStatus()){

                        DiagnosisList.addAll(obj.getData());

                        // tv_main.setText(obj.getTotal_records()+"|"+DiagnosisList.size());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : "+e.getMessage(), Toast.LENGTH_LONG).show();

                }

                return false;
            }
        });
    }
    private void FetchPatients() {

        HashMap<String, String> params = new HashMap<>();

        params.put("action", "getChildren");
        params.put("drId", "" + doctor_id);

        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);


        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {


                try {
                    //tv_main.setText(responseStr);
                    Patient obj = new Gson().fromJson(responseStr, Patient.class);

                    if (obj.getStatus()) {
                        PatientsList = new ArrayList<>();
                        PatientsList.addAll(obj.getData());

                        tv_home_total_patients.setText("Today's Patients - " + PatientsList.size());
                        tv_patients_checked_in.setText("" + PatientsList.size());
                        SetMainDrawerRecyclerView();
                        //tv_main.setText(obj.getTotal_records()+"|"+vitalList.size());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });


    }

    private void FetchDiagnosticList() {
        HashMap<String,String> params = new HashMap<>();
        params.put("action","getDiagnosticTests");
        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);
        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                try{
                    DiagnosticTests obj = new Gson().fromJson(responseStr, DiagnosticTests.class);
                    if(obj.isStatus()){

                        DiagnosticTestList.addAll(obj.getData());

                        //Home.setText(obj.getTotal_records()+"|"+DiagnosticTestList.size());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : "+e.getMessage(), Toast.LENGTH_LONG).show();

                }

                return false;
            }
        });


    }

    private void FetchVitals() {

        HashMap<String, String> params = new HashMap<>();

        params.put("action", "getChildVitals");
        params.put("patient_id", "" + SelectedPatient.getPatientId());

        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);


        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {


                try {
                    //tv_main.setText(responseStr);
                    Vitals obj = new Gson().fromJson(responseStr, Vitals.class);

                    if (obj.getStatus()) {
                        List<Vitals.Data> vitalList = new ArrayList<>();
                        vitalList.addAll(obj.getData());

                        SelectedPatient.setVitals(vitalList.get(0));

                        tv_height.setText(SelectedPatient.getVitals().getHeight() + " cm");
                        tv_weight.setText(SelectedPatient.getVitals().getWeight() + " k.g.");
                        tv_bmi.setText("" + SelectedPatient.getVitals().getBMI());

                        //tv_main.setText(obj.getTotal_records()+"|"+vitalList.size());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });

    }


    private List<String> FilterArray(String[] symptomsarr) {
        List<String> temp = new ArrayList<>();

        for (int i = 0; i < symptomsarr.length; i++){

            if(!Functions.CheckData(symptomsarr[i], StaticData.Filters)){

                temp.add(symptomsarr[i]);

            }

        }

        return temp;
    }





    private void OnExpandClicked(final int temp) {

        if (fixedheightcounter == 0) {
            fixedheight = ll_expand_main[temp].getHeight();
            fixedheightcounter++;
        }

        //Toast.makeText(this, ""+temp, Toast.LENGTH_SHORT).show();

        //if not expanded then expand
        if (!isExpanded[temp]) {
            ExpandedDetail = temp;
            transitions.expand(ll_data[temp]);
            ll_expand_main[temp].setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout
                            .LayoutParams(ll_expand_main[temp].getWidth(), ll_expand_main[temp].getHeight() / 10);
                    ll_expand_main[temp].setLayoutParams(layoutParams);

                }
            }, 100);


            //Collapse other expanded views

            for (int k = 0; k < details; k++) {

                if (k != temp && isExpanded[k] == true) {
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

        PatientsAdapter adapter = new PatientsAdapter(this, PatientsList);
        rv_main_drawer.setAdapter(adapter);
        rv_main_drawer.setHasFixedSize(true);

    }

    @Override
    public void OnPatientClick(int position,int identifier) {
        if(identifier== StaticData.Adapter_identifier.patients){

            SelectedPatientPosition = position;
            SelectedPatient = PatientsList.get(SelectedPatientPosition);
            ll_patient_drawer.setVisibility(View.GONE);
            ll_main_patient_rx.setVisibility(View.VISIBLE);
            //Toast.makeText(this, SelectedPatient.getFname()+" Selected", Toast.LENGTH_SHORT).show();

            SetPatientDetails();
        }
        else if(identifier== StaticData.Adapter_identifier.symptoms){

            AddSymptom(issuesmatched.get(position).getIssues());

        }
        else if(identifier == StaticData.Adapter_identifier.diagnosis)
        {
            AddDiagnosis(diagnosismatched.get(position).getDiagnosis());
        }
        else if(identifier == StaticData.Adapter_identifier.diagnostic)
        {
            AddDiagnosticTest(diagnostictestsmatched.get(position).getTest_name());
        }



    }

    private void SetPatientDetails() {

        tv_patient_name.setText(SelectedPatient.getFname());
        tv_patient_id_2.setText("ID : " + SelectedPatient.getPatientId());
        tv_gender_age.setText(SelectedPatient.getGender() + " | " + SelectedPatient.getAge());

        FetchVitals();

    }
    private void Speak(String text,int VR_CODE,int duration) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*textToSpeech.setSpeechRate(0.7f);
            textToSpeech.setPitch(0.5f);*/
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
            isTTSSpeaking(VR_CODE,duration);
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    public void isTTSSpeaking(final int VR_CODE,final int duration){

        final Handler h =new Handler();

        Runnable r = new Runnable() {

            public void run() {

                if (!textToSpeech.isSpeaking()) {
                    onTTSFinished(VR_CODE);
                    return;

                }

                h.postDelayed(this, 500);
            }
        };

        h.postDelayed(r, duration);
    }

    private void onTTSFinished(int VR_CODE) {

        if(VR_CODE == VR_NOMATCH_PROMPT_SYMPTOM || VR_CODE==VR_MULTIPLE_SYMPTOMS || VR_CODE==VR_MULTIPLE_DIAGNOSIS ||
        VR_CODE==VR_NOMATCH_PROMPT_DIAGNOSIS || VR_CODE==VR_MULTIPLE_DIAGNOSTIC_TEST || VR_CODE==VR_NOMATCH_PROMPT_DIAGNOSTIC_TEST)

        {
            startVoiceRecognitionActivity(VR_CODE);
        }

        //startVoiceRecognitionActivity(VR_CODE);

    }


}
