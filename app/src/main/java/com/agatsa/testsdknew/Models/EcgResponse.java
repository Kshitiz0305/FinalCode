package com.agatsa.testsdknew.Models;

public class EcgResponse {
    Integer patient_id;
    String date_of_test_conducted;
    Integer heart_rate;
    Integer pr;
    Integer qrs;
    Integer qt;
    Integer qtc;
    Integer sdnn;
    Integer mrr;
    Integer rmssd;
    Integer overall_result;

    public EcgResponse(Integer patient_id, String date_of_test_conducted, Integer heart_rate, Integer pr, Integer qrs, Integer qt, Integer qtc, Integer sdnn, Integer mrr, Integer rmssd, Integer overall_result) {
        this.patient_id = patient_id;
        this.date_of_test_conducted = date_of_test_conducted;
        this.heart_rate = heart_rate;
        this.pr = pr;
        this.qrs = qrs;
        this.qt = qt;
        this.qtc = qtc;
        this.sdnn = sdnn;
        this.mrr = mrr;
        this.rmssd = rmssd;
        this.overall_result = overall_result;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public String getDate_of_test_conducted() {
        return date_of_test_conducted;
    }

    public void setDate_of_test_conducted(String date_of_test_conducted) {
        this.date_of_test_conducted = date_of_test_conducted;
    }

    public Integer getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(Integer heart_rate) {
        this.heart_rate = heart_rate;
    }

    public Integer getPr() {
        return pr;
    }

    public void setPr(Integer pr) {
        this.pr = pr;
    }

    public Integer getQrs() {
        return qrs;
    }

    public void setQrs(Integer qrs) {
        this.qrs = qrs;
    }

    public Integer getQt() {
        return qt;
    }

    public void setQt(Integer qt) {
        this.qt = qt;
    }

    public Integer getQtc() {
        return qtc;
    }

    public void setQtc(Integer qtc) {
        this.qtc = qtc;
    }

    public Integer getSdnn() {
        return sdnn;
    }

    public void setSdnn(Integer sdnn) {
        this.sdnn = sdnn;
    }

    public Integer getMrr() {
        return mrr;
    }

    public void setMrr(Integer mrr) {
        this.mrr = mrr;
    }

    public Integer getRmssd() {
        return rmssd;
    }

    public void setRmssd(Integer rmssd) {
        this.rmssd = rmssd;
    }

    public Integer getOverall_result() {
        return overall_result;
    }

    public void setOverall_result(Integer overall_result) {
        this.overall_result = overall_result;
    }
}
