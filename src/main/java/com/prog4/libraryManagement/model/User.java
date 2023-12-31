package com.prog4.libraryManagement.model;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.prog4.libraryManagement.repository.types.PostgresEnumType;


@Entity
@Table(name = "\"user\"")
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Reference is mandatory")
    private String ref;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank(message = "Phone number is mandatory")
    private String phone;

    private LocalDate birthDate;
    private Instant entranceDatetime;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @Column(name = "\"role\"")
    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum Sex {
        M, F
    }

    public enum Status {
        ENABLED, DISABLED
    }

    public enum Role {
        ADMINISTRATOR, VISITOR
    }
}
