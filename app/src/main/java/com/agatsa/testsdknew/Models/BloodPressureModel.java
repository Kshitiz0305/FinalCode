package com.agatsa.testsdknew.Models;

public class BloodPressureModel {
    String pt_no;
    String  row_id;
    double systolic;
    double diastolic;

    public BloodPressureModel() {
        pt_no="";
        row_id="";
        systolic=0.0;
        diastolic=0.0;
    }

    public String getPt_no() {
        return pt_no;
    }

    public void setPt_no(String pt_no) {
        this.pt_no = pt_no;
    }

    public String getRow_id() {
        return row_id;
    }

    public void setRow_id(String row_id) {
        this.row_id = row_id;
    }

    public double getSystolic() {
        return systolic;
    }

    public void setSystolic(double systolic) {
        this.systolic = systolic;
    }

    public double getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(double diastolic) {
        this.diastolic = diastolic;
    }
}
