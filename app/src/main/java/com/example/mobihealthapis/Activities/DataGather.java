package com.example.mobihealthapis.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.Adapters.DataMapAdapter;
import com.example.mobihealthapis.R;
import com.example.mobihealthapis.database_call.NetworkCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import static com.example.mobihealthapis.GeneralFunctions.StaticData.VOICE_RECOGNITION_REQUEST_CODE;
import static com.example.mobihealthapis.GeneralFunctions.StaticData.Vital_Classes;
import static com.example.mobihealthapis.database_call.utils_string.API_URL.VR_APIS;

public class DataGather extends AppCompatActivity {

    private static final int READ_BLOCK_SIZE = 100;
    ImageView img_speak_pi;
    TextView tv_recognized_text,tv_data_fetched;
    RecyclerView rv_text_map;

    List<String> text_map;
    private String android_id,android_id2;
    Dialog progress_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_gather);

        Init();
    }

    private void Init() {

        android_id = UUID.randomUUID().toString();
        progress_dialog = new Dialog(this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setContentView(R.layout.progress_dialog_layout);
        progress_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress_dialog.setCancelable(false);

        img_speak_pi = findViewById(R.id.img_speak_pi);
        tv_recognized_text = findViewById(R.id.tv_recognized_text);
        rv_text_map = findViewById(R.id.rv_text_map);


        img_speak_pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceRecognitionActivity(VOICE_RECOGNITION_REQUEST_CODE);
            }
        });

        text_map = new ArrayList<>();
        SetRV();
    }

    private void SetRV() {
        DataMapAdapter adapter = new DataMapAdapter(this,text_map);
        rv_text_map.setAdapter(adapter);
        rv_text_map.setHasFixedSize(true);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK){

            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String[] rawDataFromSpeech = GetRawDataFromSpeech(matches);

            tv_recognized_text.setText("Recognized Text : "+rawDataFromSpeech[0]);

            if(rawDataFromSpeech.length>0){
                if(requestCode == VOICE_RECOGNITION_REQUEST_CODE){

                    int count = 0;
                    for(int i = 0; i < Vital_Classes.length; i++){
                        if(!rawDataFromSpeech[0].equals(Vital_Classes[i])){
                            if(!(i==2 && rawDataFromSpeech[0].equals("head")))
                                count++;
                        }
                    }

                    if(count == 4){
                        ShowMappingDialog(rawDataFromSpeech[0]);
                    }
                    else{
                        LogTheData(rawDataFromSpeech[0],rawDataFromSpeech[0]);
                        Toast.makeText(this, "Exact Match : "+rawDataFromSpeech[0], Toast.LENGTH_SHORT).show();
                    }

                }
            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ShowMappingDialog(final String data) {

        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog_mapping_layout);
        d.setCancelable(false);

        TextView matched = d.findViewById(R.id.tv_data_fetched);

        matched.setText(data);

        TextView height = d.findViewById(R.id.tv_height_dialog);
        TextView weight = d.findViewById(R.id.tv_weight_dialog);
        TextView head = d.findViewById(R.id.tv_head_dialog);
        TextView temp = d.findViewById(R.id.tv_temperature_dialog);
        TextView none = d.findViewById(R.id.tv_none_dialog);


        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogTheData(data,Vital_Classes[0]);
                d.dismiss();
            }
        });

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogTheData(data,Vital_Classes[1]);
                d.dismiss();
            }
        });

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogTheData(data,Vital_Classes[2]);
                d.dismiss();
            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogTheData(data,Vital_Classes[3]);
                d.dismiss();
            }
        });

        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();
    }

    private void LogTheData(String data, String vital_class) {

        text_map.add(data+"  ->  "+vital_class);
        SetRV();

//        LogToTextFile();

        NetworkCall ncall = new NetworkCall();
        ncall.setServerUrlWebserviceApi(VR_APIS);

        HashMap<String,String> params = new HashMap<>();
        params.put("action","map_data");
        params.put("data",data);
        params.put("class",vital_class);
        params.put("device_id",android_id);

        android_id2 = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        params.put("device_id2",android_id2);

        //Toast.makeText(this, ""+android_id, Toast.LENGTH_SHORT).show();

        progress_dialog.show();

        ncall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                progress_dialog.dismiss();
                return false;
            }
        });

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
        //startActivity(new Intent("android.intent.action.INFOSCREEN"));
    }


}
