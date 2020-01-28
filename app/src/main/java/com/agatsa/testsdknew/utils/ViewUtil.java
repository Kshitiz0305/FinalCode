package com.agatsa.testsdknew.utils;

import android.view.View;
import android.widget.TextView;
import com.agatsa.testsdknew.R;
import com.google.android.material.snackbar.Snackbar;



public class ViewUtil {

    public static void showSnackBar(View view, String message, boolean isError) {
        try {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(isError ? view.getResources().getColor(R.color.colorError) :
                    view.getResources().getColor(R.color.colorSuccess));
            TextView textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(view.getResources().getColor(R.color.white));
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
