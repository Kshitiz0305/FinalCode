package com.agatsa.testsdknew.Models;

/**
 * Created by Kshitiz on 2/19/19.
 */
public class CalculatedDataModelClass {
    private final Double euclidian_disance;
    private final String tag_value;
    private final String tag_name;

    public CalculatedDataModelClass(Double euclidian_disance, String tag_value, String tag_name) {
        this.euclidian_disance = euclidian_disance;
        this.tag_value = tag_value;
        this.tag_name = tag_name;
    }

    public Double getEuclidian_disance() {
        return euclidian_disance;
    }

    public String getTag_value() {
        return tag_value;
    }

    public String getTag_name() {
        return tag_name;
    }

}