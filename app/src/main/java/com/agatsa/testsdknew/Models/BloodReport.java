package com.agatsa.testsdknew.Models;

/**
 * Created by Kshitiz on 1/12/19.
 */

public class BloodReport {
    int pt_no;
    String row_id;
    float uric_acid;
    float chlorestrol;
    float glucose;

    public BloodReport(){
        pt_no = 0;
        row_id ="";
        uric_acid=0;
        chlorestrol =0;
        glucose=0;
    }

    public int getPt_no() {
        return pt_no;
    }

    public void setPt_no(int pt_no) {
        this.pt_no = pt_no;
    }

    public String getRow_id() {
        return row_id;
    }

    public void setRow_id(String row_id) {
        this.row_id = row_id;
    }

    public float getUric_acid() {
        return uric_acid;
    }

    public void setUric_acid(float uric_acid) {
        this.uric_acid = uric_acid;
    }

    public float getChlorestrol() {
        return chlorestrol;
    }

    public void setChlorestrol(float chlorestrol) {
        this.chlorestrol = chlorestrol;
    }

    public float getGlucose() {
        return glucose;
    }

    public void setGlucose(float glucose) {
        this.glucose = glucose;
    }
}
