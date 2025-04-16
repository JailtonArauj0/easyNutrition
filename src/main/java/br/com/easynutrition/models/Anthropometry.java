package br.com.easynutrition.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TB_ANTHROPOMETRY")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Anthropometry implements Serializable {

    @Serial
    private static final long serialVersionUID = 107452316370886152L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate evaluationDate;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private char sex;

    @Embedded
    private Skinfolds skinfolds;

    @Embedded
    private BodyCircunferences bodyCircunferences;

    @Embedded
    private NutritionalAssessment nutritionalAssessment;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

}
