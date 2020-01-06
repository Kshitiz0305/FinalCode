package com.agatsa.testsdknew.Models;

public class ECGReport {
    int pt_no;
    int row_id;
    double heartrate;
    double pr;
    double qt;
    double qtc;
    double qrs;
    double sdnn;
    double rmssd;
    double mrr;
    String finding;


    public ECGReport() {
       pt_no = 0;
        row_id = 0;
        heartrate = 0;
        pr=0;
        qt = 0;
        qtc = 0;
        qrs = 0;
        sdnn = 0;
        rmssd = 0;
        mrr = 0;
        finding = "";
    }

    public int getPt_no() {
        return pt_no;
    }

    public void setPt_no(int pt_no) {
        this.pt_no = pt_no;
    }

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public double getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(double heartrate) {
        this.heartrate = heartrate;
    }

    public double getPr() {
        return pr;
    }

    public void setPr(double pr) {
        this.pr = pr;
    }

    public double getQt() {
        return qt;
    }

    public void setQt(double qt) {
        this.qt = qt;
    }

    public double getQtc() {
        return qtc;
    }

    public void setQtc(double qtc) {
        this.qtc = qtc;
    }

    public double getQrs() {
        return qrs;
    }

    public void setQrs(double qrs) {
        this.qrs = qrs;
    }

    public double getSdnn() {
        return sdnn;
    }

    public void setSdnn(double sdnn) {
        this.sdnn = sdnn;
    }

    public double getRmssd() {
        return rmssd;
    }

    public void setRmssd(double rmssd) {
        this.rmssd = rmssd;
    }

    public double getMrr() {
        return mrr;
    }

    public void setMrr(double mrr) {
        this.mrr = mrr;
    }

    public String getFinding() {
        return finding;
    }

    public void setFinding(String finding) {
        this.finding = finding;
    }
}
