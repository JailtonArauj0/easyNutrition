package br.com.easynutrition.models;

import jakarta.persistence.*;
import lombok.*;

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

    private LocalDate evaluationDate;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double height;

    private double tricipital;
    private double abdominal;
    private double middleAxillary;
    private double thoracic;
    private double subscapular;
    private double thigh; //COXA
    private double suprailliac;

    //CIRCUNFERENCES
    private double shoulder;
    private double chest;
    private double waist;
    private double abdomen;
    private double hip;
    private double arm;
    private double forearm;
    private double medialThigh;
    private double calf;

    @ManyToOne
    private Person person;

}
