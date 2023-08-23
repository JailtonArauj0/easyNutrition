package br.com.easynutrition.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AnthropometryDTO {
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate evaluationDate;

    private double weight;
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
}
