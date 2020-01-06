package com.agatsa.testsdknew;

import android.content.Context;
import android.view.View;
import android.widget.Toast;


public class ToastUtils {
    public static void maketoast(Context context, String message) {
        Toast ToastMessage = Toast.makeText(context,message, Toast.LENGTH_SHORT);
        View toastView = ToastMessage.getView();
        toastView.setBackgroundResource(R.drawable.toast_backgraound);
        ToastMessage.show();
    }
}
