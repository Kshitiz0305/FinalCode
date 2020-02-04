package com.agatsa.testsdknew.Models;

/**
 * Created by sanjiv on 1/12/19.
 */

public class VitalSign {
    String pt_no;
    String row_id;
    double weight;
    double height;
    double tempt;
    double pulse;
    double bps;
    double bpd;
    double sto2;
    double glucose;

    public VitalSign(){
        pt_no = "";
        row_id ="";
        weight = 0;
        height =0;
        tempt =0;
        pulse =0;
        bps=0;
        bpd =0;
        sto2 =0;
        glucose =0;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getTempt() {
        return tempt;
    }

    public void setTempt(double tempt) {
        this.tempt = tempt;
    }

    public double getPulse() {
        return pulse;
    }

    public void setPulse(double pulse) {
        this.pulse = pulse;
    }

    public double getBps() {
        return bps;
    }

    public void setBps(double bps) {
        this.bps = bps;
    }

    public double getBpd() {
        return bpd;
    }

    public void setBpd(double bpd) {
        this.bpd = bpd;
    }

    public double getSto2() {
        return sto2;
    }

    public void setSto2(double sto2) {
        this.sto2 = sto2;
    }

    public double getGlucose() {
        return glucose;
    }

    public void setGlucose(double glucose) {
        this.glucose = glucose;
    }
}
