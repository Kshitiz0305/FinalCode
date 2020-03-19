package com.agatsa.testsdknew.ui;

import com.agatsa.sanketlife.development.EcgConfig;
import com.agatsa.sanketlife.development.LongEcgConfig;

import java.util.List;

public interface HistoryCallback {
    void syncEcgData(EcgConfig ecgConfig);
    void setlongecgButtonSyncAll(LongEcgConfig ecgConfig);
    void setButtonSyncAll(EcgConfig ecgConfig);
    void viewPdf(EcgConfig ecgConfig);
    void viewPdfStress(LongEcgConfig longEcgConfig);
    void syncStressData(LongEcgConfig longEcgConfig);
}
