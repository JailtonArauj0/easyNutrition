package br.com.easynutrition.domain.model.Anthropometry;

import br.com.easynutrition.api.dto.response.Anthropometry.AnthropometryDTO;
import br.com.easynutrition.domain.enums.Gender;
import br.com.easynutrition.domain.model.Person.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

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
    private Long id;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender sex;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "tricipital", column = @Column(name = "skinfold_tricipital")),
            @AttributeOverride(name = "abdominal", column = @Column(name = "skinfold_abdominal")),
            @AttributeOverride(name = "middleAxillary", column = @Column(name = "skinfold_middle_axillary")),
            @AttributeOverride(name = "thoracic", column = @Column(name = "skinfold_thoracic")),
            @AttributeOverride(name = "subscapular", column = @Column(name = "skinfold_subscapular")),
            @AttributeOverride(name = "thigh", column = @Column(name = "skinfold_thigh")),
            @AttributeOverride(name = "suprailliac", column = @Column(name = "skinfold_suprailliac"))
    })
    private Skinfolds skinfolds;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "shoulder", column = @Column(name = "circ_shoulder")),
            @AttributeOverride(name = "chest", column = @Column(name = "circ_chest")),
            @AttributeOverride(name = "waist", column = @Column(name = "circ_waist")),
            @AttributeOverride(name = "abdomen", column = @Column(name = "circ_abdomen")),
            @AttributeOverride(name = "hip", column = @Column(name = "circ_hip")),
            @AttributeOverride(name = "arm", column = @Column(name = "circ_arm")),
            @AttributeOverride(name = "forearm", column = @Column(name = "circ_forearm")),
            @AttributeOverride(name = "medialThigh", column = @Column(name = "circ_medial_thigh")),
            @AttributeOverride(name = "calf", column = @Column(name = "circ_calf"))
    })
    private BodyCircunferences bodyCircunferences;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "bmi", column = @Column(name = "assessment_bmi")),
            @AttributeOverride(name = "bmiClassification", column = @Column(name = "assessment_bmi_classification")),
            @AttributeOverride(name = "whr", column = @Column(name = "assessment_whr")),
            @AttributeOverride(name = "whrClassification", column = @Column(name = "assessment_whr_classification")),
            @AttributeOverride(name = "bodyFat", column = @Column(name = "assessment_body_fat")),
            @AttributeOverride(name = "bfClassification", column = @Column(name = "assessment_bf_classification"))
    })
    private NutritionalAssessment nutritionalAssessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private Person person;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate evaluationDate;

    public Anthropometry(Double weight, Integer height, Gender sex, Skinfolds skinfolds, BodyCircunferences bodyCircunferences, NutritionalAssessment nutritionalAssessment, Person person, LocalDate evaluationDate) {
        this.weight = weight;
        this.height = height;
        this.sex = sex;
        this.skinfolds = skinfolds;
        this.bodyCircunferences = bodyCircunferences;
        this.nutritionalAssessment = nutritionalAssessment;
        this.person = person;
        this.evaluationDate = evaluationDate;
    }

    public AnthropometryDTO toDTO() {
        return new AnthropometryDTO(this);
    }
}


