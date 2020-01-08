package com.agatsa.testsdknew;

import android.app.Application;

import com.agatsa.testsdknew.ui.LabDB;
import com.facebook.stetho.Stetho;

public class LabInABagApplication extends Application {

    public void onCreate() {
        super.onCreate();
                final LabDB db = new LabDB(getApplicationContext());
        if(BuildConfig.DEBUG)
        Stetho.initializeWithDefaults(this);
    }

}
