package com.agatsa.testsdknew.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.view.View;
import android.widget.Button;

import com.agatsa.testsdknew.BuildConfig;
import com.agatsa.testsdknew.R;

import java.io.File;

public class ViewPDFActivity extends AppCompatActivity {

    String filePath = "";
    private Button btnViewPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        if (getIntent().getExtras() != null) {
            filePath = getIntent().getExtras().getString("pdfpath");
        }

        btnViewPDF = findViewById(R.id.btnViewPDF);
        btnViewPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

    }

    public void openFile() {/*
        Log.d("File Path", filePath);
        Uri data = Uri.fromFile(new File(filePath));

        File file = new File(filePath);
        Uri uri = FileProvider.getUriForFile(ViewPDFActivity.this, getPackageName(), file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(data, "application/pdf");
        } else {
            intent.setDataAndType(uri, "application/pdf");
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);*/

        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // set leadIndex to give temporary permission to external app to use your FileProvider
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);

        // I am opening a PDF file so I give it a valid MIME type
        intent.setDataAndType(uri, "application/pdf");

        // validate that the device can open your File!
        startActivity(intent);
    }
}
