package com.agatsa.testsdknew.ui;

import com.agatsa.sanketlife.development.EcgConfig;
import com.agatsa.sanketlife.development.LongEcgConfig;

public interface HistoryCallback {
    void syncEcgData(EcgConfig ecgConfig);
    void viewPdf(EcgConfig ecgConfig);
    void viewPdfStress(LongEcgConfig longEcgConfig);
    void syncStressData(LongEcgConfig longEcgConfig);
}
