package com.agatsa.testsdknew.Models;

/**
 * Created by sanjiv on 1/12/19.
 */

public class VitalSign {
    String pt_no;
    String row_id;
    float weight;
    float height;
    float tempt;
    float pulse;
    float bps;
    float bpd;
    float sto2;
    float glucose;

    public VitalSign(){
        pt_no = "";
        row_id ="";
        weight =0;
        height =0;
        tempt =0;
        pulse =0;
        bps=0;
        bpd =0;
        sto2 =0;
        glucose =0;
    }


    public float getGlucose() {
        return glucose;
    }

    public void setGlucose(float glucose) {
        this.glucose = glucose;
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getTempt() {
        return tempt;
    }

    public void setTempt(float tempt) {
        this.tempt = tempt;
    }

    public float getPulse() {
        return pulse;
    }

    public void setPulse(float pulse) {
        this.pulse = pulse;
    }

    public float getBps() {
        return bps;
    }

    public void setBps(float bps) {
        this.bps = bps;
    }

    public float getBpd() {
        return bpd;
    }

    public void setBpd(float bpd) {
        this.bpd = bpd;
    }

    public float getSto2() {
        return sto2;
    }

    public void setSto2(float sto2) {
        this.sto2 = sto2;
    }
}
