package br.com.easynutrition.utils;

import br.com.easynutrition.api.exception.CustomException;
import br.com.easynutrition.domain.enums.Formula;
import br.com.easynutrition.domain.model.Anthropometry.Anthropometry;
import br.com.easynutrition.domain.model.CaloricExpenditure.CaloricExpenditure;
import br.com.easynutrition.domain.model.Person.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Equations {
    private Double weight;
    private Double height;
    private Integer age;
    private String sex;
    private Double activityFactor;
    private Double geb;
    private Double get;
    private Person person;

    public Equations(Anthropometry anthropometry) {
        this.weight = anthropometry.getWeight();
        this.height = (double) anthropometry.getHeight() / 100;
        this.age = anthropometry.getPerson().getAge();
        this.sex = anthropometry.getSex().name();
        this.person = anthropometry.getPerson();
    }

    public CaloricExpenditure eerIom(String sex) {
        if (sex.equalsIgnoreCase("Masculino")) {
            this.get = 662 - (9.53 * age) + activityFactor * ((15.91 * weight) + (539.6 * height));

        } else if (sex.equalsIgnoreCase("Feminino")) {
            this.get = 354 - (6.91 * age) + activityFactor * ((9.36 * weight) + (726 * height));

        } else {
            throw new CustomException("Sexo deve ser masculino ou feminino");
        }
        this.geb = 0.0;

        CaloricExpenditure caloricExpenditure = new CaloricExpenditure();
        caloricExpenditure.equationToEntity(this);
        caloricExpenditure.setFormula(Formula.EER_IOM);
        return caloricExpenditure;
        /*
        MASCULINO
        Sedentário	1.00
        Pouco ativo	1.11
        Ativo	1.25
        Muito ativo	1.48

        FEMININO
        Sedentário	1.00
        Pouco ativa	1.12
        Ativa	1.27
        Muito ativa	1.45
        */
    }

    public CaloricExpenditure harrisBenedict(String sex) {
        double heightCm = this.height * 100;
        if (sex.equalsIgnoreCase("Masculino")) {
            this.geb = 88.362 + (13.397 * weight) + (4.799 * heightCm) - (5.677 * age);

        } else {
            this.geb = 447.593 + (9.247 * weight) + (3.098 * heightCm) - (4.330 * age);

        }
        this.get = geb * activityFactor;

        CaloricExpenditure caloricExpenditure = new CaloricExpenditure();
        caloricExpenditure.equationToEntity(this);
        caloricExpenditure.setFormula(Formula.HARRIS_BENEDICT);
        return caloricExpenditure;

        /*
        Sedentário: 1.2
        Levemente ativo: 1.375
        Moderadamente ativo: 1.55
        Muito ativo: 1.725
        Extremamente ativo: 1.9
        */
    }

    public CaloricExpenditure mifflin(String sex) {
        double heightCm = this.height * 100;
        if (sex.equalsIgnoreCase("Masculino")) {
            this.geb = (10 * weight) + (6.25 * heightCm) - (5.0 * age) + 5;

        } else {
            this.geb = (10 * weight) + (6.25 * heightCm) - (5.0 * age) - 161;

        }
        this.get = geb * activityFactor;

        CaloricExpenditure caloricExpenditure = new CaloricExpenditure();
        caloricExpenditure.equationToEntity(this);
        caloricExpenditure.setFormula(Formula.MIFFLIN_ST_JEOR);
        return caloricExpenditure;
        /*
        Sedentário: 1.2
        Levemente ativo: 1.375
        Moderadamente ativo: 1.55
        Muito ativo: 1.725
        Extremamente ativo: 1.9
        */
    }
}
