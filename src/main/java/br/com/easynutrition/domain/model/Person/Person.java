package br.com.easynutrition.domain.model.Person;

import br.com.easynutrition.domain.model.Anthropometry.Anthropometry;
import br.com.easynutrition.domain.model.User.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PERSON")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 4180137796036758253L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 11)
    private String phone;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutritionist_id", nullable = false)
    @JsonIgnore
    private Users nutritionist;

    @OneToMany(mappedBy = "person", orphanRemoval = true, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Anthropometry> anthropometries;
}
