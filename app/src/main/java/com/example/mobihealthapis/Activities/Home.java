package com.example.mobihealthapis.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mobihealthapis.Adapters.AdviceAdapter;
import com.example.mobihealthapis.Adapters.MedicineAdapter;
import com.example.mobihealthapis.Adapters.MultipleAdviceAdapter;
import com.example.mobihealthapis.Adapters.MultipleDiagnosisAdapter;
import com.example.mobihealthapis.Adapters.MultipleDiagnosticTestAdapter;
import com.example.mobihealthapis.Adapters.MultipleMedicineAdapter;
import com.example.mobihealthapis.Adapters.MultipleSymptomAdapter;
import com.example.mobihealthapis.Adapters.PatientsAdapter;
import com.example.mobihealthapis.GeneralFunctions.Functions;
import com.example.mobihealthapis.GeneralFunctions.OnSwipeTouchListener;
import com.example.mobihealthapis.GeneralFunctions.PermissionsMethods;
import com.example.mobihealthapis.GeneralFunctions.StaticData;
import com.example.mobihealthapis.GeneralFunctions.transitions;
import com.example.mobihealthapis.Interface.PatientInterface;
import com.example.mobihealthapis.Models.Advice;
import com.example.mobihealthapis.Models.DataMap;
import com.example.mobihealthapis.Models.Diagnosis;
import com.example.mobihealthapis.Models.DiagnosticTests;
import com.example.mobihealthapis.Models.Issues;
import com.example.mobihealthapis.Models.Med;
import com.example.mobihealthapis.Models.Medicine;
import com.example.mobihealthapis.Models.Patient;
import com.example.mobihealthapis.Models.PatientFinal;
import com.example.mobihealthapis.Models.PatientJson;
import com.example.mobihealthapis.Models.PatientPrevRxJson;
import com.example.mobihealthapis.Models.Vitals;
import com.example.mobihealthapis.R;
import com.example.mobihealthapis.database_call.NetworkCall;
import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;
import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.mobihealthapis.GeneralFunctions.Functions.FilterDiagnosis;
import static com.example.mobihealthapis.GeneralFunctions.Functions.FilterDiagnosticTest;
import static com.example.mobihealthapis.GeneralFunctions.Functions.FilterIssues;
import static com.example.mobihealthapis.GeneralFunctions.Functions.IsInList;
import static com.example.mobihealthapis.GeneralFunctions.Functions.getDailyTimings;
import static com.example.mobihealthapis.GeneralFunctions.Functions.getDuration;
import static com.example.mobihealthapis.GeneralFunctions.Functions.getMedTimings;
import static com.example.mobihealthapis.GeneralFunctions.Functions.getVitals2;
import static com.example.mobihealthapis.GeneralFunctions.Functions.getfrequency;
import static com.example.mobihealthapis.GeneralFunctions.Functions.isNumeric;
import static com.example.mobihealthapis.GeneralFunctions.Functions.updatefrequency;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VOICE_RECOGNITION_REQUEST_CODE;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_MEDICINE_NULL_DATA;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_MULTIPLE_ADVICES;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_MULTIPLE_DIAGNOSIS;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_MULTIPLE_DIAGNOSTIC_TEST;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_MULTIPLE_MEDICINES;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_MULTIPLE_SYMPTOMS;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_NOMATCH_PROMPT_DIAGNOSIS;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_NOMATCH_PROMPT_DIAGNOSTIC_TEST;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_NOMATCH_PROMPT_MEDICINES;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_NOMATCH_PROMPT_SYMPTOM;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_UPDATE_ADVICE;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.VR_UPDATE_SYMPTOMS;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.advice;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.diagnosis;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.diagnostic;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.followup;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.medicine;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.symptoms;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patient_details.vitals;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patienttype.allpatients;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patienttype.checkedin;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patienttype.completed;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.patienttype.draft;
import static com.example.mobihealthapis.database_call.utils_string.API_URL.SEARCH_MED;
import static com.example.mobihealthapis.database_call.utils_string.API_URL.VR_APIS;

public class Home extends AppCompatActivity implements PatientInterface, RecognitionListener {

    //Splash Screen
    ConstraintLayout ll_main_splash;

    //Home
    private final int doctor_id = 40089;

    SwipeRefreshLayout swipe_refresh_home;

    int ExpandedDetail = -1;

    TextView tv_home_total_patients, tv_patient_draft, tv_patients_checked_in, tv_drawer_title;
    List<PatientFinal> Final_PatientChecked;
    ImageView img_patient;
    TextToSpeech textToSpeech;
    RelativeLayout rl_home_main;
    RecyclerView rv_main_drawer, rv_advice_list_main, rv_followup_list_main, rv_medicines_list_main;
    LinearLayout ll_activate_voice, ll_patient_drawer, ll_draft_patients, ll_checkedin_patients,
            ll_drawer_showhide, ll_drawer_selector, ll_container_draft_checked;
    RipplePulseLayout mRipplePulseLayout;
    MedicineAdapter medicineAdapter;
    String flag_for_patients = "";
    String flag_for_previous_patient = "";
    int previous_patient_position;


    String[] drawer_selected = {"Checked In", "Drafts"};

    int fixedheight, fixedheightcounter = 0;

    int SelectedPatientPosition;
    PatientFinal SelectedPatient;

    PatientFinal Final_Patient;
    List<PatientFinal> Final_PatientDraft;
    Vitals.Data Final_Vitals;
    List<Issues.Data> Final_Symptoms;
    List<Diagnosis.Data> Final_Diagnosis;
    List<DiagnosticTests.Data> Final_DiagnosticTests;
    List<Advice> Final_Advice;
    List<Medicine> Final_Medicines;
    String temp_symptom = "";
    List<Issues.Data> issuesmatched;
    List<Diagnosis.Data> diagnosismatched;
    List<DiagnosticTests.Data> diagnostictestsmatched;
    List<Med.Data> medicinematched;

    PatientsAdapter patientsadapter;

    //UPDATE POSITIONS

    int update_advice_position;


    //Patient Details

    LinearLayout ll_main_patient_rx;
    Boolean[] isExpanded;
    LinearLayout[] ll_expand_main, ll_expand, ll_after_expand, ll_data;
    ImageView[] img_expand;
    TextView tv_patient_id_2, tv_patient_name, tv_gender_age, tv_bmi, tv_weight, tv_height,
            tv_temperature, tv_followup_datetime, tv_hc;
    LinearLayout ll_previous_rx;
    List<Issues.Data> Issuelist;
    List<Diagnosis.Data> DiagnosisList;
    List<Med.Data> MedicineList;
    List<Advice> AdviceList;
    Medicine multiplemedobj;
    List<DiagnosticTests.Data> DiagnosticTestList;
    FlowLayout flow_symptoms, flow_diagnosis, flow_diagnostic;

    private int details = 7;

    List<DataMap.Data> vitals_map;
    String[] vitals_list;

    //Symptom Dialog
    LinearLayout ll_symptom_dialog;
    TextView tv_dialog_heading, tv_symptom_close_dialog, tv_dialog_count;
    RecyclerView rv_multiple_symptoms;

    //no match promopt
    LinearLayout ll__uni_prompt;
    TextView tv_promt_question, tv_promt_data, tv_promt_no, tv_promt_yes;


    //followup date
    String default_date = "21-01-2020";
    int[] default_time = {5, 0, 1};

    String f_date = "";
    int[] f_time = {-1, -1, -1};

    int symptom_update_flag = 0, diagnosis_update_flag = 0, test_update_flag = 0;

    SharedPreferences.Editor editor;

    Dialog progress_dialog;

    int created = -1;


    //Audio Recording
    MediaRecorder myAudioRecorder;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {WRITE_EXTERNAL_STORAGE, RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    String fileName = null;
    Boolean b_isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        created = -0;

        PermissionsMethods.CheckPermissions(this,Home.this);

        InitializeObjects();

    }

    //Initialization Methods
    private void InitializeObjects() {

        rl_home_main = findViewById(R.id.rl_home_main);

        rv_main_drawer = findViewById(R.id.rv_main_drawer);
        ll_activate_voice = findViewById(R.id.ll_activate_voice);
        //mRipplePulseLayout = findViewById(R.id.layout_ripplepulse);

        DiagnosticTestList = new ArrayList<>();
        DiagnosisList = new ArrayList<>();
        Issuelist = new ArrayList<>();
        MedicineList = new ArrayList<>();

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

    private void HomeInit() {

        ll_main_splash = findViewById(R.id.ll_main_splash);

        tv_home_total_patients = findViewById(R.id.tv_home_total_patients);
        tv_patients_checked_in = findViewById(R.id.tv_patients_checked_in);
        tv_patient_draft = findViewById(R.id.tv_patient_draft);

        ll_patient_drawer = findViewById(R.id.ll_patient_drawer);
        ll_checkedin_patients = findViewById(R.id.ll_checkedin_patients);
        ll_draft_patients = findViewById(R.id.ll_draft_patients);
        ll_drawer_showhide = findViewById(R.id.ll_drawer_showhide);

        Final_Symptoms = new ArrayList<>();
        Final_Diagnosis = new ArrayList<>();
        Final_DiagnosticTests = new ArrayList<>();
        Final_Advice = new ArrayList<>();
        Final_Medicines = new ArrayList<>();
        Final_Vitals = new Vitals.Data();
        flow_diagnosis = findViewById(R.id.flow_diagnosis);
        flow_diagnostic = findViewById(R.id.flow_diagnostic);
        flow_symptoms = findViewById(R.id.flow_symptoms);
        setMedicineRecyclerView();

        flow_symptoms = findViewById(R.id.flow_symptoms);


        progress_dialog = new Dialog(this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.progress_dialog_layout);
        progress_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress_dialog.setCancelable(false);
        //progress_dialog.show();

        swipe_refresh_home = findViewById(R.id.swipe_refresh_home);
        tv_drawer_title = findViewById(R.id.tv_drawer_title);
        ll_drawer_selector = findViewById(R.id.ll_drawer_selector);
        ll_container_draft_checked = findViewById(R.id.ll_container_draft_checked);



        vitals_map = new ArrayList<>();
        //new fetchpatients().execute();
        FetchPatients();
        fetchdatamap();
        //LoadData();
        ExpandedDetail = vitals;



        HomeClickMethods();
    }
/*
    private void LoadData() {
        final Handler mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == 0)
                    new fetchpatients().execute();
                return false;
            }
        });
        //FetchPatients();

        Thread t = new Thread(){
            @Override
            public void run(){
                mHandler.sendEmptyMessage(0);
            }
        };
        t.run();
    }*/

    private void HomeClickMethods() {

        ll_container_draft_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_patient_drawer.getVisibility() == View.VISIBLE) {
                    transitions.SlideUpDown(rl_home_main, ll_patient_drawer, true, 4);
                }
            }
        });

        ll_drawer_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Home.this, v);
                //final List<String> donationtype = fixed_data.GetDonationTypeList();
                popupMenu.getMenu().add(Menu.NONE, 0, 0, "Checked In");
                popupMenu.getMenu().add(Menu.NONE, 1, 1, "Drafts");
                popupMenu.getMenu().add(Menu.NONE, 2, 2, "Completed");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        //Checked In Clicked
                        if (menuItem.getItemId() == 0) {
                            flag_for_patients = checkedin;
                            SetMainDrawerRecyclerView(Final_PatientChecked, false);
                        }

                        //Drafts Clicked
                        else if (menuItem.getItemId() == 1) {
                            flag_for_patients = draft;
                            SetMainDrawerRecyclerView(Final_PatientDraft, false);
                        }

                        //Completed Clicked
                        else {
                            Toast.makeText(Home.this, "Completed Clicked", Toast.LENGTH_SHORT).show();
                        }

                        if (flag_for_patients == draft) {
                            tv_drawer_title.setText(drawer_selected[1]);
                        } else {
                            tv_drawer_title.setText(drawer_selected[0]);
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        swipe_refresh_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Toast.makeText(Home.this, "refresh", Toast.LENGTH_SHORT).show();
                FetchPatients();
//                new fetchpatients().execute();
                //LoadData();
            }
        });

        ll_draft_patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_for_patients = draft;
                ShowDraftPatients(true);
            }
        });

        ll_checkedin_patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_for_patients = checkedin;
                SetMainDrawerRecyclerView(Final_PatientChecked, true);

            }
        });

        ll_drawer_showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(flag_for_patients.equals(checkedin))
