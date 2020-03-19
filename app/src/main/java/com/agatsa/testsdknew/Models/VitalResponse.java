package com.agatsa.testsdknew.Models;

public class VitalResponse {
    String patient_id;
    String weight;
    String height;
    String tmpt;
    String pulse;
    String sto_2;
    String bp_s;
    String bp_d;
    String created_on;
    String modified_on;

    public VitalResponse(String patient_id, String weight, String height, String tmpt, String pulse, String sto_2, String bp_s, String bp_d, String created_on, String modified_on) {
        this.patient_id = patient_id;
        this.weight = weight;
        this.height = height;
        this.tmpt = tmpt;
        this.pulse = pulse;
        this.sto_2 = sto_2;
        this.bp_s = bp_s;
        this.bp_d = bp_d;
        this.created_on = created_on;
        this.modified_on = modified_on;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTmpt() {
        return tmpt;
    }

    public void setTmpt(String tmpt) {
        this.tmpt = tmpt;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getSto_2() {
        return sto_2;
    }

    public void setSto_2(String sto_2) {
        this.sto_2 = sto_2;
    }

    public String getBp_s() {
        return bp_s;
    }

    public void setBp_s(String bp_s) {
        this.bp_s = bp_s;
    }

    public String getBp_d() {
        return bp_d;
    }

    public void setBp_d(String bp_d) {
        this.bp_d = bp_d;
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
