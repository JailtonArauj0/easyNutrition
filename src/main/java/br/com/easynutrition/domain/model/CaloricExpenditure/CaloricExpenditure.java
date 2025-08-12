package br.com.easynutrition.domain.model.CaloricExpenditure;

import br.com.easynutrition.api.dto.response.CaloricExpenditure.CaloricExpenditureDTO;
import br.com.easynutrition.domain.enums.Formula;
import br.com.easynutrition.domain.model.Person.Person;
import br.com.easynutrition.utils.Equations;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "CALORIC_EXPENDITURE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CaloricExpenditure implements Serializable {

    @Serial
    private static final long serialVersionUID = 5340798625542584951L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private Double activityFactor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Formula formula;

    @Column(nullable = false)
    private Double geb;

    @Column(nullable = false)
    private Double get;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public CaloricExpenditure equationToEntity(Equations equations) {
        this.weight = equations.getWeight();
        this.height = (int) (equations.getHeight() * 100);
        this.age = equations.getAge();
        this.sex = equations.getSex();
        this.activityFactor = equations.getActivityFactor();
        this.geb = Math.floor(equations.getGeb() * 100) / 100.0;
        this.get = Math.floor(equations.getGet() * 100) / 100.0;
        this.person = equations.getPerson();
        return this;
    }

    public CaloricExpenditureDTO toDTO() {
        return new CaloricExpenditureDTO(this.id, this.formula.name(), this.activityFactor, this.geb, this.get);
    }
}
