package br.com.easynutrition.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_PERSON")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 4180137796036758253L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    private String phone;

    @Column(nullable = false, unique = true)
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @OneToMany
    private List<Anthropometry> anthropometry;

}
