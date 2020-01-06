package com.agatsa.testsdknew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agatsa.sanketlife.development.EcgConfig;
import com.agatsa.sanketlife.development.LongEcgConfig;

import java.util.ArrayList;
import java.util.List;

public class HistoryStressAdapter extends RecyclerView.Adapter<HistoryStressAdapter.HistoryViewHolder> {

    private List<LongEcgConfig> data = new ArrayList<>();
    private Context mContext;
    private HistoryCallback historyCallback;

    public HistoryStressAdapter(Context mContext, List<LongEcgConfig> data, HistoryCallback historyCallback) {
        this.mContext = mContext;
        this.data = data;
        this.historyCallback = historyCallback;
    }

    @NonNull
    @Override
    public HistoryStressAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        /*if(data.get(position) instanceof LongEcgConfigInternal) {
            holder.bindLongEcgConfig((LongEcgConfigInternal) data.get(position));
        } else {*/
            holder.bindLongEcgConfig(data.get(position));
        /*}*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView textViewHeartRate;
        TextView textViewFindings;
        TextView textViewreadings;
        Button buttonSync, buttonViewPDF;

        HistoryViewHolder(View itemView) {
            super(itemView);
            textViewFindings = itemView.findViewById(R.id.textViewFindings);
            textViewHeartRate = itemView.findViewById(R.id.textViewHeartRate);
            textViewreadings = itemView.findViewById(R.id.readings);

            buttonSync = itemView.findViewById(R.id.buttonSync);
            buttonViewPDF = itemView.findViewById(R.id.buttonViewPDF);
        }

        void bindLongEcgConfig(final LongEcgConfig config) {
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

            buttonViewPDF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    historyCallback.viewPdfStress(config);
                }
            });

            buttonSync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    historyCallback.syncStressData(config);
                }
            });
        }

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
            buttonViewPDF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    historyCallback.viewPdf(config);
                }
            });

            buttonSync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    historyCallback.syncEcgData(config);
                }
            });
        }
    }
}
