package br.com.easynutrition.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;


@Embeddable
@Data
public class Skinfolds {

    @Column(nullable = true)
    private double tricipital;

    @Column(nullable = true)
    private double abdominal;

    @Column(nullable = true)
    private double middleAxillary;

    @Column(nullable = true)
    private double thoracic;

    @Column(nullable = true)
    private double subscapular;

    @Column(nullable = true)
    private double thigh;

    @Column(nullable = true)
    private double suprailliac;
}
