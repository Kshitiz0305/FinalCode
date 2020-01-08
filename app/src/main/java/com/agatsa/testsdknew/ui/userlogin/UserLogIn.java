package com.agatsa.testsdknew.ui.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;

import com.agatsa.testsdknew.R;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class UserLogIn extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_log_in);
        }


        @Override
        public void onQRCodeRead(String text, PointF[] points) {
            Log.d("rantest",text);




        }
}

