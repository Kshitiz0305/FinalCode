package com.agatsa.testsdknew.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.afollestad.materialdialogs.MaterialDialog;
import com.agatsa.testsdknew.R;

public class DialogUtil {

    /**
     * returns common message dialog
     * @param pContext app context
     * @param pTitle title if u need any, pass null if u want 'message' as title
     * @param pMsg body of the dialog
     * @param pOK OK button text, pass null for 'OK'
     * @return constructed dialog or NULL if the pmsg is null
     */
    public static Dialog getOKDialog(Context pContext, String pTitle, String pMsg, String pOK) {
        if(pMsg == null) return null;
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pContext);
        lBuilder.setCancelable(false);
        if(pTitle == null){
            lBuilder.setTitle(pContext.getResources().getString(R.string.message_text));
        } else{
            lBuilder.setTitle(pTitle);
        }
        lBuilder.setMessage(pMsg);
        if(pOK == null){
            lBuilder.setPositiveButton(pContext.getResources().getString(R.string.ok_text), null);
        } else{
            lBuilder.setPositiveButton(pOK, null);
        }
        return lBuilder.show();
    }

    public static Dialog getOKDialog(Context pContext, String pTitle, String pMsg, String pOK, DialogInterface.OnClickListener listener) {
        if(pMsg == null) return null;
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pContext);
        lBuilder.setCancelable(false);
        if(pTitle == null){
            lBuilder.setTitle(pContext.getResources().getString(R.string.message_text));
        } else{
            lBuilder.setTitle(pTitle);
        }
        lBuilder.setMessage(pMsg);
        if(pOK == null){
            lBuilder.setPositiveButton(pContext.getResources().getString(R.string.ok_text), listener);
        } else{
            lBuilder.setPositiveButton(pOK, listener);
        }
        return lBuilder.show();
    }

    public static Dialog getNoInternetDialog(final Activity c){
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(c);

        lBuilder.setTitle("Device offline!");
        lBuilder.setMessage("Please conect to the internet and try again");
        lBuilder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                c.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        lBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return lBuilder.show();
    }



    public static Dialog getOKDialogFinsih(final Activity pContext, String pTitle, String pMsg, String pOK) {
        if(pMsg == null) return null;
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pContext);
        lBuilder.setCancelable(false);
        if(pTitle == null){
            lBuilder.setTitle(pContext.getResources().getString(R.string.message_text));
        } else{
            lBuilder.setTitle(pTitle);
        }
        lBuilder.setMessage(pMsg);
        if(pOK == null){
            lBuilder.setPositiveButton(pContext.getResources().getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    pContext.finish();
                }
            });
        } else{
            lBuilder.setPositiveButton(pOK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    pContext.finish();
                    //pContext.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                }
            });
        }
        return lBuilder.show();
    }

//    public static Dialog getProgressDialog(Context pContext) {
//        LayoutInflater inflater = (LayoutInflater)pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View lView = inflater.inflate(R.layout.progress_dialogue, null, false);
//        ProgressBar bar = (ProgressBar) lView.findViewById(R.id.progress);
//        TextView text = (TextView) lView.findViewById(R.id.loadingtext);
//
//        Dialog lDialog = new Dialog(pContext, android.R.style.Theme_Translucent_NoTitleBar);
//        lDialog.setContentView(lView);
//        WindowManager.LayoutParams lp = lDialog.getWindow().getAttributes();
//        lp.dimAmount = 0.7f;
//        lDialog.getWindow().setAttributes(lp);
//        lDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//
//        return lDialog;
//    }



