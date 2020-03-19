package com.agatsa.testsdknew.Models;

public class BloodPressureModel {
    String pt_no;
    String  row_id;
    String systolicdiastolic;
    String systolic;
    String diastolic;


    public BloodPressureModel() {
        pt_no="";
        row_id="";
        systolic="";
        diastolic="";
        systolicdiastolic="";
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
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

    public String getSystolicdiastolic() {
        return systolicdiastolic;
    }

    public void setSystolicdiastolic(String systolicdiastolic) {
        this.systolicdiastolic = systolicdiastolic;
    }


}
