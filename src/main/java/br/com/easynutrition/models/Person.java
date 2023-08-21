package br.com.easynutrition.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_PERSON")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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

    private LocalDate birthDate;

    @OneToMany
    private List<Anthropometry> anthropometry;

}