//	public static  Dialog getLogOutUserDialog(Context context,final SessionManager manager){
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setTitle("Confirm!");
//		builder.setMessage("Are you sure you want to logout?");
//		builder.setCancelable(false);
//		builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialogInterface, int i) {
//				manager.logoutUser();
//			}
//		});
//		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialogInterface, int i) {
//				dialogInterface.dismiss();
//			}
//		});
//		return builder.show();
//
//	}


    public static Dialog getOKCancelDialog(Context pContext, String pTitle, String pMsg, String pOK,String cancel, DialogInterface.OnClickListener listener) {
        if(pMsg == null) return null;
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pContext);

        if(pTitle == null){
            lBuilder.setTitle(pContext.getResources().getString(R.string.message_text));
        } else{
            lBuilder.setTitle(pTitle);
        }


        lBuilder.setMessage(pMsg);
        lBuilder.setCancelable(true);
        if(pOK == null){
            lBuilder.setNegativeButton(pContext.getResources().getString(R.string.ok_text), listener);


        } else{

            lBuilder.setNegativeButton(pOK, listener);
        }



        lBuilder.setPositiveButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        AlertDialog dialog = lBuilder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);

        return  dialog;
    }

    public static Dialog getOKCancelDialog(Context pContext, String pTitle, String pMsg, String pOK,String cancel, DialogInterface.OnClickListener listener,boolean isDissapperfromTouchOutside) {
        if(pMsg == null) return null;
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pContext);

        if(pTitle == null){
            lBuilder.setTitle(pContext.getResources().getString(R.string.message_text));
        } else{
            lBuilder.setTitle(pTitle);
        }


        lBuilder.setMessage(pMsg);
        lBuilder.setCancelable(isDissapperfromTouchOutside);
        if(pOK == null){
            lBuilder.setNegativeButton(pContext.getResources().getString(R.string.ok_text), listener);


        } else{

            lBuilder.setNegativeButton(pOK, listener);
        }



        lBuilder.setPositiveButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        AlertDialog dialog = lBuilder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);

        return  dialog;
    }


    public static Dialog getOKCancelDialog(Context pContext, String pTitle, String pMsg, String pOK,String cancel, DialogInterface.OnClickListener listener,DialogInterface.OnClickListener listener2) {
        if(pMsg == null) return null;
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pContext);

        if(pTitle == null){
            lBuilder.setTitle(pContext.getResources().getString(R.string.message_text));
        } else{
            lBuilder.setTitle(pTitle);
        }


        lBuilder.setMessage(pMsg);
        lBuilder.setCancelable(true);
        if(pOK == null){
            lBuilder.setNegativeButton(pContext.getResources().getString(R.string.ok_text), listener);



        } else{

            lBuilder.setNegativeButton(pOK, listener);
        }



//		lBuilder.setPositiveButton(cancel, new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialogInterface, int i) {
//				dialogInterface.cancel();
//			}
//		});

        if(cancel == null){
            lBuilder.setPositiveButton("cancel", listener2);


        } else{

            lBuilder.setPositiveButton(cancel, listener2);
        }

        AlertDialog dialog = lBuilder.create();



//		return lBuilder.show().getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);


        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);
        return dialog;
    }

    public static Dialog getOKCancelDialog(Context pContext, String pTitle, String pMsg, String pOK,String cancel, DialogInterface.OnClickListener listener,DialogInterface.OnClickListener listener2,boolean isDissapperfromTouchOutside) {
        if(pMsg == null) return null;
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pContext);

        if(pTitle == null){
            lBuilder.setTitle(pContext.getResources().getString(R.string.message_text));
        } else{
            lBuilder.setTitle(pTitle);
        }


        lBuilder.setMessage(pMsg);
        lBuilder.setCancelable(isDissapperfromTouchOutside);
        if(pOK == null){
            lBuilder.setNegativeButton(pContext.getResources().getString(R.string.ok_text), listener);


        } else{

            lBuilder.setNegativeButton(pOK, listener);
        }



        lBuilder.setPositiveButton(cancel,listener2 );


        AlertDialog dialog = lBuilder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);

        return  dialog;
    }

    public static MaterialDialog showProgressDialog(Context context, String title, String msg) {
        return new MaterialDialog.Builder(context)
                .content(msg)
                .progress(true, 0)
                .show();
    }



}
