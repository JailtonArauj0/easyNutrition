package br.com.easynutrition.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "TB_CALORIC_EXPENDITURE")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CaloricExpenditure implements Serializable {

    @Serial
    private static final long serialVersionUID = 5340798625542584951L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double weight;
    private double height;
    private int age;
    private char sex;
    private double activityFactor;

    @Enumerated(EnumType.STRING)
    private Formula formula;

    private double geb;
    private double get;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
