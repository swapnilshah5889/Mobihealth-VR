package com.example.mobihealthapis.Activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobihealthapis.Adapters.TestAdapter;
import com.example.mobihealthapis.R;

public class Main2Activity extends AppCompatActivity {

    LinearLayout ll_main_activity2;
    RecyclerView rv_main;
    Boolean isVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        InitObjects();
    }

    private void InitObjects() {

        ll_main_activity2 = findViewById(R.id.ll_main_activity2);
        rv_main = findViewById(R.id.rv_main);




        SetRecyclerView();
    }

    private void SetRecyclerView() {

        TestAdapter adapter = new TestAdapter(this,null,ll_main_activity2);
        rv_main.setAdapter(adapter);
        rv_main.setHasFixedSize(true);

    }
}
