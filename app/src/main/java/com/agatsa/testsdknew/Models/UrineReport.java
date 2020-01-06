package com.agatsa.testsdknew.Models;

/**
 * Created by sanjiv on 1/12/19.
 */

public class UrineReport {
    int pt_no;
    int row_id;
    float leuko;
    float nit;
    float urb;
    float protein;
    float ph;
    float blood;
    float sg;
    float ket;
    float bili;
    float glucose;
    float asc;

    public UrineReport(){
        pt_no = 0;
        row_id =0;
        glucose=0;
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

    public float getLeuko() {
        return leuko;
    }

    public void setLeuko(float leuko) {
        this.leuko = leuko;
    }

    public float getNit() {
        return nit;
    }

    public void setNit(float nit) {
        this.nit = nit;
    }

    public float getUrb() {
        return urb;
    }

    public void setUrb(float urb) {
        this.urb = urb;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public float getBlood() {
        return blood;
    }

    public void setBlood(float blood) {
        this.blood = blood;
    }

    public float getSg() {
        return sg;
    }

    public void setSg(float sg) {
        this.sg = sg;
    }

    public float getKet() {
        return ket;
    }

    public void setKet(float ket) {
        this.ket = ket;
    }

    public float getBili() {
        return bili;
    }

    public void setBili(float bili) {
        this.bili = bili;
    }

    public float getGlucose() {
        return glucose;
    }

    public void setGlucose(float glucose) {
        this.glucose = glucose;
    }

    public float getAsc() {
        return asc;
    }

    public void setAsc(float asc) {
        this.asc = asc;
    }
}
