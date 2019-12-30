package com.example.mobihealthapis.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mobihealthapis.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class pdf_generate extends AppCompatActivity {

    LinearLayout rl_generate_pdf;
    ImageView bmImage;
    FloatingActionButton fab_generate_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_generate);

        rl_generate_pdf = findViewById(R.id.rl_generate_pdf);

        rl_generate_pdf.setDrawingCacheEnabled(true);
        rl_generate_pdf.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        rl_generate_pdf.layout(0, 0, rl_generate_pdf.getMeasuredWidth(), rl_generate_pdf.getMeasuredHeight());

        rl_generate_pdf.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(rl_generate_pdf.getDrawingCache());
        rl_generate_pdf.setDrawingCacheEnabled(false); // clear drawing cache

        bmImage = findViewById(R.id.bmImage);
        bmImage.setImageBitmap(b);




        fab_generate_pdf = findViewById(R.id.fab_generate_pdf);
        fab_generate_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeneratePDF();
            }
        });
        //stringtopdf("hello swapnil");
    }

    private void GeneratePDF() {


        PrintHelper printHelper = new PrintHelper(this);

        // Set the desired scale mode.
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);

        // Get the bitmap for the ImageView's drawable.

        //ImageView mImageView = (ImageView) rl_generate_pdf;

        Bitmap bitmap = ((BitmapDrawable) bmImage.getDrawable()).getBitmap();

        // Print the bitmap.
        printHelper.printBitmap("Print Bitmap", bitmap);

    }


    public void stringtopdf(String data) {
        String extstoragedir = Environment.getExternalStorageDirectory().toString();
        File fol = new File(extstoragedir, "pdf");
        File folder = new File(fol, "MyPDFS");
        if (!folder.exists()) {
            boolean bool = folder.mkdir();
        }
        try {
            final File file = new File(folder, "sample.pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);


            PdfDocument document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new
                    PdfDocument.PageInfo.Builder(100, 100, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();

            canvas.drawText(data, 10, 10, paint);


            document.finishPage(page);
            document.writeTo(fOut);
            document.close();

        } catch (IOException e) {
            Log.i("error", e.getLocalizedMessage());
        }
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(pdf_generate.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }
}
