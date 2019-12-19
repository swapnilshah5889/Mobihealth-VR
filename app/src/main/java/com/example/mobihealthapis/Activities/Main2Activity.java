package com.example.mobihealthapis.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mobihealthapis.Adapters.TestAdapter;
import com.example.mobihealthapis.GeneralFunctions.transitions;
import com.example.mobihealthapis.GeneralFunctions.transitions.*;
import com.example.mobihealthapis.Models.Diagnosis;
import com.example.mobihealthapis.Models.DiagnosticTests;
import com.example.mobihealthapis.Models.Issues;
import com.example.mobihealthapis.Models.Med;
import com.example.mobihealthapis.Models.Vitals;
import com.example.mobihealthapis.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.mobihealthapis.GeneralFunctions.transitions.slideDown;
import static com.example.mobihealthapis.GeneralFunctions.transitions.slideUp;

public class Main2Activity extends AppCompatActivity {

    LinearLayout ll_main_activity2;
    RecyclerView rv_main;
    Boolean isVisible = true;

    LinearLayout ll_expand,ll_data;
    ImageView img_expand;

    List<Med.Data> medicines;
    List<Diagnosis.Data> DiagnosisList;
    List<Vitals.Data> vitalList;
    List<Issues.Data> Issuelist;
    List<DiagnosticTests.Data> DiagnosticTestList;
    Boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        InitObjects();
    }

    private void InitObjects() {

        ll_main_activity2 = findViewById(R.id.ll_main_activity2);
        ll_expand = findViewById(R.id.ll_expand);
        ll_data = findViewById(R.id.ll_data);
        img_expand = findViewById(R.id.img_expand);
        //rv_main = findViewById(R.id.rv_main);



        ll_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLayout(isExpanded,ll_data,img_expand);
                isExpanded = !isExpanded;
            }
        });

       // SetRecyclerView();
    }

    private void SetRecyclerView() {

        TestAdapter adapter = new TestAdapter(this,null,ll_main_activity2);
        rv_main.setAdapter(adapter);
        rv_main.setHasFixedSize(true);

    }

    private boolean toggleLayout(boolean isExpanded, LinearLayout layoutExpand,ImageView img_expand) {
        if (isExpanded) {
            transitions.expand(layoutExpand);
            img_expand.setImageResource(R.drawable.ic_minus);
        } else {
            transitions.collapse(layoutExpand);
            img_expand.setImageResource(R.drawable.ic_add_white);
        }
        return isExpanded;

    }
}
