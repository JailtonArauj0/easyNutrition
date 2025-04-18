package br.com.easynutrition.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class BodyCircunferences {
    private Double shoulder;
    private Double chest;
    private Double waist;
    private Double abdomen;
    private Double hip;
    private Double arm;
    private Double forearm;
    private Double medialThigh;
    private Double calf;
}
