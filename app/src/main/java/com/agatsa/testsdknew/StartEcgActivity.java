package com.agatsa.testsdknew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.agatsa.sanketlife.callbacks.RegisterDeviceResponse;
import com.agatsa.sanketlife.development.Errors;
import com.agatsa.sanketlife.development.InitiateEcg;

public class StartEcgActivity extends AppCompatActivity {
    int x = 1;
    InitiateEcg initiateEcg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_ecg);
        Button single = (Button) findViewById(R.id.single);
        Button limb = (Button) findViewById(R.id.limb);
        Button chest = (Button) findViewById(R.id.chest);
        Button twelve = (Button) findViewById(R.id.twelve);
        Button b3 = (Button) findViewById(R.id.register);
        Button b4 = (Button) findViewById(R.id.fetchData);
        initiateEcg = new InitiateEcg();
        final String authKey = "";
        //final String authKey= "5a3b4c16b4a56b000132f5d50aa253d8ebd34707a832ed4432413816";
        //final String authKey= "";

        single.setOnClickListener(view -> {
            Intent intent = new Intent(StartEcgActivity.this, ECGactivity.class);
            intent.putExtra("ecgType", "single");
            startActivity(intent);
        });
        limb.setOnClickListener(view -> {
            Intent intent = new Intent(StartEcgActivity.this, ECGactivity.class);
            intent.putExtra("ecgType", "limb");
            startActivity(intent);
        });
        chest.setOnClickListener(view -> {
            Intent intent = new Intent(StartEcgActivity.this, ECGactivity.class);
            intent.putExtra("ecgType", "chest");
            startActivity(intent);
        });
        twelve.setOnClickListener(view -> {
            Intent intent = new Intent(StartEcgActivity.this, ECGactivity.class);
            intent.putExtra("ecgType", "twelve");
            startActivity(intent);
        });

        b3.setOnTouchListener((view, motionEvent) -> false);
        b3.setOnClickListener(view -> initiateEcg.registerDevice(StartEcgActivity.this, authKey, new RegisterDeviceResponse() {
            @Override
            public void onSuccess(String msg) {
                Toast.makeText(StartEcgActivity.this, "device registered " + msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Errors errors) {
                Log.e("msg register", errors.getErrorMsg());

            }
        }));

        b4.setOnClickListener(view -> {

        });

    }


}
