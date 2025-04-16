package br.com.easynutrition.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class BodyCircunferences {

    @Column(nullable = true)
    private double shoulder;

    @Column(nullable = true)
    private double chest;

    @Column(nullable = true)
    private double waist;

    @Column(nullable = true)
    private double abdomen;

    @Column(nullable = true)
    private double hip;

    @Column(nullable = true)
    private double arm;

    @Column(nullable = true)
    private double forearm;

    @Column(nullable = true)
    private double medialThigh;

    @Column(nullable = true)
    private double calf;
}
