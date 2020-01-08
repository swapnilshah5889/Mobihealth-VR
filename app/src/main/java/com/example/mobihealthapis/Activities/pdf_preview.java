package com.example.mobihealthapis.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.RecyclerView;

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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nex3z.flowlayout.FlowLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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

        rl_generate_pdf.setDrawingCacheEnabled(true);
        rl_generate_pdf.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        rl_generate_pdf.layout(0, 0, rl_generate_pdf.getMeasuredWidth(), rl_generate_pdf.getMeasuredHeight());

        rl_generate_pdf.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(rl_generate_pdf.getDrawingCache());
        rl_generate_pdf.setDrawingCacheEnabled(false); // clear drawing cache

        //bmImage = findViewById(R.id.bmImage);
        bmImage = new ImageView(this);
        bmImage.setImageBitmap(b);

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
//                printPDF(rl_generate_pdf);
                itextPDFGenerate();
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


    public void itextPDFGenerate() {


        try {

            Document document = new Document();


            // Location to save
            String filename = "Patient_Name_";
            filename += new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
            String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + filename + ".pdf";
            File root = new File(filepath);
//            if(root.exists()){
//                root.createNewFile();
//            }
//            String sdcardhtmlpath = root.getPath().toString() + "/pdftest.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filepath));

            // Open to write
            document.open();
            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            document.add(new Paragraph("\n\n\n\n\n"));
            /*Bitmap bitmap = ((BitmapDrawable) bmImage.getDrawable()).getBitmap();
            ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream3);
            Image maimg = Image.getInstance(stream3.toByteArray());
            maimg.setAbsolutePosition(450  , 870);
            maimg.scalePercent(40);
            document.add(maimg);*/
            /*maimg.setAbsolutePosition(0, 0);
            //maimg.scalePercent(40);
            document.add(maimg);*/


            Font Bold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font Normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
            Font BoldTitle = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font NormalText = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);
            Font BoldText = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font Normalsmall = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            //Chunk hello = new Chunk("Hello", Bold);

            //Vitals
            Vitals.Data myvitals = Final_Patient.getVitals();
            Paragraph Vitals = new Paragraph();

            Vitals.add(new Chunk("Height : ", Bold));
            Vitals.add(new Chunk("" + myvitals.getHeight() + " CM", Normal));
            Vitals.add(new Chunk("  Weight : ", Bold));
            Vitals.add(new Chunk("" + myvitals.getWeight() + " KG", Normal));
            Vitals.add(new Chunk("  H.C. : ", Bold));
            Vitals.add(new Chunk("" + myvitals.getHead() + " CM", Normal));
            Vitals.add(new Chunk("  Temperature : ", Bold));
            Vitals.add(new Chunk("" + myvitals.getTemperature() + " °", Normal));
//            double bmi = getBMI(myvitals.getHeight(),myvitals.getWeight());
//            Vitals.add(new Chunk(" BMI : ", Bold));
//            Vitals.add(new Chunk(""+bmi, Normal));
//

            document.add(Vitals);


            //Symptoms
            if (!tv_symptoms_pdf.getText().toString().equals("")) {
                Paragraph symptoms = new Paragraph();
                symptoms.add(new Chunk("Symptoms : ", Bold));
                symptoms.add(new Chunk(tv_symptoms_pdf.getText().toString(), Normal));
                document.add(symptoms);
            }


            //Diagnosis
            if (!tv_diagnosis_pdf.getText().toString().equals("")) {
                Paragraph symptoms = new Paragraph();
                symptoms.add(new Chunk("Diagnosis : ", Bold));
                symptoms.add(new Chunk(tv_diagnosis_pdf.getText().toString(), Normal));
                document.add(symptoms);
            }


            //Medicines


            PdfPTable table = new PdfPTable(3); // 3 columns.
            table.setWidthPercentage(100);
            float[] columnWidths = {9f, 5f, 6f};

            table.setWidths(columnWidths);
            PdfPCell cell1 = new PdfPCell(new Paragraph("Medicine name", BoldTitle));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Dose", BoldTitle));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Timing/Duration", BoldTitle));


            cell1.setPadding(10);
            cell2.setPadding(10);
            cell3.setPadding(10);

            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            BaseColor cellbackground = new BaseColor(211, 211, 211);

            cell1.setBackgroundColor(cellbackground);
            cell2.setBackgroundColor(cellbackground);
            cell3.setBackgroundColor(cellbackground);
            cell1.setBorderColor(cellbackground);
            cell2.setBorderColor(cellbackground);
            cell3.setBorderColor(cellbackground);


            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.setSpacingBefore(10);
            table.setSpacingAfter(5);
            document.add(table);


            List<Medicine> medicines = Final_Patient.getFinal_Medicines();
            for (int i = 0; i < medicines.size(); i++) {

                Medicine med = medicines.get(i);
                PdfPTable table2 = new PdfPTable(4);
                table2.setWidthPercentage(100);

                float[] columnWidths2 = {1f, 8f, 5f, 6f};
                table2.setWidths(columnWidths2);
                PdfPCell cell4 = new PdfPCell(new Paragraph((i+1) + ".", BoldText));
                cell4.setRowspan(2);
                PdfPCell cell5 = new PdfPCell(new Paragraph(med.getName(), BoldText));
                // PdfPCell cell6 = new PdfPCell(new Paragraph("paracitamol"));
                double[] timings = med.getDailytimings();
                PdfPCell cell7 = new PdfPCell(new Paragraph((int)timings[0]+"-"+(int)timings[1]+"-"+(int)timings[2], BoldText));
                // PdfPCell cell8 = new PdfPCell(new Paragraph("After food"));
                PdfPCell cell9 = new PdfPCell(new Paragraph(med.getDuration()+" / "+med.getFrequency(), NormalText));

                cell4.setPaddingTop(5);
                cell5.setPaddingTop(5);
                cell7.setPadding(5);
                cell9.setPadding(5);


                cell4.setBorderColor(BaseColor.WHITE);
                cell5.setBorderColor(BaseColor.WHITE);
                cell7.setBorderColor(BaseColor.WHITE);
                cell9.setBorderColor(BaseColor.WHITE);

                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell9.setHorizontalAlignment(Element.ALIGN_CENTER);

                table2.addCell(cell4);
                table2.addCell(cell5);
                table2.addCell(cell7);
                table2.addCell(cell9);
                // table2.addCell(cell);
                // table2.addCell(cell);
                table2.completeRow();

                PdfPCell cell6 = new PdfPCell(new Paragraph(""+med.getGenericname(), Normalsmall));
                PdfPCell cell8 = new PdfPCell(new Paragraph(""+med.getAfbf(), Normalsmall));
                PdfPCell cell12 = new PdfPCell();


                cell6.setBorderColor(BaseColor.WHITE);
                cell8.setBorderColor(BaseColor.WHITE);
                cell12.setBorderColor(BaseColor.WHITE);

                cell8.setHorizontalAlignment(Element.ALIGN_CENTER);


                table2.addCell(cell6);
                table2.addCell(cell8);
                table2.addCell(cell12);


                table2.completeRow();

                // table2.setSpacingAfter(10);

                document.add(table2);

                PdfPTable table3 = new PdfPTable(1);
                table3.setWidthPercentage(100);
                PdfPCell cell13 = new PdfPCell(new Paragraph(""));

                cell13.setBorderWidthTop(0);
                cell13.setBorderWidthLeft(0);
                cell13.setBorderWidthRight(0);
                cell13.setBorderWidthBottom(1);

                cell13.setBorderColorBottom(cellbackground);
                table3.addCell(cell13);
                table3.setSpacingAfter(5);
                table3.setSpacingBefore(5);
                table3.completeRow();
                document.add(table3);
            }


            /*for(int i=1;i<25;i++)
            {
                PdfPTable table2 = new PdfPTable(4);
                table2.setWidthPercentage(100);

                float[] columnWidths2 = {1f,8f, 5f,6f};
                table2.setWidths(columnWidths2);
                PdfPCell cell4 = new PdfPCell(new Paragraph(i+".",BoldText));
                cell4.setRowspan(2);
                PdfPCell cell5 = new PdfPCell(new Paragraph("Dolo IMG 300mg",BoldText));
                // PdfPCell cell6 = new PdfPCell(new Paragraph("paracitamol"));
                PdfPCell cell7 = new PdfPCell(new Paragraph("1-0-1",BoldText));
                // PdfPCell cell8 = new PdfPCell(new Paragraph("After food"));
                PdfPCell cell9 = new PdfPCell(new Paragraph("10 Days/Daily",NormalText));

                cell4.setPaddingTop(5);
                cell5.setPaddingTop(5);
                cell7.setPadding(5);
                cell9.setPadding(5);



                cell4.setBorderColor(BaseColor.WHITE);
                cell5.setBorderColor(BaseColor.WHITE);
                cell7.setBorderColor(BaseColor.WHITE);
                cell9.setBorderColor(BaseColor.WHITE);

                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell9.setHorizontalAlignment(Element.ALIGN_CENTER);

                table2.addCell(cell4);
                table2.addCell(cell5);
                table2.addCell(cell7);
                table2.addCell(cell9);
                // table2.addCell(cell);
                // table2.addCell(cell);
                table2.completeRow();

                PdfPCell cell6 = new PdfPCell(new Paragraph("Paracetamol",Normalsmall));
                PdfPCell cell8 = new PdfPCell(new Paragraph("After food",Normalsmall));
                PdfPCell cell12 = new PdfPCell();




                cell6.setBorderColor(BaseColor.WHITE);
                cell8.setBorderColor(BaseColor.WHITE);
                cell12.setBorderColor(BaseColor.WHITE);

                cell8.setHorizontalAlignment(Element.ALIGN_CENTER);





                table2.addCell(cell6);
                table2.addCell(cell8);
                table2.addCell(cell12);



                table2.completeRow();

                // table2.setSpacingAfter(10);

                document.add(table2);

                PdfPTable table3 = new PdfPTable(1);
                table3.setWidthPercentage(100);
                PdfPCell cell13 = new PdfPCell(new Paragraph(""));

                cell13.setBorderWidthTop(0);
                cell13.setBorderWidthLeft(0);
                cell13.setBorderWidthRight(0);
                cell13.setBorderWidthBottom(1);

                cell13.setBorderColorBottom(cellbackground);
                table3.addCell(cell13);
                table3.setSpacingAfter(5);
                table3.setSpacingBefore(5);
                table3.completeRow();
                document.add(table3);
            }
*/









            //Advice
            if (!tv_advice_pdf.getText().toString().equals("")) {
                Font Bold1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
                Font Normal1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
                Paragraph symptoms = new Paragraph();
                symptoms.add(new Chunk("Advice : ", Bold1));
                symptoms.add(new Chunk(tv_advice_pdf.getText().toString(), Normal1));
                document.add(symptoms);
            }

            //Diagnostic Tests
            if (!tv_test_pdf.getText().toString().equals("")) {
                Paragraph symptoms = new Paragraph();
                symptoms.add(new Chunk("Tests Prescribed : ", Bold));
                symptoms.add(new Chunk(tv_test_pdf.getText().toString(), Normal));
                document.add(symptoms);
            }


            //Follow Up
            if (!tv_followup_pdf.getText().toString().equals("")) {
                Paragraph symptoms = new Paragraph();
                symptoms.add(new Chunk("Follow Up : ", Bold));
                symptoms.add(new Chunk(tv_followup_pdf.getText().toString(), Normal));
                document.add(symptoms);
            }


            document.close();


            //PDFView pdfView = findViewById(R.id.pdfView);

            /*Uri uripath = Uri.parse(filepath);
            pdfView.fromUri(uripath)
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    // allows to draw something on the current page, usually visible in the middle of the screen
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    .load();*/

            /*File file = new File(filepath);
            if(file.exists()){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse( "http://gunkelweb.com/coms493/texts/AI_Dummies.pdf"), "text/html");PackageManager pm = getPackageManager();
                startActivity(intent);
                *//*List<ResolveInfo> activities = pm.queryIntentActivities(intent,0);
                if(activities.size()>0) {
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }*//*
            }*/

            Toast.makeText(this, filepath, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
