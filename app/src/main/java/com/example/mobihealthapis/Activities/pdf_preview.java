package com.example.mobihealthapis.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobihealthapis.Adapters.MedicinePDFAdapter;
import com.example.mobihealthapis.Models.Advice;
import com.example.mobihealthapis.Models.Diagnosis;
import com.example.mobihealthapis.Models.DiagnosticTests;
import com.example.mobihealthapis.Models.Issues;
import com.example.mobihealthapis.Models.Medicine;
import com.example.mobihealthapis.Models.PatientFinal;
import com.example.mobihealthapis.Models.Vitals;
import com.example.mobihealthapis.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.nex3z.flowlayout.FlowLayout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;

import static com.example.mobihealthapis.GeneralFunctions.StaticData.PREF_PATIENT;

public class pdf_preview extends AppCompatActivity {

    CoordinatorLayout ll_main_pdf;
    LinearLayout ll_symptoms_pdf, ll_diagnosis_pdf, ll_advice_pdf, ll_test_pdf, ll_followup_pdf;
    NestedScrollView rl_generate_pdf;
    ImageView bmImage;
    FloatingActionButton fab_print;
    RecyclerView rv_medicines;
    FlowLayout flow_vitals;
    Intent getintent;
    String patient_id = "";
    PatientFinal Final_Patient;
    TextView tv_symptoms_pdf, tv_diagnosis_pdf, tv_advice_pdf, tv_test_pdf, tv_followup_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_preview);

        getintent = getIntent();
        if (getintent.hasExtra("patient_id")) {
            patient_id = getintent.getStringExtra("patient_id");
            //SharedPreferences prefs = getSharedPreferences(PREF_PATIENT, MODE_PRIVATE);

            String ngo_json = getintent.getStringExtra("patientjson");

            if (ngo_json != null) {

                Gson gson = new Gson();
                Final_Patient = gson.fromJson(ngo_json, PatientFinal.class);
                onInitializeObjects();

            } else {
                Toast.makeText(this, "Json null", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "patient id not found", Toast.LENGTH_SHORT).show();
        }







        /*rl_generate_pdf.setDrawingCacheEnabled(true);
        rl_generate_pdf.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        rl_generate_pdf.layout(0, 0, rl_generate_pdf.getMeasuredWidth(), rl_generate_pdf.getMeasuredHeight());

        rl_generate_pdf.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(rl_generate_pdf.getDrawingCache());
        rl_generate_pdf.setDrawingCacheEnabled(false); // clear drawing cache

        //bmImage = findViewById(R.id.bmImage);
        bmImage = new ImageView(this);
        bmImage.setImageBitmap(b);*/


    }


    private void onInitializeObjects() {
        flow_vitals = findViewById(R.id.flow_vitals);
        rl_generate_pdf = findViewById(R.id.ll_main_pdfp);
        fab_print = findViewById(R.id.fab_print);
        rv_medicines = findViewById(R.id.rv_medicines);
        ll_main_pdf = findViewById(R.id.ll_main_pdf);
        tv_symptoms_pdf = findViewById(R.id.tv_symptoms_pdf);
        ll_symptoms_pdf = findViewById(R.id.ll_symptoms_pdf);
        tv_diagnosis_pdf = findViewById(R.id.tv_diagnosis_pdf);
        ll_diagnosis_pdf = findViewById(R.id.ll_diagnosis_pdf);
        tv_advice_pdf = findViewById(R.id.tv_advice_pdf);
        ll_advice_pdf = findViewById(R.id.ll_advice_pdf);
        ll_test_pdf = findViewById(R.id.ll_test_pdf);
        tv_test_pdf = findViewById(R.id.tv_test_pdf);
        tv_followup_pdf = findViewById(R.id.tv_followup_pdf);
        ll_followup_pdf = findViewById(R.id.ll_followup_pdf);

        SetMedicine();
        SetVitals();
        SetSymptoms();
        SetDiagnosis();
        SetAdvice();
        SetDiagnosticTest();
        SetFollowup();

        fab_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GeneratePDF();
                printPDF(rl_generate_pdf);
            }
        });


    }


    //Set Data
    private void SetMedicine() {
        if (Final_Patient.getFinal_Medicines() != null) {
            MedicinePDFAdapter adapter = new MedicinePDFAdapter(this, Final_Patient.getFinal_Medicines());
            rv_medicines.setAdapter(adapter);
            rv_medicines.setHasFixedSize(true);

        }


    }

    private void SetVitals() {
        if (Final_Patient.getVitals() != null) {
            Vitals.Data myvitals = Final_Patient.getVitals();

            final ViewGroup parent = (ViewGroup) ll_main_pdf;

            //height
            final View heightview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vitalschiplayout, parent, false);
            TextView height = heightview.findViewById(R.id.tv_chip_value);

            final TextView number = heightview.findViewById(R.id.tv_chip_vital);
            number.setText("Height :");
            height.setText(myvitals.getHeight() + " CM ");
            flow_vitals.addView(heightview);

            //weight
            final View weightview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vitalschiplayout, parent, false);
            TextView weight = weightview.findViewById(R.id.tv_chip_value);

            final TextView number1 = weightview.findViewById(R.id.tv_chip_vital);
            number1.setText("Weight :");
            weight.setText(myvitals.getWeight() + " KG ");
            flow_vitals.addView(weightview);

            //head circumference
            final View headview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vitalschiplayout, parent, false);
            TextView headcircumference = headview.findViewById(R.id.tv_chip_value);

            final TextView number3 = headview.findViewById(R.id.tv_chip_vital);
            number3.setText("H.C. :");
            headcircumference.setText(myvitals.getHead() + " CM ");
            flow_vitals.addView(headview);

            //temperature
            final View temperatureview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vitalschiplayout, parent, false);
            TextView temperature = temperatureview.findViewById(R.id.tv_chip_value);

            final TextView number4 = temperatureview.findViewById(R.id.tv_chip_vital);
            number4.setText("Temperature :");
            temperature.setText(myvitals.getTemperature() + " °");
            flow_vitals.addView(temperatureview);


            //BMI
            final View bmiview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vitalschiplayout, parent, false);
            TextView bmi = bmiview.findViewById(R.id.tv_chip_value);

            final TextView number5 = bmiview.findViewById(R.id.tv_chip_vital);
            number5.setText("BMI :");
            bmi.setText(myvitals.getBMI() + "");
            flow_vitals.addView(bmiview);


        }

    }

    private void SetSymptoms() {
        if (Final_Patient.getFinal_Symptoms() != null) {
            ll_symptoms_pdf.setVisibility(View.VISIBLE);

            List<Issues.Data> symptoms_list = Final_Patient.getFinal_Symptoms();

            for (int i = 0; i < symptoms_list.size(); i++) {
                if ((i + 1) == symptoms_list.size())
                    tv_symptoms_pdf.setText(tv_symptoms_pdf.getText() + " " + symptoms_list.get(i).getIssues());
                else
                    tv_symptoms_pdf.setText(tv_symptoms_pdf.getText() + " " + symptoms_list.get(i).getIssues() + ", ");
            }
        } else {
            ll_symptoms_pdf.setVisibility(View.GONE);
        }
    }

    private void SetDiagnosis() {
        if (Final_Patient.getFinal_Diagnosis() != null) {
            ll_diagnosis_pdf.setVisibility(View.VISIBLE);

            List<Diagnosis.Data> diagnosislist = Final_Patient.getFinal_Diagnosis();

            for (int i = 0; i < diagnosislist.size(); i++) {
                if ((i + 1) == diagnosislist.size())
                    tv_diagnosis_pdf.setText(tv_diagnosis_pdf.getText() + " " + diagnosislist.get(i).getDiagnosis());
                else
                    tv_diagnosis_pdf.setText(tv_diagnosis_pdf.getText() + " " + diagnosislist.get(i).getDiagnosis() + ", ");
            }
        } else {
            ll_diagnosis_pdf.setVisibility(View.GONE);
        }

    }

    private void SetAdvice() {
        if (Final_Patient.getFinal_Advice() != null) {
            ll_advice_pdf.setVisibility(View.VISIBLE);

            List<Advice> advicelist = Final_Patient.getFinal_Advice();

            for (int i = 0; i < advicelist.size(); i++) {
                if ((i + 1) == advicelist.size())
                    tv_advice_pdf.setText(tv_advice_pdf.getText() + " " + advicelist.get(i).getAdvice_data());
                else
                    tv_advice_pdf.setText(tv_advice_pdf.getText() + " " + advicelist.get(i).getAdvice_data() + ". ");
            }
        } else {
            ll_advice_pdf.setVisibility(View.GONE);
        }
    }

    private void SetDiagnosticTest() {
        if (Final_Patient.getFinal_DiagnosticTests() != null) {
            ll_test_pdf.setVisibility(View.VISIBLE);

            List<DiagnosticTests.Data> diagnostictestlist = Final_Patient.getFinal_DiagnosticTests();

            for (int i = 0; i < diagnostictestlist.size(); i++) {
                if ((i + 1) == diagnostictestlist.size())
                    tv_test_pdf.setText(tv_test_pdf.getText() + " " + diagnostictestlist.get(i).getTest_name());
                else
                    tv_test_pdf.setText(tv_test_pdf.getText() + " " + diagnostictestlist.get(i).getTest_name() + ", ");
            }
        } else {
            ll_test_pdf.setVisibility(View.GONE);
        }

    }

    private void SetFollowup() {


        String mydate = Final_Patient.getF_date();
        int[] mytime = Final_Patient.getF_time();


        if (!mydate.equals("") && mytime[0] != -1) {
            ll_followup_pdf.setVisibility(View.VISIBLE);
            String ampm = "";
            if (mytime[2] == 0)
                ampm = "A.M.";
            else
                ampm = "P.M.";
            String time_1 = "";
            if (mytime[1] >= 0 && mytime[1] < 10)
                time_1 = mytime[0] + ":0" + mytime[1] + " " + ampm;
            else
                time_1 = mytime[0] + ":" + mytime[1] + " " + ampm;


            tv_followup_pdf.setText(mydate + " " + time_1);

        } else {
            ll_followup_pdf.setVisibility(View.GONE);
        }


    }



    //PDF Methods
    public void printPDF(View view) {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        printManager.print("print_any_view_job_name", new ViewPrintAdapter(this,
                findViewById(R.id.ll_main_pdfp)), null);
    }

    private void GeneratePDF() {


        PrintHelper printHelper = new PrintHelper(this);

        // Set the desired scale mode.
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        printHelper.setOrientation(PrintHelper.ORIENTATION_PORTRAIT);

        // Get the bitmap for the ImageView's drawable.

        //ImageView mImageView = (ImageView) rl_generate_pdf;

        Bitmap bitmap = ((BitmapDrawable) bmImage.getDrawable()).getBitmap();

        // Print the bitmap.
        printHelper.printBitmap("Print Bitmap", bitmap);

    }

    public class ViewPrintAdapter extends PrintDocumentAdapter {

        private PrintedPdfDocument mDocument;
        private Context mContext;
        private View mView;

        public ViewPrintAdapter(Context context, View view) {
            mContext = context;
            mView = view;
        }

        @Override
        public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal,
                             LayoutResultCallback callback, Bundle extras) {

            mDocument = new PrintedPdfDocument(mContext, newAttributes);

            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }

            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder("prescription.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(1);

            PrintDocumentInfo info = builder.build();
            callback.onLayoutFinished(info, true);
        }

        @Override
        public void onWrite(PageRange[] pages, ParcelFileDescriptor destination,
                            CancellationSignal cancellationSignal,
                            WriteResultCallback callback) {

            //A4
            //PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
            // Start the page
            PdfDocument.Page page = mDocument.startPage(1);
            // Create a bitmap and put it a canvas for the view to draw to. Make it the size of the view
            Bitmap bitmap = Bitmap.createBitmap(mView.getWidth(), mView.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            mView.draw(canvas);
            // create a Rect with the view's dimensions.
            Rect src = new Rect(0, 0, mView.getWidth(), mView.getHeight());
            // get the page canvas and measure it.
            Canvas pageCanvas = page.getCanvas();
            float pageWidth = pageCanvas.getWidth();
            float pageHeight = pageCanvas.getHeight();
            // how can we fit the Rect src onto this page while maintaining aspect ratio?
            float scale = Math.min(pageWidth / src.width(), pageHeight / src.height());
            float left = pageWidth / 2 - src.width() * scale / 2;
            float top = pageHeight / 2 - src.height() * scale / 2;
            float right = pageWidth / 2 + src.width() * scale / 2;
            float bottom = pageHeight / 2 + src.height() * scale / 2;
          /*  float left = 0;
            float top = 0;
            float right = 0;
            float bottom = 0;
          */
            RectF dst = new RectF(left, top, right, bottom);


            pageCanvas.drawBitmap(bitmap, src, dst, null);
            mDocument.finishPage(page);

            try {
                mDocument.writeTo(new FileOutputStream(
                        destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                mDocument.close();
                mDocument = null;
            }
            callback.onWriteFinished(new PageRange[]{new PageRange(0, 0)});
        }
    }
}
