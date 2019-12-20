package com.example.mobihealthapis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mobihealthapis.Adapters.PatientsAdapter;
import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;
import com.skyfishjy.library.RippleBackground;

public class Home extends AppCompatActivity {

    RecyclerView rv_main_drawer;
    LinearLayout ll_activate_voice;
    RipplePulseLayout mRipplePulseLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        InitializeObjects();

    }

    private void InitializeObjects() {

        rv_main_drawer = findViewById(R.id.rv_main_drawer);
        ll_activate_voice = findViewById(R.id.ll_activate_voice);
        //mRipplePulseLayout = findViewById(R.id.layout_ripplepulse);

        ll_activate_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      mRipplePulseLayout.startRippleAnimation();
            }
        });

        SetMainDrawerRecyclerView();
    }

    private void SetMainDrawerRecyclerView() {

        PatientsAdapter adapter = new PatientsAdapter(this);
        rv_main_drawer.setAdapter(adapter);
        rv_main_drawer.setHasFixedSize(true);

    }
}
