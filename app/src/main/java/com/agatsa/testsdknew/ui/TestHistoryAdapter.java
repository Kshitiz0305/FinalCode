package com.agatsa.testsdknew.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agatsa.sanketlife.development.EcgConfig;
import com.agatsa.testsdknew.R;

import java.util.ArrayList;
import java.util.List;

public class TestHistoryAdapter extends RecyclerView.Adapter<TestHistoryAdapter.NewHistoryViewHolder> {

    List<EcgConfig> data=new ArrayList<>();
    private Context mContext;


    public TestHistoryAdapter(Context mContext, List<EcgConfig> data) {
        this.mContext = mContext;
        this.data = data;

    }

    @NonNull
    @Override
    public TestHistoryAdapter.NewHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_history, parent, false);
        return new NewHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewHistoryViewHolder holder, int position) {
        /*if(data.get(position) instanceof LongEcgConfigInternal) {
            holder.bindLongEcgConfig((LongEcgConfigInternal) data.get(position));
        } else {*/
        holder.bindEcgConfig(data.get(position));

        /*}*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    public class NewHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView textViewHeartRate;
        TextView textViewFindings;
        TextView textViewreadings;
        Button buttonSync,buttonViewPDF;


        NewHistoryViewHolder(View itemView) {
            super(itemView);
            textViewFindings = itemView.findViewById(R.id.textViewFindings);
            textViewHeartRate = itemView.findViewById(R.id.textViewHeartRate);
            textViewreadings = itemView.findViewById(R.id.readings);
            buttonSync = itemView.findViewById(R.id.buttonSync);
            buttonViewPDF = itemView.findViewById(R.id.buttonViewPDF);
        }

        /*void bindLongEcgConfig(LongEcgConfigInternal config) {
            textViewFindings.setText(config.getFinding());
            textViewHeartRate.setText(config.getHeartRate());
            String readings = "PR: " + config.getpR() + " \n" +
                    "QRS: " + config.getqRs() + " \n" +
                    "QT: " + config.getqT() + " \n" +
                    "QTc: " + config.getqTc() + " \n" +
                    "SDNN: " + config.getSdnn() + " \n" +
                    "mRR: " + config.getmRR()+ " \n" +
                    "rmssd: " + config.getRmssd() + "" ;

            textViewreadings.setText(readings);
        }*/

        void bindEcgConfig(final EcgConfig config) {
            textViewFindings.setText(config.getFinding());
            textViewHeartRate.setText(String.valueOf(config.getHeartRate()));
            String readings = "PR: " + config.getpR() + " \n" +
                    "QRS: " + config.getqRs() + " \n" +
                    "QT: " + config.getqT() + " \n" +
                    "QTc: " + config.getqTc() + " \n" +
                    "SDNN: " + config.getSdnn() + " \n" +
                    "mRR: " + config.getmRR()+ " \n" +
                    "rmssd: " + config.getRmssd() + "" ;

            textViewreadings.setText(readings);
//            buttonViewPDF.setOnClickListener(view -> historyCallback.viewPdf(config));
//
//            buttonSync.setOnClickListener(view -> {
//                for(int i = 0; i < data.size();i++){
//                    historyCallback.syncEcgData(data.get(i));
//
//
//                }
//
//
////                for(int i=0;i<data.size();i++){
////                   if(data.get(i)!=null){
////                       historyCallback.syncEcgData(config);
////
////                   }
////
////
////
////                }
//
//            });
        }
    }
}
