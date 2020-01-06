package com.agatsa.testsdknew.Models;

public class GlucoseModel {
    int pt_no;
    int row_id;
    double ptGlucose;

    public GlucoseModel() {
        pt_no = 0;
        ptGlucose =0;
        row_id=0;

    }

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public int getPt_no() {
        return pt_no;
    }

    public void setPt_no(int pt_no) {
        this.pt_no = pt_no;
    }

    public double getPtGlucose() {
        return ptGlucose;
    }

    public void setPtGlucose(double ptGlucose) {
        this.ptGlucose = ptGlucose;
    }
}
