package br.com.easynutrition.utils;

import br.com.easynutrition.models.Anthropometry.Anthropometry;
import br.com.easynutrition.models.Anthropometry.BodyCircunferences;
import br.com.easynutrition.models.Person;
import br.com.easynutrition.models.Skinfolds;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Classifications {
    private final Double weight;
    private final Double height;
    private final Character sex;
    private final Integer age;
    private final Double tricipital;
    private final Double abdominal;
    private final Double middleAxillary;
    private final Double thoracic;
    private final Double subscapular;
    private final Double thigh;
    private final Double suprailliac;
    private final Double waist;
    private final Double hip;

    public Classifications(Anthropometry anthropometry) {
        this.weight = anthropometry.getWeight();
        this.height = (double) anthropometry.getHeight() / 100;
        this.sex = anthropometry.getSex().getCode();
        this.age = anthropometry.getPerson().getAge();

        Skinfolds skinfolds = anthropometry.getSkinfolds();
        this.tricipital = skinfolds.getTricipital();
        this.abdominal = skinfolds.getAbdominal();
        this.middleAxillary = skinfolds.getMiddleAxillary();
        this.thoracic = skinfolds.getThoracic();
        this.subscapular = skinfolds.getSubscapular();
        this.thigh = skinfolds.getThigh();
        this.suprailliac = skinfolds.getSuprailliac();

        BodyCircunferences circunferences = anthropometry.getBodyCircunferences();
        this.waist = circunferences.getWaist();
        this.hip = circunferences.getHip();
    }

    public double bmi() {
        double bmi = (this.weight / this.height) / this.height;
        return Math.round(bmi * 100.0) / 100.0;
    }

    public String bmiClassification() {
        double bmiValue = (this.weight / this.height) / this.height;
        if(age < 60) {
            if (bmiValue < 18.5) {
                return "Baixo Peso";
            } else if (bmiValue >= 18.5 && bmiValue < 25) {
                return "Eutrófico";
            } else if (bmiValue >= 25 && bmiValue < 30) {
                return "Sobrepeso";
            } else if (bmiValue >= 30 && bmiValue < 35) {
                return "Obesidade Grau I";
            } else if (bmiValue >= 35 && bmiValue < 40) {
                return "Obesidade Grau II";
            } else if (bmiValue >= 40) {
                return "Obesidade Grau III";
            }
            return "Não foi possível classificar.";

        } else {
            if (bmiValue < 22) {
                return "Baixo Peso";
            } else if (bmiValue >= 22 && bmiValue < 27) {
                return "Eutrófico";
            } else if (bmiValue >= 27) {
                return "Sobrepeso";
            }
            return "Não foi possível classificar.";
        }
    }

    public double whr() {
        double whr = this.waist / this.hip;
        return Math.round(whr * 100.0) / 100.0;
    }

    public String whrClassification() {
        double whrValue = this.waist / this.hip;

        if (sex == 'M' || sex == 'm') {
            if (whrValue < 0.95) {
                return "Baixo Risco";
            } else if (whrValue > 0.95 && whrValue < 1.0) {
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
            double percentage = ((4.95 / bodyDensity) - 4.50) * 100;
            return Math.round(percentage * 100.0) / 100.0;

        } else if (sex == 'F' || sex == 'f') {
            double bodyDensity = 1.097 - 0.00046971 * (skinFoldsSum) + 0.00000056 * (skinFoldsSum * skinFoldsSum) - 0.00012828 * age;
            double percentage = ((4.95 / bodyDensity) - 4.50) * 100;
            return Math.round(percentage * 100.0) / 100.0;
        }
        return 0;
    }

    public String bfClassification(double percentage) {
        if (this.sex == 'M' || this.sex == 'm') {
            if (age >= 20 && age <= 29) {
                if (percentage < 11) {
                    return "Excelente (atlético)";
                } else if (percentage >= 11 && percentage < 13) {
                    return "Bom";
                } else if (percentage >= 14 && percentage < 20) {
                    return "Dentro da média";
                } else if (percentage >= 21 && percentage < 23) {
                    return "Regular";
                } else {
                    return "Alto percentual de Gordura";
                }
            } else if (age >= 30 && age <= 39) {
                if (percentage < 12) {
                    return "Excelente (atlético)";
                } else if (percentage >= 12 && percentage < 14) {
                    return "Bom";
                } else if (percentage >= 15 && percentage < 21) {
                    return "Dentro da média";
                } else if (percentage >= 22 && percentage < 24) {
                    return "Regular";
                } else {
                    return "Alto percentual de Gordura";
                }
            } else if (age >= 40 && age <= 49) {
                if (percentage < 14) {
                    return "Excelente (atlético)";
                } else if (percentage >= 14 && percentage < 16) {
                    return "Bom";
                } else if (percentage >= 17 && percentage < 23) {
                    return "Dentro da média";
                } else if (percentage >= 24 && percentage < 26) {
                    return "Regular";
                } else {
                    return "Alto percentual de Gordura";
                }
            } else if (age >= 50 && age <= 59) {
                if (percentage < 15) {
                    return "Excelente (atlético)";
                } else if (percentage >= 15 && percentage < 17) {
                    return "Bom";
                } else if (percentage >= 18 && percentage < 24) {
                    return "Dentro da média";
                } else if (percentage >= 25 && percentage < 27) {
                    return "Regular";
                } else {
                    return "Alto percentual de Gordura";
                }
            } else if (age >= 60) {
                if (percentage < 16) {
                    return "Excelente (atlético)";
                } else if (percentage >= 16 && percentage < 18) {
                    return "Bom";
                } else if (percentage >= 19 && percentage < 25) {
                    return "Dentro da média";
                } else if (percentage >= 26 && percentage < 28) {
                    return "Regular";
                } else {
                    return "Alto percentual de Gordura";
                }
            }

        } else if (this.sex == 'F' || this.sex == 'f') {
            if (age >= 20 && age <= 29) {
                if (percentage < 16) {
                    return "Excelente (atlético)";
                } else if (percentage >= 16 && percentage < 19) {
                    return "Bom";
                } else if (percentage >= 20 && percentage < 28) {
                    return "Dentro da média";
                } else if (percentage >= 29 && percentage < 31) {
                    return "Regular";
                } else {
                    return "Alto percentual de Gordura";
                }
            } else if (age >= 30 && age <= 39) {
                if (percentage < 17) {
                    return "Excelente (atlético)";
                } else if (percentage >= 17 && percentage < 20) {
                    return "Bom";
                } else if (percentage >= 21 && percentage < 29) {
                    return "Dentro da média";
                } else if (percentage >= 30 && percentage < 32) {
                    return "Regular";
                } else {
                    return "Alto percentual de Gordura";
                }
            } else if (age >= 40 && age <= 49) {
                if (percentage < 18) {
                    return "Excelente (atlético)";
                } else if (percentage >= 18 && percentage < 21) {
                    return "Bom";
                } else if (percentage >= 22 && percentage < 30) {
                    return "Dentro da média";
                } else if (percentage >= 31 && percentage < 33) {
                    return "Regular";
                } else {
                    return "Alto percentual de Gordura";
                }
            } else if (age >= 50 && age <= 59) {
                if (percentage < 19) {
                    return "Excelente (atlético)";
                } else if (percentage >= 19 && percentage < 22) {
                    return "Bom";
                } else if (percentage >= 23 && percentage < 31) {
                    return "Dentro da média";
                } else if (percentage >= 32 && percentage < 34) {
                    return "Regular";
                } else {
                    return "Alto percentual de Gordura";
                }
            } else if (age >= 60) {
                if (percentage < 20) {
                    return "Excelente (atlético)";
                } else if (percentage >= 20 && percentage < 23) {
                    return "Bom";
                } else if (percentage >= 24 && percentage < 32) {
                    return "Dentro da média";
                } else if (percentage >= 33 && percentage < 35) {
                    return "Regular";
                } else {
                    return "Alto percentual de Gordura";
                }
            }
        }
        return "Classificação desconhecida";
    }
}
