package com.agatsa.testsdknew.utils;

import android.content.Context;

import com.agatsa.testsdknew.R;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;



public class ErrorUtil {

    public static String getErrorMessage(Context context, Throwable e) {
        if (e instanceof SocketTimeoutException)
            return context.getString(R.string.msg_timeout);
        else if (e instanceof IOException)
            return context.getString(R.string.msg_no_internet);
        else {
            try {
                String code = String.valueOf(((HttpException) e).code());
                if (code.equals("503"))
                    return context.getString(R.string.msg_503);
                if (code.equals("404"))
                    return context.getString(R.string.msg_404);
                return code;
            } catch (Exception ex) {
                ex.printStackTrace();
                return e.toString();
            }
        }
    }
}
