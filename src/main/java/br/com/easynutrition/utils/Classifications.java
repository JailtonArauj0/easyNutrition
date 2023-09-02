package br.com.easynutrition.utils;

import br.com.easynutrition.models.Anthropometry;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Classifications {
    private double weight;
    private double height;
    private char sex;
    private int age;
    private double tricipital;
    private double abdominal;
    private double middleAxillary;
    private double thoracic;
    private double subscapular;
    private double thigh;
    private double suprailliac;

    private double waist;
    private double hip;

    public Classifications(Anthropometry anthropometry) {
        this.weight = anthropometry.getWeight();
        this.height = anthropometry.getHeight();
        this.tricipital = anthropometry.getTricipital();
        this.abdominal = anthropometry.getAbdominal();
        this.middleAxillary = anthropometry.getMiddleAxillary();
        this.thoracic = anthropometry.getThoracic();
        this.subscapular = anthropometry.getSubscapular();
        this.thigh = anthropometry.getThigh();
        this.suprailliac = anthropometry.getSuprailliac();
        this.waist = anthropometry.getWaist();
        this.hip = anthropometry.getHip();
    }

    public double bmi() {
        return (this.weight / this.height) / this.height;
    }

    public String bmiClassification() {
        double bmiValue = (this.weight / this.height) / this.height;

        if (bmiValue < 18.5) {
            return "Abaixo do Peso";
        } else if (bmiValue > 18.5 && bmiValue < 24.9 ) {
            return "Peso Normal";
        } else if (bmiValue > 25 && bmiValue < 29.9) {
            return "Sobrepeso";
        } else if (bmiValue > 30 && bmiValue < 35){
            return "Obesidade Grau I";
        }else if (bmiValue > 35 && bmiValue < 40){
            return "Obesidade Grau II";
        }else if (bmiValue > 40){
            return "Obesidade Grau III";
        }
        return "Não foi possível classificar";
    }

    public double whr() {
        return this.waist / this.hip;
    }

    public String whrClassification() {
        double whrValue = this.waist / this.hip;

        if (sex == 'M' || sex == 'm') {
            if (whrValue < 0.95) {
                return "Baixo Risco";
            } else if (whrValue > 0.95 && whrValue <1.0) {
                return "Médio Risco";
            } else if (whrValue > 1.0) {
                return "Alto Risco";
            }
        } else if (sex == 'F' || sex == 'f') {
            if (whrValue < 0.80) {
                return "Baixo Risco";
            } else if (whrValue > 0.8 && whrValue <= 0.85) {
                return "Médio Risco";
            } else if (whrValue > 0.85) {
                return "Alto Risco";
            }
        }
        return "Não foi possível classificar";
    }

    public double fatPercentagePollock() {
        double skinFoldsSum = tricipital + abdominal + middleAxillary + thoracic + subscapular + thigh + suprailliac;
        if (sex == 'M' || sex == 'm') {
            double bodyDensity = 1.112 - 0.00043499 * (skinFoldsSum) + 0.00000055 * (skinFoldsSum * skinFoldsSum) - 0.00028826 * age;
            return ((4.95 / bodyDensity) - 4.50) * 100;

        } else if (sex == 'F' || sex == 'f') {
            double bodyDensity = 1.097 - 0.00046971 * (skinFoldsSum) + 0.00000056 * (skinFoldsSum * skinFoldsSum) - 0.00012828 * age;
            return ((4.95 / bodyDensity) - 4.50) * 100;
        }
        return 0;
    }
}
