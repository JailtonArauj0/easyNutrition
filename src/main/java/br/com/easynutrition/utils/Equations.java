package br.com.easynutrition.utils;

import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;

@Data
public class Equations {

    private Double weight;
    private Double height;
    private Integer age;
    private Character sex;
    private Double activityFactor;
    private Double geb;
    private Double get;


    public void eerIom(char sex) {

        if (sex == 'M') {
            if (this.activityFactor == 1.2) {
                this.get = 753.07 - (10.83 * age) + (6.50 * height) + (14.10 * weight);

            } else if (this.activityFactor == 1.375) {
                this.get = 581.47 - (10.83 * age) + (8.30 * height) + (14.94 * weight);

            } else if (this.activityFactor == 1.55) {
                this.get = 1004.82 - (10.83 * age) + (6.52 * height) + (15.91 * weight);

            } else if (this.activityFactor == 1.725) {
                this.get = -517.88 - (10.83 * age) + (15.61 * height) + (19.11 * weight);

            }
        } else {
            if (this.activityFactor == 1.2) {
                this.get = 584.90 - (7.01 * age) + (5.72 * height) + (11.71 * weight);

            } else if (this.activityFactor == 1.375) {
                this.get = 575.77 - (7.01 * age) + (6.60 * height) + (12.14 * weight);

            } else if (this.activityFactor == 1.55) {
                this.get = 710.25 - (7.01 * age) + (6.54 * height) + (12.34 * weight);

            } else if (this.activityFactor == 1.725) {
                this.get = 511.83 - (7.01 * age) + (9.07 * height) + (12.56 * weight);

            }
        }
    }

    public void harrisBenedict(char sex) {

        if (sex == 'M') {
            this.geb = 66.5 + (13.75 * weight) + (5.003 * height) - (6.75 * age);

        } else {
            this.geb = 655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age);

        }
        this.get = geb * activityFactor;
        /*
        Sedentary: 1.2
        Lightly active: 1.375
        Moderately active ~ Active: 1.55
        Very active: 1.725
        */
    }

    public void mifflin(char sex) {

        if (sex == 'M') {
            this.geb = (10 * weight) + (6.25 * height) - (5.0 * age) + 5;

        } else {
            this.geb = (10 * weight) + (6.25 * height) - (5.0 * age) - 161;

        }
        this.get = geb * activityFactor;
    }
}
