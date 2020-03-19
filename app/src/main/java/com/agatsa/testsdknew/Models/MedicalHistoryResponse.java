package com.agatsa.testsdknew.Models;

public class MedicalHistoryResponse {
    Integer patient_id;
    String any_allergy;
    String any_disease_before;
    String other_illness;
    String current_medication;
    String do_excercise;
    String do_smoke;
    String do_alcohol;
    String date;
    String location_of_registration;

    public MedicalHistoryResponse(Integer patient_id, String any_allergy, String any_disease_before, String other_illness, String current_medication, String do_excercise, String do_smoke, String do_alcohol, String date, String location_of_registration) {
        this.patient_id = patient_id;
        this.any_allergy = any_allergy;
        this.any_disease_before = any_disease_before;
        this.other_illness = other_illness;
        this.current_medication = current_medication;
        this.do_excercise = do_excercise;
        this.do_smoke = do_smoke;
        this.do_alcohol = do_alcohol;
        this.date = date;
        this.location_of_registration = location_of_registration;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public String getAny_allergy() {
        return any_allergy;
    }

    public void setAny_allergy(String any_allergy) {
        this.any_allergy = any_allergy;
    }

    public String getAny_disease_before() {
        return any_disease_before;
    }

    public void setAny_disease_before(String any_disease_before) {
        this.any_disease_before = any_disease_before;
    }

    public String getOther_illness() {
        return other_illness;
    }

    public void setOther_illness(String other_illness) {
        this.other_illness = other_illness;
    }

    public String getCurrent_medication() {
        return current_medication;
    }

    public void setCurrent_medication(String current_medication) {
        this.current_medication = current_medication;
    }

    public String getDo_excercise() {
        return do_excercise;
    }

    public void setDo_excercise(String do_excercise) {
        this.do_excercise = do_excercise;
    }

    public String getDo_smoke() {
        return do_smoke;
    }

    public void setDo_smoke(String do_smoke) {
        this.do_smoke = do_smoke;
    }

    public String getDo_alcohol() {
        return do_alcohol;
    }

    public void setDo_alcohol(String do_alcohol) {
        this.do_alcohol = do_alcohol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation_of_registration() {
        return location_of_registration;
    }

    public void setLocation_of_registration(String location_of_registration) {
        this.location_of_registration = location_of_registration;
    }
}
