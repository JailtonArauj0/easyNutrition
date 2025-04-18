CREATE TABLE ANTHROPOMETRY
(
    id                            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    -- Informações básicas
    weight                        DECIMAL(4, 1) NOT NULL,
    height                        INTEGER       NOT NULL,
    sex                           CHAR(1)       NOT NULL,
    evaluation_date               TIMESTAMP     NOT NULL,

    -- Dobras cutâneas (Skinfolds)
    skinfold_tricipital           DECIMAL(4, 1),
    skinfold_abdominal            DECIMAL(4, 1),
    skinfold_middle_axillary      DECIMAL(4, 1),
    skinfold_thoracic             DECIMAL(4, 1),
    skinfold_subscapular          DECIMAL(4, 1),
    skinfold_thigh                DECIMAL(4, 1),
    skinfold_suprailliac          DECIMAL(4, 1),

    -- Circunferências (BodyCircunferences)
    circ_shoulder                 DECIMAL(4, 1),
    circ_chest                    DECIMAL(4, 1),
    circ_waist                    DECIMAL(4, 1),
    circ_abdomen                  DECIMAL(4, 1),
    circ_hip                      DECIMAL(4, 1),
    circ_arm                      DECIMAL(4, 1),
    circ_forearm                  DECIMAL(4, 1),
    circ_medial_thigh             DECIMAL(4, 1),
    circ_calf                     DECIMAL(4, 1),

    -- Avaliação nutricional (NutritionalAssessment)
    assessment_bmi                DOUBLE PRECISION,
    assessment_bmi_classification VARCHAR(255),
    assessment_whr                DOUBLE PRECISION,
    assessment_whr_classification VARCHAR(255),
    assessment_body_fat           DOUBLE PRECISION,
    assessment_bf_classification  VARCHAR(255),

    -- Relacionamento com Person
    person_id                     BIGINT,

    -- Constraints
    CONSTRAINT fk_anthropometry_person FOREIGN KEY (person_id) REFERENCES PERSON (id)
);