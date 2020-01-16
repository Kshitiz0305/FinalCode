package com.agatsa.testsdknew.Models;

public class GlucoseModel {
    String pt_no;
    String  row_id;
    String ptGlucose;

    public GlucoseModel() {
        pt_no = "";
        row_id="";
        ptGlucose ="";

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

    public String getPtGlucose() {
        return ptGlucose;
    }

    public void setPtGlucose(String ptGlucose) {
        this.ptGlucose = ptGlucose;
    }
}
