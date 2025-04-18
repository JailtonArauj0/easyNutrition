package br.com.easynutrition.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Skinfolds {
    private Double tricipital;
    private Double abdominal;
    private Double middleAxillary;
    private Double thoracic;
    private Double subscapular;
    private Double thigh;
    private Double suprailliac;
}
