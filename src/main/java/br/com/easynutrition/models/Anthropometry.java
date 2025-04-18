package br.com.easynutrition.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ANTHROPOMETRY")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Anthropometry implements Serializable {

    @Serial
    private static final long serialVersionUID = 107452316370886152L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime evaluationDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;
}
