package br.com.easynutrition.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate evaluationDate;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double height;
    @Column(nullable = false)
    private char sex;

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
    @JoinColumn(name = "person_id")
    private Person person;

}
