package com.example.mobihealthapis.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    List<Vitals.Data> vitalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_main = findViewById(R.id.tv_main);

        //FetchMEDs();

        FetchIssues();

        //FetchVitals();
        
    }

    private void FetchIssues() {



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
