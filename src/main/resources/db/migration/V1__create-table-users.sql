CREATE TABLE USERS
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY,
    email             VARCHAR(255) NOT NULL UNIQUE,
    password          VARCHAR(255) NOT NULL,
    full_name         VARCHAR(255) NOT NULL,
    cpf               VARCHAR(11)  NOT NULL UNIQUE,
    phone             VARCHAR(11) DEFAULT NULL,
    users_role        VARCHAR(50)  NOT NULL,
    registration_date TIMESTAMP,
    PRIMARY KEY (id)
)