//                {
//                    SetMainDrawerRecyclerView(Final_PatientChecked);
//                }
//                else{
//                    ShowDraftPatients();
//                }
                if ((ll_patient_drawer.getVisibility() == View.GONE ||
                        ll_patient_drawer.getVisibility() == View.INVISIBLE))
                    ShowHidePatientList(1);
                else
                    ShowHidePatientList(0);
            }
        });

    }

    private void ShowDraftPatients(Boolean show) {

//        SharedPreferences prefs = getSharedPreferences(PREF_PATIENT, Context.MODE_PRIVATE);
//        Map<String, ?> entries = prefs.getAll();
//        Set<String> keys = entries.keySet();

        //  prefs.edit().remove("1").commit();
        //  SetMainDrawerRecyclerView();





      /*  for (String key : keys) {
            String PatientJson = prefs.getString(key, null);
            Log.e("patientjson", PatientJson);
            if (PatientJson != null) {

                Gson gson = new Gson();
                PatientFinal temp = gson.fromJson(PatientJson, PatientFinal.class);
                PatientDraft.add(temp.getPatientDetails());

            } else {
                Toast.makeText(this, "Json null", Toast.LENGTH_SHORT).show();
            }
        }*/
       /* PatientDraft = new ArrayList<>();
        HashMap<String,String> params = new HashMap<>();
        params.put("action","getAllPrescriptions");
        params.put("patient_type","draft");

        NetworkCall ncall = new NetworkCall();
        ncall.setServerUrlWebserviceApi(VR_APIS);

        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                try {
                    PatientJson obj = new Gson().fromJson(responseStr, PatientJson.class);
                    if (obj.getStatus()) {

                     // List<PatientFinal>
                      for(int i =0;i< obj.getData().size();i++)
                      {
                          Gson gson = new Gson();
                          PatientFinal temp = gson.fromJson(obj.getData().get(i).getRx_json(), PatientFinal.class);
                          PatientDraft.add(temp.getPatientDetails());
                      }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });*/

        SetMainDrawerRecyclerView(Final_PatientDraft, show);

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

        //followup
        tv_followup_datetime = findViewById(R.id.tv_followup_datetime);

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

        //SetAdvice();

        // SetFollowUp();

        //  SetMedicines();


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

        ll_previous_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FetchPreviousRX();

            }
        });

        //OnExpandClicked(vitals);

    }


    //Voice Recogntion Methods
    public void startVoiceRecognitionActivity(int VOICE_REQUEST_CODE) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_REQUEST_CODE);

        /*Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_WEB_SEARCH_ONLY, "false");
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, "2000");
        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(this);
        recognizer.startListening(intent);*/


    }//VOICE_RECOGNITION_REQUEST_CODE

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String[] finalarr = GetRawDataFromSpeech(matches);

            //Show Patients & Stop Rx & CRUD Data
            if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {


                if (finalarr.length > 0) {

                    //Show Checkedin/Draft patients
                    if (finalarr[0].equals("show")) {
                        if (finalarr.length > 1) {
                            int temp = Functions.Soundex.StringCompareFine("checked", finalarr[1], 3);
                            int temp1 = Functions.Soundex.StringCompareCoarse("draft", finalarr[1], 3);
                            if (temp < 5 && temp >= 0) {
                                flag_for_patients = checkedin;
                                SetMainDrawerRecyclerView(Final_PatientChecked, true);

                            } else if (temp1 < 7 && temp1 >= 0) {

                                flag_for_patients = draft;
                                ShowDraftPatients(true);
                            }

                        }

                    }

                    //Stop Rx & CRUD Data
                    else if (ll_main_patient_rx.getVisibility() == View.VISIBLE) {

                        //Stop Rx and Upload as Draft
                        if (finalarr[0].equals("stop")) {
                            if (finalarr.length > 1) {
                                if (finalarr[1].equals("prescription") || finalarr[1].equals("description")) {

                                    GeneratePrescription(draft);

                                }
                            }
                        }

                        //Set/Add/Update/Delete Data
                        else if (finalarr[0].equals("write") || finalarr[0].equals("right")
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
                                        SetAdvice();
                                        break;
                                    }

                                    default:
                                        Toast.makeText(this, "Command not recognized", Toast.LENGTH_SHORT).show();
                                        break;

                                }
                            }

                        }
                        //setting vitals when expanded
                        else if (Functions.CheckData2(finalarr, vitals_list) && ExpandedDetail == vitals) {
                            SetVitals(finalarr);
                        }
                        //Update
                        else if (finalarr[0].equals("update")) {
                            if (finalarr.length > 2) {
                                if (ExpandedDetail == medicine) {

                                    if (finalarr[1].equals("number")) {
                                        if (isNumeric(finalarr[2])) {
                                            int pos = Integer.parseInt(finalarr[2]);
                                            if (IsInList(pos, Final_Medicines.size())) {
                                                pos--;
                                                String[] arr = new String[finalarr.length - 3];
                                                int j = 0;
                                                for (int i = 3; i < finalarr.length; i++) {
                                                    arr[j] = finalarr[i];
                                                    j++;
                                                }

                                                updateMedicine(arr, pos);
                                            } else {
                                                Toast.makeText(this, "Position Out Of Bounds", Toast.LENGTH_SHORT).show();
                                            }

                                        } else if (finalarr[2].equals("one")) {

                                            int pos = 1;
                                            if (IsInList(pos, Final_Medicines.size())) {
                                                pos--;
                                                String[] arr = new String[finalarr.length - 3];
                                                int j = 0;
                                                for (int i = 3; i < finalarr.length; i++) {
                                                    arr[j] = finalarr[i];
                                                    j++;
                                                }

                                                updateMedicine(arr, pos);
                                            } else {
                                                Toast.makeText(this, "Position Out Of Bounds", Toast.LENGTH_SHORT).show();
                                            }

                                        } else if (finalarr[2].equals("two") || finalarr[2].equals("to")
                                                || finalarr[2].equals("do")) {
                                            int pos = 2;
                                            if (IsInList(pos, Final_Medicines.size())) {
                                                pos--;
                                                String[] arr = new String[finalarr.length - 3];
                                                int j = 0;
                                                for (int i = 3; i < finalarr.length; i++) {
                                                    arr[j] = finalarr[i];
                                                    j++;
                                                }

                                                updateMedicine(arr, pos);
                                            } else {
                                                Toast.makeText(this, "Position Out Of Bounds", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                } else if (ExpandedDetail == advice) {

                                    if (finalarr[1].equals("number")) {
                                        if (isNumeric(finalarr[2])) {
                                            update_advice_position = Integer.parseInt(finalarr[2]);
                                            if (IsInList(update_advice_position, Final_Advice.size())) {
                                                update_advice_position--;
                                                ShowAdviceDialog("advice", AdviceList, VR_UPDATE_ADVICE);

                                            }

                                        }
                                    }

                                } else if (ExpandedDetail == symptoms) {
                                    if (finalarr[1].equals("number")) {
                                        if (isNumeric(finalarr[2])) {
                                            update_advice_position = Integer.parseInt(finalarr[2]);
                                            if (IsInList(update_advice_position, Final_Symptoms.size())) {
                                                update_advice_position--;
                                                String[] newsymptom = new String[finalarr.length - 3];
                                                if (finalarr.length > 3) {
                                                    int x = 0;
                                                    for (int i = 3; i < finalarr.length; i++) {
                                                        newsymptom[x] = finalarr[i];
                                                        x++;
                                                    }

                                                    updateSymptoms(update_advice_position, newsymptom);


                                                }

                                            }

                                        }
                                    }

                                } else if (ExpandedDetail == diagnosis) {
                                    if (finalarr[1].equals("number")) {
                                        if (isNumeric(finalarr[2])) {
                                            update_advice_position = Integer.parseInt(finalarr[2]);
                                            if (IsInList(update_advice_position, Final_Diagnosis.size())) {
                                                update_advice_position--;
                                                String[] newsymptom = new String[finalarr.length - 3];
                                                if (finalarr.length > 3) {
                                                    int x = 0;
                                                    for (int i = 3; i < finalarr.length; i++) {
                                                        newsymptom[x] = finalarr[i];
                                                        x++;
                                                    }

                                                    updateDiagnosis(update_advice_position, newsymptom);


                                                }

                                            }

                                        }
                                    }

                                } else if (ExpandedDetail == diagnostic) {
                                    if (finalarr[1].equals("number")) {
                                        if (isNumeric(finalarr[2])) {
                                            update_advice_position = Integer.parseInt(finalarr[2]);
                                            if (IsInList(update_advice_position, Final_DiagnosticTests.size())) {
                                                update_advice_position--;
                                                String[] newsymptom = new String[finalarr.length - 3];
                                                if (finalarr.length > 3) {
                                                    int x = 0;
                                                    for (int i = 3; i < finalarr.length; i++) {
                                                        newsymptom[x] = finalarr[i];
                                                        x++;
                                                    }

                                                    updateDiagnosticTest(update_advice_position, newsymptom);


                                                }

                                            }

                                        }
                                    }

                                }


                            }
                        }
                        // delete
                        else if (finalarr[0].equals("delete")) {

                            if (finalarr.length > 2) {
                                //if symptoms expanded
                                if (ExpandedDetail == symptoms) {
                                    if (finalarr[1].equals("number") && finalarr.length > 2) {
                                        if (isNumeric(finalarr[2])) {
                                            int temp = Integer.parseInt(finalarr[2]);
                                            if (IsInList(temp, Final_Symptoms.size())) {
                                                temp--;
                                                DeleteSymptom(temp);
                                            }
                                        }
                                    }
                                }
                                //if diagnosis expanded
                                else if (ExpandedDetail == diagnosis) {
                                    if (finalarr[1].equals("number") && finalarr.length > 2) {
                                        if (isNumeric(finalarr[2])) {
                                            int temp = Integer.parseInt(finalarr[2]);
                                            if (IsInList(temp, Final_Diagnosis.size())) {
                                                temp--;
                                                DeleteDiagnosis(temp);
                                            }
                                        }
                                    }
                                }
                                //if diagnostic test expanded
                                else if (ExpandedDetail == diagnostic) {
                                    if (finalarr[1].equals("number") && finalarr.length > 2) {
                                        if (isNumeric(finalarr[2])) {
                                            int temp = Integer.parseInt(finalarr[2]);
                                            if (IsInList(temp, Final_DiagnosticTests.size())) {
                                                temp--;
                                                DeleteDiagnosticTest(temp);
                                            }
                                        }
                                    }
                                } else if (ExpandedDetail == medicine) {
                                    if (finalarr[1].equals("number") && finalarr.length > 2) {
                                        if (isNumeric(finalarr[2])) {
                                            int temp = Integer.parseInt(finalarr[2]);
                                            if (IsInList(temp, Final_Medicines.size())) {
                                                temp--;
                                                DeleteMedicine(temp);
                                            }
                                        }
                                    }
                                } else if (ExpandedDetail == advice) {
                                    if (finalarr[1].equals("number") && finalarr.length > 2) {
                                        if (isNumeric(finalarr[2])) {
                                            int temp = Integer.parseInt(finalarr[2]);
                                            if (IsInList(temp, Final_Advice.size())) {
                                                temp--;
                                                DeleteAdvice(temp);
                                            }
                                        }
                                    }
                                }


                                //global delete
                                if (finalarr[1].equals("symptom")) {
                                    if (finalarr[2].equals("number") && finalarr.length > 3) {
                                        if (isNumeric(finalarr[3])) {
                                            int temp = Integer.parseInt(finalarr[3]);
                                            if (IsInList(temp, Final_Symptoms.size())) {
                                                temp--;
                                                DeleteSymptom(temp);
                                            }
                                        }
                                    }
                                } else if (finalarr[1].equals("diagnosis")) {
                                    if (finalarr[2].equals("number") && finalarr.length > 3) {
                                        if (isNumeric(finalarr[3])) {
                                            int temp = Integer.parseInt(finalarr[3]);
                                            if (IsInList(temp, Final_Diagnosis.size())) {
                                                temp--;
                                                DeleteDiagnosis(temp);
                                            }
                                        }
                                    }
                                } else if (finalarr[1].equals("test")) {
                                    if (finalarr[2].equals("number") && finalarr.length > 3) {
                                        if (isNumeric(finalarr[3])) {
                                            int temp = Integer.parseInt(finalarr[3]);
                                            if (IsInList(temp, Final_DiagnosticTests.size())) {
                                                temp--;
                                                DeleteDiagnosticTest(temp);
                                            }
                                        }
                                    }
                                } else if (finalarr[1].equals("medicine")) {
                                    if (finalarr[2].equals("number") && finalarr.length > 3) {
                                        if (isNumeric(finalarr[3])) {
                                            int temp = Integer.parseInt(finalarr[3]);
                                            if (IsInList(temp, Final_Medicines.size())) {
                                                temp--;
                                                DeleteMedicine(temp);
                                            }
                                        }
                                    }
                                } else if (finalarr[1].equals("advice") || finalarr[1].equals("advise")) {
                                    if (finalarr[2].equals("number") && finalarr.length > 3) {
                                        if (isNumeric(finalarr[3])) {
                                            int temp = Integer.parseInt(finalarr[3]);
                                            if (IsInList(temp, Final_Advice.size())) {
                                                temp--;
                                                DeleteAdvice(temp);
                                            }
                                        }
                                    }
                                }


                            }

                        }
                        //Generate Prescription
                        else if (finalarr[0].equals("generate")) {

                            if (finalarr[1].equals("prescription")) {
                                GeneratePrescription(completed);
                            }

                        }
                        //add  symptoms
                        else if (ExpandedDetail == symptoms) {

                            SetSymptoms(finalarr);

                        }
                        //add diagnosis
                        else if (ExpandedDetail == diagnosis) {
                            SetDiagnosis(finalarr);
                        }
                        //add diagnostic
                        else if (ExpandedDetail == diagnostic) {
                            SetDiagnosticTests(finalarr);
                        }
                        //add medicine
                        else if (ExpandedDetail == medicine) {
                            SetMedicines(finalarr);
                        }
                        //add followup
                        else if (ExpandedDetail == followup) {
                            SetFollowUp(finalarr);
                        }
                        //add advice
                        else if (ExpandedDetail == advice) {
                            SetAdvice();
                        }

                    }

                }

            }

            //Multiple Symptoms
            else if (requestCode == VR_MULTIPLE_SYMPTOMS && resultCode == RESULT_OK) {

                ll_symptom_dialog.setVisibility(View.GONE);
                if (finalarr.length > 2) {
                    if (finalarr[0].equals("select")) {
                        if (finalarr[1].equals("number")) {
                            if (isNumeric(finalarr[2])) {
                                int pos = Integer.parseInt(finalarr[2]);
                                if (IsInList(pos, issuesmatched.size())) {
                                    pos--;
                                    if (symptom_update_flag == 1) {
                                        Final_Symptoms.get(update_advice_position).setIssues(issuesmatched.get(pos).getIssues());
                                        View itemview = flow_symptoms.getChildAt(update_advice_position);
                                        TextView temp = itemview.findViewById(R.id.tv_chip_data);
                                        temp.setText(issuesmatched.get(pos).getIssues());
                                        ll__uni_prompt.setVisibility(View.GONE);
                                        ll_symptom_dialog.setVisibility(View.GONE);
                                        symptom_update_flag = 0;
                                    } else
                                        AddSymptom(issuesmatched.get(pos).getIssues());
                                }

                            }
                        }
                    }
                }

            }
            //Multiple diagnosis
            else if (requestCode == VR_MULTIPLE_DIAGNOSIS && resultCode == RESULT_OK) {

                ll_symptom_dialog.setVisibility(View.GONE);
                if (finalarr.length > 2) {
                    if (finalarr[0].equals("select")) {
                        if (finalarr[1].equals("number")) {
                            if (isNumeric(finalarr[2])) {
                                int pos = Integer.parseInt(finalarr[2]);
                                if (IsInList(pos, diagnosismatched.size())) {
                                    pos--;
                                    if (diagnosis_update_flag == 1) {
                                        Final_Diagnosis.get(update_advice_position).setDiagnosis(diagnosismatched.get(pos).getDiagnosis());
                                        View itemview = flow_diagnosis.getChildAt(update_advice_position);
                                        TextView temp = itemview.findViewById(R.id.tv_chip_data);
                                        temp.setText(diagnosismatched.get(pos).getDiagnosis());
                                        ll__uni_prompt.setVisibility(View.GONE);
                                        ll_symptom_dialog.setVisibility(View.GONE);
                                        diagnosis_update_flag = 0;
                                    } else
                                        AddDiagnosis(diagnosismatched.get(pos).getDiagnosis());
                                }

                            }
                        }
                    }
                }

            }
            //Multiple Diagnostic test
            else if (requestCode == VR_MULTIPLE_DIAGNOSTIC_TEST && resultCode == RESULT_OK) {

                ll_symptom_dialog.setVisibility(View.GONE);
                if (finalarr.length > 2) {
                    if (finalarr[0].equals("select")) {
                        if (finalarr[1].equals("number")) {
                            if (isNumeric(finalarr[2])) {
                                int pos = Integer.parseInt(finalarr[2]);
                                if (IsInList(pos, diagnostictestsmatched.size())) {
                                    pos--;
                                    if (test_update_flag == 1) {
                                        Final_DiagnosticTests.get(update_advice_position).setTest_name(diagnostictestsmatched.get(pos).getTest_name());
                                        View itemview = flow_diagnostic.getChildAt(update_advice_position);
                                        TextView temp = itemview.findViewById(R.id.tv_chip_data);
                                        temp.setText(diagnostictestsmatched.get(pos).getTest_name());
                                        ll__uni_prompt.setVisibility(View.GONE);
                                        ll_symptom_dialog.setVisibility(View.GONE);
                                        test_update_flag = 0;
                                    } else
                                        AddDiagnosticTest(diagnostictestsmatched.get(pos).getTest_name());
                                }

                            }
                        }
                    }
                }

            }
            //Multiple Medicines
            else if (requestCode == VR_MULTIPLE_MEDICINES && resultCode == RESULT_OK) {

                if (finalarr.length > 2) {
                    if (finalarr[0].equals("select")) {
                        if (finalarr[1].equals("number")) {
                            if (isNumeric(finalarr[2])) {
                                int pos = Integer.parseInt(finalarr[2]);
                                if (IsInList(pos, MedicineList.size())) {
                                    pos--;
                                    AddMedicine1(MedicineList.get(pos), multiplemedobj);
                                }

                            }
                        }
                    }
                }

            }
            //Multiple Advices
            else if (requestCode == VR_MULTIPLE_ADVICES && resultCode == RESULT_OK) {

                if (finalarr.length > 2) {
                    if (finalarr[0].equals("select")) {
                        if (finalarr[1].equals("number")) {
                            if (isNumeric(finalarr[2])) {
                                int pos = Integer.parseInt(finalarr[2]);
                                if (IsInList(pos, AdviceList.size())) {
                                    pos--;
                                    AddAdvice(AdviceList.get(pos));
                                }

                            }
                        }
                    }
                }

            }

            //No match Symptom
            else if (requestCode == VR_NOMATCH_PROMPT_SYMPTOM && resultCode == RESULT_OK) {

                if (finalarr.length > 0) {

                    if ((finalarr[0].equals("yes") || finalarr[0].equals("accept") || finalarr[0].equals("except")) &&
                            (!temp_symptom.equals(""))) {
                        if (symptom_update_flag == 1) {
                            if (IsInList(update_advice_position, Final_Symptoms.size())) {
                                Final_Symptoms.get(update_advice_position).setIssues(temp_symptom);
                                View itemview = flow_symptoms.getChildAt(update_advice_position);
                                TextView temp = itemview.findViewById(R.id.tv_chip_data);
                                temp.setText(temp_symptom);
                                ll__uni_prompt.setVisibility(View.GONE);
                                ll_symptom_dialog.setVisibility(View.GONE);
                                symptom_update_flag = 0;
                            }
                        } else
                            AddSymptom(temp_symptom);

                    } else if (finalarr[0].equals("no") || finalarr[0].equals("decline")) {
                        ll__uni_prompt.setVisibility(View.GONE);
                        symptom_update_flag = 0;
                    }
                }

            }
            //No match diagnosis
            else if (requestCode == VR_NOMATCH_PROMPT_DIAGNOSIS && resultCode == RESULT_OK) {

                if (finalarr.length > 0) {

                    if ((finalarr[0].equals("yes") || finalarr[0].equals("accept") || finalarr[0].equals("except")) &&
                            (!temp_symptom.equals(""))) {

                        if (diagnosis_update_flag == 1) {
                            Log.e("error", Final_Diagnosis.size() + "|" + diagnosismatched.size());
                            Final_Diagnosis.get(update_advice_position).setDiagnosis(temp_symptom);
                            View itemview = flow_diagnosis.getChildAt(update_advice_position);
                            TextView temp = itemview.findViewById(R.id.tv_chip_data);
                            temp.setText(temp_symptom);
                            ll__uni_prompt.setVisibility(View.GONE);
                            ll_symptom_dialog.setVisibility(View.GONE);
                            diagnosis_update_flag = 0;
                        } else
                            AddDiagnosis(temp_symptom);
                    } else if (finalarr[0].equals("no") || finalarr[0].equals("decline")) {
                        ll__uni_prompt.setVisibility(View.GONE);
                        diagnosis_update_flag = 0;
                    }
                }

            }
            //No match Diagnostic Test
            else if (requestCode == VR_NOMATCH_PROMPT_DIAGNOSTIC_TEST && resultCode == RESULT_OK) {

                if (finalarr.length > 0) {

                    if ((finalarr[0].equals("yes") || finalarr[0].equals("accept") || finalarr[0].equals("except")) &&
                            (!temp_symptom.equals(""))) {

                        if (test_update_flag == 1) {
                            //Log.e("error",Final_DiagnosticTests.size()+"|"+diagnosismatched.size());
                            Final_DiagnosticTests.get(update_advice_position).setTest_name(temp_symptom);
                            View itemview = flow_diagnostic.getChildAt(update_advice_position);
                            TextView temp = itemview.findViewById(R.id.tv_chip_data);
                            temp.setText(temp_symptom);
                            ll__uni_prompt.setVisibility(View.GONE);
                            ll_symptom_dialog.setVisibility(View.GONE);
                            test_update_flag = 0;
                        } else
                            AddDiagnosticTest(temp_symptom);

                    } else if (finalarr[0].equals("no") || finalarr[0].equals("decline")) {
                        ll__uni_prompt.setVisibility(View.GONE);
                        test_update_flag = 0;
                    }
                }

            }
            //Update Advice
            else if (requestCode == VR_UPDATE_ADVICE && resultCode == RESULT_OK) {

                ll_symptom_dialog.setVisibility(View.GONE);

                if (finalarr.length > 2) {
                    if (finalarr[0].equals("select")) {
                        if (finalarr[1].equals("number")) {
                            if (isNumeric(finalarr[2])) {
                                int pos = Integer.parseInt(finalarr[2]);
                                if (IsInList(pos, AdviceList.size())) {
                                    pos--;
                                    updateAdvice(pos);
                                }

                            }
                        }
                    }

                }

            }

            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    private String[] GetRawDataFromSpeech(ArrayList<String> matches) {

        if (matches.contains("information")) {
            informationMenu();
        }


        String finaldata = matches.get(0).toLowerCase();

        char s = finaldata.charAt(finaldata.length()-1);

        if(s == '.'){
            finaldata = finaldata.substring(0,finaldata.length()-1);
        }
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

    private void Speak(String text, int VR_CODE, int duration) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*textToSpeech.setSpeechRate(0.7f);
            textToSpeech.setPitch(0.5f);*/
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            isTTSSpeaking(VR_CODE, duration);
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    public void isTTSSpeaking(final int VR_CODE, final int duration) {

        final Handler h = new Handler();

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

        if (VR_CODE == VR_NOMATCH_PROMPT_SYMPTOM || VR_CODE == VR_MULTIPLE_SYMPTOMS || VR_CODE == VR_MULTIPLE_DIAGNOSIS ||
                VR_CODE == VR_NOMATCH_PROMPT_DIAGNOSIS || VR_CODE == VR_MULTIPLE_DIAGNOSTIC_TEST || VR_CODE == VR_NOMATCH_PROMPT_DIAGNOSTIC_TEST
                || VR_CODE == VR_MULTIPLE_MEDICINES || VR_CODE == VR_NOMATCH_PROMPT_MEDICINES || VR_CODE == VR_MULTIPLE_ADVICES
                || VR_CODE == VR_UPDATE_ADVICE || VR_CODE == VR_UPDATE_SYMPTOMS
        ) {
            startVoiceRecognitionActivity(VR_CODE);
        }

        //startVoiceRecognitionActivity(VR_CODE);

    }


    //Setting Data

    private void SetPatientDetails() {

        tv_patient_name.setText(SelectedPatient.getPatientDetails().getFname());
        tv_patient_id_2.setText("ID : " + SelectedPatient.getPatientDetails().getPatientId());
        tv_gender_age.setText(SelectedPatient.getPatientDetails().getGender() + " | " + SelectedPatient.getPatientDetails().getAge());

    }

    private void SetMedicines(String[] arr) {

        medicinematched = new ArrayList<>();
        MedicineList = new ArrayList<>();
        multiplemedobj = null;
        if (arr[0].equals("take")) {
            FetchMedicine(arr, 1);

        } else {
            FetchMedicine(arr, 0);
        }


    }

    private void SetFollowUp(String[] finalarr) {
        //if(f_date.equals(""))
        String t_date = GetDate(finalarr);

        int[] t_time = GetTime(finalarr);
        if (!t_date.equals(""))
            f_date = t_date;

        String time_1 = "";
        String ampm = "";
        if (!(t_time[0] == -1)) {
            f_time = t_time;

            if (f_time[2] == 0)
                ampm = "A.M.";
            else
                ampm = "P.M.";

            if (f_time[1] >= 0 && f_time[1] < 10)
                time_1 = "Time : " + f_time[0] + ":0" + f_time[1] + " " + ampm;
            else
                time_1 = "Time : " + f_time[0] + ":" + f_time[1] + " " + ampm;

        }


        String date_1 = "";
        date_1 = "Date : " + f_date;

        if (date_1.equals("") || !time_1.equals(""))
            tv_followup_datetime.setText(date_1 + " | " + time_1);
        else if (date_1.equals("") && time_1.equals("")) {
            tv_followup_datetime.setText("No Follow Up Added.");
        }

    }

    private void SetAdvice() {
        AdviceList = new ArrayList<>();


        //Final_Advice.addAll();
        Fetch_Advice();

    }

    private void SetSymptoms(String[] arr) {

        if (Issuelist.size() > 0) {

            List<String> filtered_symptopms = FilterArray(arr);

            issuesmatched = new ArrayList<>();

            for (int i = 0; i < Issuelist.size(); i++) {

                if (Issuelist.get(i).getIssues().toLowerCase().contains(filtered_symptopms.get(0))) {
                    issuesmatched.add(Issuelist.get(i));
                }
            }

            //More than one filters and more than one issues
            if (issuesmatched.size() > 1) {
                if (filtered_symptopms.size() > 1)
                    issuesmatched = FilterIssues(1, filtered_symptopms, issuesmatched);

                ShowSymptomDialog("Symptom Options", issuesmatched, VR_MULTIPLE_SYMPTOMS);
                Toast.makeText(this, "" + issuesmatched.size(), Toast.LENGTH_SHORT).show();
            }
            //no issues matched
            else if (issuesmatched.size() == 0) {
                String temp = "";
                for (int i = 0; i < arr.length; i++) {
                    temp += arr[i] + " ";
                }
                ShowNoMatchPrompt(temp, "Symptom");
                Toast.makeText(this, "No Results !", Toast.LENGTH_SHORT).show();
            }
            //Exact issue matched
            else if (issuesmatched.size() == 1) {
                AddSymptom(issuesmatched.get(0).getIssues());
                Toast.makeText(this, "" + issuesmatched.get(0).getIssues(), Toast.LENGTH_SHORT).show();
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

        //HashMap<String, Double> rawVitals = Functions.getVitals(data);
        HashMap<String,Double> rawVitals =getVitals2(data,vitals_list);
        if (rawVitals.size() > 0) {
            for (String key : rawVitals.keySet()) {
                Double a = 0.0;
                switch (key) {
                    case "height":
                        int height = (int) Math.round(rawVitals.get(key));
                        tv_height.setText(height + " CM");
                        Final_Vitals.setHeight(height);

                        break;
                    case "weight":
                        double weight = rawVitals.get(key);
                        tv_weight.setText(weight + " KG");
                        Final_Vitals.setWeight(weight);
                        break;
                    case "head":
                        a = rawVitals.get(key);
                        tv_hc.setText(a + " CM");
                        Final_Vitals.setHead(a);
                        break;
                    case "temperature":
                        int temp = (int) Math.round(rawVitals.get(key));
                        tv_temperature.setText(temp + " °");
                        Final_Vitals.setTemperature(temp);
                        break;

                }
            }

            if (Final_Vitals.getWeight() > 0 && Final_Vitals.getHeight() > 0){

                double bmi = (Final_Vitals.getWeight())/((Final_Vitals.getHeight()/10)*(Final_Vitals.getHeight()/10));
                Final_Vitals.setBMI(bmi);
                tv_bmi.setText(""+bmi);

            }

        }




    }

    private void SetDiagnosis(String[] arr) {


        if (DiagnosisList.size() > 0) {
            flow_diagnosis = findViewById(R.id.flow_diagnosis);
            ViewGroup parent = (ViewGroup) rl_home_main;

            List<String> filtered_diagnosis = FilterArray(arr);

            diagnosismatched = new ArrayList<>();

            for (int i = 0; i < DiagnosisList.size(); i++) {

                if (DiagnosisList.get(i).getDiagnosis().toLowerCase().contains(filtered_diagnosis.get(0))) {
                    diagnosismatched.add(DiagnosisList.get(i));
                }
            }

            //More than one filters and more than one issues
            if (diagnosismatched.size() > 1) {
                if (filtered_diagnosis.size() > 1)
                    diagnosismatched = FilterDiagnosis(1, filtered_diagnosis, diagnosismatched);

                ShowDiagnosisDialog("Diagnosis Options", diagnosismatched);
                Toast.makeText(this, "" + diagnosismatched.size(), Toast.LENGTH_SHORT).show();
            }
            //no issues matched
            else if (diagnosismatched.size() == 0) {
                String temp = "";
                for (int i = 0; i < arr.length; i++) {
                    temp += arr[i] + " ";
                }
                ShowNoMatchPrompt(temp, "Diagnosis");
                Toast.makeText(this, "No Results !", Toast.LENGTH_SHORT).show();
            } else if (diagnosismatched.size() == 1) {
                AddDiagnosis(diagnosismatched.get(0).getDiagnosis());
                Toast.makeText(this, "" + diagnosismatched.get(0).getDiagnosis(), Toast.LENGTH_SHORT).show();
            }


        }


    }

    private void SetDiagnosticTests(String[] arr) {

        if (DiagnosticTestList.size() > 0) {
            flow_diagnostic = findViewById(R.id.flow_diagnostic);
            ViewGroup parent = (ViewGroup) rl_home_main;

            List<String> filtered_diagnosis = FilterArray(arr);

            diagnostictestsmatched = new ArrayList<>();

            for (int i = 0; i < DiagnosticTestList.size(); i++) {

                if (DiagnosticTestList.get(i).getTest_name().toLowerCase().contains(filtered_diagnosis.get(0))) {
                    diagnostictestsmatched.add(DiagnosticTestList.get(i));
                }
            }

            //More than one filters and more than one issues
            if (diagnostictestsmatched.size() > 1) {
                if (filtered_diagnosis.size() > 1)
                    diagnostictestsmatched = FilterDiagnosticTest(1, filtered_diagnosis, diagnostictestsmatched);

                ShowDiagnosticTestDialog("Diagnosis Options", diagnostictestsmatched);
                Toast.makeText(this, "" + diagnostictestsmatched.size(), Toast.LENGTH_SHORT).show();
            }
            //no issues matched
            else if (diagnostictestsmatched.size() == 0) {
                String temp = "";
                for (int i = 0; i < arr.length; i++) {
                    temp += arr[i] + " ";
                }
                ShowNoMatchPrompt(temp, "Diagnostic");
                Toast.makeText(this, "No Results !", Toast.LENGTH_SHORT).show();
            } else if (diagnostictestsmatched.size() == 1) {
                AddDiagnosticTest(diagnostictestsmatched.get(0).getTest_name());
                Toast.makeText(this, "" + diagnostictestsmatched.get(0).getTest_name(), Toast.LENGTH_SHORT).show();
            }


        }

    }


    //Add Data to views

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
        number.setText((Final_DiagnosticTests.size() + 1) + ".");
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
        number.setText((Final_Symptoms.size() + 1) + ".");
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
        number.setText((Final_Diagnosis.size() + 1) + ".");
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

    private void AddMedicine1(Med.Data data, Medicine multiplemedobj) {
        ll_symptom_dialog.setVisibility(View.GONE);
        Log.i("da", "Hello");
        multiplemedobj.setName(data.getMedicine_name());
        multiplemedobj.setGenericname(data.getMedicine_generic_name());
        Final_Medicines.add(multiplemedobj);
        //medicineAdapter.notifyItemInserted(Final_Medicines.size()-1);
        setMedicineRecyclerView();
        if (!CheckMedicineNullData(multiplemedobj)) {
            Speak("Please provide other details for " + multiplemedobj.getName(), VR_MEDICINE_NULL_DATA, 3000);
        }
    }

    private void AddMedicine(Med.Data data, String[] arr) {

        LinkedList<String> linkedlist = new LinkedList<>(Arrays.asList(arr));
        Medicine obj = new Medicine(

                data.getMedicine_name(),
                data.getMedicine_generic_name(),
                getfrequency(linkedlist),
                getDuration(linkedlist),
                getMedTimings(linkedlist),
                getDailyTimings(linkedlist)

        );


        Final_Medicines.add(obj);
        setMedicineRecyclerView();

        if (!CheckMedicineNullData(obj)) {
            Speak("Please provide other details for " + obj.getName(), VR_MEDICINE_NULL_DATA, 3000);
        }
    }

    private void AddAdvice(Advice advice) {
        ll_symptom_dialog.setVisibility(View.GONE);
        Final_Advice.add(advice);
        SetAdviceRecyclerView();

    }


    //Update Data

    private void updateDiagnosticTest(int update_advice_position, String[] arr) {

        if (DiagnosticTestList.size() > 0) {
            flow_diagnostic = findViewById(R.id.flow_diagnostic);
            ViewGroup parent = (ViewGroup) rl_home_main;

            List<String> filtered_diagnosis = FilterArray(arr);

            diagnostictestsmatched = new ArrayList<>();

            for (int i = 0; i < DiagnosticTestList.size(); i++) {

                if (DiagnosticTestList.get(i).getTest_name().toLowerCase().contains(filtered_diagnosis.get(0))) {
                    diagnostictestsmatched.add(DiagnosticTestList.get(i));
                }
            }

            //More than one filters and more than one issues
            if (diagnostictestsmatched.size() > 1) {
                if (filtered_diagnosis.size() > 1)
                    diagnostictestsmatched = FilterDiagnosticTest(1, filtered_diagnosis, diagnostictestsmatched);
                test_update_flag = 1;
                ShowDiagnosticTestDialog("Diagnosis Options", diagnostictestsmatched);
                Toast.makeText(this, "" + diagnostictestsmatched.size(), Toast.LENGTH_SHORT).show();
            }
            //no issues matched
            else if (diagnostictestsmatched.size() == 0) {
                test_update_flag = 1;
                String temp = "";
                for (int i = 0; i < arr.length; i++) {
                    temp += arr[i] + " ";
                }
                ShowNoMatchPrompt(temp, "Diagnostic");
                Toast.makeText(this, "No Results !", Toast.LENGTH_SHORT).show();
            }
            //Exact Match
            else if (diagnostictestsmatched.size() == 1) {
                test_update_flag = 0;
                Final_DiagnosticTests.get(update_advice_position).setTest_name(diagnostictestsmatched.get(0).getTest_name());
                View itemview = flow_diagnostic.getChildAt(update_advice_position);
                TextView temp = itemview.findViewById(R.id.tv_chip_data);
                temp.setText(diagnostictestsmatched.get(0).getTest_name());
                Toast.makeText(this, "" + diagnostictestsmatched.get(0).getTest_name(), Toast.LENGTH_SHORT).show();
            }


        }

    }

    private void updateDiagnosis(int update_advice_position, String[] arr) {


        if (DiagnosisList.size() > 0) {
            flow_diagnosis = findViewById(R.id.flow_diagnosis);
            ViewGroup parent = (ViewGroup) rl_home_main;

            List<String> filtered_diagnosis = FilterArray(arr);

            diagnosismatched = new ArrayList<>();

            for (int i = 0; i < DiagnosisList.size(); i++) {

                if (DiagnosisList.get(i).getDiagnosis().toLowerCase().contains(filtered_diagnosis.get(0))) {
                    diagnosismatched.add(DiagnosisList.get(i));
                }
            }

            //More than one filters and more than one issues
            if (diagnosismatched.size() > 1) {
                if (filtered_diagnosis.size() > 1)
                    diagnosismatched = FilterDiagnosis(1, filtered_diagnosis, diagnosismatched);

                diagnosis_update_flag = 1;
                ShowDiagnosisDialog("Diagnosis Options", diagnosismatched);
                Toast.makeText(this, "" + diagnosismatched.size(), Toast.LENGTH_SHORT).show();
            }
            //no issues matched
            else if (diagnosismatched.size() == 0) {
                diagnosis_update_flag = 1;
                String temp = "";
                for (int i = 0; i < arr.length; i++) {
                    temp += arr[i] + " ";
                }
                ShowNoMatchPrompt(temp, "Diagnosis");
                Toast.makeText(this, "No Results !", Toast.LENGTH_SHORT).show();
            }
            //Exact Match
            else if (diagnosismatched.size() == 1) {
                diagnosis_update_flag = 0;
                Final_Diagnosis.get(update_advice_position).setDiagnosis(diagnosismatched.get(0).getDiagnosis());
                View itemview = flow_diagnosis.getChildAt(update_advice_position);
                TextView temp = itemview.findViewById(R.id.tv_chip_data);
                temp.setText(diagnosismatched.get(0).getDiagnosis());
                Toast.makeText(this, "" + diagnosismatched.get(0).getDiagnosis(), Toast.LENGTH_SHORT).show();
            }


        }

    }

    private void updateSymptoms(int update_advice_position, String arr[]) {

        if (Issuelist.size() > 0) {

            List<String> filtered_symptopms = FilterArray(arr);

            issuesmatched = new ArrayList<>();

            for (int i = 0; i < Issuelist.size(); i++) {

                if (Issuelist.get(i).getIssues().toLowerCase().contains(filtered_symptopms.get(0))) {
                    issuesmatched.add(Issuelist.get(i));
                }

            }

            //More than one filters and more than one issues
            if (issuesmatched.size() > 1) {
                if (filtered_symptopms.size() > 1)
                    issuesmatched = FilterIssues(1, filtered_symptopms, issuesmatched);

                symptom_update_flag = 1;
                ShowSymptomDialog("Symptom Options", issuesmatched, VR_MULTIPLE_SYMPTOMS);
                Toast.makeText(this, "" + issuesmatched.size(), Toast.LENGTH_SHORT).show();
            }
            //no issues matched
            else if (issuesmatched.size() == 0) {
                symptom_update_flag = 1;
                String temp = "";
                for (int i = 0; i < arr.length; i++) {
                    temp += arr[i] + " ";
                }
                ShowNoMatchPrompt(temp, "Symptom");
                Toast.makeText(this, "No Results !", Toast.LENGTH_SHORT).show();
            }
            //Exact issue matched
            else if (issuesmatched.size() == 1) {
                symptom_update_flag = 0;
                Final_Symptoms.get(update_advice_position).setIssues(issuesmatched.get(0).getIssues());
                View itemview = flow_symptoms.getChildAt(update_advice_position);
                TextView temp = itemview.findViewById(R.id.tv_chip_data);
                temp.setText(issuesmatched.get(0).getIssues());
                Toast.makeText(this, "" + issuesmatched.get(0).getIssues(), Toast.LENGTH_SHORT).show();
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

    private void updateMedicine(String[] arr, int pos) {

        LinkedList<String> linkedlistnew = new LinkedList<>(Arrays.asList(arr));

        Medicine objnew = Final_Medicines.get(pos);

        String freq = updatefrequency(linkedlistnew);
        String duration = getDuration(linkedlistnew);
        String MedTimings = getMedTimings(linkedlistnew);
        double[] dailytimes = getDailyTimings(linkedlistnew);

        if (freq != null) {
            if (!freq.equals(objnew.getFrequency())) {
                objnew.setFrequency(freq);
            }
        }
        if (duration != null) {
            if (!duration.equals(objnew.getDuration())) {
                objnew.setDuration(duration);
            }
        }
        if (MedTimings != null) {
            if (!MedTimings.equals(objnew.getAfbf())) {
                objnew.setAfbf(MedTimings);
            }
        }
        if (!(dailytimes[0] == 0 && dailytimes[1] == 0 && dailytimes[2] == 0)) {
            double temp[] = objnew.getDailytimings();
            if (!(dailytimes[0] == temp[0] && dailytimes[1] == temp[1] && dailytimes[2] == temp[2])) {
                objnew.setDailytimings(dailytimes);
            }

        }
        Final_Medicines.set(pos, objnew);
        medicineAdapter.notifyItemChanged(pos);
        if (!CheckMedicineNullData(objnew)) {
            Speak("Please provide other details for " + objnew.getName(), VR_MEDICINE_NULL_DATA, 3000);
        }

    }

    private void updateAdvice(int pos) {

        String advicenew = AdviceList.get(pos).getAdvice_data();
        Final_Advice.get(update_advice_position).setAdvice_data(advicenew);
        SetAdviceRecyclerView();

    }


    //Delete Data

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

    private void DeleteAdvice(int position) {
        Final_Advice.remove(position);
        SetAdviceRecyclerView();
    }

    private void DeleteMedicine(int position) {

        Final_Medicines.remove(position);
        setMedicineRecyclerView();

    }


    //Show Dialog For Multiple Matches and No Match

    private void ShowSymptomDialog(String symptom_options, List<Issues.Data> issuesmatched, int VR_CODE) {

        tv_dialog_count.setText(issuesmatched.size() + " Symptoms Matched In The Database");

        tv_dialog_heading.setText(symptom_options);

        MultipleSymptomAdapter adapter = new MultipleSymptomAdapter(this, issuesmatched);

        rv_multiple_symptoms.setAdapter(adapter);
        rv_multiple_symptoms.setHasFixedSize(true);

        ll_symptom_dialog.setVisibility(View.VISIBLE);
        Speak("Multiple matches found in database.Please select one", VR_CODE, 3000);
    }

    private void ShowMedicineDialog(String symptom_options, List<Med.Data> medicinematched) {

        tv_dialog_count.setText(medicinematched.size() + " Medicines Matched In The Database");

        tv_dialog_heading.setText(symptom_options);

        MultipleMedicineAdapter adapter = new MultipleMedicineAdapter(this, medicinematched);

        rv_multiple_symptoms.setAdapter(adapter);
        rv_multiple_symptoms.setHasFixedSize(true);

        ll_symptom_dialog.setVisibility(View.VISIBLE);
        Speak("Multiple matches found in database.Please select one", VR_MULTIPLE_MEDICINES, 3000);
    }

    private void ShowAdviceDialog(String symptom_option, List<Advice> advice, int VR_CODE) {
        tv_dialog_heading.setText(symptom_option);

        MultipleAdviceAdapter adapter = new MultipleAdviceAdapter(this, advice);


        rv_multiple_symptoms.setAdapter(adapter);
        rv_multiple_symptoms.setHasFixedSize(true);
        ll_symptom_dialog.setVisibility(View.VISIBLE);

        Speak("Please select one advice", VR_CODE, 3000);


    }

    private void ShowDiagnosisDialog(String diagnosis_option, List<Diagnosis.Data> diagnosismatched) {

        tv_dialog_count.setText(diagnosismatched.size() + " Diagnosis Matched In The Database");

        tv_dialog_heading.setText(diagnosis_option);

        MultipleDiagnosisAdapter adapter = new MultipleDiagnosisAdapter(this, diagnosismatched);

        rv_multiple_symptoms.setAdapter(adapter);
        rv_multiple_symptoms.setHasFixedSize(true);

        ll_symptom_dialog.setVisibility(View.VISIBLE);
        Speak("Multiple matches found in database.Please select one", VR_MULTIPLE_DIAGNOSIS, 3000);
    }

    private void ShowDiagnosticTestDialog(String diagnosis_option, List<DiagnosticTests.Data> diagnosismatched) {

        tv_dialog_count.setText(diagnosismatched.size() + " Diagnostic test Matched In The Database");

        tv_dialog_heading.setText(diagnosis_option);

        MultipleDiagnosticTestAdapter adapter = new MultipleDiagnosticTestAdapter(this, diagnostictestsmatched);

        rv_multiple_symptoms.setAdapter(adapter);
        rv_multiple_symptoms.setHasFixedSize(true);

        ll_symptom_dialog.setVisibility(View.VISIBLE);
        Speak("Multiple matches found in database.Please select one", VR_MULTIPLE_DIAGNOSTIC_TEST, 3000);
    }

    private void ShowNoMatchPrompt(final String data, String type) {

        if (type.equals("Symptom")) {

            tv_promt_data.setText(type + " : " + data);
            temp_symptom = data;
            tv_promt_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll__uni_prompt.setVisibility(View.GONE);
                    symptom_update_flag = 0;
                }
            });


            tv_promt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (symptom_update_flag == 1) {
                        Final_Symptoms.get(update_advice_position).setIssues(temp_symptom);
                        View itemview = flow_symptoms.getChildAt(update_advice_position);
                        TextView temp = itemview.findViewById(R.id.tv_chip_data);
                        temp.setText(temp_symptom);
                        ll__uni_prompt.setVisibility(View.GONE);
                        ll_symptom_dialog.setVisibility(View.GONE);
                        symptom_update_flag = 0;
                    } else
                        AddSymptom(data);


                }
            });


            ll__uni_prompt.setVisibility(View.VISIBLE);
            if (symptom_update_flag == 1) {
                Speak("No Match found. Do you want to update this symptom ?", VR_NOMATCH_PROMPT_SYMPTOM, 1000);
            } else
                Speak(tv_promt_question.getText().toString(), VR_NOMATCH_PROMPT_SYMPTOM, 1000);


        } else if (type.equals("Diagnosis")) {
            tv_promt_data.setText(type + " : " + data);
            temp_symptom = data;
            tv_promt_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll__uni_prompt.setVisibility(View.GONE);
                    diagnosis_update_flag = 0;
                }
            });


            tv_promt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (symptom_update_flag == 1) {
                        Final_Diagnosis.get(update_advice_position).setDiagnosis(temp_symptom);
                        View itemview = flow_diagnosis.getChildAt(update_advice_position);
                        TextView temp = itemview.findViewById(R.id.tv_chip_data);
                        temp.setText(temp_symptom);
                        ll__uni_prompt.setVisibility(View.GONE);
                        ll_symptom_dialog.setVisibility(View.GONE);
                        diagnosis_update_flag = 0;
                    } else
                        AddDiagnosis(data);


                }
            });


            ll__uni_prompt.setVisibility(View.VISIBLE);
            Speak("No Matches Found in Database.Do you want to add this Diagnosis?", VR_NOMATCH_PROMPT_DIAGNOSIS, 1000);


        } else if (type.equals("Diagnostic")) {
            tv_promt_data.setText(type + " : " + data);
            temp_symptom = data;
            tv_promt_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ll__uni_prompt.setVisibility(View.GONE);
                    test_update_flag = 0;
                }
            });


            tv_promt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (test_update_flag == 1) {
                        Final_DiagnosticTests.get(update_advice_position).setTest_name(temp_symptom);
                        View itemview = flow_diagnostic.getChildAt(update_advice_position);
                        TextView temp = itemview.findViewById(R.id.tv_chip_data);
                        temp.setText(temp_symptom);
                        ll__uni_prompt.setVisibility(View.GONE);
                        ll_symptom_dialog.setVisibility(View.GONE);
                        test_update_flag = 0;
                    } else
                        AddDiagnosticTest(data);


                }
            });


            ll__uni_prompt.setVisibility(View.VISIBLE);
            Speak("No Matches Found in Database.Do you want to add this Diagnostic test?", VR_NOMATCH_PROMPT_DIAGNOSTIC_TEST, 1000);


        }
    }


    //Fetch Data

    private void FetchSymptoms() {
        final Dialog dialog = Functions.createDialog(Home.this);
        if(created != 0){
            Functions.showDialog(dialog);
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "getIssues");
        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);
        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                Functions.dismissDialog(dialog);
                try {
                    Issues obj = new Gson().fromJson(responseStr, Issues.class);
                    if (obj.isStatus()) {

                        Issuelist.addAll(obj.getData());

                        //  tv_main.setText(obj.getTotal_records()+"|"+Issuelist.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : " + e.getMessage(), Toast.LENGTH_LONG).show();

                }

                return false;
            }
        });

    }

    private void FetchDiagnosis() {

        final Dialog dialog = Functions.createDialog(Home.this);
        if(created != 0){
            Functions.showDialog(dialog);
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "getDiagnosis");
        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);
        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                Functions.dismissDialog(dialog);
                try {
                    Diagnosis obj = new Gson().fromJson(responseStr, Diagnosis.class);
                    if (obj.isStatus()) {

                        DiagnosisList.addAll(obj.getData());

                        // tv_main.setText(obj.getTotal_records()+"|"+DiagnosisList.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : " + e.getMessage(), Toast.LENGTH_LONG).show();

                }

                return false;
            }
        });
    }

    private void FetchPatients() {

        //progress_dialog.show();
        final Dialog dialog = Functions.createDialog(Home.this);
        if(created != 0) {
            Functions.showDialog(dialog);
        }

        HashMap<String, String> params = new HashMap<>();

        params.put("action", "getChildren");
        params.put("drId", "" + doctor_id);

        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);

        /*ncall.performPostCall(VR_APIS,params);
        ncall.setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                return false;
            }
        });
        */

        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {


                // progress_dialog.dismiss();
                try {
                    //tv_main.setText(responseStr);
                    Patient obj = new Gson().fromJson(responseStr, Patient.class);

                    if (obj.getStatus()) {

                        Log.d("api",responseStr);
                        List<Patient.Data> PatientsList = new ArrayList<>();
                        PatientsList.addAll(obj.getData());

                        tv_home_total_patients.setText("Today's Patients - " + PatientsList.size());

                        Final_PatientChecked = new ArrayList<>();
                        for (int i = 0; i < PatientsList.size(); i++) {
                            Final_PatientChecked.add(new PatientFinal(PatientsList.get(i)));
                        }

                        //progress_dialog.show();
                        Final_PatientDraft = new ArrayList<>();
                        HashMap<String, String> params = new HashMap<>();
                        params.put("action", "getAllPrescriptions");
                        params.put("patient_type", "draft");

                        NetworkCall ncall = new NetworkCall();
                        ncall.setServerUrlWebserviceApi(VR_APIS);

                        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
                            @Override
                            public boolean setResponse(String responseStr) {
                                //progress_dialog.dismiss();
                                Functions.dismissDialog(dialog);
                                if(created == 0) {
                                    //transitions.FadeInOut(ll_main_splash, true, Home.this, 300);
                                    transitions.SlideUpDown(rl_home_main, ll_main_splash, true, 0);
                                    created = -1;
                                }
                                swipe_refresh_home.setRefreshing(false);
                                try {
                                    PatientJson obj = new Gson().fromJson(responseStr, PatientJson.class);
                                    if (obj.getStatus()) {
                                        Log.d("api",responseStr);
                                        // List<PatientFinal>
                                        for (int i = 0; i < obj.getData().size(); i++) {
                                            Gson gson = new Gson();
                                            PatientFinal temp = gson.fromJson(obj.getData().get(i).getRx_json(), PatientFinal.class);
                                            Final_PatientDraft.add(temp);
                                            for (int l = 0; l < Final_PatientChecked.size(); l++) {
                                                if (Final_PatientChecked.get(l).getPatientDetails().getPatientId() ==
                                                        temp.getPatientDetails().getPatientId()) {

                                                    Final_PatientChecked.remove(l);

                                                }
                                            }
                                        }
                                        tv_patients_checked_in.setText("" + Final_PatientChecked.size());
                                        tv_patient_draft.setText(Final_PatientDraft.size() + "");

                                        if (flag_for_patients.equals(checkedin)) {
                                            SetMainDrawerRecyclerView(Final_PatientChecked, false);
                                        } else {
                                            ShowDraftPatients(false);
                                        }
                                    } else {
                                        tv_patients_checked_in.setText("" + Final_PatientChecked.size());
                                        tv_patient_draft.setText(Final_PatientDraft.size() + "");
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(Home.this, "Catch : " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                                return false;
                            }
                        });


                        //  SetMainDrawerRecyclerView(PatientsList);
                        //tv_main.setText(obj.getTotal_records()+"|"+vitalList.size());
                    } else {
                        swipe_refresh_home.setRefreshing(false);
                        //progress_dialog.dismiss();
                        Functions.dismissDialog(dialog);
                    }

                } catch (Exception e) {
                    swipe_refresh_home.setRefreshing(false);
                    //progress_dialog.dismiss();
                    Functions.dismissDialog(dialog);
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });


    }

    private void FetchDiagnosticList() {
        final Dialog dialog = Functions.createDialog(Home.this);
        if(created != 0){
            Functions.showDialog(dialog);
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "getDiagnosticTests");
        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);
        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                Functions.dismissDialog(dialog);
                try {
                    DiagnosticTests obj = new Gson().fromJson(responseStr, DiagnosticTests.class);
                    if (obj.isStatus()) {

                        DiagnosticTestList.addAll(obj.getData());

                        //Home.setText(obj.getTotal_records()+"|"+DiagnosticTestList.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : " + e.getMessage(), Toast.LENGTH_LONG).show();

                }

                return false;
            }
        });


    }

    private void FetchVitals() {

        final Dialog dialog = Functions.createDialog(Home.this);
        if(created != 0){
            Functions.showDialog(dialog);
        }
        HashMap<String, String> params = new HashMap<>();

        params.put("action", "getChildVitals");
        params.put("patient_id", "" + SelectedPatient.getPatientDetails().getPatientId());

        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);


        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                Functions.dismissDialog(dialog);
                try {
                    //tv_main.setText(responseStr);
                    Vitals obj = new Gson().fromJson(responseStr, Vitals.class);

                    if (obj.getStatus()) {
                        List<Vitals.Data> vitalList = new ArrayList<>();
                        vitalList.addAll(obj.getData());

                        SelectedPatient.setVitals(vitalList.get(0));

                        Final_Vitals.setHeight(SelectedPatient.getVitals().getHeight());
                        Final_Vitals.setWeight(SelectedPatient.getVitals().getWeight());
                        Final_Vitals.setBMI(SelectedPatient.getVitals().getBMI());
                        tv_height.setText(SelectedPatient.getVitals().getHeight() + " CM");
                        tv_weight.setText(SelectedPatient.getVitals().getWeight() + " KG");
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

    private void FetchMedicine(final String[] arr, final int pos) {

        final Dialog dialog = Functions.createDialog(Home.this);
        if(created != 0){
            Functions.showDialog(dialog);
        }
        medicinematched = new ArrayList<>();
        MedicineList = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        String keyword = arr[pos];


        params.put("search", "yes");
        params.put("search_keyword", keyword);

        NetworkCall call = new NetworkCall();
        call.setServerUrlWebserviceApi(SEARCH_MED);

        call.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                Functions.dismissDialog(dialog);
                try {
                    Med obj = new Gson().fromJson(responseStr, Med.class);
                    if (obj.getStatus()) {

                        MedicineList.addAll(obj.getData());

                        FilterMedicine(arr, pos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });


    }

    private void Fetch_Advice() {
        AdviceList.add(new Advice("1", "Stay hydrated", Integer.parseInt("10")));
        AdviceList.add(new Advice("2", "Make time for good sleep", Integer.parseInt("20")));
        AdviceList.add(new Advice("3", " If you need to…lose weight", Integer.parseInt("30")));
        AdviceList.add(new Advice("4", " Move more", Integer.parseInt("5")));
        AdviceList.add(new Advice("5", "Stop smoking", Integer.parseInt("1")));
        AdviceList.add(new Advice("6", "Take your medication correctly", Integer.parseInt("6")));
        AdviceList.add(new Advice("7", "Wash your hands", Integer.parseInt("79")));
        AdviceList.add(new Advice("8", " Cover up in the sun", Integer.parseInt("11")));
        AdviceList.add(new Advice("9", "Relax", Integer.parseInt("4")));
        AdviceList.add(new Advice("10", "Eat well", Integer.parseInt("2")));

        //List<Advice> sortedList = new ArrayList<>();
        ShowAdviceDialog("advice", AdviceList, VR_MULTIPLE_ADVICES);

    }

    private void fetchdatamap() {

        final Dialog dialog = Functions.createDialog(Home.this);
        if(created != 0){
            Functions.showDialog(dialog);
        }
        NetworkCall ncall = new NetworkCall();
        ncall.setServerUrlWebserviceApi(VR_APIS);

        HashMap<String,String> params = new HashMap<>();
        params.put("action","getAlldatamap");

        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                Functions.dismissDialog(dialog);
                try {
                    DataMap obj = new Gson().fromJson(responseStr, DataMap.class);
                    if (obj.getStatus()) {
                        vitals_map = new ArrayList<>();

                        vitals_map.addAll(obj.getData());
                        processmapeddata();
                        //  tv_main.setText(obj.getTotal_records()+"|"+Issuelist.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Home.this, "Catch : " + e.getMessage(), Toast.LENGTH_LONG).show();

                }


                return false;
            }
        });
    }

    private void FetchPreviousRX(){
        final Dialog dialog = Functions.createDialog(Home.this);
        if(created != 0){
            Functions.showDialog(dialog);
        }
        HashMap<String,String> params = new HashMap<>();
        params.put("action","getPreviousRx");
        params.put("patient_id",""+SelectedPatient.getPatientDetails().getPatientId());

        NetworkCall ncall = new NetworkCall();
        ncall.setServerUrlWebserviceApi(VR_APIS);
        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                Functions.dismissDialog(dialog);
                try{

                    PatientPrevRxJson obj = new Gson().fromJson(responseStr, PatientPrevRxJson.class);
                    if (obj.getStatus()){
                        Gson gson = new Gson();
                        PatientFinal temp = gson.fromJson(obj.getData().getRx_json(), PatientFinal.class);
                        SelectedPatient = temp;
                        ResumePatient();
                    }
                    else{
                        Speak("No previous prescription available for the selected patient",-1,3000);
                    }

                }
                catch (Exception e){
                    Toast.makeText(Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return false;
            }
        });
    }



    //Recycler Views

    private void setMedicineRecyclerView() {
        rv_medicines_list_main = findViewById(R.id.rv_medicines_list_main);
        medicineAdapter = new MedicineAdapter(this, Final_Medicines);
        rv_medicines_list_main.setAdapter(medicineAdapter);
        rv_medicines_list_main.setHasFixedSize(true);
    }

    private void SetMainDrawerRecyclerView(List<PatientFinal> PatientList, Boolean show) {

        patientsadapter = new PatientsAdapter(this, PatientList);
        rv_main_drawer.setAdapter(patientsadapter);
        rv_main_drawer.setHasFixedSize(true);
        if (show)
            ShowHidePatientList(1);

    }

    private void SetAdviceRecyclerView() {
        rv_advice_list_main = findViewById(R.id.rv_advice_list_main);
        AdviceAdapter adviceAdapter = new AdviceAdapter(this, Final_Advice);
        rv_advice_list_main.setAdapter(adviceAdapter);
        rv_advice_list_main.setHasFixedSize(true);
    }


    //Filter Data Array
    private List<String> FilterArray(String[] symptomsarr) {
        List<String> temp = new ArrayList<>();

        for (int i = 0; i < symptomsarr.length; i++) {

            if (!Functions.CheckData(symptomsarr[i], StaticData.Filters)) {

                temp.add(symptomsarr[i]);

            }

        }

        return temp;
    }

    public void FilterMedicine(String[] arr, int pos) {


        String rawdata = "";

   /*     for(int p = 0; p < arr.length; p++){
            rawdata +=arr[p]+" ";
            if(p == 5){
                break;
            }
        }
        for(int i = 0; i<MedicineList.size();i++) {
            if(rawdata.contains(MedicineList.get(i).getMedicine_name().toLowerCase()))
            {
                medicinematched.add(MedicineList.get(i));
            }

        }
       */


        medicinematched = new ArrayList<>();
        medicinematched.addAll(MedicineList);
        int x = 0;
        if (pos == 1) {
            x = 2;
        } else
            x = 1;


        for (int i = x; i < arr.length; i++) {

            if (MedicineList.size() > 0) {
                medicinematched = new ArrayList<>();
                for (int j = 0; j < MedicineList.size(); j++) {
                    String temp1 = MedicineList.get(j).getMedicine_name().toLowerCase().trim();
                    if (temp1.contains(arr[i])) {
                        medicinematched.add(MedicineList.get(j));
                    }
                }

                if (medicinematched.size() > 0) {
                    MedicineList = new ArrayList<>();
                    MedicineList.addAll(medicinematched);
                } else
                    break;


            }
        }

        for (int i = 0; i < MedicineList.size(); i++) {
            Log.i("Medicine ", MedicineList.get(i).getMedicine_name() + " ," + MedicineList.get(i).getId());

        }
        if (MedicineList.size() > 1) {
            LinkedList<String> linkedlist = new LinkedList<>(Arrays.asList(arr));
            multiplemedobj = new Medicine(
                    null,
                    null,
                    getfrequency(linkedlist),
                    getDuration(linkedlist),
                    getMedTimings(linkedlist),
                    getDailyTimings(linkedlist)

            );
            ShowMedicineDialog("Medicine Options", MedicineList);

        } else if (MedicineList.size() == 1) {
            AddMedicine(MedicineList.get(0), arr);
        } else {

        }

    }


    //Animate Views

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

    private void ShowHidePatientList(int x) {

        if (flag_for_patients == draft) {
            tv_drawer_title.setText(drawer_selected[1]);
        } else {
            tv_drawer_title.setText(drawer_selected[0]);
        }
        if (x == 1 && (ll_patient_drawer.getVisibility() == View.GONE ||
                ll_patient_drawer.getVisibility() == View.INVISIBLE)) {
            //ll_patient_drawer.setVisibility(View.VISIBLE);
            transitions.SlideUpDown(rl_home_main, ll_patient_drawer, false, 4);
        } else if (ll_patient_drawer.getVisibility() == View.VISIBLE) {
            //ll_patient_drawer.setVisibility(View.GONE);
            transitions.SlideUpDown(rl_home_main, ll_patient_drawer, true, 4);
        }


    }


    //Generate Final Prescription
    private void GeneratePrescription(final String patient_type) {

        stopRecording();

        Final_Patient = SelectedPatient;

        if (Final_Vitals != null) {
            Final_Patient.setVitals(Final_Vitals);
        }

        if (Final_Symptoms != null) {
            if (Final_Symptoms.size() > 0)
                Final_Patient.setFinal_Symptoms(Final_Symptoms);
        }

        if (Final_Diagnosis != null) {
            if (Final_Diagnosis.size() > 0)
                Final_Patient.setFinal_Diagnosis(Final_Diagnosis);
        }

        if (Final_DiagnosticTests != null) {
            if (Final_DiagnosticTests.size() > 0)
                Final_Patient.setFinal_DiagnosticTests(Final_DiagnosticTests);
        }

        if (Final_Advice != null) {
            if (Final_Advice.size() > 0)
                Final_Patient.setFinal_Advice(Final_Advice);
        }

        HashMap<String, String> params = new HashMap<>();

        //Upload as Draft
        if (patient_type.equals(draft)) {

            progress_dialog.show();
            if (Final_Medicines != null) {
                if (Final_Medicines.size() > 0)
                    Final_Patient.setFinal_Medicines(Final_Medicines);

            }

            if (!f_date.equals("")) {
                Final_Patient.setF_date(f_date);

            }
            if ((f_time[0] != -1)) {
                Final_Patient.setF_time(f_time);
            }
            final String patientjson = new Gson().toJson(Final_Patient);
            ResetData();
            Gson gson = new Gson();
            if (flag_for_previous_patient.equals(draft))
                params.put("action", "updatePrescription");
            else
                params.put("action", "insertPrescription");

            params.put("patient_id", Final_Patient.getPatientDetails().getPatientId() + "");
            params.put("rx_json", patientjson);
            params.put("patient_type", patient_type);
            NetworkCall ncall = new NetworkCall();
            ncall.setServerUrlWebserviceApi(VR_APIS);

            ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
                @Override
                public boolean setResponse(String responseStr) {

                    progress_dialog.dismiss();
                    try {
                        JSONObject reader = new JSONObject(responseStr);
                        if (reader.getString("status").equals("true")) {
                            //ResetData();
                            Intent i = new Intent(Home.this, pdf_preview.class);
                            i.putExtra("patient_id", "" + Final_Patient.getPatientDetails().getPatientId());
                            i.putExtra("patientjson", patientjson);
                            // startActivity(i);

                            FetchPatients();

                        } else {
                            Toast.makeText(Home.this, "" + responseStr, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(Home.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
        }

        //Upload as Completed
        else {
            int tempcount = 0;

            if (Final_Medicines != null) {
                if (Final_Medicines.size() > 0) {
                    Final_Patient.setFinal_Medicines(Final_Medicines);
                }
                else{
                    tempcount++;
                    Speak("Medicines cannot be zero in a prescription!", VR_MEDICINE_NULL_DATA, 3000);
                    //Toast.makeText(this, "Medicines cannot be zero in a prescription !", Toast.LENGTH_SHORT).show();
                }
                String text = "";
                for (int i = 0; i < Final_Medicines.size(); i++) {
                    if (!CheckMedicineNullData(Final_Medicines.get(i))) {
                        text += Final_Medicines.get(i).getName() + " ";
                    }
                }
                if (!text.equals("")) {
                    tempcount++;
                    Speak("Please provide complete details for " + text, VR_MEDICINE_NULL_DATA, 3000);
                }

            }

            if (!f_date.equals("") && (f_time[0] != -1)) {
                Final_Patient.setF_date(f_date);
                Final_Patient.setF_time(f_time);
            } else if (f_date.equals("") && (f_time[0] == -1)) {
                Speak("Please provide followup details", VR_MEDICINE_NULL_DATA, 3000);
            } else if (f_date.equals("") || (f_time[0] == -1)) {
                tempcount++;
                Speak("Please provide complete details for followup ", VR_MEDICINE_NULL_DATA, 3000);
            }

            final String patientjson = new Gson().toJson(Final_Patient);

            //if no null data than upload the rx
            if (tempcount == 0) {

                progress_dialog.show();
                params.put("action", "insertPrescription");


                params.put("patient_id", Final_Patient.getPatientDetails().getPatientId() + "");
                params.put("rx_json", patientjson);
                params.put("patient_type", patient_type);
                NetworkCall ncall = new NetworkCall();
                ncall.setServerUrlWebserviceApi(VR_APIS);

                ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
                    @Override
                    public boolean setResponse(String responseStr) {
                        progress_dialog.dismiss();
                        try {
                            JSONObject reader = new JSONObject(responseStr);
                            if (reader.getString("status").equals("true")) {
                                //ResetData();
                                Intent i = new Intent(Home.this, pdf_preview.class);
                                i.putExtra("patient_id", "" + Final_Patient.getPatientDetails().getPatientId());
                                i.putExtra("patientjson", patientjson);
                                /*Final_PatientChecked.remove(Final_Patient);
                                Final_PatientDraft.add(Final_Patient);
                                if(flag_for_patients.equals(checkedin))
                                    patientsadapter = new PatientsAdapter(Home.this,Final_PatientChecked);
                                else
                                    patientsadapter = new PatientsAdapter(Home.this,Final_PatientDraft);

                                rv_main_drawer.setAdapter(patientsadapter);
                                rv_main_drawer.setHasFixedSize(true);*/
                                transitions.SlideUpDown(rl_home_main, ll_main_patient_rx, true, 0);
                                SelectedPatient = null;
                                FetchPatients();
                                startActivity(i);
                            } else {
                                Toast.makeText(Home.this, "" + responseStr, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(Home.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });

            }


        }


    }


    //Other Methods

    private int[] GetTime(String[] finalarr) {

        int[] t = {-1, -1, -1};

        String time = "";
        for (int i = 0; i < finalarr.length; i++) {
            if (finalarr[i].equals("at")) {
                if (i < finalarr.length - 1) {
                    time = finalarr[i + 1];
                    StringTokenizer st = new StringTokenizer(time, ":");
                    if (st.countTokens() > 0) {
                        try {
                            t[0] = Integer.parseInt(st.nextToken());
                            t[1] = Integer.parseInt(st.nextToken());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (finalarr[i].equals("a.m.")) {
                t[2] = 0;
            }

            if (finalarr[i].equals("p.m.")) {
                t[2] = 1;
            }
        }

        return t;
    }

    private String GetDate(String[] finalarr) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String App_Date = "";
        int final_day = -1;
        int final_mon = -1;
        int final_year = -1;
        int current_mon = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int current_year = Calendar.getInstance().get(Calendar.YEAR);

        String rawdata = "";
        int on_pos = -1;
        int after_pos = -1;
        for (int i = 0; i < finalarr.length; i++) {
            rawdata += finalarr[i] + " ";
            if (finalarr[i].equals("on")) {
                on_pos = i;
            }
            if (finalarr[i].equals("after")) {
                after_pos = i;
            }
        }

        //Today Tomorrow
        if (after_pos == -1 && on_pos == -1) {

            for (int j = 0; j < StaticData.DateFilters.length; j++) {
                if (rawdata.contains(StaticData.DateFilters[j])) {


                    switch (StaticData.DateFilters[j]) {
                        case "today":

                            App_Date = dateFormat.format(today);
                            return App_Date;

                        case "tomorrow":


                            calendar.add(Calendar.DAY_OF_YEAR, 1);
                            Date tomorrow = calendar.getTime();

                            App_Date = dateFormat.format(tomorrow);
                            return App_Date;
                    }

                }
            }

        }
        //on 21/21st January; after (1 month)/(5 days)/(3 weeks)
        else {
            //on
            if (on_pos != -1) {
                if ((on_pos + 1) < finalarr.length) {
                    String day = finalarr[on_pos + 1];
                    if (day.contains("st") || day.contains("th") || day.contains("rd") || day.contains("nd")) {

                        day = day.replace("st", "");
                        day = day.replace("rd", "");
                        day = day.replace("th", "");
                        day = day.replace("nd", "");

                    }

                    try {

                        List<String> months = StaticData.months();
                        final_day = Integer.parseInt(day);
                        int temp = months.indexOf(finalarr[on_pos + 2]) + 1;
                        if (temp > 0 && temp <= 12) {
                            final_mon = temp;
                            if (final_mon < current_mon) {
                                final_year = current_year + 1;
                            } else
                                final_year = current_year;
                        }

                        App_Date = final_day + "-" + final_mon + "-" + final_year;
                        return App_Date;


                    } catch (Exception e) {
                    }

                }

            }
            //after
            else {
                String value = finalarr[after_pos + 1];
                String specifier = finalarr[after_pos + 2];

                try {
                    int value1 = -1;
                    if (value.equals("a")) {
                        value1 = 1;
                    } else {
                        value1 = Integer.parseInt(value);
                    }
                    if (value1 != -1) {
                        switch (specifier) {

                            case "day":
                            case "days": {
                                calendar.add(Calendar.DAY_OF_YEAR, value1);
                                Date tomorrow = calendar.getTime();

                                App_Date = dateFormat.format(tomorrow);
                                return App_Date;
                            }

                            case "week":
                            case "weeks": {
                                calendar.add(Calendar.WEEK_OF_MONTH, value1);
                                Date tomorrow = calendar.getTime();
                                App_Date = dateFormat.format(tomorrow);
                                return App_Date;
                            }

                            case "month":
                            case "months": {
                                calendar.add(Calendar.MONTH, value1);
                                Date tomorrow = calendar.getTime();
                                App_Date = dateFormat.format(tomorrow);
                                return App_Date;
                            }

                        }
                    }


                } catch (Exception e) {
                }
            }
        }


        return App_Date;
    }

    @Override
    public void OnPatientClick(int position, int identifier) {
        if (identifier == StaticData.Adapter_identifier.patients) {

            switch (flag_for_patients) {
                case checkedin:
                    stopRecording();
                    if (SelectedPatient != null) {
                        flag_for_previous_patient = checkedin;
                        previous_patient_position = SelectedPatientPosition;
                        GeneratePrescription(draft);
                    }
                    SelectedPatientPosition = position;
                    SelectedPatient = Final_PatientChecked.get(SelectedPatientPosition);
                    FetchVitals();

                    break;
                case allpatients:
                    break;
                case draft:
                    stopRecording();
                    if (SelectedPatient != null) {
                        flag_for_previous_patient = draft;
                        previous_patient_position = SelectedPatientPosition;
                        GeneratePrescription(draft);

                    }

                    SelectedPatientPosition = position;
                    SelectedPatient = Final_PatientDraft.get(SelectedPatientPosition);
                    ResumePatient();
                    break;
            }

            fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            fileName += "/"+SelectedPatient.getPatientDetails().getPatientId()+"_";
            fileName += new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
            fileName += ".3gp";
            Toast.makeText(this, ""+fileName, Toast.LENGTH_SHORT).show();
            startRecording(fileName);


            transitions.SlideUpDown(rl_home_main, ll_patient_drawer, true, 4);
            transitions.SlideUpDown(rl_home_main, ll_main_patient_rx, false, 0);
            //ll_main_patient_rx.setVisibility(View.VISIBLE);
            //Toast.makeText(this, SelectedPatient.getFname()+" Selected", Toast.LENGTH_SHORT).show();

            SetPatientDetails();

        } else if (identifier == StaticData.Adapter_identifier.symptoms) {


            if (symptom_update_flag == 1) {
                Final_Symptoms.get(update_advice_position).setIssues(issuesmatched.get(position).getIssues());
                View itemview = flow_symptoms.getChildAt(update_advice_position);
                TextView temp = itemview.findViewById(R.id.tv_chip_data);
                temp.setText(issuesmatched.get(position).getIssues());
                ll__uni_prompt.setVisibility(View.GONE);
                ll_symptom_dialog.setVisibility(View.GONE);
                symptom_update_flag = 0;
            } else
                AddSymptom(issuesmatched.get(position).getIssues());

        } else if (identifier == StaticData.Adapter_identifier.diagnosis) {

            if (diagnosis_update_flag == 1) {
                Final_Diagnosis.get(update_advice_position).setDiagnosis(diagnosismatched.get(position).getDiagnosis());
                View itemview = flow_diagnosis.getChildAt(update_advice_position);
                TextView temp = itemview.findViewById(R.id.tv_chip_data);
                temp.setText(diagnosismatched.get(position).getDiagnosis());
                ll__uni_prompt.setVisibility(View.GONE);
                ll_symptom_dialog.setVisibility(View.GONE);
                diagnosis_update_flag = 0;
            } else
                AddDiagnosis(diagnosismatched.get(position).getDiagnosis());
        } else if (identifier == StaticData.Adapter_identifier.diagnostic) {

            if (test_update_flag == 1) {
                Final_DiagnosticTests.get(update_advice_position).setTest_name(diagnostictestsmatched.get(position).getTest_name());
                View itemview = flow_diagnostic.getChildAt(update_advice_position);
                TextView temp = itemview.findViewById(R.id.tv_chip_data);
                temp.setText(diagnostictestsmatched.get(position).getTest_name());
                ll__uni_prompt.setVisibility(View.GONE);
                ll_symptom_dialog.setVisibility(View.GONE);
                test_update_flag = 0;
            } else
                AddDiagnosticTest(diagnostictestsmatched.get(position).getTest_name());

        } else if (identifier == StaticData.Adapter_identifier.medicine_add) {
            AddMedicine1(MedicineList.get(position), multiplemedobj);
        } else if (identifier == StaticData.Adapter_identifier.medicine_delete) {
            DeleteMedicine(position);
        } else if (identifier == StaticData.Adapter_identifier.advice) {
            DeleteAdvice(position);
        } else if (identifier == StaticData.Adapter_identifier.advice_add) {
            AddAdvice(AdviceList.get(position));
        }


    }

    private void ResetData() {

        //Final Variables
        Final_Advice = new ArrayList<>();
        Final_Medicines = new ArrayList<>();
        Final_Diagnosis = new ArrayList<>();
        Final_DiagnosticTests = new ArrayList<>();
        Final_Symptoms = new ArrayList<>();
        Final_Vitals = new Vitals.Data();
        f_time = new int[3];
        f_date = "";

        //Rx Data
        tv_followup_datetime.setText("No Follow Up Added.");
        flow_diagnostic.removeAllViews();
        flow_diagnosis.removeAllViews();
        flow_symptoms.removeAllViews();
        SetAdviceRecyclerView();
        setMedicineRecyclerView();

        //vitals
        tv_bmi.setText("");
        tv_height.setText("");
        tv_weight.setText("");
        tv_hc.setText("");
        tv_temperature.setText("");


    }

    private void ResumePatient() {

//        tv_height.setText(Final_Vitals.getHeight()+" CM");
//        tv_weight.setText(Final_Vitals.getWeight()+" KG");

        Final_Symptoms = SelectedPatient.getFinal_Symptoms();
        Final_Diagnosis = SelectedPatient.getFinal_Diagnosis();
        Final_DiagnosticTests = SelectedPatient.getFinal_DiagnosticTests();
        Final_Medicines = SelectedPatient.getFinal_Medicines();
        Final_Vitals = SelectedPatient.getVitals();
        Final_Advice = SelectedPatient.getFinal_Advice();
        f_date = SelectedPatient.getF_date();
        f_time = SelectedPatient.getF_time();


        if (Final_Symptoms != null) {

            if (Final_Symptoms.size() > 0) {
                for (int i = 0; i < Final_Symptoms.size(); i++) {
                    final ViewGroup parent = (ViewGroup) rl_home_main;
                    final View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.dda_list_layout, parent, false);
                    TextView tempdata = itemView.findViewById(R.id.tv_chip_data);
                    LinearLayout ll_chip_delete = itemView.findViewById(R.id.ll_chip_delete);
                    //   itemView.setId(Final_Symptoms.size());
                    ll_chip_delete.setClickable(true);
                    //final int temp_pos = Final_Symptoms.size();

                    final Issues.Data dtemp = new Issues.Data(Final_Symptoms.get(i).getIssues());
                    final TextView number = itemView.findViewById(R.id.tv_chip_number);
                    number.setText((i + 1) + ".");

                    ll_chip_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int temp_pos = itemView.getId();
                            Final_Symptoms.remove(dtemp);
                            flow_symptoms.removeView(itemView);
                            //DeleteSymptom(temp_pos);

                            for (int j = 0; j < flow_symptoms.getChildCount(); j++) {
                                View view1 = flow_symptoms.getChildAt(j);
                                //if (view1.getId()>temp_pos) {
                                final TextView number = view1.findViewById(R.id.tv_chip_number);
                                number.setText((j + 1) + ".");
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
            }
        }

        if (Final_Diagnosis != null) {

            if (Final_Diagnosis.size() > 0) {
                for (int i = 0; i < Final_Diagnosis.size(); i++) {
                    final ViewGroup parent = (ViewGroup) rl_home_main;
                    final View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.dda_list_layout, parent, false);
                    TextView tempdata = itemView.findViewById(R.id.tv_chip_data);
                    LinearLayout ll_chip_delete = itemView.findViewById(R.id.ll_chip_delete);
                    // itemView.setId(Final_Diagnosis.size());
                    ll_chip_delete.setClickable(true);
                    //final int temp_pos = Final_Symptoms.size();

                    final Diagnosis.Data dtemp = new Diagnosis.Data(Final_Diagnosis.get(i).getDiagnosis());
                    final TextView number = itemView.findViewById(R.id.tv_chip_number);
                    number.setText((i + 1) + ".");
                    // Final_Diagnosis.add(dtemp);

                    ll_chip_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int temp_pos = itemView.getId();
                            Final_Diagnosis.remove(dtemp);
                            flow_diagnosis.removeView(itemView);
                            //DeleteSymptom(temp_pos);

                            for (int j = 0; j < flow_diagnosis.getChildCount(); j++) {
                                View view1 = flow_diagnosis.getChildAt(j);
                                //if (view1.getId()>temp_pos) {
                                final TextView number = view1.findViewById(R.id.tv_chip_number);
                                number.setText((j + 1) + ".");
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
            }
        }

        if (Final_DiagnosticTests != null) {

            if (Final_DiagnosticTests.size() > 0) {

                for (int i = 0; i < Final_DiagnosticTests.size(); i++) {
                    final ViewGroup parent = (ViewGroup) rl_home_main;
                    final View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.dda_list_layout, parent, false);
                    TextView tempdata = itemView.findViewById(R.id.tv_chip_data);
                    LinearLayout ll_chip_delete = itemView.findViewById(R.id.ll_chip_delete);
                    // itemView.setId(Final_DiagnosticTests.size());
                    ll_chip_delete.setClickable(true);
                    //final int temp_pos = Final_Symptoms.size();

                    final DiagnosticTests.Data dtemp = new DiagnosticTests.Data(Final_DiagnosticTests.get(i).getTest_name());
                    final TextView number = itemView.findViewById(R.id.tv_chip_number);
                    number.setText((i + 1) + ".");
                    //  Final_DiagnosticTests.add(dtemp);

                    ll_chip_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int temp_pos = itemView.getId();
                            Final_DiagnosticTests.remove(dtemp);
                            flow_diagnostic.removeView(itemView);
                            //DeleteSymptom(temp_pos);

                            for (int j = 0; j < flow_diagnostic.getChildCount(); j++) {
                                View view1 = flow_diagnostic.getChildAt(j);
                                //if (view1.getId()>temp_pos) {
                                final TextView number = view1.findViewById(R.id.tv_chip_number);
                                number.setText((j + 1) + ".");
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


            }
        }

        if (Final_Medicines != null) {

            if (Final_Medicines.size() > 0) {
                setMedicineRecyclerView();
            }
        }

        if (Final_Vitals != null) {

            tv_bmi.setText("" + Final_Vitals.getBMI());
            tv_hc.setText("" + Final_Vitals.getHead()+" CM");
            tv_height.setText("" + Final_Vitals.getHeight()+" CM");
            tv_weight.setText("" + Final_Vitals.getWeight()+" KG");
            tv_temperature.setText("" + Final_Vitals.getTemperature()+" °");
        }

        if (Final_Advice != null) {

            if (Final_Advice.size() > 0) {
                SetAdviceRecyclerView();
            }
        }

        if (!(SelectedPatient.getF_date().equals("")) && SelectedPatient.getF_time()[0] != -1) {

            String ampm = "";
            if (f_time[2] == 0)
                ampm = "A.M.";
            else
                ampm = "P.M.";
            String time_1 = "";
            if (f_time[1] >= 0 && f_time[1] < 10)
                time_1 = "Time : " + f_time[0] + ":0" + f_time[1] + " " + ampm;
            else
                time_1 = "Time : " + f_time[0] + ":" + f_time[1] + " " + ampm;

            String date_1 = "";
            date_1 = "Date : " + f_date;

            tv_followup_datetime.setText(date_1 + " | " + time_1);

        } else {
            tv_followup_datetime.setText("No Follow Up Added.");
        }

    }

    private Boolean CheckMedicineNullData(Medicine obj) {

        double i[] = obj.getDailytimings();
        if (obj.getFrequency() == null || obj.getDuration() == null || obj.getAfbf() == null || (i[0] == 0 && i[1] == 0 && i[2] == 0)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }


    @Override
    public void onBackPressed() {
        if (ll_main_patient_rx.getVisibility() == View.VISIBLE) {
            transitions.SlideUpDown(rl_home_main, ll_main_patient_rx, true, 0);
            stopRecording();
            //ll_main_patient_rx.setVisibility(View.GONE);
        } else
            super.onBackPressed();
    }


    public class fetchpatients extends AsyncTask{

        Dialog progress_dialog1;
        @Override
        protected void onPreExecute() {


            progress_dialog1 = new Dialog(Home.this);
            progress_dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progress_dialog1.setContentView(R.layout.progress_dialog_layout);
            progress_dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progress_dialog1.setCancelable(false);
            progress_dialog1.show();
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            FetchPatients();
            //fetchdatamap();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            progress_dialog1.dismiss();
            super.onPostExecute(o);
        }
    }



    private void processmapeddata() {
        vitals_list = new String[4];

        for (int i = 0 ;i < vitals_map.size();i++)
        {

            switch (vitals_map.get(i).getMap().toString()){

                case "height":
                    if(vitals_list[0]==null)
                    {
                        vitals_list[0] = vitals_map.get(i).getData()+" ";
                    }
                    else {
                        vitals_list[0] += vitals_map.get(i).getData() + " ";
                    }
                    break;
                case "weight":
                    if(vitals_list[1]==null)
                    {
                        vitals_list[1] = vitals_map.get(i).getData()+" ";
                    }
                    else {
                        vitals_list[1] += vitals_map.get(i).getData() + " ";
                    }
                    break;
                case "head":
                    if(vitals_list[2]==null)
                    {
                        vitals_list[2] = vitals_map.get(i).getData()+" ";
                    }
                    else {
                        vitals_list[2] += vitals_map.get(i).getData()+" ";
                    }

                    break;
                case "temperature":
                    if(vitals_list[3]==null)
                    {
                        vitals_list[3] = vitals_map.get(i).getData()+" ";
                    }
                    else {
                        vitals_list[3] += vitals_map.get(i).getData()+" ";
                    }
                    break;

            }

        }



    }


    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Home.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        //Toast.makeText(Home.this, "Permission Granted",Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(Home.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }


    public void startRecording(String fileName){
        if(!b_isRecording) {


            if (checkPermission()) {

                myAudioRecorder = new MediaRecorder();

                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecorder.setOutputFile(fileName);


                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                    b_isRecording = true;
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                //btn_start.setEnabled(false);
                //btn_stop.setEnabled(true);

                Toast.makeText(Home.this, "Recording started", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }


    public void stopRecording(){

        //myAudioRecorder.

        if(b_isRecording) {

            fileName = "";
            myAudioRecorder.stop();
            b_isRecording = false;
            Toast.makeText(Home.this, "Recording Completed",
                    Toast.LENGTH_LONG).show();
        }
    }

}


