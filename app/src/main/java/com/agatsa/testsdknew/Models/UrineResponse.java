package com.agatsa.testsdknew.Models;

public class UrineResponse {
    String patient_id;
    Integer leuk;
    Integer nit;
    Integer urb;
    Integer pro;
    Integer ph;
    Integer blood;
    Integer sg;
    Integer ket;
    Integer bil;
    Integer glucose;
    Integer aa;
    String average_colo_test;
    String strip_photo_uri;
    String created_on;
    String modified_on;

    public UrineResponse(String patient_id, Integer leuk, Integer nit, Integer urb, Integer pro, Integer ph, Integer blood, Integer sg, Integer ket, Integer bil, Integer glucose, Integer aa, String average_colo_test, String strip_photo_uri, String created_on, String modified_on) {
        this.patient_id = patient_id;
        this.leuk = leuk;
        this.nit = nit;
        this.urb = urb;
        this.pro = pro;
        this.ph = ph;
        this.blood = blood;
        this.sg = sg;
        this.ket = ket;
        this.bil = bil;
        this.glucose = glucose;
        this.aa = aa;
        this.average_colo_test = average_colo_test;
        this.strip_photo_uri = strip_photo_uri;
        this.created_on = created_on;
        this.modified_on = modified_on;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public Integer getLeuk() {
        return leuk;
    }

    public void setLeuk(Integer leuk) {
        this.leuk = leuk;
    }

    public Integer getNit() {
        return nit;
    }

    public void setNit(Integer nit) {
        this.nit = nit;
    }

    public Integer getUrb() {
        return urb;
    }

    public void setUrb(Integer urb) {
        this.urb = urb;
    }

    public Integer getPro() {
        return pro;
    }

    public void setPro(Integer pro) {
        this.pro = pro;
    }

    public Integer getPh() {
        return ph;
    }

    public void setPh(Integer ph) {
        this.ph = ph;
    }

    public Integer getBlood() {
        return blood;
    }

    public void setBlood(Integer blood) {
        this.blood = blood;
    }

    public Integer getSg() {
        return sg;
    }

    public void setSg(Integer sg) {
        this.sg = sg;
    }

    public Integer getKet() {
        return ket;
    }

    public void setKet(Integer ket) {
        this.ket = ket;
    }

    public Integer getBil() {
        return bil;
    }

    public void setBil(Integer bil) {
        this.bil = bil;
    }

    public Integer getGlucose() {
        return glucose;
    }

    public void setGlucose(Integer glucose) {
        this.glucose = glucose;
    }

    public Integer getAa() {
        return aa;
    }

    public void setAa(Integer aa) {
        this.aa = aa;
    }

    public String getAverage_colo_test() {
        return average_colo_test;
    }

    public void setAverage_colo_test(String average_colo_test) {
        this.average_colo_test = average_colo_test;
    }

    public String getStrip_photo_uri() {
        return strip_photo_uri;
    }

    public void setStrip_photo_uri(String strip_photo_uri) {
        this.strip_photo_uri = strip_photo_uri;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getModified_on() {
        return modified_on;
    }

    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }
}
