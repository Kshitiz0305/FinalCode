package com.agatsa.testsdknew.Models;

/**
 * Created by sanjiv on 1/12/19.
 */

public class UrineReport {
    String pt_no;
    String row_id;
    String leuko;
    String nit;
    String urb;
    String protein;
    String ph;
    String blood;
    String sg;
    String ket;
    String bili;
    String glucose;
    String asc;

    public UrineReport() {
        this.pt_no ="";
        this.row_id = "";
        this.leuko = "";
        this.nit = "";
        this.urb = "";
        this.protein = "";
        this.ph = "";
        this.blood = "";
        this.sg = "";
        this.ket = "";
        this.bili = "";
        this.glucose = "";
        this.asc ="";
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

    public String getLeuko() {
        return leuko;
    }

    public void setLeuko(String leuko) {
        this.leuko = leuko;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getUrb() {
        return urb;
    }

    public void setUrb(String urb) {
        this.urb = urb;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getBili() {
        return bili;
    }

    public void setBili(String bili) {
        this.bili = bili;
    }

    public String getGlucose() {
        return glucose;
    }

    public void setGlucose(String glucose) {
        this.glucose = glucose;
    }

    public String getAsc() {
        return asc;
    }

    public void setAsc(String asc) {
        this.asc = asc;
    }
}
