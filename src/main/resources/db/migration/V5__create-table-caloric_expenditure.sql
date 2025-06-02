CREATE TABLE CALORIC_EXPENDITURE
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY,
    weight          DECIMAL(4, 1) NOT NULL,
    height          INTEGER       NOT NULL,
    age             INTEGER       NOT NULL,
    sex             VARCHAR(10)   NOT NULL,
    activity_factor DECIMAL(3, 2) NOT NULL,
    formula         VARCHAR(40)   NOT NULL,
    geb             DECIMAL(6, 2),
    get             DECIMAL(6, 2),
    person_id       BIGINT        NOT NULL,
    CONSTRAINT PK_PERSON FOREIGN KEY (person_id) REFERENCES person (id),
    PRIMARY KEY (id)
)