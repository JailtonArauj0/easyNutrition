package br.com.easynutrition.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "TB_TEE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Tee implements Serializable {

    @Serial
    private static final long serialVersionUID = 5340798625542584951L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double weight;

    private int height;

    private double activityFactor;

    @Enumerated(EnumType.STRING)
    private Formula formula;

    @OneToOne
    private Person person;
}
