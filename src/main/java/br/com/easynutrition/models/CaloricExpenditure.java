package br.com.easynutrition.models;

import br.com.easynutrition.enums.Formula;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "TB_CALORIC_EXPENDITURE")
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
}
