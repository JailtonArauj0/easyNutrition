CREATE TABLE PERSON
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) DEFAULT NULL,
    age             INT          NOT NULL,
    phone           VARCHAR(11)  DEFAULT NULL,
    cpf             VARCHAR(11)  NOT NULL UNIQUE,
    birth_date      DATE         NOT NULL,
    nutritionist_id BIGINT       NOT NULL,
    CONSTRAINT fk_nutritionist FOREIGN KEY (nutritionist_id) REFERENCES USERS(id),
    PRIMARY KEY (id)
)