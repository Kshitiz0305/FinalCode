package com.agatsa.testsdknew;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

public class PdfViewActivity extends AppCompatActivity {
    Button viewPdfButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        viewPdfButton=(Button)findViewById(R.id.button_view);
        viewPdfButton.setOnClickListener(view -> showPdfReport(getIntent().getStringExtra("fileUrl")));
    }
    private void showPdfReport(String filePath) {
        try {
            if (!filePath.equals("")) {
                File file = new File(filePath);
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri photoURI = FileProvider.getUriForFile(PdfViewActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file);
                    intent.setDataAndType(photoURI, "application/pdf");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Please install a PDF Viewer.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        } catch (NullPointerException e) {
            Log.d("sanketDoc", "Exception in opening pdf: " + e.toString());
            Toast.makeText(this, "No file found.",
                    Toast.LENGTH_SHORT).show();
        }
    }


}
