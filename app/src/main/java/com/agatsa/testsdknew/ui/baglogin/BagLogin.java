package com.agatsa.testsdknew.ui.baglogin;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.agatsa.testsdknew.R;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class BagLogin extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag_login);
    }


    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.d("rantest", text);

    }

    }
