package com.example.mobihealthapis.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobihealthapis.Models.Diagnosis;
import com.example.mobihealthapis.Models.DiagnosticTests;
import com.example.mobihealthapis.Models.Issues;
import com.example.mobihealthapis.Models.Med;
import com.example.mobihealthapis.Models.Vitals;
import com.example.mobihealthapis.R;
import com.example.mobihealthapis.database_call.NetworkCall;
import com.google.gson.Gson;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.mobihealthapis.database_call.utils_string.API_URL.SEARCH_MED;
import static com.example.mobihealthapis.database_call.utils_string.API_URL.VR_APIS;

public class MainActivity extends AppCompatActivity {

    TextView tv_main;
    List<Med.Data> medicines;
    List<Diagnosis.Data> DiagnosisList;
    List<Vitals.Data> vitalList;
    List<Issues.Data> Issuelist;
    List<DiagnosticTests.Data> DiagnosticTestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_main = findViewById(R.id.tv_main);

        //FetchMEDs();
            FetchDiagnosis();
       // FetchIssues();
        // FetchDiagnosticList();
        //FetchVitals();

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
                        DiagnosisList = new ArrayList<>();
                        DiagnosisList.addAll(obj.getData());

                        tv_main.setText(obj.getTotal_records()+"|"+DiagnosisList.size());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Catch : "+e.getMessage(), Toast.LENGTH_LONG).show();

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
                        DiagnosticTestList = new ArrayList<>();
                        DiagnosticTestList.addAll(obj.getData());

                        tv_main.setText(obj.getTotal_records()+"|"+DiagnosticTestList.size());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Catch : "+e.getMessage(), Toast.LENGTH_LONG).show();

                }

                return false;
            }
        });


    }

    private void FetchIssues() {
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
                        Issuelist = new ArrayList<>();
                        Issuelist.addAll(obj.getData());

                        tv_main.setText(obj.getTotal_records()+"|"+Issuelist.size());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Catch : "+e.getMessage(), Toast.LENGTH_LONG).show();

                }

                return false;
            }
        });

    }

    private void FetchVitals() {

        HashMap<String,String> params = new HashMap<>();

        params.put("action","getVitals");
        params.put("patient_id","23001");

        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(VR_APIS);


        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {


                try {
                    //tv_main.setText(responseStr);
                    Vitals obj = new Gson().fromJson(responseStr, Vitals.class);

                    if(obj.isStatus()){
                        vitalList = new ArrayList<>();
                        vitalList.addAll(obj.getData());

                        //tv_main.setText(obj.getTotal_records()+"|"+vitalList.size());
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Catch : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });

    }

    private void FetchMEDs() {

        HashMap<String,String> params = new HashMap<>();

        params.put("search_keyword","dolo");
        params.put("search","yes");
        medicines = new ArrayList<>();
        NetworkCall ncall = new NetworkCall();

        ncall.setServerUrlWebserviceApi(SEARCH_MED);

        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                 try {
                    Med obj2 = new Gson().fromJson(responseStr, Med.class);
                    medicines.addAll(obj2.getData());

                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Catch : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });

    }
}
